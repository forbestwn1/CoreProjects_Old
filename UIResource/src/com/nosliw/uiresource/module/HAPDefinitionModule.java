package com.nosliw.uiresource.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.component.HAPAttachmentContainer;
import com.nosliw.data.core.component.HAPAttachmentReference;
import com.nosliw.data.core.component.HAPChildrenComponentId;
import com.nosliw.data.core.component.HAPChildrenComponentIdContainer;
import com.nosliw.data.core.component.HAPComponentImp;
import com.nosliw.data.core.component.HAPComponentUtility;
import com.nosliw.uiresource.common.HAPInfoDecoration;

/**
Module is a independent entity that is runnable within a container
It is a simple application which contains pages and transition between pages
Also be aware that module can be run in different container (pc browser, mobile browser, ...), try to make it platform independent
for instance, for a module that shows a school information, it contains two pages: list of school, school info
    when in mobile phone, the school info should overlap on top of list page
    when in pc, the list page and the school page should diplay sid by side

Don't need to define service information here, as service information will be gathered from all the mdoule ui definition
 */
@HAPEntityWithAttribute
public class HAPDefinitionModule extends HAPComponentImp{
	
	@HAPAttribute
	public static String UI = "ui";
	
	@HAPAttribute
	public static String UIDECORATION = "uiDecoration";
	
	private String m_id;
	
	// all the module uis (name -- definition)
	private List<HAPDefinitionModuleUI> m_uis;

	private List<HAPInfoDecoration> m_uiDecoration;
	
	public HAPDefinitionModule(String id) {
		super(id);
		this.m_uis = new ArrayList<HAPDefinitionModuleUI>();
		this.m_uiDecoration = new ArrayList<HAPInfoDecoration>();
	}
	
	@Override
	public String getId() {   return this.m_id;   }
	 
	public List<HAPDefinitionModuleUI> getUIs(){  return this.m_uis;  }
	public void addUI(HAPDefinitionModuleUI ui) {   this.m_uis.add(ui);   }
	
	public void setUIDecoration(List<HAPInfoDecoration> decs) {  this.m_uiDecoration = decs;    }
	public List<HAPInfoDecoration> getUIDecoration(){   return this.m_uiDecoration;    }
	
	@Override
	public HAPChildrenComponentIdContainer getChildrenComponentId() {
		HAPChildrenComponentIdContainer out = new HAPChildrenComponentIdContainer();
		//ui part
		for(HAPDefinitionModuleUI ui : this.getUIs()) {
			if(!HAPDefinitionModuleUI.STATUS_DISABLED.equals(ui.getStatus())) {
				HAPAttachmentContainer mappedParentAttachment = HAPComponentUtility.buildNameMappedAttachment(this.getAttachmentContainer(), ui);
				HAPAttachmentReference pageAttachment = (HAPAttachmentReference)this.getAttachmentContainer().getElement(HAPConstant.RUNTIME_RESOURCE_TYPE_UIRESOURCE, ui.getPage());
				out.addChildCompoentId(new HAPChildrenComponentId(ui.getName(), pageAttachment.getId(), ui.getInfo()), mappedParentAttachment);
			}
		}
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(UI, HAPJsonUtility.buildJson(this.m_uis, HAPSerializationFormat.JSON));
		jsonMap.put(UIDECORATION, HAPJsonUtility.buildJson(this.m_uiDecoration, HAPSerializationFormat.JSON));
	}
}
