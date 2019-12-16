package com.nosliw.data.core.resource.external;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;

public class HAPExternalMappingUtility {

	
	public static final String ATTRIBUTE_MODE = "overide"; 
	public static final String OVERRIDE_MODE_CONFIGURABLE = "configurable"; 
	public static final String OVERRIDE_MODE_NONE = "none"; 
	
	public static HAPDefinitionExternalMapping parseDefinition(JSONObject externalDefJson) {
		HAPDefinitionExternalMapping out = new HAPDefinitionExternalMapping();
		for(Object key : externalDefJson.keySet()) {
			String type = (String)key;
			JSONArray byNameArray = externalDefJson.getJSONArray(type);
			for(int i=0; i<byNameArray.length(); i++) {
				HAPDefinitionExternalMappingEle ele = new HAPDefinitionExternalMappingEle(type);
				ele.buildObject(byNameArray.getJSONObject(i), HAPSerializationFormat.JSON);
				out.addElement(type, ele);
			}
		}
		return out;
	}

	
	public static boolean isOverridenByParent(HAPDefinitionExternalMappingEle ele) {
		String mode = (String)ele.getInfoValue(ATTRIBUTE_MODE);
		if(mode==null)   mode =  OVERRIDE_MODE_NONE;
		return OVERRIDE_MODE_CONFIGURABLE.equals(mode);
	}
}
