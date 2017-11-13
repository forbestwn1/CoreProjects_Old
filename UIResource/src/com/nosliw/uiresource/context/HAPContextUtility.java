package com.nosliw.uiresource.context;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPExpressionDefinition;
import com.nosliw.uiresource.definition.HAPConstantDef;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnit;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnitResource;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnitTag;
import com.nosliw.uiresource.expression.HAPUIResourceExpressionContext;
import com.nosliw.uiresource.tag.HAPUITagDefinition;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContextElementAbsolute;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContextElment;

public class HAPContextUtility {

	private static String processName(String name, Map<String, Object> parms){
		
	}
	
	private static HAPContextNode buildContxtNode(HAPContextNode contextNode){
		
	}
	
	private static HAPContextNodeRoot processUITagContextElement(HAPUITagDefinitionContextElment element, HAPContext parentContext){
		String type = element.getType();
		switch(type){
			case HAPConstant.UIRESOURCE_ROOTTYPE_ABSOLUTE:
				HAPUITagDefinitionContextElementAbsolute eleDef = (HAPUITagDefinitionContextElementAbsolute)element;
				HAPContextNodeRootAbsolute out = new HAPContextNodeRootAbsolute(eleDef);
				return eleDef;
				break;
			case HAPConstant.UIRESOURCE_ROOTTYPE_RELATIVE:
				
				break;
		}
	}
	
	public static void processExpressionContext(HAPUIDefinitionUnit parent, HAPUIDefinitionUnit uiDefinition){

		HAPUIResourceExpressionContext expContext = uiDefinition.getExpressionContext();

		//variables
		switch(uiDefinition.getType()){
		case HAPConstant.UIRESOURCE_TYPE_RESOURCE:
			//for resource
			expContext.addVariables(discoverDataVariablesInContext(((HAPUIDefinitionUnitResource)uiDefinition).getContext()));
			break;
		case HAPConstant.UIRESOURCE_TYPE_TAG:
			//for tag
			HAPUIDefinitionUnitTag tag = (HAPUIDefinitionUnitTag)uiDefinition;
			HAPUITagDefinition uiTagDefinition = null;
			if(uiTagDefinition.isInheritContext()){
				//add parent
				for(String rootEleName : parent.getContext().getElements().keySet()){
					HAPUITagDefinitionContextElmentRelative relativeEle = new HAPUITagDefinitionContextElmentRelative(rootEleName);
					tag.getContext().addElement(rootEleName, processUITagContextElement(relativeEle, parent.getContext()));
				}
				
				expContext.addVariables(parent.getExpressionContext().getVariables());
			}
			
			Map<String, HAPUITagDefinitionContextElment> defEles = uiTagDefinition.getContextDefinitions();
			for(String name : defEles.keySet()){
				String realName = processName(name, tag.getAttributes());
				tag.getContext().addElement(realName, processUITagContextElement(defEles.get(name), parent.getContext()));
			}
			
//			for(HAPUITagDefinitionContextElment contextEle : uiTagDefinition.getContextDefinitions()){
//				//process context name
//				Map<String, String> parms = new LinkedHashMap<String, String>();
//				parms.putAll(tag.getAttributes());
//				String nameDef = contextEle.getNameDefinition();
//				HAPScriptExpression scriptExpression = new HAPScriptExpression(nameDef);
//				HAPRuntimeTaskExecuteScriptExpression task = new HAPRuntimeTaskExecuteScriptExpression(scriptExpression, parms, null);
//				HAPServiceData serviceData = runtime.executeTaskSync(task);
//				String elementName = (String)serviceData.getData();
//
//				HAPDataTypeCriteria eleCriteria = contextEle.getCriteria();
//				if(eleCriteria!=null)   expContext.addVariable(elementName, eleCriteria);
//
//				
//				String parentNodeName = contextEle.getParentContextPath();
//				Map<String, HAPDataTypeCriteria> parentCriterias = parent.getExpressionContext().getVariables();
//				for(String parentVarName : parentCriterias.keySet()){
//					int index = parentVarName.indexOf(parentNodeName);
//					if(index!=-1){
//						String fullName = elementName + "." + parentVarName.substring(index);
//						expContext.addVariable(fullName, parentCriterias.get(parentVarName));
//					}
//				}
//			}
			break;
		}

		HAPUIResourceExpressionContext parentExpContext = parent==null?null:parent.getExpressionContext();
		
		//build data constants
		if(parent!=null)	expContext.addConstants(parentExpContext.getConstants());
		Map<String, HAPConstantDef> constantDefs = uiDefinition.getConstantDefs();
		for(String name : constantDefs.keySet()){
			HAPData data = constantDefs.get(name).getDataValue();
			if(data!=null)		expContext.addConstant(name, data);
		}
		
		//get all expressions definitions
		//expression from parent first
		if(parent!=null){
			Map<String, HAPExpressionDefinition> parentExpDefs = parentExpContext.getExpressionDefinitions();
			for(String id : parentExpDefs.keySet())    expContext.addExpressionDefinition(parentExpDefs.get(id));
		}
		//expression from current
		for(HAPExpressionDefinition expDef : uiDefinition.getOtherExpressionDefinitions())	expContext.addExpressionDefinition(expDef);
		
		//prepress expressions
		//preprocess attributes operand in expressions
//		HAPUIResourceExpressionUtility.processAttributeOperandInExpression(m_expressionDefinitionSuite, m_variables);
		
		//children ui tags
		Iterator<HAPUIDefinitionUnitTag> its = uiDefinition.getUITags().iterator();
		while(its.hasNext()){
			HAPUIDefinitionUnitTag uiTag = its.next();
			processExpressionContext(uiDefinition, uiTag);
		}
		
		
	}
	
	public static Map<String, HAPDataTypeCriteria> discoverDataVariablesInContext(HAPContext context){
		Map<String, HAPDataTypeCriteria> out = new LinkedHashMap<String, HAPDataTypeCriteria>();
		for(String rootName : context.getElements().keySet()){
			processCriteria(rootName, (HAPContextNode)context.getElements().get(rootName), out);
		}
		return out;
	}
	
	private static void processCriteria(String path, HAPContextNode node, Map<String, HAPDataTypeCriteria> criterias){
		HAPContextNodeCriteria definition = node.getDefinition();
		if(definition!=null){
			criterias.put(path, definition.getValue());
		}
		else{
			Map<String, HAPContextNode> children = node.getChildren();
			for(String childName : children.keySet()){
				String childPath = HAPNamingConversionUtility.cascadeComponentPath(path, childName);
				processCriteria(childPath, children.get(childName), criterias);
			}
		}
	}
	
}
