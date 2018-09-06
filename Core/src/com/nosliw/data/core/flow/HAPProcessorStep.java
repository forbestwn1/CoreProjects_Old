package com.nosliw.data.core.flow;

import java.util.Map;

import com.nosliw.common.utils.HAPProcessContext;
import com.nosliw.data.core.HAPData;

public interface HAPProcessorStep {

	/**
	 * Process expression definition
	 * 		parse it to operand
	 * 		solve constant
	 * 		solve reference
	 * 		discovery 
	 * @param id  the id assigned to expression
	 * @param expDef   expression definition need to process
	 * @param contextExpressionDefinitions   other expressions that may need during solving reference
	 * @param variableCriterias   variable criterias that need to respect during discovery
	 * @return
	 */
	HAPExecutableStep process(
			HAPDefinitionStep stepDefinition,
			HAPExecutableTask taskExpressionExe,
			int index, String name,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, 
			Map<String, HAPData> contextConstants,
			HAPProcessContext context
	);

	void postProcess(
			HAPExecutableStep executableStep,
			HAPDefinitionStep stepDefinition,
			HAPExecutableTask taskExpressionExe,
			int index, String name,
			Map<String, HAPDefinitionTask> contextTaskDefinitions, 
			Map<String, HAPData> contextConstants,
			HAPProcessContext context
	);

}