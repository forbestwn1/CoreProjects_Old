package com.nosliw.data.core.script.expression;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.criteria.HAPVariableInfo;
import com.nosliw.data.core.script.expression.literate.HAPUtilityScriptLiterate;

public class HAPUtilityScriptExpression {

	public static HAPScript newScript(String content) {
		String type = null;
		String script = null;
		if(HAPUtilityScriptLiterate.isText(content)) {
			type = HAPConstant.SCRIPT_TYPE_TEXT;
			script = content;
		}
		else {
			List<HAPScript> iterateSegs = HAPUtilityScriptLiterate.parseScriptLiterate(content);
			if(iterateSegs.size()==1) {
				type = HAPConstant.SCRIPT_TYPE_EXPRESSION;
				script = iterateSegs.get(0).getScript();
			}
			else {
				type = HAPConstant.SCRIPT_TYPE_LITERATE;
				script = content;
			}
		}
		return HAPScript.newScript(script, type);
	}
	
	public static void addConstantDefinition(Map<String, HAPDefinitionConstant> to, Set<HAPDefinitionConstant> from) {
		for(HAPDefinitionConstant def : from) {
			addConstantDefinition(to, def);
		}
	}
	
	public static void addConstantDefinition(Map<String, HAPDefinitionConstant> constantDefinitions, HAPDefinitionConstant constantDefinition) {
		HAPDefinitionConstant existing = constantDefinitions.get(constantDefinition.getId());
		if(existing==null)  constantDefinitions.put(constantDefinition.getId(), constantDefinition);
	}

	public static void addVariableInfo(Map<String, HAPVariableInfo> variableInfos, HAPVariableInfo variableInfo) {
		HAPVariableInfo existing = variableInfos.get(variableInfo.getId());
		if(existing==null)   variableInfos.put(variableInfo.getId(), variableInfo);
		else {
			if(existing.getCriteria()==null && variableInfo.getCriteria()!=null) {
				existing.setCriteria(variableInfo.getCriteria());
			}
		}
	}

	public static Map<String, HAPData> getConstantData(Map<String, Object> constantsValue){
		Map<String, HAPData> constantsData = new LinkedHashMap<String, HAPData>();
		for(String name : constantsValue.keySet()) {
			if(constantsValue.get(name) instanceof HAPData) {
				constantsData.put(name, (HAPData)constantsValue.get(name));
			}
		}
		return constantsData;
	}
	
	//process variables in expression, 
	//	for attribute operation a.b.c.d which have responding definition in context, 
	//			replace attribute operation with one variable operation
	//  for attribute operation a.b.c.d which have responding definition a.b.c in context, 
	//			replace attribute operation with one variable operation(a.b.c) and getChild operation
//	public static void processAttributeOperandInExpression(HAPResourceDefinitionExpressionGroup expressionDefinition, final Map<String, HAPVariableInfo> varsInfo){
//		HAPOperandUtility.processAttributeOperandInExpressionOperand(expressionDefinition.getOperand(), varsInfo);
//	}
	

}

