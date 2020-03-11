package com.nosliw.data.core.process;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoUtility;
import com.nosliw.common.info.HAPEntityInfoWritable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataUtility;
import com.nosliw.data.core.resource.HAPResourceDependency;
import com.nosliw.data.core.runtime.HAPExecutableImpEntityInfo;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
public class HAPExecutableResultActivityBranch extends HAPExecutableImpEntityInfo{

	@HAPAttribute
	public static String FLOW = "flow";

	@HAPAttribute
	public static String DATA = "data";

	private HAPDefinitionSequenceFlow m_flow;
	
	private HAPData m_data;

	public HAPExecutableResultActivityBranch() {}

	//next activity
	public HAPExecutableResultActivityBranch(HAPDefinitionResultActivityBranch definition) {
		super(definition);
		this.m_data = definition.getData();
		this.m_flow = definition.getFlow();
	}
	
	public HAPDefinitionSequenceFlow getFlow() {  return this.m_flow;  }
	public HAPData getData() {   return this.m_data;    }
	
	@Override
	public HAPExecutableResultActivityBranch clone() {
		throw new RuntimeException();
	}

	@Override
	public void cloneToEntityInfo(HAPEntityInfoWritable entityInfo) {
		HAPEntityInfoUtility.cloneTo(this, entityInfo);
	}

	@Override
	public void buildEntityInfoByJson(Object json) {	}

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		this.m_flow = new HAPDefinitionSequenceFlow();
		this.m_flow.buildObject(jsonObj.getJSONObject(FLOW), HAPSerializationFormat.JSON);
		
		JSONObject dataJsonObj = jsonObj.optJSONObject(DATA);
		if(dataJsonObj!=null)	HAPDataUtility.buildDataWrapperFromJson(dataJsonObj);
		return true;  
	}

	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		HAPEntityInfoUtility.buildJsonMap(jsonMap, this);
		jsonMap.put(FLOW, this.getFlow().toStringValue(HAPSerializationFormat.JSON));
		if(this.getData()!=null) jsonMap.put(DATA, this.getData().toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildResourceJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPRuntimeInfo runtimeInfo) {
		jsonMap.put(FLOW, this.getFlow().toStringValue(HAPSerializationFormat.JSON));
		if(this.getData()!=null) jsonMap.put(DATA, this.getData().toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	protected void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {
		super.buildResourceDependency(dependency, runtimeInfo);
	}

}
