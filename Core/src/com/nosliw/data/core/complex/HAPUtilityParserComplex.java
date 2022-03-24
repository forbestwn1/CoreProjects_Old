package com.nosliw.data.core.complex;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.common.HAPWithValueStructure;
import com.nosliw.data.core.component.HAPWithAttachment;
import com.nosliw.data.core.domain.HAPContextParser;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.domain.HAPInfoDefinitionEntityInDomain;
import com.nosliw.data.core.domain.HAPManagerDomainEntityDefinition;
import com.nosliw.data.core.domain.HAPUtilityParserEntity;

public class HAPUtilityParserComplex {

	//parse value structure in complex
	public static void parseValueStructureInComplex(HAPIdEntityInDomain complexEntityId, JSONObject entityJsonObj, HAPDomainDefinitionEntity definitionDomain, HAPManagerDomainEntityDefinition domainEntityManager) {
		HAPInfoDefinitionEntityInDomain complexEntityInfo = definitionDomain.getEntityInfo(complexEntityId);
		
		//parse value structure
		JSONObject valueStructureJsonObj = entityJsonObj.optJSONObject(HAPWithValueStructure.VALUESTRUCTURE);
		HAPIdEntityInDomain valueStructureEntityId = HAPUtilityParserEntity.parseEntity(valueStructureJsonObj, HAPConstantShared.RUNTIME_RESOURCE_TYPE_VALUESTRUCTURECOMPLEX, new HAPContextParser(definitionDomain, complexEntityInfo.getBaseLocationPath()), domainEntityManager);
		
		((HAPDefinitionEntityComplex)complexEntityInfo.getEntity()).setValueStructureComplexId(valueStructureEntityId);
	}
	
	//parse attachment in complex
	public static void parseAttachmentInComplex(HAPIdEntityInDomain complexEntityId, JSONObject entityJsonObj, HAPDomainDefinitionEntity definitionDomain, HAPManagerDomainEntityDefinition domainEntityManager) {
		HAPInfoDefinitionEntityInDomain complexEntityInfo = definitionDomain.getEntityInfo(complexEntityId);
		
		//parse attachment
		JSONObject attachmentJsonObj = entityJsonObj.optJSONObject(HAPWithAttachment.ATTACHMENT);
		HAPIdEntityInDomain attachmentEntityId = HAPUtilityParserEntity.parseEntity(attachmentJsonObj, HAPConstantShared.RUNTIME_RESOURCE_TYPE_ATTACHMENT, new HAPContextParser(definitionDomain, complexEntityInfo.getBaseLocationPath()), domainEntityManager);

		((HAPDefinitionEntityComplex)complexEntityInfo.getEntity()).setAttachmentContainerId(attachmentEntityId);
	}

	//parse content in container entity
	public static void parseContentInComplexContainerEntity(HAPIdEntityInDomain entityId, JSONObject jsonObj, String elementEntityType, HAPConfigureParentRelationComplex parentRelation, HAPContextParser parserContext) {
		HAPDefinitionEntityComplexContainer containerEntity  = (HAPDefinitionEntityComplexContainer)parserContext.getDefinitionDomain().getComplexEntityInfo(entityId).getComplexEntity();
		
		HAPManagerDomainEntityDefinition domainEntityMan = parserContext.getRuntimeEnvironment().getDomainEntityManager();
		
		//parse complex part
		parseContentInComplexEntity(entityId, jsonObj, parserContext);
		
		//parse element
		JSONArray eleArray = jsonObj.getJSONArray(HAPDefinitionEntityComplexContainer.ELEMENT);
		for(int i=0; i<eleArray.length(); i++){
			JSONObject eleObjJson = eleArray.getJSONObject(i);
			HAPIdEntityInDomain eleEntityId = HAPUtilityParserEntity.parseEntity(eleObjJson, elementEntityType, parserContext);
			containerEntity.addEntityElement(eleEntityId);
			
			//build parent relation for complex child
			HAPConfigureEntityInDomainComplex entityInfo = (HAPConfigureEntityInDomainComplex)parserContext.getDefinitionDomain().getEntityInfo(eleEntityId);
			entityInfo.setParentId(entityId);
			entityInfo.setParentRelationConfigure(parentRelation);
		}
	}
	
	
}