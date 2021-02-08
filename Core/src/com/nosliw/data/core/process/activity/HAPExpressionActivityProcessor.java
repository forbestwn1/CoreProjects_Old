package com.nosliw.data.core.process.activity;

import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.data.variable.HAPVariableDataInfo;
import com.nosliw.data.core.data.variable.HAPVariableInfo;
import com.nosliw.data.core.expression.HAPExecutableExpression;
import com.nosliw.data.core.expression.HAPUtilityExpressionProcessConfigure;
import com.nosliw.data.core.process.HAPBuilderResultContext;
import com.nosliw.data.core.process.HAPContextProcessor;
import com.nosliw.data.core.process.HAPDefinitionActivity;
import com.nosliw.data.core.process.HAPExecutableActivity;
import com.nosliw.data.core.process.HAPExecutableActivityNormal;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPExecutableResultActivityNormal;
import com.nosliw.data.core.process.HAPManagerProcess;
import com.nosliw.data.core.process.HAPProcessorActivity;
import com.nosliw.data.core.process.HAPUtilityProcess;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafData;
import com.nosliw.data.core.script.context.HAPContextDefinitionLeafValue;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextStructure;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociation;
import com.nosliw.data.core.script.expression.HAPExecutableScriptEntity;
import com.nosliw.data.core.script.expression.HAPExecutableScriptGroup;
import com.nosliw.data.core.script.expression.HAPProcessorScript;
import com.nosliw.data.core.script.expression.HAPUtilityScriptExpression;
import com.nosliw.data.core.service.use.HAPDefinitionServiceProvider;

public class HAPExpressionActivityProcessor implements HAPProcessorActivity{

	private HAPBuilderResultContext m_resultContextBuilder = new HAPBuilderResultContext1(); 
	 
	@Override
	public HAPExecutableActivity process(
			HAPDefinitionActivity activityDefinition, 
			String id, 
			HAPContextProcessor processContext,
			HAPExecutableProcess processExe,
			HAPContextGroup processDataContext, 
			Map<String, HAPExecutableDataAssociation> processResults,
			Map<String, HAPDefinitionServiceProvider> serviceProviders,
			HAPManagerProcess processManager,
			HAPRuntimeEnvironment runtimeEnv,
			HAPConfigureContextProcessor configure, 
			HAPProcessTracker processTracker) {
		 
		HAPExpressionActivityDefinition definition = (HAPExpressionActivityDefinition)activityDefinition;
		HAPExpressionActivityExecutable out = new HAPExpressionActivityExecutable(id, definition);
		
		//process input and create flat input context for activity
		HAPUtilityProcess.processNormalActivityInputDataAssocation(out, definition, processDataContext, runtimeEnv);
		HAPContext activityContext = (HAPContext)out.getInputDataAssociation().getOutput().getOutputStructure(); 
		
		//process script expression defined in activity
		HAPUtilityProcess.buildScriptExpressionProcessContext(activityContext, out.getScriptExpressionProcessContext());
//		HAPScriptExpression scriptExpression = HAPProcessorScript.processScriptExpression(definition.getScript(), out.getScriptExpressionProcessContext(), HAPUtilityExpressionProcessConfigure.setDoDiscovery(null), runtimeEnv.getExpressionManager(), runtimeEnv.getRuntime());
		HAPExecutableScriptGroup scriptExpressionGroup = HAPProcessorScript.processSimpleScript(definition.getScript().getScript().getScript(), definition.getScript().getScript().getType(), activityContext, null, runtimeEnv.getExpressionManager(), HAPUtilityExpressionProcessConfigure.setDoDiscovery(null), runtimeEnv, processTracker);
		out.setScriptExpression(scriptExpressionGroup);

		//merge discovered variable in activity back to process variable
		Set<HAPVariableInfo> affectedActivityVariablesInfo = HAPUtilityScriptExpression.getDataVariables(out.getScriptExpression(), null); 
//		HAPUtilityProcess.mergeDataVariableInActivityToProcessContext(affectedActivityVariablesInfo, activityContext, processDataContext);

		//process success result
		HAPExecutableResultActivityNormal successResultExe = HAPUtilityProcess.processNormalActivityResult(out, definition, HAPConstantShared.ACTIVITY_RESULT_SUCCESS, processDataContext, m_resultContextBuilder, runtimeEnv);
		out.addResult(HAPConstantShared.ACTIVITY_RESULT_SUCCESS, successResultExe);
		
		return out;
	}

	class HAPBuilderResultContext1 implements HAPBuilderResultContext {
		@Override
		public HAPContextStructure buildResultContext(String resultName, HAPExecutableActivityNormal activity) {
			HAPContext out = new HAPContext();
			if(HAPConstantShared.ACTIVITY_RESULT_SUCCESS.equals(resultName)) {
				String outputVar = HAPConstantShared.ACTIVITY_OUTPUTVARIABLE_OUTPUT;
				HAPExpressionActivityExecutable expressionActExt = (HAPExpressionActivityExecutable)activity;
				HAPExecutableScriptGroup scriptExpressionGroup = expressionActExt.getScriptExpression();
				HAPExecutableScriptEntity scriptExpression = scriptExpressionGroup.getScript(null);
				if(scriptExpression.isDataExpression()) {
					//if script expression is data expression only, then affect result
					HAPExecutableExpression expExe = scriptExpressionGroup.getExpression().getExpressionItems().values().iterator().next();
					HAPDataTypeCriteria outputCriteria = expExe.getOperand().getOperand().getOutputCriteria();
					out.addElement(HAPUtilityProcess.buildOutputVarialbeName(outputVar), new HAPContextDefinitionLeafData(new HAPVariableDataInfo(outputCriteria)));
				}
				else {
					out.addElement(HAPUtilityProcess.buildOutputVarialbeName(outputVar), new HAPContextDefinitionLeafValue());
				}
			}
			return out;
		}
	}
}
