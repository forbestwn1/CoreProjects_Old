package com.nosliw.data.core.complex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.data.core.component.HAPContextProcessor;
import com.nosliw.data.core.domain.HAPContainerEntity;
import com.nosliw.data.core.domain.HAPContextDomain;
import com.nosliw.data.core.domain.HAPDomainAttachment;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.domain.HAPDomainExecutableEntity;
import com.nosliw.data.core.domain.HAPDomainValueStructure;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.domain.HAPInfoContainerElement;
import com.nosliw.data.core.domain.HAPInfoDefinitionEntityInDomain;
import com.nosliw.data.core.domain.entity.attachment.HAPDefinitionEntityContainerAttachment;
import com.nosliw.data.core.domain.entity.valuestructure.HAPDefinitionEntityComplexValueStructure;
import com.nosliw.data.core.domain.entity.valuestructure.HAPInfoPartSimple;
import com.nosliw.data.core.domain.entity.valuestructure.HAPProcessorValueStructureInComponent;
import com.nosliw.data.core.domain.entity.valuestructure.HAPUtilityComplexValueStructure;
import com.nosliw.data.core.domain.entity.valuestructure.HAPValueStructureInComplex;
import com.nosliw.data.core.resource.HAPResourceDefinition;

public class HAPManagerComplexEntity {

	private Map<String, HAPPluginComplexEntityProcessor> m_processorPlugins;

	public HAPManagerComplexEntity() {
		this.m_processorPlugins = new LinkedHashMap<String, HAPPluginComplexEntityProcessor>();
	}

	private void expandResourceReference(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
		HAPDomainValueStructure valueStructureDomain = exeDomain.getValueStructureDomain();
		HAPDomainAttachment attachmentDomain = exeDomain.getAttachmentDomain();
		
		//create executable and add to domain
		HAPInfoDefinitionEntityInDomain entityDefInfo = defDomain.getEntityInfo(complexEntityDefinitionId);
		HAPDefinitionEntityComplex complexEntityDef = (HAPDefinitionEntityComplex)entityDefInfo.getEntity();
		String entityType = complexEntityDef.getEntityType();
		HAPExecutableEntityComplex exeEntity = this.m_processorPlugins.get(entityType).newExecutable();
		HAPIdEntityInDomain complexeEntityExeId = domainContext.addExecutableEntity(exeEntity, complexEntityDefinitionId);
		HAPExecutableEntityComplex complexEntityExe = exeDomain.getExecutableEntity(complexeEntityExeId);

		Map<String, HAPIdEntityInDomain> simpleAttributes = complexEntityDef.getSimpleAttributes();
		for(String attrName : simpleAttributes.keySet()) {
			HAPIdEntityInDomain attrEntityDefId = simpleAttributes.get(attrName);
			HAPInfoDefinitionEntityInDomain entityInfo = defDomain.getEntityInfo(attrEntityDefId);
			if(entityInfo.getEntity()==null && entityInfo.getAttachmentReference()==null && entityInfo.getResourceId()!=null) {
				HAPResourceDefinition resourceDef = processContext.getRuntimeEnvironment().getResourceDefinitionManager().getResourceDefinition(entityInfo.getResourceId(), processContext.getDomainContext().getDefinitionDomain(), processContext.getLocalReferenceBase());
				
			}
		}
		
	}

	
	public HAPIdEntityInDomain process(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
		
		//expand resource reference
		
		
		//build executable complexe entity
		HAPIdEntityInDomain rootId = defDomain.getRootComplexEntity();
		buildExecutableTree(rootId, processContext);
		
		//process attachment
		processAttachment(rootId, processContext);

		//expand attachment reference
		
		//expand reference in value strucutre
		expandValueStructure(rootId, processContext);

		//process value structure
		processValueStructureTree(rootId, processContext);
		
		HAPPluginComplexEntityProcessor processPlugin = this.m_processorPlugins.get(complexEntityDefinitionId.getEntityType());
		processPlugin.process(complexEntityDefinitionId, processContext);
		return domainContext.getExecutableIdByDefinitionId(complexEntityDefinitionId);
	}

	
	private HAPIdEntityInDomain buildExecutableTree(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPContextDomain domainContext = processContext.getDomainContext();
		HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
		HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
		HAPDomainValueStructure valueStructureDomain = exeDomain.getValueStructureDomain();
		HAPDomainAttachment attachmentDomain = exeDomain.getAttachmentDomain();
		
		//create executable and add to domain
		HAPInfoDefinitionEntityInDomain entityDefInfo = defDomain.getEntityInfo(complexEntityDefinitionId);
		HAPDefinitionEntityComplex complexEntityDef = (HAPDefinitionEntityComplex)entityDefInfo.getEntity();
		String entityType = complexEntityDef.getEntityType();
		HAPExecutableEntityComplex exeEntity = this.m_processorPlugins.get(entityType).newExecutable();
		HAPIdEntityInDomain complexeEntityExeId = domainContext.addExecutableEntity(exeEntity, complexEntityDefinitionId);
		HAPExecutableEntityComplex complexEntityExe = exeDomain.getExecutableEntity(complexeEntityExeId);
		
		//add value structure to domain
//		String valueStructureComplexId = valueStructureDomain.addValueStructureComplex((HAPDefinitionEntityComplexValueStructure)defDomain.getEntityInfo(complexEntityDef.getValueStructureComplexId()).getEntity());
//		complexEntityExe.setValueStructureComplexId(valueStructureComplexId);
		
		
		//build executable for simple complex attribute
		Map<String, HAPIdEntityInDomain> simpleAttributes = complexEntityDef.getSimpleAttributes();
		for(String attrName : simpleAttributes.keySet()) {
			HAPIdEntityInDomain attrEntityDefId = simpleAttributes.get(attrName);
			HAPInfoDefinitionEntityInDomain entityInfo = defDomain.getEntityInfo(attrEntityDefId);
			if(entityInfo.isComplexEntity()) {
				HAPIdEntityInDomain attrEntityExeId = buildExecutableTree(attrEntityDefId, processContext);
				complexEntityExe.setSimpleComplexAttribute(attrName, attrEntityExeId);
			}
		}
		
		//build executable for container complex attribute
		Map<String, HAPContainerEntity> containerAttributes = complexEntityDef.getContainerAttributes();
		for(String attrName : containerAttributes.keySet()) {
			HAPContainerEntity container = containerAttributes.get(attrName);
			List<HAPInfoContainerElement> eleInfos = container.getAllElementsInfo();
			for(HAPInfoContainerElement eleInfo : eleInfos) {
				HAPIdEntityInDomain eleId = eleInfo.getElementEntityId();
				HAPInfoDefinitionEntityInDomain eleEntityInfo = defDomain.getEntityInfo(eleId);
				if(eleEntityInfo.isComplexEntity()) {
					HAPIdEntityInDomain eleEntityExeId = buildExecutableTree(eleId, processContext);
					HAPInfoContainerElement exeEleInfo = eleInfo.cloneContainerElementInfo();
					exeEleInfo.setElementEntityId(eleEntityExeId);
					complexEntityExe.addContainerAttributeElementComplex(attrName, exeEleInfo);
				}
			}
		}
		
		return complexeEntityExeId;
	}
	
