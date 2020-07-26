package com.nosliw.data.core.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;

public class HAPQuestionGroup extends HAPQuestion{

	@HAPAttribute
	public static final String CHILDREN = "children";

	private List<HAPQuestion> m_children;

	public HAPQuestionGroup() {
		this.m_children = new ArrayList<HAPQuestion>();
	}
	
	public HAPQuestionGroup(String question) {
		super(question);
		this.m_children = new ArrayList<HAPQuestion>();
	}

	@Override
	public String getType() {	return HAPConstant.STORYDESIGN_QUESTIONTYPE_GROUP;	}

	public void addChild(HAPQuestion item) {
		this.m_children.add(item);
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		
		JSONArray itemArray = jsonObj.optJSONArray(CHILDREN);
		if(itemArray!=null) {
			for(int i=0; i<itemArray.length(); i++) {
				HAPQuestion question = HAPQuestion.parseQuestion(itemArray.getJSONObject(i));
				this.addChild(question);
			}
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CHILDREN, HAPJsonUtility.buildJson(this.m_children, HAPSerializationFormat.JSON));
	}
}