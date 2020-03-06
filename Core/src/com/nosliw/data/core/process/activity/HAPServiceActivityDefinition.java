package com.nosliw.data.core.process.activity;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.data.core.process.HAPDefinitionActivity;
import com.nosliw.data.core.process.HAPDefinitionActivityTask;

public class HAPServiceActivityDefinition extends HAPDefinitionActivityTask{

	@HAPAttribute
	public static String PROVIDER = "provider";

	private String m_provider;
	
	public HAPServiceActivityDefinition(String type) {
		super(type);
	}

	public String getProvider() {   return this.m_provider;  }

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.m_provider = jsonObj.optString(PROVIDER);
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PROVIDER, this.m_provider);
	}
	
	@Override
	public HAPDefinitionActivity cloneActivityDefinition() {
		HAPServiceActivityDefinition out = new HAPServiceActivityDefinition(this.getType());
		this.cloneToTaskActivityDefinition(out);
		out.m_provider = this.m_provider;
		return out;
	}


}
