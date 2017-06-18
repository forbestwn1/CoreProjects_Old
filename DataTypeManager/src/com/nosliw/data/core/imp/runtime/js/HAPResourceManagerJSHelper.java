package com.nosliw.data.core.imp.runtime.js;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.data.core.imp.io.HAPDBAccess;
import com.nosliw.data.core.runtime.HAPResource;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceManager;

public class HAPResourceManagerJSHelper implements HAPResourceManager{

	private HAPDBAccess m_dbAccess = HAPDBAccess.getInstance();
	
	@Override
	public List<HAPResource> getResources(List<HAPResourceId> resourcesId) {
		List<HAPResource> out = new ArrayList<HAPResource>();
		for(HAPResourceId resourceId : resourcesId){
			out.add(this.getResource(resourceId));
		}
		return out;
	}

	@Override
	public HAPResource getResource(HAPResourceId resourceId) {
		HAPResourceDataHelperImp helperResource = this.m_dbAccess.getResourceHelper(resourceId.getId());
		return new HAPResource(resourceId, helperResource, null);
	}

}
