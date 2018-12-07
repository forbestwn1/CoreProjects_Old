package com.nosliw.data.core.script.context;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.core.expression.HAPDefinitionExpression;
import com.nosliw.data.core.expression.HAPExpressionProcessConfigureUtil;
import com.nosliw.data.core.operand.HAPOperandUtility;
import com.nosliw.data.core.runtime.js.rhino.task.HAPRuntimeTaskExecuteScriptExpression;
import com.nosliw.data.core.script.expressionscript.HAPContextScriptExpressionProcess;
import com.nosliw.data.core.script.expressionscript.HAPScriptExpression;
import com.nosliw.data.core.script.expressionscript.HAPScriptExpressionScriptSegment;
import com.nosliw.data.core.script.expressionscript.HAPScriptExpressionUtility;

public class HAPProcessorContextConstant {

	static public HAPContextGroup process(
			HAPContextGroup originalContextGroup,
			HAPContextGroup parentContextGroup,
			String inheritMode,
			HAPEnvContextProcessor contextProcessorEnv){

		HAPContextGroup merged = merge(originalContextGroup, parentContextGroup, inheritMode);
		
		return solidateConstantDefs(merged, contextProcessorEnv);
	}

	//merge constant with parent
	private static HAPContextGroup merge(
			HAPContextGroup contextGroup,
			HAPContextGroup parentContextGroup,
			String inheritMode){
		HAPContextGroup out = contextGroup.clone();
		if(!HAPConfigureContextProcessor.VALUE_INHERITMODE_NONE.equals(inheritMode)) {
			if(parentContextGroup!=null) {
				//merge constants with parent
				for(String contextCategary : HAPContextGroup.getInheritableContextTypes()) {
					for(String name : parentContextGroup.getContext(contextCategary).getElementNames()) {
						if(parentContextGroup.getElement(contextCategary, name).isConstant()) {
							if(contextGroup.getElement(contextCategary, name)==null) {
								out.addElement(name, parentContextGroup.getElement(contextCategary, name).cloneContextDefinitionRoot(), contextCategary);
							}
						}
					}
				}
			}
		}
		return out;
	}

	/**
	 * process all constants defs to get data of constant
	 * it will keep the json structure and only calculate the leaf value 
	 * 		constantDefs : all available constants
	 */
	static private HAPContextGroup solidateConstantDefs(
			HAPContextGroup originalContextGroup,
			HAPEnvContextProcessor contextProcessorEnv){
		HAPContextGroup out = new HAPContextGroup(originalContextGroup.getInfo());
		for(String categary : HAPContextGroup.getAllContextTypes()) {
			Map<String, HAPContextDefinitionRoot> cotextDefRoots = originalContextGroup.getElements(categary);
			for(String name : cotextDefRoots.keySet()) {
				HAPContextDefinitionRoot contextDefRoot = cotextDefRoots.get(name);
				if(contextDefRoot.isConstant()) {
					solidateConstantDefRoot(new HAPContextDefinitionRootId(categary, name), originalContextGroup, out, contextProcessorEnv);
				}
				else {
					//other type, just do clone
					out.addElement(name, contextDefRoot.cloneContextDefinitionRoot(), categary);
				}
			}
		}
		return out;
	}

	static private HAPContextDefinitionRoot solidateConstantDefRoot(
			HAPContextDefinitionRootId contextDefRootId, 
			HAPContextGroup originalContextGroup,
			HAPContextGroup targetContextGroup,
			HAPEnvContextProcessor contextProcessorEnv) {

		HAPContextDefinitionRoot out = (HAPContextDefinitionRoot)targetContextGroup.getElement(contextDefRootId);
		if(out==null) {
			//calculate the value 
			HAPContextDefinitionRoot orgNode = (HAPContextDefinitionRoot)originalContextGroup.getElement(contextDefRootId);
			out = orgNode.cloneContextDefinitionRoot();
			HAPContextDefinitionLeafConstant contextDefEle = (HAPContextDefinitionLeafConstant)orgNode.getDefinition();
			Object data = processConstantDefJsonNode(contextDefEle.getValue(), originalContextGroup, targetContextGroup, contextProcessorEnv);
			if(data==null)   data = contextDefEle.getValue();
			contextDefEle.setValue(data);
			targetContextGroup.addElement(contextDefRootId.getName(), out, contextDefRootId.getCategary());
		}
		return out;
	}
	
