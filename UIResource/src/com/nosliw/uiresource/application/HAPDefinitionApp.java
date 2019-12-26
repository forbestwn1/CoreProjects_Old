package com.nosliw.uiresource.application;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.core.component.HAPComponent;

@HAPEntityWithAttribute
public class HAPDefinitionApp extends HAPComponent{

	@HAPAttribute
	public static final String ENTRY = "entry";
	
	@HAPAttribute
	public static final String APPLICATIONDATA = "applicationData";
	
	//one mini app may have different entry for different senario. 
	private Map<String, HAPDefinitionAppEntryUI> m_entries;

	//global data definition 
	//it can be stateful data(the data that can retrieve next time you use the app)
	private Map<String, HAPDefinitionAppData> m_applicationData;

	public HAPDefinitionApp(String id) {
		super(id);
		this.m_entries = new LinkedHashMap<String, HAPDefinitionAppEntryUI>();
		this.m_applicationData = new LinkedHashMap<String, HAPDefinitionAppData>();
	}
	
	public Map<String, HAPDefinitionAppData> getApplicationData(){   return this.m_applicationData;   }
	public void setApplicationData(Map<String, HAPDefinitionAppData> dataDef) {		if(dataDef!=null)   this.m_applicationData = dataDef;	}

	public Collection<HAPDefinitionAppEntryUI> getEntrys(){   return this.m_entries.values();    }
	public HAPDefinitionAppEntryUI getEntry(String entry) {  return this.m_entries.get(entry);  }
	public void addEntry(HAPDefinitionAppEntryUI entry) {
		String name = entry.getName();
		if(HAPBasicUtility.isStringEmpty(name)) {
			name = HAPUtilityApp.ENTRY_DEFAULT;
			entry.setName(name);
		}
		this.m_entries.put(name, entry);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ENTRY, HAPJsonUtility.buildJson(this.m_entries, HAPSerializationFormat.JSON));
		jsonMap.put(APPLICATIONDATA, HAPJsonUtility.buildJson(this.m_applicationData, HAPSerializationFormat.JSON));
	}
}
