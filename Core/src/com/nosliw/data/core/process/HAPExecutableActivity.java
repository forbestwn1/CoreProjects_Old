package com.nosliw.data.core.process;

import java.util.Map;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;

@HAPEntityWithAttribute
public abstract class HAPExecutableActivity extends HAPSerializableImp implements HAPExecutable{

	private int m_index;
	
	private String m_name;
	
	public HAPExecutableActivity(int index, String name) {
		this.m_index = index;
		this.m_name = name;
	}
	
	public int getIndex() { return this.m_index;  }
	public String getName() {   return this.m_name;   }
	
	public abstract HAPDataTypeCriteria getExitDataTypeCriteria();
	
	public abstract void updateReferencedExecute(Map<String, HAPExecutableProcess> references);
	
}
