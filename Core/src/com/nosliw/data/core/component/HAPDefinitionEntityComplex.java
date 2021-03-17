package com.nosliw.data.core.component;

import java.util.List;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.data.core.common.HAPWithDataContext;

//entity that have data definition and attachment
public interface HAPDefinitionEntityComplex  extends HAPEntityInfo, HAPWithDataContext, HAPWithAttachment{

	//context part that reference to attachment
	List<HAPContextReference> getContextReferences();
	
}