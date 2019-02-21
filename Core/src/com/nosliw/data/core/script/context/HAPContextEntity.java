package com.nosliw.data.core.script.context;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.info.HAPEntityInfoWritable;
import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPContextEntity  extends HAPContext implements HAPEntityInfo{

	private HAPEntityInfoImp m_entityInfo;
	
	public HAPContextEntity() {
		this.m_entityInfo = new HAPEntityInfoImp();
	}

	@Override
	public HAPInfo getInfo() {  return this.m_entityInfo.getInfo();  }

	@Override
	public String getName() {  return this.m_entityInfo.getName();  }

	@Override
	public String getDescription() {  return this.m_entityInfo.getDescription();  }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(NAME, this.getName());
		jsonMap.put(DESCRIPTION, this.getDescription());
		jsonMap.put(INFO, HAPJsonUtility.buildJson(this.getInfo(), HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.m_entityInfo.buildObject(jsonObj, HAPSerializationFormat.JSON);
		return true;  
	}

	@Override
	public void cloneToEntityInfo(HAPEntityInfoWritable entityInfo) {  this.m_entityInfo.cloneToEntityInfo(entityInfo); }

	@Override
	public void buildEntityInfoByJson(Object json) {	this.m_entityInfo.buildObject(json, HAPSerializationFormat.JSON);	}
}
