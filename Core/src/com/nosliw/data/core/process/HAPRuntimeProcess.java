package com.nosliw.data.core.process;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.core.resource.HAPResourceManagerRoot;
import com.nosliw.data.core.script.context.data.HAPContextData;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableWrapperTask;

public interface HAPRuntimeProcess {

	void executeProcess(HAPExecutableProcess processExe, HAPContextData input, HAPProcessResultHandler resultHandler, HAPResourceManagerRoot resourceManager);

	void executeEmbededProcess(HAPExecutableWrapperTask<HAPExecutableProcess> processExe, HAPContextData parentContext, HAPProcessResultHandler resultHandler, HAPResourceManagerRoot resourceManager);

	HAPServiceData executeProcess(HAPExecutableProcess process, HAPContextData parentContext, HAPResourceManagerRoot resourceManager);
 
	HAPServiceData executeEmbededProcess(HAPExecutableWrapperTask<HAPExecutableProcess> process, HAPContextData input, HAPResourceManagerRoot resourceManager);
	
}
