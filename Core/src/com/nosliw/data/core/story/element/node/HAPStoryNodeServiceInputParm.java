package com.nosliw.data.core.story.element.node;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.service.interfacee.HAPServiceParm;
import com.nosliw.data.core.story.HAPStoryNodeImp;
import com.nosliw.data.core.story.change.HAPChangeResult;

@HAPEntityWithAttribute
public class HAPStoryNodeServiceInputParm extends HAPStoryNodeImp{

	public final static String STORYNODE_TYPE = HAPConstant.STORYNODE_TYPE_SERVICEINPUTPARM; 

	@HAPAttribute
	public static final String PARMDEFINITION = "parmDefinition";

	private HAPServiceParm m_parmDefinition;
	
	public HAPStoryNodeServiceInputParm() {}
	
	public HAPStoryNodeServiceInputParm(HAPServiceParm parmDefinition) {
		super(STORYNODE_TYPE);
		this.m_parmDefinition = parmDefinition;
	}

	@Override
	public HAPChangeResult patch(String path, Object value) {
		return super.patch(path, value);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		JSONObject defJsonObj = jsonObj.optJSONObject(PARMDEFINITION);
		if(defJsonObj!=null) {
			this.m_parmDefinition = new HAPServiceParm();
			this.m_parmDefinition.buildObject(defJsonObj, HAPSerializationFormat.JSON);
		}
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_parmDefinition!=null)	jsonMap.put(PARMDEFINITION, this.m_parmDefinition.toStringValue(HAPSerializationFormat.JSON));
	}

}
