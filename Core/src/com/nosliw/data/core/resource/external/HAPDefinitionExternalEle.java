package com.nosliw.data.core.resource.external;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.core.resource.HAPResourceId;

//
@HAPEntityWithAttribute
public class HAPDefinitionExternalEle extends HAPEntityInfoImp{

	@HAPAttribute
	public static String ID = "id";
	
	@HAPAttribute
	public static String TYPE = "type";
	
	private String m_type;
	
	private HAPResourceId m_id;
	
	public HAPDefinitionExternalEle(String type) {
		this.m_type = type;
	}

	public HAPResourceId getId() {	return this.m_id;  }
	
	private String getType() {
		if(this.m_type==null && this.m_id!=null) {
			this.m_type = this.m_id.getType();
		}
		return this.m_type;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
		if(this.m_id!=null)		jsonMap.put(ID, this.m_id.toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.buildEntityInfoByJson(jsonObj);
		if(this.m_type==null)   this.m_type = jsonObj.getString(TYPE);
		Object id = jsonObj.get(ID);
		if(id!=null) {
			this.m_id = HAPResourceId.newInstance(this.m_type, id);
		}
		return true;  
	}

	@Override
	public boolean equals(Object obj) {
		boolean out = false;
		if(obj instanceof HAPDefinitionExternalEle) {
			HAPDefinitionExternalEle ele = (HAPDefinitionExternalEle)obj;
			if(super.equals(ele)) {
				if(HAPBasicUtility.isEquals(ele.getType(), this.getType())) {
					if(HAPBasicUtility.isEquals(ele.m_id, this.m_id)) {
						out = true;
					}
				}
			}
		}
		return out;
	}
}
