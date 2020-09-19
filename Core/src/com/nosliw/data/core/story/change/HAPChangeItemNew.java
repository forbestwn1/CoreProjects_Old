package com.nosliw.data.core.story.change;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.story.HAPIdElement;
import com.nosliw.data.core.story.HAPParserElement;
import com.nosliw.data.core.story.HAPStoryElement;

public class HAPChangeItemNew extends HAPChangeItem{

	public static final String MYCHANGETYPE = HAPConstant.STORYDESIGN_CHANGETYPE_NEW;

	@HAPAttribute
	public static final String ALIAS = "alias";

	@HAPAttribute
	public static final String ELEMENT = "element";

	private String m_alias;
	
	private HAPEntityOrReference m_entityOrReference;
	
	public HAPChangeItemNew() {
		super(MYCHANGETYPE);
	}

	public HAPChangeItemNew(HAPEntityOrReference entityOrReference, String alias) {
		this();
		this.m_alias = alias;
		this.m_entityOrReference = entityOrReference;
	}
	
	public String getAlias() {	return this.m_alias;	}

	public HAPEntityOrReference getEntityOrReference() {   return this.m_entityOrReference;    }
	public void setEntityOrReference(HAPEntityOrReference entityOrReference) {   this.m_entityOrReference = entityOrReference;   }
	
	private HAPStoryElement getElement() {
		HAPStoryElement out = null;
		if(this.m_entityOrReference.getEntityOrReferenceType().equals(HAPConstant.ENTITY)) {
			out = (HAPStoryElement)this.m_entityOrReference;
		}
		else {
			HAPIdElement elementId = (HAPIdElement)this.m_entityOrReference;
			out = this.getStory().getElement(elementId);
		}
		return out;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		this.m_alias = (String)jsonObj.opt(ALIAS);
		JSONObject eleObj = jsonObj.optJSONObject(ELEMENT);
		if(eleObj!=null) {
			HAPParserElement.parseElement(eleObj, this.getStory());
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENT, this.getElement().toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ALIAS, this.m_alias);
	}
}
