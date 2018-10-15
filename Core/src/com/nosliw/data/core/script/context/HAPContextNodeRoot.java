package com.nosliw.data.core.script.context;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.serialization.HAPSerializable;

/**
 * Root node in context definition 
 */
@HAPEntityWithAttribute
public interface HAPContextNodeRoot extends HAPSerializable{

	@HAPAttribute
	public static final String TYPE = "type";

	@HAPAttribute
	public static final String INFO = "info";

	//
	public static final String INHERIT_MODE = "inherit";
	public static final String INHERIT_MODE_FINAL = "final";
	
	
	String getType();
	
	HAPEntityInfo getInfo();
	
	HAPContextNodeRoot toSolidContextNodeRoot(Map<String, Object> constants, HAPEnvContextProcessor contextProcessorEnv);

	HAPContextNodeRoot cloneContextNodeRoot();
}
