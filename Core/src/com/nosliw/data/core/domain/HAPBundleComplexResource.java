package com.nosliw.data.core.domain;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPGeneratorId;
import com.nosliw.data.core.complex.HAPExecutableEntityComplex;
import com.nosliw.data.core.resource.HAPInfoResourceIdNormalize;
import com.nosliw.data.core.resource.HAPResourceIdSimple;

//all information for complex resource 
public class HAPBundleComplexResource {

	//root resource
	private HAPResourceIdSimple m_rootResourceId;
	
	//entity domain definition
	private HAPDomainEntityDefinitionGlobal m_definitionDomain;
	
	//entity domain executable
	private HAPDomainEntityExecutableResourceComplex m_executableDomain;

	//mapping between complex entity definition id to executable id 
	private Map<HAPIdEntityInDomain, HAPIdEntityInDomain> m_executableComplexEntityIdByDefinitionComplexEntityId;
	private Map<HAPIdEntityInDomain, HAPIdEntityInDomain> m_definitionComplexEntityIdByExecutableComplexEntityId;
	
	//all other complex resource this resource depend on
	private Set<HAPResourceIdSimple> m_complexResourceDependency;
	
	private HAPDomainAttachment m_attachmentDomain;
	
	//id generator
	private HAPGeneratorId m_idGenerator;

	public HAPBundleComplexResource(HAPResourceIdSimple rootResourceId, HAPDomainEntityDefinitionGlobal definitionDomain) {
		this.m_rootResourceId = rootResourceId;
		this.m_definitionDomain = definitionDomain;
		this.m_idGenerator = new HAPGeneratorId();
		this.m_executableDomain = new HAPDomainEntityExecutableResourceComplex(this.m_idGenerator);
		this.m_attachmentDomain = new HAPDomainAttachment(this.m_idGenerator);
		this.m_executableComplexEntityIdByDefinitionComplexEntityId = new LinkedHashMap<HAPIdEntityInDomain, HAPIdEntityInDomain>();
		this.m_definitionComplexEntityIdByExecutableComplexEntityId = new LinkedHashMap<HAPIdEntityInDomain, HAPIdEntityInDomain>();
		this.m_complexResourceDependency = new HashSet<HAPResourceIdSimple>();
	}
	
	public HAPResourceIdSimple getRootResourceId() {    return this.m_rootResourceId;    }
	
	public HAPIdEntityInDomain getDefinitionRootEntityId() {	return this.m_definitionDomain.getResourceDefinitionByResourceId(this.m_rootResourceId).getEntityId();  }
	public HAPIdEntityInDomain getExecutableRootEntityId() {    return this.getExecutableEntityIdByDefinitionEntityId(this.getDefinitionRootEntityId());   }

	public HAPDomainEntityDefinitionGlobal getDefinitionDomain() {		return this.m_definitionDomain; 	}
	
	public HAPDomainEntityExecutableResourceComplex getExecutableDomain() {    return this.m_executableDomain;     }
	
	public HAPDomainValueStructure getValueStructureDomain() {    return this.m_executableDomain.getValueStructureDomain();    }
	
	public HAPDomainAttachment getAttachmentDomain() {   return this.m_attachmentDomain;    }
	
	public HAPIdEntityInDomain getExecutableEntityIdByDefinitionEntityId(HAPIdEntityInDomain defEntityId) {   return this.m_executableComplexEntityIdByDefinitionComplexEntityId.get(defEntityId);  	}
	public HAPIdEntityInDomain getDefinitionEntityIdByExecutableEntityId(HAPIdEntityInDomain defEntityId) {   return this.m_definitionComplexEntityIdByExecutableComplexEntityId.get(defEntityId);  	}
	
	public void addComplexResourceDependency(HAPResourceIdSimple resourceId) {   this.m_complexResourceDependency.add(resourceId);    }
	public Set<HAPResourceIdSimple> getComplexResourceDependency(){    return this.m_complexResourceDependency;     }
	
	public HAPInfoEntityInDomainExecutable getEntityInfoExecutable(HAPInfoResourceIdNormalize normalizedResourceInfo) {
		HAPIdEntityInDomain entityId = this.m_definitionDomain.getResourceDomainBySimpleResourceId(normalizedResourceInfo.getRootResourceIdSimple()).getRootEntityId();
		HAPIdEntityInDomain outEntityDefId = HAPUtilityDomain.getEntityDescent(entityId, normalizedResourceInfo.getPath(), this.m_definitionDomain);
		HAPIdEntityInDomain outEntityExeId = this.getExecutableEntityIdByDefinitionEntityId(outEntityDefId);
		return this.m_executableDomain.getEntityInfoExecutable(outEntityExeId);
	}

	public HAPIdEntityInDomain addExecutableEntity(HAPIdEntityInDomain definitionEntityId, HAPExecutableEntityComplex executableEntity, HAPExtraInfoEntityInDomainExecutable extraInfo) {
		HAPIdEntityInDomain out = this.m_executableDomain.addExecutableEntity(executableEntity, extraInfo);
		this.m_executableComplexEntityIdByDefinitionComplexEntityId.put(extraInfo.getEntityDefinitionId(), out);
		this.m_definitionComplexEntityIdByExecutableComplexEntityId.put(out, extraInfo.getEntityDefinitionId());
		return out;
	}

	public HAPIdEntityInDomain addExecutableEntity(HAPIdEntityInDomain definitionEntityId, HAPIdComplexEntityInGlobal complexEntityIdInGloabal, HAPExtraInfoEntityInDomainExecutable extraInfo) {
		HAPIdEntityInDomain out = this.m_executableDomain.addExecutableEntity(complexEntityIdInGloabal, extraInfo);
		this.m_executableComplexEntityIdByDefinitionComplexEntityId.put(extraInfo.getEntityDefinitionId(), out);
		this.m_definitionComplexEntityIdByExecutableComplexEntityId.put(out, extraInfo.getEntityDefinitionId());
		return out;
	}

}
