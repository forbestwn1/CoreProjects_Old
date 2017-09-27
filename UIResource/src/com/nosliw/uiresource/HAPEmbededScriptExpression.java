package com.nosliw.uiresource;

import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPEmbededScriptExpression extends HAPSerializableImp{

	private HAPScriptExpression m_scriptExpression;
	
	public HAPEmbededScriptExpression(HAPScriptExpression scriptExpression){
		this.m_scriptExpression = scriptExpression;
	}

	public HAPScriptExpression getScriptExpression(){
		return this.m_scriptExpression;
	}
	
}