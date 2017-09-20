package com.nosliw.uiresource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;

@HAPEntityWithAttribute
public class HAPContextElement {

	@HAPAttribute
	public static final String NAME = "name";

	@HAPAttribute
	public static final String CRITERIA  = "criteria";

	@HAPAttribute
	public static final String CHILDREN  = "children";
	
	@HAPAttribute
	public static final String DEFAULT = "default";
	
	private String m_name;
	
	private HAPDataTypeCriteria m_criteria;
	
	private Map<String, HAPDataTypeCriteria> m_children;
	
	private String m_default;
	
	public HAPContextElement(String name){
		this.m_children = new LinkedHashMap<String, HAPDataTypeCriteria>();
		this.m_name = name;
	}
	
	public String getName(){  return this.m_name;  }
	
	public void setDefault(String defau){		this.m_default = defau;	}

	public void setCriteria(HAPDataTypeCriteria criteria){		this.m_criteria = criteria;	}
	
	public void addChild(String path, HAPDataTypeCriteria criteria){		this.m_children.put(path, criteria);	}
	
}