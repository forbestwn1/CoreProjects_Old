package com.nosliw.uiresource.tag;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.expression.HAPExpressionManager;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.context.HAPContext;
import com.nosliw.uiresource.context.HAPContextGroup;
import com.nosliw.uiresource.context.HAPContextNodeRoot;
import com.nosliw.uiresource.context.HAPContextParser;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnitResource;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnitTag;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnitUtility;
import com.nosliw.uiresource.parser.HAPUIResourceParser;

public class HAPUITagUtility {

	public static void processIncludeTags(HAPUIDefinitionUnitResource uiResource, HAPUIResourceManager uiResourceMan, HAPDataTypeHelper dataTypeHelper, HAPUITagManager uiTagMan, HAPRuntime runtime, HAPExpressionManager expressionManager, HAPUIResourceParser uiResourceParser){
		Set<HAPUIDefinitionUnitTag> includeTags = new HashSet<HAPUIDefinitionUnitTag>();
		HAPUIDefinitionUnitUtility.getUITagByName(uiResource, HAPConstant.UITAG_NAME_INCLUDE, includeTags);
		for(HAPUIDefinitionUnitTag includeTag : includeTags){
			processIncludeTag(includeTag, uiResource, uiResourceMan, dataTypeHelper, uiTagMan, runtime, expressionManager, uiResourceParser);			
		}
	}	
	
	private static void processIncludeTag(HAPUIDefinitionUnitTag includeTagResource, HAPUIDefinitionUnitResource rootResource, HAPUIResourceManager uiResourceMan, HAPDataTypeHelper dataTypeHelper, HAPUITagManager uiTagMan, HAPRuntime runtime, HAPExpressionManager expressionManager, HAPUIResourceParser uiResourceParser){
		String includeResourceName = includeTagResource.getAttributes().get(HAPConstant.UITAG_NAME_INCLUDE_PARM_SOURCE);
		String contextMapName = includeTagResource.getAttributes().get(HAPConstant.UITAG_NAME_INCLUDE_PARM_CONTEXT);

		//build include tag
		HAPUIDefinitionUnitResource uiResource = uiResourceMan.getUIResourceDefinitionByName(includeResourceName);
		uiResourceParser.parseContent(includeTagResource, uiResource.getSource());

		//get context mapping definition
		HAPUITagDefinitionContext contextDef = null;
		JSONObject contextMappingJson = (JSONObject)rootResource.getConstantValues().get(contextMapName);
		if(contextMappingJson!=null){
			contextDef = new HAPUITagDefinitionContext();
			HAPContextParser.parseContextInTagDefinition(contextMappingJson, contextDef);
			//then apply it in include context
			includeTagResource.setContextDefinition(contextDef);
		}
		
		//loop through all context elements in included resource
		//if context element cannot find mapping, then it should be escalated to context in root resource
		HAPContextGroup contextGroup = includeTagResource.getContext();
		for(String contextType : contextGroup.getContextTypes()){
			HAPContext context = contextGroup.getContext(contextType);
			Map<String, HAPContextNodeRoot> elements = context.getElements();
			for(String eleName : elements.keySet()){
				HAPUITagDefinitionContextElment defEle = null;
				if(contextDef!=null)	defEle = contextDef.getElements(contextType).get(eleName);
				if(defEle==null){
					//not mapped, add to root context
					HAPContextNodeRoot rootNode = elements.get(eleName);
					rootResource.getContext().getContext(contextType).addElement(eleName, rootNode);
				}
			}
		}
	}

}