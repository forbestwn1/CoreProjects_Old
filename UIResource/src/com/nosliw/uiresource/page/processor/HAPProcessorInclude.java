package com.nosliw.uiresource.page.processor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPNamingConversionUtility;
import com.nosliw.data.core.component.attachment.HAPAttachment;
import com.nosliw.data.core.component.attachment.HAPUtilityAttachment;
import com.nosliw.data.core.resource.HAPManagerResourceDefinition;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIPage;
import com.nosliw.uiresource.page.definition.HAPDefinitionUITag;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnit;
import com.nosliw.uiresource.page.definition.HAPParserPage;
import com.nosliw.uiresource.page.definition.HAPUtilityPage;

public class HAPProcessorInclude {
	
	public static void processInclude(HAPDefinitionUIUnit uiResourceDef, HAPParserPage uiResourceParser, HAPUIResourceManager uiResourceMan, HAPManagerResourceDefinition resourceDefManager) {
		//find all include tag units
		Set<HAPDefinitionUITag> includeTagUnits = HAPUtilityPage.getUITagByName(uiResourceDef, HAPConstantShared.UITAG_NAME_INCLUDE);
		for(HAPDefinitionUITag includeTagUnit : includeTagUnits){
			//get include page definition
			String includePageName = includeTagUnit.getAttributes().get(HAPConstantShared.UITAG_NAME_INCLUDE_PARM_SOURCE);
			HAPAttachment pageAtt = uiResourceDef.getAttachment(HAPConstantShared.RUNTIME_RESOURCE_TYPE_UIRESOURCE, includePageName);
			HAPDefinitionUIPage includePageDef = (HAPDefinitionUIPage)HAPUtilityAttachment.getResourceDefinition(pageAtt, resourceDefManager);
			
			//append include page to include tag
			uiResourceParser.parseAndBuildUIDefinition(includeTagUnit, includePageDef.getSource());
			
			//prcess mapping
			processIncludeMapping(includeTagUnit, pageAtt);
			
			//keep expand grand children include page
			processInclude(includeTagUnit, uiResourceParser, uiResourceMan, resourceDefManager);
		}
	}

	//process mapping from page attachment adaptor
	//set those mapping as attribute for include tag
	private static void processIncludeMapping(HAPDefinitionUITag includeTagUnit, HAPAttachment pageAtt) {
		JSONObject adaptorObj = (JSONObject)pageAtt.getAdaptor();
		if(adaptorObj!=null) {
			{
				//context mapping
				Map<String, String> mapping = new LinkedHashMap<String, String>();
				JSONObject mappingObj = adaptorObj.optJSONObject(HAPConstantShared.UITAG_PARM_CONTEXT);
				for(Object key : mappingObj.keySet()) {
					mapping.put((String)key, mappingObj.getString((String)key));
				}
				String mappingStr = HAPNamingConversionUtility.cascadePropertyValuePairs(mapping);
				if(mappingStr!=null)   includeTagUnit.addAttribute(HAPConstantShared.UITAG_PARM_CONTEXT, mappingStr);
			}

			{
				//event mapping
				Map<String, String> mapping = new LinkedHashMap<String, String>();
				JSONObject mappingObj = adaptorObj.optJSONObject(HAPConstantShared.UITAG_PARM_EVENT);
				for(Object key : mappingObj.keySet()) {
					mapping.put((String)key, mappingObj.getString((String)key));
				}
				String mappingStr = HAPNamingConversionUtility.cascadePropertyValuePairs(mapping);
				if(mappingStr!=null)   includeTagUnit.addAttribute(HAPConstantShared.UITAG_PARM_EVENT, mappingStr);
			}
		
			{
				//command mapping
				Map<String, String> mapping = new LinkedHashMap<String, String>();
				JSONObject mappingObj = adaptorObj.optJSONObject(HAPConstantShared.UITAG_PARM_COMMAND);
				for(Object key : mappingObj.keySet()) {
					mapping.put((String)key, mappingObj.getString((String)key));
				}
				String mappingStr = HAPNamingConversionUtility.cascadePropertyValuePairs(mapping);
				if(mappingStr!=null)   includeTagUnit.addAttribute(HAPConstantShared.UITAG_PARM_COMMAND, mappingStr);
			}
		}
	}
}