package com.nosliw.data.core.process.plugin;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPScript;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.process.HAPContextProcessor;
import com.nosliw.data.core.process.HAPDefinitionActivity;
import com.nosliw.data.core.process.HAPExecutableActivity;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPManagerProcessDefinition;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.service.use.HAPDefinitionServiceProvider;

//each type of activity should provide a plugin which contains information:
//   how to parse activity definition
//	 how to process definition to executable
//   script for the runtime
@HAPEntityWithAttribute
public interface HAPPluginActivity {

	@HAPAttribute
	public static String TYPE = "type";
	
	@HAPAttribute
	public static String SCRIPT = "script";
	
	String getType();
	
	//process activity definition to executable
	HAPExecutableActivity process(
			HAPDefinitionActivity activityDefinition,
			String id,
			HAPContextProcessor processContext,
			HAPExecutableProcess processExe,
			HAPContextGroup context,
			Map<String, HAPExecutableDataAssociation> results,
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPManagerProcessDefinition processManager,
			HAPRequirementContextProcessor contextProcessRequirement,
			HAPConfigureContextProcessor configure, 
			HAPProcessTracker processTracker
	);
	
	HAPDefinitionActivity buildActivityDefinition(Object obj);
	
	HAPScript getScript(String env);
}
