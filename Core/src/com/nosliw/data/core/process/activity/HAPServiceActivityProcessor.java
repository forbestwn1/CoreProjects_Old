package com.nosliw.data.core.process.activity;

import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.process.HAPBuilderResultContext;
import com.nosliw.data.core.process.HAPContextProcessor;
import com.nosliw.data.core.process.HAPDefinitionActivity;
import com.nosliw.data.core.process.HAPExecutableActivity;
import com.nosliw.data.core.process.HAPExecutableActivityNormal;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPExecutableResultActivityNormal;
import com.nosliw.data.core.process.HAPManagerProcessDefinition;
import com.nosliw.data.core.process.HAPProcessorActivity;
import com.nosliw.data.core.process.HAPUtilityProcess;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextStructure;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.service.use.HAPDefinitionServiceProvider;
import com.nosliw.data.core.service.use.HAPDefinitionServiceUse;
import com.nosliw.data.core.service.use.HAPExecutableServiceUse;
import com.nosliw.data.core.service.use.HAPProcessorServiceUse;

public class HAPServiceActivityProcessor implements HAPProcessorActivity{

	@Override
	public HAPExecutableActivity process(
			HAPDefinitionActivity activityDefinition, 
			String id,
			HAPContextProcessor processContext,
			HAPExecutableProcess processExe,
			HAPContextGroup processDataContext,
			Map<String, HAPExecutableDataAssociation> results,
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPManagerProcessDefinition processManager,
			HAPRequirementContextProcessor contextProcessRequirement, 
			HAPConfigureContextProcessor configure, 
			HAPProcessTracker processTracker) {
		HAPServiceActivityDefinition serviceActDef = (HAPServiceActivityDefinition)activityDefinition;
		HAPServiceActivityExecutable out = new HAPServiceActivityExecutable(id, serviceActDef);

		//input
		HAPUtilityProcess.processNormalActivityInputDataAssocation(out, processDataContext, contextProcessRequirement);

		//provider
		HAPDefinitionServiceProvider provider = serviceProviders.get(serviceActDef.getProvider());
		out.setServiceProvider(provider);
		
		//prepare service use def
		HAPDefinitionServiceUse serviceUseDef = new HAPDefinitionServiceUse();
		serviceUseDef.setProvider(serviceActDef.getProvider());
		serviceUseDef.setServiceMapping(serviceActDef.getTaskMapping());
		
		//process service use def
		HAPExecutableServiceUse serviceUseExe = HAPProcessorServiceUse.process(serviceUseDef, provider.getServiceInterface(), processDataContext, configure, contextProcessRequirement);
		out.setService(serviceUseExe);

		//process success result
		HAPBuilderResultContext m_resultContextBuilder = new HAPBuilderResultContext1(processDataContext); 
		HAPExecutableResultActivityNormal successResultExe = HAPUtilityProcess.processNormalActivityResult(out, HAPConstant.ACTIVITY_RESULT_SUCCESS, processDataContext, m_resultContextBuilder, contextProcessRequirement);
		out.addResult(HAPConstant.ACTIVITY_RESULT_SUCCESS, successResultExe);
		
		return out;
	}
	
	class HAPBuilderResultContext1 implements HAPBuilderResultContext {
		
		HAPContextGroup m_processContext;
		
		public HAPBuilderResultContext1(HAPContextGroup processContext) {
			this.m_processContext = processContext;
		}
		
		@Override
		public HAPContextStructure buildResultContext(String resultName, HAPExecutableActivityNormal activity) {
			HAPContextGroup out = new HAPContextGroup();
			if(HAPConstant.ACTIVITY_RESULT_SUCCESS.equals(resultName)) {
				HAPServiceActivityExecutable serviceActivity = (HAPServiceActivityExecutable)activity;
				return serviceActivity.getService().getServiceMapping().getOutputMapping(resultName).getOutput().getOutputStructure();
			}
			return out;
		}
	}
}
