package com.nosliw.data.core.expression;

import com.nosliw.common.updatename.HAPUpdateName;
import com.nosliw.common.updatename.HAPUpdateNamePrefix;
import com.nosliw.data.core.component.HAPUtilityComponent;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextStructure;

public class HAPUtilityExpression {

	public static HAPContextStructure getContext(Object expressionGroupDef, HAPContext extraContext, HAPRuntimeEnvironment runtimeEnv) {
		return HAPUtilityComponent.getContext(expressionGroupDef, extraContext, HAPUtilityExpressionProcessConfigure.getContextProcessConfigurationForExpression(), runtimeEnv);
	}
	
	//make global name
	public static HAPUpdateName getUpdateNameGlobal(HAPExecutableExpressionGroup expression) {
		return new HAPUpdateNamePrefix(expression.getId()+"_");
	}
	
	//get local name according to global name
	public static String getLocalName(HAPExecutableExpressionGroup expression, String globalName) {
		return globalName.substring((expression.getId()+"_").length());
	}

}
