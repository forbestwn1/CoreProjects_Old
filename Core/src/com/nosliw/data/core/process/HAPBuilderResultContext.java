package com.nosliw.data.core.process;

import com.nosliw.data.core.valuestructure.HAPValueStructureDefinition;

public interface HAPBuilderResultContext {

	//it is for activity plugin to build context for particular result defined in activity
	HAPValueStructureDefinition buildResultContext(String result, HAPExecutableActivityNormal activity);

}
