package com.nosliw.data.core.expression;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPRelationship;

public class HAPMatchers extends HAPSerializableImp{

	private Map<HAPDataTypeId, HAPMatcher> m_matchers;
	
	public HAPMatchers(){
		this.m_matchers = new LinkedHashMap<HAPDataTypeId, HAPMatcher>();
	}

	public void addMatcher(HAPMatcher matcher){
		this.m_matchers.put(matcher.getDataTypeId(), matcher);
	}
	
	/**
	 * Get all relationships invovled in this matchers 
	 */
	public Set<HAPRelationship> getRelationships(){
		Set<HAPRelationship> out = new HashSet<HAPRelationship>();
		for(HAPDataTypeId dataTypeId : this.m_matchers.keySet()){
			out.addAll(this.m_matchers.get(dataTypeId).discoverRelationship());
		}
		return out;
	}

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildJsonMap(jsonMap, typeJsonMap);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		for(HAPDataTypeId dataTypeId : this.m_matchers.keySet()){
			HAPMatcher matcher = this.m_matchers.get(dataTypeId);
			jsonMap.put(HAPSerializeManager.getInstance().toStringValue(dataTypeId, HAPSerializationFormat.LITERATE),
					HAPSerializeManager.getInstance().toStringValue(matcher, HAPSerializationFormat.JSON));
		}
	}
}
