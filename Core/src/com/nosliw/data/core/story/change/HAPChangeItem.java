package com.nosliw.data.core.story.change;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public abstract class HAPChangeItem extends HAPEntityInfoImp{

	@HAPAttribute
	public static final String CHANGETYPE = "changeType";

	@HAPAttribute
	public static final String REVERTCHANGES = "revertChanges";

	@HAPAttribute
	public static final String REVERTABLE = "revertable";

	private String m_changeType;
	
	private List<HAPChangeItem> m_revertChanges;

	private boolean m_revertable;
	
	public HAPChangeItem(String changeType) {
		this.m_changeType = changeType;
		this.m_revertable = true;
	}

	public String getChangeType() {    return this.m_changeType;    }
	
	public void setRevertChanges(List<HAPChangeItem> revertChanges) {    this.m_revertChanges = revertChanges;      }
	public List<HAPChangeItem> getRevertChanges(){     return this.m_revertChanges;       }
	
	public boolean isRevertable() {    return this.m_revertable;     }
	public void setRevertable(boolean revertable) {     this.m_revertable = revertable;      }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		this.m_changeType = jsonObj.getString(CHANGETYPE);
		Object revertableObj = jsonObj.opt(REVERTABLE);
		if(revertableObj!=null)  this.m_revertable = (Boolean)revertableObj; 
		
		JSONArray revertChangesArray = jsonObj.optJSONArray(REVERTCHANGES);
		if(revertChangesArray!=null) {
			this.m_revertChanges = new ArrayList<HAPChangeItem>();
			for(int i=0; i<revertChangesArray.length(); i++) {
				this.m_revertChanges.add(HAPParserChange.parseChangeItem(revertChangesArray.getJSONObject(i)));
			}
		}
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CHANGETYPE, this.m_changeType);
		if(this.m_revertChanges!=null)   jsonMap.put(REVERTCHANGES, HAPJsonUtility.buildJson(this.m_revertChanges, HAPSerializationFormat.JSON));
		jsonMap.put(REVERTABLE, this.m_revertable+"");
		typeJsonMap.put(REVERTABLE, Boolean.class);
	}
}