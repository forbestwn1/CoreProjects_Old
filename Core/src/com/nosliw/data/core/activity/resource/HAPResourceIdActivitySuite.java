package com.nosliw.data.core.activity.resource;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.resource.HAPResourceIdSimple;
import com.nosliw.data.core.resource.HAPSupplementResourceId;

public class HAPResourceIdActivitySuite  extends HAPResourceIdSimple{

	private HAPIdActivitySuite m_activitySuiteId; 
	
	public HAPResourceIdActivitySuite(){  super(HAPConstantShared.RUNTIME_RESOURCE_TYPE_ACTIVITYSUITE);  }

	public HAPResourceIdActivitySuite(HAPResourceIdSimple resourceId){
		this();
		this.cloneFrom(resourceId);
	}
	
	public HAPResourceIdActivitySuite(String idLiterate) {
		this();
		init(idLiterate, null);
	}

	public HAPResourceIdActivitySuite(HAPIdActivitySuite activitySuiteId){
		this();
		init(null, null);
		this.m_activitySuiteId = activitySuiteId;
		this.m_id = HAPSerializeManager.getInstance().toStringValue(activitySuiteId, HAPSerializationFormat.LITERATE); 
	}

	public HAPResourceIdActivitySuite(String id, HAPSupplementResourceId supplement){
		this();
		init(id, supplement);
	}
	
	@Override
	protected void setId(String id){
		super.setId(id);
		this.m_activitySuiteId = new HAPIdActivitySuite(id);
	}

	public HAPIdActivitySuite getExpressionSuiteId(){  return this.m_activitySuiteId;	}
	
	@Override
	public HAPResourceIdActivitySuite clone(){
		HAPResourceIdActivitySuite out = new HAPResourceIdActivitySuite();
		out.cloneFrom(this);
		return out;
	}
}
