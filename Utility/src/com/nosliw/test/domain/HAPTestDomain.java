package com.nosliw.test.domain;

import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPGeneratorId;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.domain.HAPResultExecutableEntityInDomain;
import com.nosliw.data.core.domain.HAPUtilityDomain;
import com.nosliw.data.core.imp.runtime.js.rhino.HAPRuntimeEnvironmentImpRhino;
import com.nosliw.data.core.resource.HAPResourceDefinition;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.resource.HAPResourceIdSimple;

public class HAPTestDomain {

	public static void main(String[] args) {
		HAPRuntimeEnvironmentImpRhino runtimeEnvironment = new HAPRuntimeEnvironmentImpRhino();
		HAPDomainDefinitionEntity defDomain = new HAPDomainDefinitionEntity(new HAPGeneratorId(), runtimeEnvironment.getDomainEntityManager());

		HAPResourceId valueStructureResourceId = new HAPResourceIdSimple(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TEST_COMPLEX1, "test1"); 

		HAPResourceDefinition resourceDef = runtimeEnvironment.getResourceDefinitionManager().getResourceDefinition(valueStructureResourceId, defDomain);
		String expandedJsonStr = HAPUtilityDomain.getEntityExpandedJsonString(resourceDef.getEntityId(), defDomain);
		System.out.println(HAPJsonUtility.formatJson(expandedJsonStr));

		HAPResultExecutableEntityInDomain executableResult = HAPUtilityDomain.getResourceExecutableComplexEntity(valueStructureResourceId, runtimeEnvironment);
		
	}
}
