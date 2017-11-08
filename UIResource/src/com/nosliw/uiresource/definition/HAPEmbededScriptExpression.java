package com.nosliw.uiresource.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.uiresource.expression.HAPScriptExpression;

@HAPEntityWithAttribute
public class HAPEmbededScriptExpression extends HAPSerializableImp{

	@HAPAttribute
	public static final String SCRIPTEXPRESSION = "scriptExpression";

	@HAPAttribute
	public static final String UIID = "uiId";
	
	private HAPScriptExpression m_scriptExpression;
	
	private String m_uiId;
	
	public HAPEmbededScriptExpression(String uiId, HAPScriptExpression scriptExpression){
		this.m_uiId = uiId;
		this.m_scriptExpression = scriptExpression;
	}

	public HAPScriptExpression getScriptExpression(){		return this.m_scriptExpression;	}
	
	public String getUIId(){  return this.m_uiId;   }

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(SCRIPTEXPRESSION, this.m_scriptExpression.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(UIID, this.m_uiId);
	}
	
}