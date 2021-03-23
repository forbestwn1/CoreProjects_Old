package com.nosliw.uiresource.page.processor;

import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.data.HAPUtilityDataComponent;
import com.nosliw.data.core.expression.HAPDefinitionExpressionSuite;
import com.nosliw.data.core.expression.HAPUtilityExpressionComponent;
import com.nosliw.data.core.expression.HAPUtilityExpressionProcessConfigure;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.script.expression.HAPContextProcessExpressionScript;
import com.nosliw.data.core.script.expression.HAPDefinitionScriptGroupImp;
import com.nosliw.data.core.script.expression.HAPExecutableScriptGroup;
import com.nosliw.data.core.script.expression.HAPProcessorScript;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIEmbededScriptExpressionInAttribute;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIEmbededScriptExpressionInContent;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIBody;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitTag;
import com.nosliw.uiresource.page.execute.HAPUIEmbededScriptExpressionInAttribute;
import com.nosliw.uiresource.page.execute.HAPUIEmbededScriptExpressionInContent;

public class HAPProcessorUIExpressionScript {

	//constants
//	Map<String, Object> constantsValue = flatContext.getConstantValue();
//	for(String name : constantsValue.keySet()) {
//		Object constantValue = constantsValue.get(name);
//		uiExe.addConstantValue(name, constantValue);
//	}
	
	//build variables
//	uiExe.getExpressionSuiteInContext().setContextStructure(flatContext.getContext());
//	Map<String, HAPVariableInfo> varsInfo = HAPUtilityContext.discoverDataVariablesInContext(flatContext.getContext());
//	for(String varName : varsInfo.keySet()) {
//		uiExe.getExpressionContext().addDataVariable(varName, varsInfo.get(varName));
//	}
	
	public static void buildExpressionScriptProcessContext(HAPExecutableUIUnit exeUnit){
		HAPExecutableUIBody body = exeUnit.getBody();
		HAPDefinitionUIUnit uiUnitDef = exeUnit.getUIUnitDefinition();

		HAPContextProcessExpressionScript processScriptContext = body.getProcessExpressionScriptContext();
		
		//expression context
		processScriptContext.setContextStructure(body.getFlatContext().getContext());
		
		//expression suite from attachment
		HAPDefinitionExpressionSuite expressionSuite = HAPUtilityExpressionComponent.buildExpressionSuiteFromComponent(uiUnitDef, body.getFlatContext().getContext());
		processScriptContext.setExpressionDefinitionSuite(expressionSuite);
		
		//constant from attachment
		Set<HAPDefinitionConstant> constantsDef = HAPUtilityDataComponent.buildConstantDefinition(uiUnitDef.getAttachmentContainer());
		for(HAPDefinitionConstant constantDef : constantsDef) {
			processScriptContext.addConstantDefinition(constantDef);
		}
		
		//constant from context
		Map<String, Object> constantsValue = body.getFlatContext().getConstantValue();
		for(String id : constantsValue.keySet()) {
			HAPDefinitionConstant constantDef = new HAPDefinitionConstant(id, constantsValue.get(id));
			processScriptContext.addConstantDefinition(constantDef);
			if(constantDef.isData()) {
				processScriptContext.addConstantDefinition(constantDef);
			}
		}
		
		//child tag
		for(HAPExecutableUIUnitTag childTag : body.getUITags()) {
			buildExpressionScriptProcessContext(childTag);			
		}
	}
	
	public static void processUIScriptExpression(HAPExecutableUIUnit exeUnit, HAPRuntimeEnvironment runtimeEnv){
		HAPExecutableUIBody body = exeUnit.getBody();
		HAPDefinitionUIUnit uiUnitDef = exeUnit.getUIUnitDefinition();
		
		//embeded script in content
		for(HAPDefinitionUIEmbededScriptExpressionInContent embededContent : uiUnitDef.getScriptExpressionsInContent()) {
			body.addScriptExpressionsInContent(new HAPUIEmbededScriptExpressionInContent(embededContent));
		}
		//embeded script in tag attribute 
		for(HAPDefinitionUIEmbededScriptExpressionInAttribute embededAttribute : uiUnitDef.getScriptExpressionsInAttribute()) {
			body.addScriptExpressionsInAttribute(new HAPUIEmbededScriptExpressionInAttribute(embededAttribute));
		}
		//embeded script in custom tag attribute
		for(HAPDefinitionUIEmbededScriptExpressionInAttribute embededAttribute : uiUnitDef.getScriptExpressionsInTagAttribute()) {
			body.addScriptExpressionsInTagAttribute(new HAPUIEmbededScriptExpressionInAttribute(embededAttribute));
		}

		
		HAPDefinitionScriptGroupImp scriptGroup = new HAPDefinitionScriptGroupImp();
		for(HAPDefinitionUIEmbededScriptExpressionInContent embededContent : uiUnitDef.getScriptExpressionsInContent()) {
			scriptGroup.addEntityElement(embededContent);
		}
		//embeded script in tag attribute 
		for(HAPDefinitionUIEmbededScriptExpressionInAttribute embededAttribute : uiUnitDef.getScriptExpressionsInAttribute()) {
			scriptGroup.addEntityElement(embededAttribute);
		}
		//embeded script in custom tag attribute
		for(HAPDefinitionUIEmbededScriptExpressionInAttribute embededAttribute : uiUnitDef.getScriptExpressionsInTagAttribute()) {
			scriptGroup.addEntityElement(embededAttribute);
		}

		HAPExecutableScriptGroup scriptGroupExe = HAPProcessorScript.processScript(exeUnit.getId(), scriptGroup, body.getProcessExpressionScriptContext(), runtimeEnv.getExpressionManager(), HAPUtilityExpressionProcessConfigure.setDoDiscovery(null), runtimeEnv, new HAPProcessTracker());
		body.setScriptGroupExecutable(scriptGroupExe);
		
//		//process all embeded script expression
//		List<HAPEmbededScriptExpression> embededScriptExpressions = HAPUtilityExecutable.getEmbededScriptExpressionFromExeUnit(exeUnit);
//		for(HAPEmbededScriptExpression embededScriptExpression : embededScriptExpressions) {
//			HAPProcessorScript.processEmbededScriptExpression(embededScriptExpression, exeUnit.getExpressionContext(), HAPUtilityExpressionProcessConfigure.setDoDiscovery(null), expressionManager, runtime);
//		}
//
//		//when a embeded in tag attribute turn out to be constant, then replace constant value with embeded  
//		Set<HAPUIEmbededScriptExpressionInAttribute> removed = new HashSet<HAPUIEmbededScriptExpressionInAttribute>();
//		Set<HAPUIEmbededScriptExpressionInAttribute> all = exeUnit.getScriptExpressionsInTagAttribute();
//		for(HAPUIEmbededScriptExpressionInAttribute embededScriptExpression : all){
//			if(embededScriptExpression.isConstant()){
//				String value = embededScriptExpression.getValue();
//				HAPExecutableUIUnitTag tag = exeUnit.getUITagesByName().get(embededScriptExpression.getUIId());
//				tag.addAttribute(embededScriptExpression.getAttribute(), value);
//				removed.add(embededScriptExpression);
//			}
//		}
//		for(HAPUIEmbededScriptExpressionInAttribute embededScriptExpression : removed)	all.remove(embededScriptExpression);

		
		//child tag
		for(HAPExecutableUIUnitTag childTag : body.getUITags()) {
			processUIScriptExpression(childTag, runtimeEnv);			
		}
	}
	
}