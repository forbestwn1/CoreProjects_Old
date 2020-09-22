package com.nosliw.data.core.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.story.change.HAPChangeItem;
import com.nosliw.data.core.story.change.HAPParserChange;

@HAPEntityWithAttribute
public class HAPAnswer extends HAPSerializableImp{

	@HAPAttribute
	public static final String CHANGES = "changes";

	@HAPAttribute
	public static final String QUESTIONID = "questionId";
	
	private List<HAPChangeItem> m_changes;
	
	private String m_questionId;
	
	public HAPAnswer() {
		this.m_changes = new ArrayList<HAPChangeItem>();
	}
	
	public List<HAPChangeItem> getChanges(){    return this.m_changes;    }
	
	public String getQuestionId() {   return this.m_questionId;    }
	
	public void addChanges(HAPChangeItem change) {    
		this.m_changes.add(change);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		JSONArray changesArray = jsonObj.optJSONArray(CHANGES);
		if(changesArray!=null) {
			for(int i=0; i<changesArray.length(); i++) {
				this.m_changes.add(HAPParserChange.parseChangeItem(changesArray.getJSONObject(i)));
			}
		}
		this.m_questionId = (String)jsonObj.opt(QUESTIONID);
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(QUESTIONID, this.m_questionId);
		jsonMap.put(CHANGES, HAPJsonUtility.buildJson(this.m_changes, HAPSerializationFormat.JSON));
	}
}
