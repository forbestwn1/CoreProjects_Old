package com.nosliw.data.core.resource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.complex.attachment.HAPContainerAttachment;
import com.nosliw.data.core.component.HAPDefinitionResourceComplex;
import com.nosliw.data.core.component.HAPLocalReferenceBase;
import com.nosliw.data.core.component.HAPUtilityComponent;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.resource.dynamic.HAPManagerDynamicResource;

public class HAPManagerResourceDefinition {

	private Map<String, HAPPluginResourceDefinition> m_plugins;
	private HAPManagerDynamicResource m_dynamicResourceManager;
	
	public HAPManagerResourceDefinition(HAPManagerDynamicResource dynamicResourceMan) {
		this.m_plugins = new LinkedHashMap<String, HAPPluginResourceDefinition>();
		this.m_dynamicResourceManager = dynamicResourceMan;
	}

	public HAPResourceDefinition getResourceDefinition(HAPResourceId resourceId) {
		return getResourceDefinition(resourceId, null);
	}

	public HAPResourceDefinition getResourceDefinition(HAPResourceId resourceId, HAPDomainDefinitionEntity entityDomain, HAPLocalReferenceBase localRefBase) {
		HAPResourceDefinition out = new HAPResourceDefinition(resourceId);
		String resourceType = resourceId.getResourceType();
		String resourceStructure = resourceId.getStructure();
		if(resourceStructure.equals(HAPConstantShared.RESOURCEID_TYPE_SIMPLE)) {
			HAPResourceIdSimple simpleId = (HAPResourceIdSimple)resourceId;
			HAPIdEntityInDomain resourceEntityId = this.m_plugins.get(resourceType).getResourceEntityBySimpleResourceId(simpleId, entityDomain);
			out.setEntityId(resourceEntityId);
		}
		else if(resourceStructure.equals(HAPConstantShared.RESOURCEID_TYPE_LOCAL)) {
			HAPResourceIdLocal localResourceId = (HAPResourceIdLocal)resourceId;
			HAPIdEntityInDomain entityId =  this.m_plugins.get(resourceType).getResourceEntityByLocalResourceId(localResourceId, localRefBase, entityDomain);
			out.setEntityId(entityId);
		}
		else if(resourceStructure.equals(HAPConstantShared.RESOURCEID_TYPE_EMBEDED)) {
			HAPResourceIdEmbeded embededId = (HAPResourceIdEmbeded)resourceId;
			//get parent resource def first
			HAPResourceDefinition parentResourceDef = this.getResourceDefinition(embededId.getParentResourceId(), entityDomain, localRefBase);
			//get child resource by path
			HAPResourceDefinitionOrId defOrId = parentResourceDef.getChild(embededId.getPath());
			if(HAPConstantShared.REFERENCE.equals(defOrId.getEntityOrReferenceType())) {
				//resource id
				out = this.getResourceDefinition((HAPResourceId)defOrId, entityDomain, parentResourceDef.getLocalReferenceBase());
			}
			else {
				//resource def
				out = (HAPResourceDefinition)defOrId;
				out.setLocalReferenceBase(parentResourceDef.getLocalReferenceBase());
			}
		}
		else if(resourceStructure.equals(HAPConstantShared.RESOURCEID_TYPE_DYNAMIC)) {
			HAPResourceIdDynamic dynamicResourceId = (HAPResourceIdDynamic)resourceId;
			out = this.m_dynamicResourceManager.buildResource(dynamicResourceId.getBuilderId(), dynamicResourceId.getParms());
		} 
		
//		if(out instanceof HAPWithAttachment) {
//			//merge attachment with supplment in resource id
//			HAPUtilityAttachment.mergeAttachmentInResourceIdSupplementToContainer(resourceId, ((HAPWithAttachment)out).getAttachmentContainer(), HAPConstant.INHERITMODE_PARENT);
//		}
		
		return out;
	}
	
	public HAPIdEntityInDomain parseEntityDefinition(Object obj, String entityType, HAPDomainDefinitionEntity entityDomain, HAPLocalReferenceBase localRefBase) {
		return this.m_plugins.get(entityType).parseResourceEntity(obj, entityDomain, localRefBase);
	}
	
	public HAPDefinitionResourceComplex getAdjustedComplextResourceDefinition(HAPResourceId resourceId, HAPContainerAttachment parentAttachment) {
		HAPDefinitionResourceComplex out = (HAPDefinitionResourceComplex)this.getResourceDefinition(resourceId);
		HAPUtilityComponent.mergeWithParentAttachment(out, parentAttachment);
		return out;
	}
	
	public HAPEntityResourceDefinition parseResourceEntity(String type, Object content) {
		return this.m_plugins.get(type).parseResourceEntity(content);
	}
	
	public void registerPlugin(HAPPluginResourceDefinition plugin) {
		this.m_plugins.put(plugin.getResourceType(), plugin);
	}
}
