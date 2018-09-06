package com.nosliw.uiresource.processor;

import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextNodeRoot;
import com.nosliw.data.core.script.context.HAPContextNodeRootConstant;
import com.nosliw.data.core.script.context.HAPContextRootNodeId;
import com.nosliw.data.core.script.context.HAPEnvContextProcessor;
import com.nosliw.data.core.script.context.HAPProcessorContext;
import com.nosliw.data.core.script.context.HAPUtilityContext;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitTag;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContext;
import com.nosliw.uiresource.tag.HAPUITagId;
import com.nosliw.uiresource.tag.HAPUITagManager;

public class HAPProcessorUIContext {

	//process context information
	public static void process(HAPExecutableUIUnit uiExe, HAPContextGroup parentContext, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		
		if(uiExe.getType().equals(HAPConstant.UIRESOURCE_TYPE_TAG)) {
			//for custom, merge parent context with context definition in tag definition first
			parentContext = buildUITagContext((HAPExecutableUIUnitTag)uiExe, parentContext, uiTagMan, contextProcessorEnv);
		}
		
		//merge with context defined in tag unit
		HAPContextGroup extContextGroup = HAPProcessorContext.process(uiExe.getUIUnitDefinition().getContextDefinition(), parentContext, new HAPConfigureContextProcessor(), contextProcessorEnv);
		uiExe.setContext(extContextGroup);

		//build flat context
		HAPContext flatContext = buildFlatContext(uiExe.getContext());
		uiExe.setFlatContext(flatContext);

		Map<String, Object> constantsValue = flatContext.getConstantValue();
		for(String name : constantsValue.keySet()) {
			Object constantValue = constantsValue.get(name);
			uiExe.addConstantValue(name, constantValue);
		}
		
		//build variables
		Map<String, HAPVariableInfo> varsInfo = HAPUtilityContext.discoverDataVariablesInContext(flatContext);
		for(String varName : varsInfo.keySet()) {
			uiExe.getExpressionContext().addVariable(varName, varsInfo.get(varName));
		}
	}
	
	private static HAPContext buildFlatContext(HAPContextGroup context) {
		HAPContext out = new HAPContext();
		for(String categary : context.getContextTypesWithPriority()) {
			Map<String, HAPContextNodeRoot> eles = context.getElements(categary);
			for(String name : eles.keySet()) {
				String updatedName = new HAPContextRootNodeId(categary, name).getFullName();
				out.addElement(updatedName, eles.get(name));
				
				//
				HAPContextNodeRoot newEle = HAPUtilityContext.createInheritedElement(eles.get(name), null, updatedName);
				out.addElement(name, newEle);
			}
		}
		return out;
	}
	

	
	//build context for ui Tag
	private static HAPContextGroup buildUITagContext(HAPExecutableUIUnitTag uiTag, HAPContextGroup parentContext, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		//get contextDef 
		HAPUITagDefinitionContext tagDefinitionContext = uiTagMan.getUITagDefinition(new HAPUITagId(uiTag.getUIUnitTagDefinition().getTagName())).getContext();

		//add attribute constant as part of tagContext
		Map<String, String> constants = uiTag.getAttributes();
		HAPContextGroup tagContext = tagDefinitionContext.clone();
		for(String cstName : constants.keySet()) {
			HAPContextNodeRootConstant cstRootNode = new HAPContextNodeRootConstant(constants.get(cstName));
			tagContext.addElement(cstName, cstRootNode, HAPConstant.UIRESOURCE_CONTEXTTYPE_INTERNAL);
		}
		
		HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
		configure.inheritMode = tagDefinitionContext.getInheritMode();

		if(uiTag.getUIUnitTagDefinition().getTagName().equals("textinput")){
			int kkkk = 5555;
			kkkk++;
		}
		
		
		return HAPProcessorContext.process(tagContext, parentContext, configure, contextProcessorEnv);
	}
}