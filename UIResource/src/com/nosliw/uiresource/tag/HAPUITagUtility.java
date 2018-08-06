package com.nosliw.uiresource.tag;

import java.util.Map;

import com.nosliw.data.context.HAPContextGroup;
import com.nosliw.data.context.HAPContextNodeRootRelative;
import com.nosliw.data.context.HAPContextUtility;
import com.nosliw.data.context.HAPEnvContextProcessor;
import com.nosliw.uiresource.page.HAPUIDefinitionUnitTag;

public class HAPUITagUtility {

	
	//build context for ui Tag
	public static void buildUITagContext(HAPContextGroup parentContext, HAPUIDefinitionUnitTag uiTag, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		//get contextDef 
		HAPUITagDefinitionContext tagContextDefinition = uiTagMan.getUITagDefinition(new HAPUITagId(uiTag.getTagName())).getContext();

		Map<String, String> constants = uiTag.getAttributes();
		
		if(tagContextDefinition.isInherit()){
			//add public context from parent
			for(String rootEleName : parentContext.getPublicContext().getElements().keySet()){
				HAPContextNodeRootRelative relativeEle = new HAPContextNodeRootRelative();
				relativeEle.setPath(rootEleName);
				uiTag.getContext().getPublicContext().addElement(rootEleName, HAPContextUtility.processContextDefinitionElement(rootEleName, relativeEle, parentContext, constants, contextProcessorEnv));
			}
		}

		//element defined in tag definition
		HAPContextUtility.processContextGroupDefinition(parentContext, tagContextDefinition, uiTag.getContext(), constants, contextProcessorEnv);
	}
}
