package com.nosliw.data.core.imp.runtime.js.resource;

import com.nosliw.data.core.imp.runtime.js.HAPDataAccessRuntimeJS;
import com.nosliw.data.core.imp.runtime.js.HAPResourceManagerDataAccess;
import com.nosliw.data.core.runtime.HAPResource;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceIdConverter;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;

public class HAPResourceManagerJSConverter extends HAPResourceManagerDataAccess{

	public HAPResourceManagerJSConverter(HAPDataAccessRuntimeJS dataAccess){
		super(dataAccess);
	}
	
	@Override
	public HAPResource getResource(HAPResourceId resourceId, HAPRuntimeInfo runtimeInfo) {
		HAPResourceIdConverter resourceIdOperation = new HAPResourceIdConverter(resourceId);
		HAPResourceDataJSConverterImp converterResource = this.getDataAccess().getDataTypeConverter(resourceIdOperation.getConverter());
		if(converterResource!=null)		return new HAPResource(resourceId, converterResource, null);
		else return null;
	}

}
