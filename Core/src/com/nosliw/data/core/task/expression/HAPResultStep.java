package com.nosliw.data.core.task.expression;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.HAPData;

public class HAPResultStep extends HAPSerializableImp{

	@HAPAttribute
	public static String DATA = "data";
	
	@HAPAttribute
	public static String VARIABLENAME = "variableName";
	
	@HAPAttribute
	public static String EXIT = "exit";
	
	@HAPAttribute
	public static String NEXT = "next";

	private HAPData m_data;
	
	private String m_variableName;
	
	private boolean m_exit;
	
	private String m_next;

	public static HAPResultStep createExitResult(HAPData data){
		HAPResultStep out = new HAPResultStep();
		out.m_data = data;
		out.m_exit = true;
		return out;
	}
	
	public static HAPResultStep createJumpResult(String next){
		HAPResultStep out = new HAPResultStep();
		out.m_next = next;
		out.m_exit = false;
		return out;
	}
	
	public static HAPResultStep createNextStepResult(HAPData data, String variableName){
		HAPResultStep out = new HAPResultStep();
		out.m_data = data;
		out.m_variableName = variableName;
		out.m_exit = false;
		return out;
	}
	
	public boolean isExit() {  return this.m_exit;  }
	public String getNext() {   return this.m_next;  }
	public HAPData getData() {   return this.m_data;    }
	public String getVariableName() {   return this.m_variableName;   }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATA, HAPJsonUtility.buildJson(this.m_data, HAPSerializationFormat.JSON));
		jsonMap.put(NEXT, m_next);
		jsonMap.put(EXIT, m_exit+"");
		jsonMap.put(VARIABLENAME, m_variableName);
	}
}