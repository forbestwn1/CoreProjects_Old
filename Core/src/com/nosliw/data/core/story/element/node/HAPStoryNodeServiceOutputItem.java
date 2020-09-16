package com.nosliw.data.core.story.element.node;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.service.interfacee.HAPServiceOutput;
import com.nosliw.data.core.story.HAPStoryNodeImp;
import com.nosliw.data.core.story.change.HAPChangeResult;

@HAPEntityWithAttribute
public class HAPStoryNodeServiceOutputItem extends HAPStoryNodeImp{

	public final static String STORYNODE_TYPE = HAPConstant.STORYNODE_TYPE_SERVICEOUTPUTITEM; 

	@HAPAttribute
	public static final String OUTPUTITEM = "outputItem";

	private HAPServiceOutput m_outputItem;
	
	public HAPStoryNodeServiceOutputItem() {}
	
	public HAPStoryNodeServiceOutputItem(HAPServiceOutput outputItem) {
		super(STORYNODE_TYPE);
		this.m_outputItem = outputItem;
	}

	@Override
	public HAPChangeResult patch(String path, Object value) {
		return super.patch(path, value);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		JSONObject defJsonObj = jsonObj.optJSONObject(OUTPUTITEM);
		if(defJsonObj!=null) {
			this.m_outputItem = new HAPServiceOutput();
			this.m_outputItem.buildObject(defJsonObj, HAPSerializationFormat.JSON);
		}
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_outputItem!=null)	jsonMap.put(OUTPUTITEM, this.m_outputItem.toStringValue(HAPSerializationFormat.JSON));
	}

}
