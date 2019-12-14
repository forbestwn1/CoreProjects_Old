package com.nosliw.data.core.runtime.js.resource;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.resource.HAPResourceId;

public class HAPResourceIdJSGateway extends HAPResourceId{

	private HAPJSGatewayId m_gatewayId; 
	
	public HAPResourceIdJSGateway(){}

	public HAPResourceIdJSGateway(HAPResourceId resourceId){
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdJSGateway(String idLiterate) {
		init(HAPConstant.RUNTIME_RESOURCE_TYPE_JSGATEWAY, idLiterate, null);
	}

	public HAPResourceIdJSGateway(HAPJSGatewayId gatewayId){
		init(HAPConstant.RUNTIME_RESOURCE_TYPE_JSGATEWAY, null, null);
		this.setGatewayId(gatewayId);
	}

	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_gatewayId = new HAPJSGatewayId(id);
	}

	public HAPJSGatewayId getGatewayId(){  return this.m_gatewayId;	}
	protected void setGatewayId(HAPJSGatewayId gatewayId){
		this.m_gatewayId = gatewayId;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(gatewayId, HAPSerializationFormat.LITERATE); 
	}
	
	@Override
	public HAPResourceIdJSGateway clone(){
		HAPResourceIdJSGateway out = new HAPResourceIdJSGateway();
		out.cloneFrom(this);
		return out;
	}

}
