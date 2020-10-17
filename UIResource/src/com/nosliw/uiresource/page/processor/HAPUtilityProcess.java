package com.nosliw.uiresource.page.processor;

import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafConstant;
import com.nosliw.data.core.script.context.HAPContextDefinitionRoot;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPParentContext;
import com.nosliw.data.core.script.context.HAPProcessorContext;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.uiresource.page.tag.HAPUITagDefinition;
import com.nosliw.uiresource.page.tag.HAPUITagDefinitionContext;
import com.nosliw.uiresource.page.tag.HAPUtilityUITag;

public class HAPUtilityProcess {

	//build context for ui Tag
	public static HAPContextGroup buildUITagContext(HAPUITagDefinition tagDef, HAPContextGroup parentContext, Map<String, String> attributes, HAPConfigureContextProcessor contextProcessorConfig, HAPRequirementContextProcessor contextProcessRequirement){
		//get contextDef 
		HAPUITagDefinitionContext tagDefinitionContext = tagDef.getContext();

		//add attribute constant as part of tagContext
		Map<String, String> tagAttrs = HAPUtilityUITag.getTagAttributeValue(tagDef, attributes);
		HAPContextGroup tagContext = tagDefinitionContext.cloneContextGroup();
		for(String name : tagAttrs.keySet()) {
			HAPContextDefinitionLeafConstant cstRootNode = new HAPContextDefinitionLeafConstant(tagAttrs.get(name));
			tagContext.addElement(HAPConstant.NOSLIW_RESERVE_ATTRIBUTE + name, new HAPContextDefinitionRoot(cstRootNode), HAPConstant.UIRESOURCE_CONTEXTTYPE_PRIVATE);
		}
		return HAPProcessorContext.processStatic(tagContext, HAPParentContext.createDefault(parentContext), HAPUtilityConfiguration.getContextProcessConfigurationForTagDefinition(tagDefinitionContext, contextProcessorConfig), contextProcessRequirement);
	}
}
