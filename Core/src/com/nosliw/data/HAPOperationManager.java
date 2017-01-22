package com.nosliw.data;

public interface HAPOperationManager {

	public String getLanguage();
	
	public HAPOperationResource getDataOperationResource(HAPDataTypeInfo dataTypeInfo, HAPOperationInfo dataOpInfo);

	public HAPOperationResource getExpressionExecuteResource(HAPExpression expression);

	public HAPOperationResource prepareResources(HAPOperationResource resources);

}