package com.nosliw.data.core.domain.testing.testcomplex1;

import java.util.List;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.complex.HAPPluginComplexEntityProcessorImp;
import com.nosliw.data.core.component.HAPContextProcessor;
import com.nosliw.data.core.domain.HAPDomainEntityDefinitionGlobal;
import com.nosliw.data.core.domain.HAPDomainValueStructure;
import com.nosliw.data.core.domain.HAPEmbededWithExecutable;
import com.nosliw.data.core.domain.HAPEmbededWithId;
import com.nosliw.data.core.domain.HAPExecutableBundle;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.domain.HAPInfoEntityInDomainDefinition;
import com.nosliw.data.core.domain.container.HAPContainerEntity;
import com.nosliw.data.core.domain.container.HAPInfoContainerElementImp;
import com.nosliw.data.core.domain.entity.valuestructure.HAPExecutableEntityComplexValueStructure;
import com.nosliw.data.core.domain.testing.testsimple1.HAPExecutableTestSimple1;
import com.nosliw.data.core.domain.testing.testsimple1.HAPProcessorTestSimple1;

public class HAPPluginComplexEntityProcessorTestComplex1 extends HAPPluginComplexEntityProcessorImp{

	public HAPPluginComplexEntityProcessorTestComplex1() {
		super(HAPExecutableTestComplex1.class);
	}

	@Override
	public void process(HAPIdEntityInDomain complexEntityExecutableId, HAPContextProcessor processContext) {
		
		HAPExecutableBundle currentBundle = processContext.getCurrentBundle();
		HAPDomainEntityDefinitionGlobal definitionDomain = currentBundle.getDefinitionDomain();
		HAPDomainValueStructure valueStructureDomain = currentBundle.getValueStructureDomain();
		
		HAPExecutableTestComplex1 executableEntity = (HAPExecutableTestComplex1)currentBundle.getExecutableDomain().getEntityInfoExecutable(complexEntityExecutableId).getEntity();
		HAPExecutableEntityComplexValueStructure valueStructureComplex = executableEntity.getValueStructureComplex();
		
		HAPIdEntityInDomain complexEntityDefinitionId = currentBundle.getDefinitionEntityIdByExecutableEntityId(complexEntityExecutableId);
		HAPDefinitionEntityTestComplex1 definitionEntity = (HAPDefinitionEntityTestComplex1)definitionDomain.getEntityInfoDefinition(complexEntityDefinitionId).getEntity();
		
		//normal attribute
		Map<String, HAPEmbededWithId> simpleAttrs = definitionEntity.getSimpleAttributes();
		for(String simpleAttrName : simpleAttrs.keySet()) {
			HAPIdEntityInDomain attrEntityId = simpleAttrs.get(simpleAttrName).getEntityId();
			HAPInfoEntityInDomainDefinition attrEntityInfo = definitionDomain.getEntityInfoDefinition(attrEntityId);
			if(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TEST_SIMPLE1.equals(attrEntityInfo.getEntityType())) {
				HAPExecutableTestSimple1 simpleTest1Exe = HAPProcessorTestSimple1.process(attrEntityInfo.getEntityId(), processContext);
				executableEntity.setAttribute( simpleAttrName, simpleTest1Exe);
			}
		}
		
		//container attribute
		Map<String, HAPContainerEntity> containerAttrs = definitionEntity.getContainerAttributes();
		for(String attrName : containerAttrs.keySet()) {
			HAPContainerEntity containerEntityDef = containerAttrs.get(attrName);
			HAPContainerEntity conatinerEntityExe = containerEntityDef.cloneContainerEntity();
			
			List<HAPInfoContainerElementImp> eleInfoExes = conatinerEntityExe.getAllElementsInfo();
			for(HAPInfoContainerElementImp eleInfoExe : eleInfoExes) {
				HAPEmbededWithId embededDef = (HAPEmbededWithId)eleInfoExe.getEmbededElementEntity();
				HAPInfoEntityInDomainDefinition attrEntityInfo = definitionDomain.getEntityInfoDefinition(embededDef.getEntityId());
				if(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TEST_SIMPLE1.equals(attrEntityInfo.getEntityType())) {
					HAPExecutableTestSimple1 simpleTest1Exe = HAPProcessorTestSimple1.process(attrEntityInfo.getEntityId(), processContext);
					HAPEmbededWithExecutable embededExe = new HAPEmbededWithExecutable(simpleTest1Exe);
					eleInfoExe.setEmbededElementEntity(embededExe);
				}
			}
			executableEntity.setAttribute(attrName, conatinerEntityExe);
		}
		
		
		
		
//		String variable = definitionEntity.getVariable();
//		
//		HAPInfoReferenceResolve resolve = HAPUtilityStructureElementReference.resolveElementReference(new HAPReferenceElementInStructureComplex(variable), new HAPCandidatesValueStructureComplex(valueStructureComplex, valueStructureComplex), new HAPConfigureResolveStructureElementReference(), valueStructureDomain);
//		
//		System.out.println(new HAPIdVariable(resolve.structureId, variable).toStringValue(HAPSerializationFormat.JSON));
		
	}

}
