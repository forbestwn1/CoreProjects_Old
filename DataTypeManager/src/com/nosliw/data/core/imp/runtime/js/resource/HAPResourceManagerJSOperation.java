package com.nosliw.data.core.imp.runtime.js.resource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.data.core.HAPOperation;
import com.nosliw.data.core.imp.HAPDataAccessDataType;
import com.nosliw.data.core.imp.runtime.js.HAPDataAccessRuntimeJS;
import com.nosliw.data.core.imp.runtime.js.HAPResourceManagerDataAccess;
import com.nosliw.data.core.runtime.HAPResource;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceIdOperation;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;

@HAPEntityWithAttribute
public class HAPResourceManagerJSOperation extends HAPResourceManagerDataAccess{

	@HAPAttribute
	public static final String INFO_OPERATIONINFO = "operationInfo";
	
	private HAPDataAccessDataType m_dataTypeDataAccess = null;
	
	public HAPResourceManagerJSOperation(HAPDataAccessRuntimeJS dataAccess, HAPDataAccessDataType dataTypeDataAccess){
		super(dataAccess);
		this.m_dataTypeDataAccess = dataTypeDataAccess;
	}
	
	@Override
	public HAPResource getResource(HAPResourceId resourceId, HAPRuntimeInfo runtimeInfo) {
		HAPResourceIdOperation resourceIdOperation = new HAPResourceIdOperation(resourceId);
		HAPResourceDataJSOperationImp operationResource = this.getDataAccess().getJSOperation(resourceIdOperation.getOperationId());
		if(operationResource==null)  return null;
		
		HAPOperation operationInfo = this.m_dataTypeDataAccess.getOperationInfoByName(resourceIdOperation.getOperationId(), resourceIdOperation.getOperationId().getOperation());

		Map<String, Object> info = new LinkedHashMap<String, Object>();
		info.put(INFO_OPERATIONINFO, operationInfo);
		return new HAPResource(resourceId, operationResource, info);
	}
}