	private void processAttachment(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {

		//add attachment container to attachment domain
		HAPUtilityComplexEntity.traversComplexEntityTree(complexEntityDefinitionId, new HAPProcessorComplexEntity() {
			@Override
			public void process(HAPInfoDefinitionEntityInDomain complexEntityDefInfo, HAPInfoDefinitionEntityInDomain parentComplexEntityDefInfo,
					HAPContextProcessor processContext) {
				if(parentComplexEntityDefInfo!=null) {
					HAPContextDomain domainContext = processContext.getDomainContext();
					HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
					HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
					HAPDomainAttachment attachmentDomain = exeDomain.getAttachmentDomain();

					HAPInfoDefinitionEntityInDomain entityDefInfo = defDomain.getEntityInfo(complexEntityDefinitionId);
					HAPDefinitionEntityComplex complexEntityDef = (HAPDefinitionEntityComplex)entityDefInfo.getEntity();

					HAPIdEntityInDomain childComplexEntityExeId = domainContext.getExecutableIdByDefinitionId(complexEntityDefInfo.getEntityId());
					
					attachmentDomain.addAttachmentContainer((HAPDefinitionEntityContainerAttachment)defDomain.getEntityInfo(complexEntityDef.getAttachmentContainerId()).getEntity(), childComplexEntityExeId);
				}

			}}, processContext);

		//process 
		HAPUtilityComplexEntity.traversComplexEntityTree(complexEntityDefinitionId, new HAPProcessorComplexEntity() {
			@Override
			public void process(HAPInfoDefinitionEntityInDomain complexEntityDefInfo, HAPInfoDefinitionEntityInDomain parentComplexEntityDefInfo,
					HAPContextProcessor processContext) {
				if(parentComplexEntityDefInfo!=null) {
					HAPContextDomain domainContext = processContext.getDomainContext();
					HAPDomainDefinitionEntity defDomain = domainContext.getDefinitionDomain();
					HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
					HAPDomainAttachment attachmentDomain = exeDomain.getAttachmentDomain();

					HAPInfoDefinitionEntityInDomain entityDefInfo = defDomain.getEntityInfo(complexEntityDefinitionId);
					HAPDefinitionEntityComplex complexEntityDef = (HAPDefinitionEntityComplex)entityDefInfo.getEntity();
					HAPIdEntityInDomain complexEntityExeId = domainContext.getExecutableIdByDefinitionId(complexEntityDefinitionId);
					HAPDefinitionEntityContainerAttachment parentAttachmentContainer =  attachmentDomain.getAttachmentContainer(complexEntityExeId);

					HAPIdEntityInDomain childComplexEntityExeId = domainContext.getExecutableIdByDefinitionId(complexEntityDefInfo.getEntityId());
					HAPDefinitionEntityContainerAttachment childAttachmentContainer =  attachmentDomain.getAttachmentContainer(childComplexEntityExeId);
					
					childAttachmentContainer.merge(parentAttachmentContainer, defDomain.getParentInfo(complexEntityDefInfo.getEntityId()).getParentRelationConfigure().getAttachmentRelationMode());
				}

			}}, processContext);
	}
	
	private void expandValueStructure(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPUtilityComplexEntity.traversComplexEntityTree(complexEntityDefinitionId, new HAPProcessorComplexEntity() {
			@Override
			public void process(HAPConfigureEntityInDomainComplex complexEntityInfo, HAPConfigureEntityInDomainComplex parentComplexEntityInfo,
					HAPContextProcessor processContext) {
				if(parentComplexEntityInfo!=null) {
					HAPContextDomain domainContext = processContext.getDomainContext();
					HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();
					HAPDomainValueStructure valueStructureDomain = exeDomain.getValueStructureDomain();

					HAPIdEntityInDomain complexEntityExeId = domainContext.getExecutableIdByDefinitionId(complexEntityDefinitionId);
					HAPExecutableEntityComplex complexEntityExe = domainContext.getExecutableEntityByExecutableId(complexEntityExeId);

					//expand reference
					HAPDefinitionEntityComplexValueStructure valueStructureComplex = valueStructureDomain.getValueStructureComplex(complexEntityExe.getValueStructureComplexId());
					List<HAPInfoPartSimple> simpleValueStructureParts = HAPUtilityComplexValueStructure.getAllSimpleParts(valueStructureComplex);
					for(HAPInfoPartSimple simpleValueStructurePart : simpleValueStructureParts) {
						HAPProcessorValueStructureInComponent.expandReference((HAPValueStructureInComplex)simpleValueStructurePart.getSimpleValueStructurePart().getValueStructure(), processContext);
					}
				}

			}}, processContext);
	}
	
	private void processValueStructureTree(HAPIdEntityInDomain complexEntityDefinitionId, HAPContextProcessor processContext) {
		HAPUtilityComplexEntity.traversComplexEntityTree(complexEntityDefinitionId, new HAPProcessorComplexEntity() {
			@Override
			public void process(HAPConfigureEntityInDomainComplex complexEntityInfo, HAPConfigureEntityInDomainComplex parentComplexEntityInfo,
					HAPContextProcessor processContext) {
				if(parentComplexEntityInfo!=null) {
					HAPContextDomain domainContext = processContext.getDomainContext();
					HAPDomainExecutableEntity exeDomain = domainContext.getExecutableDomain();

					HAPIdEntityInDomain complexEntityExeId = domainContext.getExecutableIdByDefinitionId(complexEntityDefinitionId);
					HAPExecutableEntityComplex complexEntityExe = domainContext.getExecutableEntityByExecutableId(complexEntityExeId);

					HAPIdEntityInDomain parentComplexEntityExeId = domainContext.getExecutableIdByDefinitionId(parentComplexEntityInfo.getEntityId());
					HAPExecutableEntityComplex parentComplexEntityExe = domainContext.getExecutableEntityByExecutableId(parentComplexEntityExeId);

					//process inheritance
					HAPUtilityComplexValueStructure.processValueStructureInheritance(complexEntityExe.getValueStructureComplexId(), parentComplexEntityExe.getValueStructureComplexId(), parentComplexEntityInfo.getParentRelationConfigure().getValueStructureRelationMode(), exeDomain.getValueStructureDomain());
				}

			}}, processContext);
	}
	
	
	public void registerProcessorPlugin(HAPPluginComplexEntityProcessor processorPlugin) {
		this.m_processorPlugins.put(processorPlugin.getEntityType(), processorPlugin);
	}
	
}