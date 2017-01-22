package com.nosliw.data.datatype.importer.js;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.data.HAPResource;
import com.nosliw.data.datatype.importer.HAPResourceDataOperationImp;

public class HAPJSOperationInfo {

	public String m_operationId;
	
	private String m_script;
	
	private List<HAPResource> m_resources;
	
	public HAPJSOperationInfo(String script, List<HAPResourceDataOperationImp> resources, String operationId){
		this.m_operationId = operationId;
		this.m_script = script;
		this.m_resources = new ArrayList<HAPResource>();
		this.m_resources.addAll(resources);
	}
	
	public String getOperationId(){
		return this.m_operationId;
	}
	
	public String getScript(){
		return this.m_script;
	}
	
	public void addResource(HAPResource resource){
		this.m_resources.add(resource);
	}
	
	public List<HAPResource> getResources(){
		return this.m_resources;
	}
	
}