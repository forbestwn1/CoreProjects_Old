package com.nosliw.test.domain;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.domain.HAPPackageExecutable;
import com.nosliw.data.core.imp.runtime.js.rhino.HAPRuntimeEnvironmentImpRhino;
import com.nosliw.data.core.resource.HAPResourceIdSimple;

public class HAPTestDomain {

	public static void main(String[] args) {
		HAPRuntimeEnvironmentImpRhino runtimeEnvironment = new HAPRuntimeEnvironmentImpRhino();
//		HAPDomainEntityDefinitionGlobal globalDomain = new HAPDomainEntityDefinitionGlobal(new HAPGeneratorId(), runtimeEnvironment.getDomainEntityManager(), runtimeEnvironment.getResourceDefinitionManager());
		
		HAPResourceIdSimple resourceId = new HAPResourceIdSimple(HAPConstantShared.RUNTIME_RESOURCE_TYPE_TEST_COMPLEX1, "parent");
		
		//definition
//		HAPResourceDefinition resourceDef = runtimeEnvironment.getResourceDefinitionManager().getResourceDefinition(resourceId, globalDomain);
//		String expandedJsonStr = HAPUtilityDomain.getEntityExpandedJsonString(resourceDef.getEntityId(), globalDomain);
//		System.out.println(HAPJsonUtility.formatJson(expandedJsonStr));

		//process
		HAPPackageExecutable executablePackage = runtimeEnvironment.getComplexEntityManager().getExecutablePackage(resourceId);
		
//		System.out.println();
//		System.out.println();
//		System.out.println("*******************************************************************************");
//		System.out.println();
//		System.out.println();
//		System.out.println(HAPJsonUtility.formatJson(executableResult.getDomainContext().getAttachmentDomain().toString()));

//		System.out.println();
//		System.out.println();
//		System.out.println("*******************************************************************************");
//		System.out.println();
//		System.out.println();
//		System.out.println(HAPJsonUtility.formatJson(executableResult.getDomainContext().getExecutableDomain().toString()));
	}
}
