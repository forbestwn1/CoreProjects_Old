package com.nosliw.data.core.resource;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.serialization.HAPSerializable;

public interface HAPEntityResourceDefinition extends HAPEntityInfo, HAPSerializable{

	String getEntityType();
	
	HAPEntityResourceDefinition getChildResourceEntity(String child);
	
}
