package com.nosliw.data.core.script.context.dataassociation.none;

import java.util.Map;

import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.script.context.HAPParentContext;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociationImp;
import com.nosliw.data.core.script.context.dataassociation.HAPOutputStructure;

public class HAPExecutableDataAssociationNone extends HAPExecutableDataAssociationImp{

	public HAPExecutableDataAssociationNone() {}

	public HAPExecutableDataAssociationNone(HAPDefinitionDataAssociationNone definition, HAPParentContext input) {
		super(definition, input);
	}
	
	@Override
	public HAPParentContext getInput() {	return new HAPParentContext();	}
	
	@Override
	public HAPOutputStructure getOutput() {
		HAPOutputStructure out = new HAPOutputStructure();
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

	@Override
	protected void buildResourceJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPRuntimeInfo runtimeInfo) {
		super.buildResourceJsonMap(jsonMap, typeJsonMap, runtimeInfo);
	}
}
