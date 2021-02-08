package com.nosliw.data.core.imp.runtime.js.resource;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.imp.HAPDataAccessDataType;
import com.nosliw.data.core.imp.runtime.js.HAPDataAccessRuntimeJS;
import com.nosliw.data.core.runtime.js.HAPResourceManagerJS;

public class HAPResourceManagerJSImp extends HAPResourceManagerJS{

	public HAPResourceManagerJSImp(HAPDataAccessRuntimeJS runtimeJSDataAccess, HAPDataAccessDataType dataTypeDataAccess){
		this.registerResourceManager(HAPConstantShared.RUNTIME_RESOURCE_TYPE_OPERATION, new HAPResourceManagerJSOperation(runtimeJSDataAccess, dataTypeDataAccess, this));
		this.registerResourceManager(HAPConstantShared.RUNTIME_RESOURCE_TYPE_CONVERTER, new HAPResourceManagerJSConverter(runtimeJSDataAccess, this));
		this.registerResourceManager(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSLIBRARY, new HAPResourceManagerJSLibrary(this));
		this.registerResourceManager(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSHELPER, new HAPResourceManagerJSHelper(runtimeJSDataAccess, this));
		this.registerResourceManager(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSGATEWAY, new HAPResourceManagerJSGateway(this));
	}
	
}
