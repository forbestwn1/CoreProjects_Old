package com.nosliw.data.core.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.domain.entity.attachment.HAPDefinitionEntityContainerAttachment;

public class HAPDomainAttachment extends HAPSerializableImp{

	public static final String ATTACHMENT = "attachment";
	
	private Map<HAPIdEntityInDomain, HAPDefinitionEntityContainerAttachment> m_attachmentContainerByComplexeExeId;
	
	public HAPDomainAttachment() {
		this.m_attachmentContainerByComplexeExeId = new LinkedHashMap<HAPIdEntityInDomain, HAPDefinitionEntityContainerAttachment>();
	}
	
	public void addAttachmentContainer(HAPDefinitionEntityContainerAttachment attachmentContainer, HAPIdEntityInDomain complexIdExe) {
		if(attachmentContainer!=null)		this.m_attachmentContainerByComplexeExeId.put(complexIdExe, attachmentContainer.cloneAttachmentContainer());
		else this.m_attachmentContainerByComplexeExeId.put(complexIdExe, new HAPDefinitionEntityContainerAttachment());
	}
	
	public HAPDefinitionEntityContainerAttachment getAttachmentContainer(HAPIdEntityInDomain complexeId) {
		return this.m_attachmentContainerByComplexeExeId.get(complexeId);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		Map<String, String> attachmentJsonMap = new LinkedHashMap<String, String>();
		for(HAPIdEntityInDomain entityId : this.m_attachmentContainerByComplexeExeId.keySet()) {
			attachmentJsonMap.put(entityId.toStringValue(HAPSerializationFormat.LITERATE), this.m_attachmentContainerByComplexeExeId.get(entityId).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(ATTACHMENT, HAPJsonUtility.buildMapJson(attachmentJsonMap));
	}
}