	/**
	 * Process any json node
	 * @param nodeValue  
	 * @param refConstants   reference constants
	 * @param constantDefs
	 * @param idGenerator
	 * @param expressionMan
	 * @param runtime
	 * @return
	 */
	static private Object processConstantDefJsonNode(
			Object nodeValue,
			HAPContextGroup originalContextGroup,
			HAPContextGroup targetContextGroup,
			HAPEnvContextProcessor contextProcessorEnv) {
		Object out = null;
		try{
			if(nodeValue instanceof JSONObject){
				JSONObject outJsonObj = new JSONObject();
				JSONObject jsonObj = (JSONObject)nodeValue;
				Iterator<String> keys = jsonObj.keys();
				while(keys.hasNext()){
					String key = keys.next();
					Object childValue = jsonObj.get(key);
					Object data = processConstantDefJsonNode(childValue, originalContextGroup, targetContextGroup, contextProcessorEnv);
					if(data!=null)  outJsonObj.put(key, data);   
				}
				out = outJsonObj;
			}
			else if(nodeValue instanceof JSONArray){
				JSONArray outJsonArray = new JSONArray();
				JSONArray jsonArray = (JSONArray)nodeValue;
				for(int i=0; i<jsonArray.length(); i++){
					Object childNode = jsonArray.get(i);
					Object data = processConstantDefJsonNode(childNode, originalContextGroup, targetContextGroup, contextProcessorEnv);
					if(data!=null)   outJsonArray.put(i, data);
				}
				out = outJsonArray;
			}
			else{
				out = processConstantDefLeaf(nodeValue, originalContextGroup, targetContextGroup, contextProcessorEnv);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * Calculate leaf data
	 * as script expression can only defined in leaf node 
	 * @param leafData
	 * @param constantDefs
	 * @param idGenerator
	 * @param expressionMan
	 * @param runtime
	 * @return
	 * 		if leafData does not contain script expression, then return leafData (respect the value type)
	 * 		if leafData contains both script expression and plain text, then return string value
	 * 		if leafData contains a script expression only, then return the result value of sript expression
	 */
	static private Object processConstantDefLeaf(
			Object leafData,
			HAPContextGroup originalContextGroup,
			HAPContextGroup targetContextGroup,
			HAPEnvContextProcessor contextProcessorEnv) {

		//if leafData is a script expression, then use valueObj to respect the type of return value
		Object valueObj = null;
		//otherwise, if leafData contains both script expression and plain text, then build string value instead
		StringBuffer valueStr = new StringBuffer();
		//try to discover script expression in leaf content 
		List<Object> segments = HAPScriptExpressionUtility.discoverEmbededScriptExpression(leafData.toString());
		for(Object segment : segments){
			if(segment instanceof HAPScriptExpression){
				//only process script expression
				HAPScriptExpression sciptExpression = (HAPScriptExpression)segment;

				//find all required constants names in script expressions
				Set<String> expConstantNames = new HashSet<String>();
				Set<String> scriptConstantNames = new HashSet<String>();
				for(Object uiExpEle : sciptExpression.getElements()){
					if(uiExpEle instanceof HAPDefinitionExpression)		expConstantNames.addAll(HAPOperandUtility.discoveryUnsolvedConstants(((HAPDefinitionExpression)uiExpEle).getOperand()));
					else if(uiExpEle instanceof HAPScriptExpressionScriptSegment)		scriptConstantNames.addAll(((HAPScriptExpressionScriptSegment)uiExpEle).getConstantNames());
				}
				
				//build constants data required by expression
				HAPContextScriptExpressionProcess expProcessContext = new HAPContextScriptExpressionProcess();
				for(String constantName : expConstantNames){
					HAPContextDefinitionRootId refNodeId = solveReferencedNodeId(new HAPContextDefinitionRootId(constantName), originalContextGroup);
					HAPContextDefinitionRoot contextDefRoot = targetContextGroup.getElement(refNodeId);
					if(contextDefRoot==null) 	contextDefRoot = solidateConstantDefRoot(refNodeId, originalContextGroup, targetContextGroup, contextProcessorEnv);
					expProcessContext.addConstant(constantName, ((HAPContextDefinitionLeafConstant)contextDefRoot.getDefinition()).getDataValue());
				}
				
				//build constants required by script
				Map<String, Object> scriptConstants = new LinkedHashMap<String, Object>();
				for(String scriptConstantName : scriptConstantNames){
					HAPContextDefinitionRootId refNodeId = solveReferencedNodeId(new HAPContextDefinitionRootId(scriptConstantName), originalContextGroup);
					HAPContextDefinitionRoot contextDefRoot = targetContextGroup.getElement(refNodeId);
					if(contextDefRoot==null) 	contextDefRoot = solidateConstantDefRoot(refNodeId, originalContextGroup, targetContextGroup, contextProcessorEnv);
					scriptConstants.put(scriptConstantName, ((HAPContextDefinitionLeafConstant)contextDefRoot.getDefinition()).getValue());
				}
				
				//process expression in scriptExpression
				sciptExpression.processExpressions(expProcessContext, HAPExpressionProcessConfigureUtil.setDoDiscovery(null), contextProcessorEnv.expressionManager);
				
				//execute script expression
				HAPRuntimeTaskExecuteScriptExpression task = new HAPRuntimeTaskExecuteScriptExpression(sciptExpression, null, scriptConstants);
				HAPServiceData serviceData = contextProcessorEnv.runtime.executeTaskSync(task);
				
				if(segments.size()>1){
					//if have more than one segment, use the string value of result of script expression
					valueStr.append(serviceData.getData().toString());
				}
				else{
					//if have only one script expression, then use its object value
					valueObj = serviceData.getData();
				}
			}
			else{
				//build string value
				valueStr.append(segment.toString());
			}
		}
		
		if(valueObj!=null){
			//if only have one js expression
			return valueObj;
		}
		else if(segments.size()==1){
			//if no js expression, use the original one
			return leafData;
		}
		else{
			return valueStr.toString();
		}
	}
	
	private static HAPContextDefinitionRootId solveReferencedNodeId(HAPContextDefinitionRootId nodeId, HAPContextGroup candidateGroup) {
		if(nodeId.getCategary()!=null)   return nodeId;
		for(String categary : HAPContextGroup.getVisibleContextTypes()) {
			HAPContextDefinitionRoot contextDefRoot = candidateGroup.getElement(categary, nodeId.getName());
			if(contextDefRoot!=null && contextDefRoot.isConstant()) {
				return new HAPContextDefinitionRootId(categary, nodeId.getName());
			}
		}
		return null;
	}
}
