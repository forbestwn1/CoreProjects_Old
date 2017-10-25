package com.nosliw.uiresource.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;

@HAPEntityWithAttribute
public class HAPContext extends HAPSerializableImp{

	@HAPAttribute
	public static final String ELEMENTS = "elements";
	
	private Map<String, HAPContextNodeRoot> m_elements;
	
	private Map<String, HAPDataTypeCriteria> m_criterias;
	
	public HAPContext(){
		this.m_elements = new LinkedHashMap<String, HAPContextNodeRoot>();
	}
	
	public void addElement(String name, HAPContextNodeRoot rootEle){
		this.m_elements.put(name, rootEle);
	}

	public Map<String, HAPContextNodeRoot> getElements(){  return this.m_elements;  }
	
	public Map<String, HAPDataTypeCriteria> getCriterias(){
		if(this.m_criterias==null){
			this.m_criterias = new LinkedHashMap<String, HAPDataTypeCriteria>();
			for(String rootName : this.m_elements.keySet()){
				this.processCriteria(rootName, this.m_elements.get(rootName), m_criterias);
			}
		}
		return this.m_criterias;
	}
	
	public void hardMergeWith(HAPContext context){
		Map<String, HAPContextNodeRoot> eles = context.getElements();
		for(String rootName : eles.keySet()){
			this.m_elements.put(rootName, eles.get(rootName));
		}
		this.m_criterias = null;
	}
	
	private void processCriteria(String path, HAPContextNode node, Map<String, HAPDataTypeCriteria> criterias){
		HAPContextNodeDefinition definition = node.getDefinition();
		if(definition!=null){
			criterias.put(path, definition.getValue());
		}
		else{
			Map<String, HAPContextNode> children = node.getChildren();
			for(String childName : children.keySet()){
				String childPath = HAPNamingConversionUtility.cascadeComponentPath(path, childName);
				this.processCriteria(childPath, children.get(childName), criterias);
			}
		}
	}
	
	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		for(String rootName : this.m_criterias.keySet()){
			jsonMap.put(rootName, this.m_elements.get(rootName).toStringValue(HAPSerializationFormat.JSON));
		}
	}
}
