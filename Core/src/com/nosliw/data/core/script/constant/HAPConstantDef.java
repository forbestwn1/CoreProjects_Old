package com.nosliw.data.core.script.constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataWrapper;

@HAPEntityWithAttribute
public class HAPConstantDef  extends HAPSerializableImp{
	@HAPAttribute
	public static String LITERATE = "literate";

	@HAPAttribute
	public static String PROCESSED = "processed";
	
	@HAPAttribute
	public static String VALUE = "value";
	
	private Object m_definitionObj;
	
	private Object m_value;
	
	private boolean m_isProcessed = false;
	
	//name of other constants that reference to this constant
	private Set<String> m_referenced; 
	
	public HAPConstantDef(Object defObj){
		this.m_definitionObj = defObj;
		this.m_referenced = new HashSet<String>();
	}

	public Object getDefinitionValue(){  return this.m_definitionObj;   }
	
	/**
	 * get data after processing the constant
	 * @return
	 */
	public Object getValue(){	return this.m_value;	}
	public void setValue(Object value){	this.m_value = value;	}
	
	public boolean isProcessed(){  return this.m_isProcessed; }
	public void processed(){   this.m_isProcessed = true;   }
	
	/**
	 * Get data value of value
	 * if not data, then return null
	 * if is data, then return data object
	 */
	public HAPData getDataValue(){
		HAPDataWrapper out = new HAPDataWrapper();
		boolean isData = out.buildObjectByLiterate(this.m_value.toString());
		if(isData)  return out;
		else return null;
	}

	public void addReferenced(String name) {    this.m_referenced.add(name);  }
	public Set<String> getReferenced() {   return this.m_referenced;  }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(LITERATE, this.m_definitionObj.toString());
		jsonMap.put(PROCESSED, this.m_isProcessed+"");
		typeJsonMap.put(PROCESSED, Boolean.class);
		if(this.m_value!=null) jsonMap.put(VALUE, this.m_value.toString());
		typeJsonMap.put(VALUE, this.m_value.getClass());
	}
}
