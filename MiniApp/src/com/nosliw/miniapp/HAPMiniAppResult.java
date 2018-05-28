package com.nosliw.miniapp;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.serialization.HAPSerializeUtility;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataWrapper;

public class HAPMiniAppResult extends HAPSerializableImp{

	@HAPAttribute
	public static String RESULT = "result";

	@HAPAttribute
	public static String UIMODULE = "uiModule";

	private Map<String, HAPData> m_result;

	private HAPUIModule111 m_uiModule;
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(RESULT, HAPJsonUtility.buildJson(this.m_result, HAPSerializationFormat.JSON));
		jsonMap.put(UIMODULE, HAPJsonUtility.buildJson(this.m_uiModule, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected boolean buildObjectByFullJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_result = HAPSerializeUtility.buildMapFromJsonObject(HAPDataWrapper.class.getName(), jsonObj.optJSONObject(RESULT));
		this.m_uiModule = (HAPUIModule111)HAPSerializeManager.getInstance().buildObject(HAPUIModule111.class.getName(), jsonObj.optJSONObject(UIMODULE), HAPSerializationFormat.JSON);
		return true;
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		return this.buildObjectByFullJson(json);
	}
}
