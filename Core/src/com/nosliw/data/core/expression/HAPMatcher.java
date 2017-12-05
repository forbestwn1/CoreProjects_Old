package com.nosliw.data.core.expression;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPRelationship;

/**
 * Store matcher information (match from one data type to another data type)
 * 		source data type id
 * 		relationship
 * 		sub matchers by name
 */
@HAPEntityWithAttribute
public class HAPMatcher extends HAPSerializableImp{

	@HAPAttribute
	public static String DATATYPEID = "dataTypeId";

	@HAPAttribute
	public static String RELATIONSHIP = "relationship";
	
	@HAPAttribute
	public static String SUBMATCHERS = "subMatchers";
	
	private HAPDataTypeId m_dataTypeId;
	
	private HAPRelationship m_relationship;
	
	private Map<String, HAPMatchers> m_subMatchers = new LinkedHashMap<String, HAPMatchers>();
	
	public HAPMatcher(HAPDataTypeId dataTypeId, HAPRelationship relationship){
		this.m_dataTypeId = dataTypeId;
		this.m_relationship = relationship;
	}
	
	public HAPDataTypeId getDataTypeId(){   return this.m_dataTypeId;  }
	
	public HAPRelationship getRelationship(){  return this.m_relationship;  }
	
	public Map<String, HAPMatchers> getSubMatchers(){  return this.m_subMatchers;    }
	
	public void removeAllSubMatcher(){  this.m_subMatchers.clear();  }
	
	public Set<HAPRelationship> discoverRelationships(){
		Set<HAPRelationship> out = new HashSet<HAPRelationship>();
		out.add(this.m_relationship);
		for(String name : this.m_subMatchers.keySet()){
			out.addAll(this.m_subMatchers.get(name).discoverRelationships());
		}
		return out;
	}
	
	public void addSubMatchers(String name, HAPMatchers matcher){
		this.m_subMatchers.put(name, matcher);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(DATATYPEID, HAPSerializeManager.getInstance().toStringValue(this.m_dataTypeId, HAPSerializationFormat.LITERATE));
		jsonMap.put(RELATIONSHIP, HAPSerializeManager.getInstance().toStringValue(this.m_relationship, HAPSerializationFormat.JSON));
		jsonMap.put(SUBMATCHERS, HAPSerializeManager.getInstance().toStringValue(this.m_subMatchers, HAPSerializationFormat.JSON));
	}
}
