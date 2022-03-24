package com.nosliw.data.core.domain.entity.attachment;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

public class HAPReferenceAttachment extends HAPSerializableImp implements HAPEntityOrReference{

	@HAPAttribute
	public static String DATATYPE = "dataType";

	@HAPAttribute
	public static String NAME = "name";

	private String m_dataType;
	
	private String m_name;
	
	@Override
	public String getEntityOrReferenceType() {   return HAPConstantShared.REFERENCE;    }

	public String getDataType() {   return this.m_dataType;  }
	
	public String getName() {   return this.m_name;  }

	public static HAPReferenceAttachment newInstance(String name, String type) {
		HAPReferenceAttachment out = new HAPReferenceAttachment();
		out.buildObject(name, HAPSerializationFormat.LITERATE);
		if(out.m_dataType==null)  out.m_dataType = type;
		return out;
	}

	public static HAPReferenceAttachment newInstance(Object obj, String type) {
		HAPReferenceAttachment out = new HAPReferenceAttachment();
		out.m_dataType = type;
		if(obj instanceof JSONObject) {
			out.buildObjectByJson(obj);
		}
		else if(obj instanceof String) {
			out.buildObjectByLiterate((String)obj);
		}
		return out;
	}

	public static HAPReferenceAttachment newInstance(Object obj) {
		return newInstance(obj, null);
	}

	private HAPReferenceAttachment() {}
	
	@Override
	protected String buildLiterate(){  return HAPUtilityNamingConversion.cascadeLevel1(m_name, m_dataType); }
	
	@Override
	protected boolean buildObjectByLiterate(String literateValue){
		String[] segs = HAPUtilityNamingConversion.parseLevel1(literateValue);
		this.m_name = segs[0];
		if(segs.length>1)  this.m_dataType = segs[1];
		return true;  
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		Object typeObj = jsonObj.opt(DATATYPE);
		if(typeObj!=null)		this.m_dataType = (String)typeObj;
		this.m_name = jsonObj.getString(NAME);
		return true;  
	}
	
}