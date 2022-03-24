package com.nosliw.data.core.domain.entity.valuestructure;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.domain.HAPDefinitionEntityInDomainSimple;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.structure.HAPRootStructure;

public class HAPDefinitionEntityValueStructure extends HAPDefinitionEntityInDomainSimple{

	public static String ENTITY_TYPE = HAPConstantShared.RUNTIME_RESOURCE_TYPE_VALUESTRUCTURE;

	@HAPAttribute
	public static final String VALUE = "value";

	private Map<String, HAPRootStructure> m_rootByName;
	
	public HAPDefinitionEntityValueStructure() {
		this.m_rootByName = new LinkedHashMap<String, HAPRootStructure>();
	}
	
	public HAPRootStructure addRoot(HAPRootStructure root) {
		root = root.cloneRoot();
		String name = root.getName();
		this.m_rootByName.put(name, root);
		return root;
	}

	public Set<String> getRootNames(){   return this.m_rootByName.keySet();    }
	
	public HAPRootStructure getRootByName(String rootName) {   return this.m_rootByName.get(rootName);  }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		for(String rootName : this.m_rootByName.keySet()) {
			jsonMap.put(rootName, this.m_rootByName.get(rootName).toStringValue(HAPSerializationFormat.JSON));
		}
	}
	
	@Override
	protected void buildExpandedJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPDomainDefinitionEntity entityDefDomain){
		this.buildJsonMap(jsonMap, typeJsonMap);
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean out = false;
		if(obj instanceof HAPDefinitionEntityValueStructure) {
			HAPDefinitionEntityValueStructure context = (HAPDefinitionEntityValueStructure)obj;
			if(context.getRootNames().equals(this.getRootNames())) {
				for(String eleName : this.getRootNames()) {
					out = this.getRootByName(eleName).equals(context.getRootByName(eleName));
					if(!out)  
						break;
				}
				return true;
			}
		}
		return out;
	}

}