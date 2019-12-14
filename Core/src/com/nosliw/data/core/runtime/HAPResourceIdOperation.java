package com.nosliw.data.core.runtime;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPOperationId;
import com.nosliw.data.core.resource.HAPResourceId;

public class HAPResourceIdOperation extends HAPResourceId{

	private HAPOperationId m_operationId;
	
	public HAPResourceIdOperation(){}

	public HAPResourceIdOperation(HAPResourceId resourceId){
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdOperation(String idLiterate) {
		this.init(HAPConstant.RUNTIME_RESOURCE_TYPE_OPERATION, idLiterate, null);
	}

	public HAPResourceIdOperation(HAPOperationId operationId){
		this.init(HAPConstant.RUNTIME_RESOURCE_TYPE_OPERATION, null, null);
		this.setOperationId(operationId);
	}
	
	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_operationId = new HAPOperationId(id);
	}

	public HAPOperationId getOperationId(){  return this.m_operationId;	}
	protected void setOperationId(HAPOperationId operationId){
		this.m_operationId = operationId;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(operationId, HAPSerializationFormat.LITERATE); 
	}

	@Override
	public HAPResourceIdOperation clone(){
		HAPResourceIdOperation out = new HAPResourceIdOperation();
		out.cloneFrom(this);
		return out;
	}
}
