package com.nosliw.test.uisource;

import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.imp.runtime.js.rhino.HAPRuntimeEnvironmentImpRhino;
import com.nosliw.uiresource.HAPUIResource;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.definition.HAPUIDefinitionUnitResource;

public class HAPUIResourceTest {

	public static void main(String[] agrs){

		//module init
		HAPRuntimeEnvironmentImpRhino runtimeEnvironment = new HAPRuntimeEnvironmentImpRhino();
		
		//start runtime
		runtimeEnvironment.getRuntime().start();

		HAPUIResourceManager uiResourceMan = new HAPUIResourceManager(runtimeEnvironment.getExpressionManager(), runtimeEnvironment.getRuntime());

		String file = HAPFileUtility.getFileNameOnClassPath(HAPUIResourceTest.class, "Example1.res");
		HAPUIDefinitionUnitResource uiResource = uiResourceMan.addUIResourceDefinition(file);
		HAPUIResource resource = uiResourceMan.getUIResource("Example1");
		System.out.println(resource);
		
	}
	
}
