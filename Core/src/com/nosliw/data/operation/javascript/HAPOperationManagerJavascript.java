package com.nosliw.data.operation.javascript;

import com.nosliw.data.HAPOperationInfo;
import com.nosliw.data.HAPOperationResource;
import com.nosliw.data.HAPExpression;
import com.nosliw.data.HAPOperationManager;

public class HAPOperationManagerJavascript implements HAPOperationManager{

	@Override
	public String getLanguage() {
		return null;
	}

	@Override
	public HAPOperationResource getDataOperationResource(HAPOperationInfo dataOpInfo) {
		return null;
	}

	@Override
	public HAPOperationResource getExpressionExecuteResource(HAPExpression expression) {
		return null;
	}

	@Override
	public HAPOperationResource prepareResources(HAPOperationResource resources) {
		return null;
	}

}