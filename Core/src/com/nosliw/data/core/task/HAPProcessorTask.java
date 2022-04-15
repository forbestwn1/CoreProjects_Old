package com.nosliw.data.core.task;

import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.component.HAPContextProcessor;
import com.nosliw.data.core.domain.entity.valuestructure.HAPWrapperValueStructureDefinition;

public interface HAPProcessorTask {

	/**
	 */
	HAPExecutableTask process(
			HAPDefinitionTask taskDefinition,
			String id,
			HAPContextProcessor processContext,
			HAPWrapperValueStructureDefinition valueStructureWrapper,
			HAPProcessTracker processTracker
	);

}
