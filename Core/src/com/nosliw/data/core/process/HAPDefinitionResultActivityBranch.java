package com.nosliw.data.core.process;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataUtility;

@HAPEntityWithAttribute
public class HAPDefinitionResultActivityBranch extends HAPEntityInfoWritableImp{

	@HAPAttribute
	public static String FLOW = "flow";

	@HAPAttribute
	public static String DATA = "data";

	//next activity
	private HAPDefinitionSequenceFlow m_flow;
	
	private HAPData m_data;
	
	public HAPDefinitionSequenceFlow getFlow() {  return this.m_flow;  }
	
	public HAPData getData() {    return this.m_data;    }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		try{
			super.buildObjectByJson(json);
			JSONObject jsonObj = (JSONObject)json;

			this.m_flow = new HAPDefinitionSequenceFlow();
			this.m_flow.buildObject(jsonObj.optJSONObject(FLOW), HAPSerializationFormat.JSON);
			
			HAPDataUtility.buildDataWrapperFromJson(jsonObj.optJSONObject(DATA));
			
			return true;  
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(FLOW, m_flow.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(DATA, m_data.toStringValue(HAPSerializationFormat.JSON));
	}
}
