package com.nosliw.data.core.component;

import com.nosliw.common.info.HAPEntityInfoWritable;

public interface HAPAttachment extends HAPEntityInfoWritable{

	String getType();

	String getResourceType();
	
	@Override
	HAPAttachment clone();
}
