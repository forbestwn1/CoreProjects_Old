package com.nosliw.data.context;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;

//a group of context
//normally contexts are grouped according to type : public, private, ...
@HAPEntityWithAttribute
public class HAPContextGroup extends HAPSerializableImp{
	
	private Map<String, HAPContext> m_contexts;
	
	public HAPContextGroup(){
		this.m_contexts = new LinkedHashMap<String, HAPContext>();
		for(String type : getAllContextTypes()){
			this.m_contexts.put(type, new HAPContext());
		}
	}

	public static String[] getAllContextTypes(){
		String[] contextTypes = {
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PROTECTED,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_INTERNAL,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PRIVATE,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_EXCLUDED
		};
		return contextTypes;
	}

	//context type that can be inherited by child
	public static String[] getInheritableContextTypes(){
		String[] contextTypes = {
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PROTECTED,
		};
		return contextTypes;
	}

	public static String[] getVisibleContextTypes(){
		String[] contextTypes = {
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_PROTECTED,
			HAPConstant.UIRESOURCE_CONTEXTTYPE_INTERNAL,
		};
		return contextTypes;
	}

	public void empty() {
		for(String type : getAllContextTypes()) {
			this.getContext(type).empty();
		}
	}
	
	public HAPContext getPublicContext(){  return this.getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC);  }
	public HAPContext getProtectedContext(){  return this.getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_PROTECTED);  }
	public HAPContext getInternalContext(){  return this.getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_INTERNAL);  }
	public HAPContext getPrivateContext(){  return this.getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_PRIVATE);  }
	public HAPContext getExcludedContext(){  return this.getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_EXCLUDED);  }

	public void addPublicElement(String name, HAPContextNodeRoot ele){  this.addElement(name, ele, HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC);  }
	public void addProtectedElement(String name, HAPContextNodeRoot ele){  this.addElement(name, ele, HAPConstant.UIRESOURCE_CONTEXTTYPE_PROTECTED);  }
	public void addInternalElement(String name, HAPContextNodeRoot ele){  this.addElement(name, ele, HAPConstant.UIRESOURCE_CONTEXTTYPE_INTERNAL);  }
	public void addPrivateElement(String name, HAPContextNodeRoot ele){  this.addElement(name, ele, HAPConstant.UIRESOURCE_CONTEXTTYPE_PRIVATE);  }
	public void addExcludedElement(String name, HAPContextNodeRoot ele){  this.addElement(name, ele, HAPConstant.UIRESOURCE_CONTEXTTYPE_EXCLUDED);  }

	public Map<String, HAPContextNodeRoot> getElements(String contextType){  return this.getContext(contextType).getElements();  }
	
	public void addElement(String name, HAPContextNodeRoot rootEle, String type){	this.getContext(type).addElement(name, rootEle);	}
	
	public HAPContext getContext(String type){		return this.m_contexts.get(type);	}
	
	public HAPContextNodeRoot getContextNode(String type, String name) {  return this.m_contexts.get(type).getElement(name);   }
	
	public void hardMergeWith(HAPContextGroup contextGroup){
		for(String type : this.m_contexts.keySet()){
			this.getContext(type).hardMergeWith(contextGroup.getContext(type));
		}
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		for(String type : this.m_contexts.keySet()){
			jsonMap.put(type, HAPJsonUtility.buildJson(this.m_contexts.get(type), HAPSerializationFormat.JSON));
		}
	}
}
