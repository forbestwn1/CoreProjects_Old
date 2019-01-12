package com.nosliw.uiresource.resource;

import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPUIModuleId extends HAPSerializableImp{

	private String m_id;
	
	public HAPUIModuleId(String id){
		this.m_id = id;
	}
	
	public String getId(){  return this.m_id;  }

	@Override
	protected String buildLiterate(){
		return this.m_id;
	}

	@Override
	protected boolean buildObjectByLiterate(String literateValue){
		this.m_id = literateValue;
		return true;
	}
}
