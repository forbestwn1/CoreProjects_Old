package com.nosliw.data.core.runtime;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.data.core.HAPInfo;

/**
 * This interface represent a resource 
 * Every resource have 
 * 		a global unique id
 *  	type of resource
 *  	status of resource: available, not available
 */
@HAPEntityWithAttribute
public class HAPResource extends HAPSerializableImp{

	@HAPAttribute
	public static String ID = "id";

	@HAPAttribute
	public static String RESOURCEDATA = "resourceData";

	@HAPAttribute
	public static String INFO = "info";
	
	private HAPResourceId m_id;
	
	private HAPResourceData m_resourceData;
	
	private HAPInfo m_info;
	
	private Set<HAPResourceId> m_dependency = new HashSet<HAPResourceId>();
	
	public HAPResource(HAPResourceId id, HAPResourceData resourceData, HAPInfo info){
		this.m_id = id;
		this.m_resourceData = resourceData;
		this.m_info = info;
	}
	
	public HAPResourceId getId(){		return this.m_id;	}
	
	public HAPInfo getInfo(){  return this.m_info;  }
	
	public HAPResourceData getResourceData(){  return this.m_resourceData;  }
	
	public Set<HAPResourceId> getDependentResources(){  return this.m_dependency;  }
	public void addDependendcy(HAPResourceId resourceId){  this.m_dependency.add(resourceId); }
	
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ID, HAPSerializeManager.getInstance().toStringValue(this.m_id, HAPSerializationFormat.JSON));
		jsonMap.put(RESOURCEDATA, HAPSerializeManager.getInstance().toStringValue(this.m_resourceData, HAPSerializationFormat.JSON));
		jsonMap.put(INFO, HAPSerializeManager.getInstance().toStringValue(this.m_info, HAPSerializationFormat.JSON));
	}
	
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildFullJsonMap(jsonMap, typeJsonMap);
	}
	
}