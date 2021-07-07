package com.nosliw.test.activity;

import java.io.FileNotFoundException;
import java.util.Map;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.core.activity.resource.HAPResourceDefinitionActivitySuite;
import com.nosliw.data.core.component.attachment.HAPUtilityAttachment;
import com.nosliw.data.core.imp.runtime.js.rhino.HAPRuntimeEnvironmentImpRhino;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.runtime.HAPInfoRuntimeTaskTask;
import com.nosliw.data.core.runtime.js.rhino.task.HAPRuntimeTaskExecuteTaskRhino;
import com.nosliw.data.core.task.HAPExecutableTaskSuite;
import com.nosliw.data.core.task.resource.HAPUtilityResourceTask;
import com.nosliw.data.core.valuestructure.HAPUtilityValueStructure;

public class HAPActivityMain {

	static public void main(String[] args) throws FileNotFoundException {
		String suite = "test";
		String activity = "expression";
		String testData = "testData";
		
		HAPRuntimeEnvironmentImpRhino runtimeEnvironment = new HAPRuntimeEnvironmentImpRhino();

		HAPResourceId resourceId = HAPUtilityResourceTask.buildResourceId(suite);
		HAPExecutableTaskSuite taskSuiteExe = runtimeEnvironment.getTaskManager().getTaskSuite(resourceId);
		
		HAPResourceDefinitionActivitySuite activitySuiteDefinition = (HAPResourceDefinitionActivitySuite)runtimeEnvironment.getResourceDefinitionManager().getResourceDefinition(resourceId);
		
		Map<String, Object> input = HAPUtilityAttachment.getTestValueFromAttachment(activitySuiteDefinition, testData);
		Map<String, Object> inputById = HAPUtilityValueStructure.replaceValueNameWithId(taskSuiteExe.getValueStructureDefinitionWrapper().getValueStructure(), input);

		HAPRuntimeTaskExecuteTaskRhino task = new HAPRuntimeTaskExecuteTaskRhino(new HAPInfoRuntimeTaskTask(taskSuiteExe, activity, input), runtimeEnvironment);
		HAPServiceData out = runtimeEnvironment.getRuntime().executeTaskSync(task);
		
		System.out.println(out);
	}
	
}
