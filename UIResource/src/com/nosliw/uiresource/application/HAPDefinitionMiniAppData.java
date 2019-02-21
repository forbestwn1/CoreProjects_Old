package com.nosliw.uiresource.application;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.data.core.script.context.HAPContextEntity;

@HAPEntityWithAttribute
public class HAPDefinitionMiniAppData extends HAPContextEntity{

	public HAPDefinitionMiniAppData() {
	}
	
	@Override
	public boolean buildObjectByJson(Object obj) {
		super.buildObjectByJson(obj);
		JSONObject jsonObj = (JSONObject)obj;
		return true;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
}
