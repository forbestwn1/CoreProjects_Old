package com.nosliw.data.core.process.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPScript;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.process.HAPActivityPluginId;
import com.nosliw.data.core.process.HAPExecutableActivityBranch;
import com.nosliw.data.core.process.resource.HAPResourceIdActivityPlugin;
import com.nosliw.data.core.resource.HAPResourceDependency;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPRuntimeJSScriptUtility;
import com.nosliw.data.core.script.expression.HAPProcessContextScriptExpression;
import com.nosliw.data.core.script.expression.HAPScriptExpression;

public class HAPSwitchActivityExecutable extends HAPExecutableActivityBranch{

	@HAPAttribute
	public static String SCRIPTEXPRESSION = "scriptExpression";

	@HAPAttribute
	public static String SCRIPTEXPRESSIONSCRIPT = "scriptExpressionScript";

	private HAPProcessContextScriptExpression m_expressionProcessContext;

	private HAPScriptExpression m_scriptExpression;

	public HAPSwitchActivityExecutable(String id, HAPSwitchActivityDefinition activityDef) {
		super(id, activityDef);
		this.m_expressionProcessContext = new HAPProcessContextScriptExpression();
	}

	public HAPProcessContextScriptExpression getScriptExpressionProcessContext() {
		return this.m_expressionProcessContext;
	}
	
	public void setScriptExpression(HAPScriptExpression scriptExpression) {    this.m_scriptExpression = scriptExpression;    }
	public HAPScriptExpression getScriptExpression() {   return this.m_scriptExpression;  }
	
	public HAPSwitchActivityDefinition getSwitchActivityDefinition() {   return (HAPSwitchActivityDefinition)this.getActivityDefinition();   }

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		List<HAPResourceDependency> out = new ArrayList<HAPResourceDependency>();
		out.add(new HAPResourceDependency(new HAPResourceIdActivityPlugin(new HAPActivityPluginId(HAPConstant.ACTIVITY_TYPE_SWITCH))));
		out.addAll(this.m_scriptExpression.getResourceDependency(runtimeInfo));
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(SCRIPTEXPRESSION, this.m_scriptExpression.toStringValue(HAPSerializationFormat.JSON));
	}
	
	@Override
	protected void buildResourceJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPRuntimeInfo runtimeInfo) {
		super.buildResourceJsonMap(jsonMap, typeJsonMap, runtimeInfo);
		jsonMap.put(SCRIPTEXPRESSIONSCRIPT, HAPRuntimeJSScriptUtility.buildScriptExpressionJSFunction(this.m_scriptExpression));
		typeJsonMap.put(SCRIPTEXPRESSIONSCRIPT, HAPScript.class);
	}	
}