package com.nosliw.data.core.story.element.node;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.service.definition.HAPInfoServiceStatic;
import com.nosliw.data.core.story.HAPStoryElement;
import com.nosliw.data.core.story.HAPStoryNodeImp;
import com.nosliw.data.core.story.change.HAPChangeItemPatch;
import com.nosliw.data.core.story.change.HAPChangeResult;
import com.nosliw.data.core.story.change.HAPUtilityChange;

@HAPEntityWithAttribute
public class HAPStoryNodeService extends HAPStoryNodeImp{
	
	public final static String STORYNODE_TYPE = HAPConstantShared.STORYNODE_TYPE_SERVICE; 
	
	@HAPAttribute
	public static final String REFERENCEID = "referenceId";
	
	private String m_referenceId;
	
	public HAPStoryNodeService() {
		super(STORYNODE_TYPE);
	}
	
	public String getReferenceId() {   return this.m_referenceId;   }
	public void setReferenceId(String refId) {    this.m_referenceId = refId;    }

	@Override
	public HAPChangeResult patch(String path, Object value, HAPRuntimeEnvironment runtimeEnv) {
		HAPChangeResult out = super.patch(path, value, runtimeEnv);
		if(out!=null)  return out; 
		else {
			out = new HAPChangeResult();
			if(REFERENCEID.equals(path)) {
				out.addRevertChange(HAPUtilityChange.buildChangePatch(this, REFERENCEID, this.m_referenceId));
				this.m_referenceId = (String)value;
				
				if(this.m_referenceId!=null) {
					HAPInfoServiceStatic serviceDef = runtimeEnv.getServiceManager().getServiceDefinitionManager().getDefinition(this.m_referenceId).getStaticInfo();
					out.addExtendChange(new HAPChangeItemPatch(this.getElementId(), EXTRA, serviceDef));
					out.addExtendChange(new HAPChangeItemPatch(this.getElementId(), DISPLAYRESOURCE, serviceDef.getInterface().getDisplayResource()));
				}
				
				return out;
			}
		}
		return null;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryNodeService out = new HAPStoryNodeService();
		super.cloneTo(out);
		out.m_referenceId = this.m_referenceId;
		return out;
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildObjectByJson(jsonObj);
		this.m_referenceId = (String)jsonObj.opt(REFERENCEID);
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(REFERENCEID, this.m_referenceId);
	}

}
