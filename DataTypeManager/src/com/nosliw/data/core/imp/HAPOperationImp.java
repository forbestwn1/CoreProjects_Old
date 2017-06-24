package com.nosliw.data.core.imp;

import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.strvalue.HAPStringableValue;
import com.nosliw.common.strvalue.HAPStringableValueEntityWithID;
import com.nosliw.common.strvalue.HAPStringableValueList;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPOperation;
import com.nosliw.data.core.HAPOperationOutInfo;
import com.nosliw.data.core.HAPOperationParmInfo;

public class HAPOperationImp extends HAPStringableValueEntityWithID implements HAPOperation{

	public static String _VALUEINFO_NAME;

	@HAPAttribute
	public static String DATATYPNAME = "dataTypeName";
	
	public HAPOperationImp(){
		this.updateAtomicChildStrValue(TYPE, HAPConstant.DATAOPERATION_TYPE_NORMAL);
	}
	
	@Override
	public String getName() {		return this.getAtomicAncestorValueString(NAME);	}

	@Override
	public HAPInfo getInfo() {		return (HAPInfo)this.getAtomicAncestorValueObject(INFO, HAPInfo.class);  }

	public HAPDataTypeId getDataTypeName(){ return (HAPDataTypeId)this.getAtomicAncestorValueObject(DATATYPNAME, HAPDataTypeId.class); }
	public void setDataTypeName(HAPDataTypeId dataTypeId){  this.updateAtomicChildObjectValue(DATATYPNAME, dataTypeId);  }
	
	@Override
	public List<HAPOperationParmInfo> getParmsInfo() {
		HAPStringableValueList list = this.getListAncestorByPath(PAMRS);
		return (List<HAPOperationParmInfo>)list.getListValue();
	}
	
	public void addParmsInfo(HAPOperationParmInfo parmInfo){
		HAPStringableValueList list = this.getListAncestorByPath(PAMRS);
		list.addChild((HAPStringableValue)parmInfo);
	}

	@Override
	public HAPOperationOutInfo getOutputInfo() {		return (HAPOperationOutInfo)this.getEntityAncestorByPath(OUTPUT);	}
	public void setOutputInfo(HAPOperationOutInfo outputInfo){	this.updateChild(OUTPUT, (HAPStringableValue)outputInfo);	}

	@Override
	public String getType() {  return this.getAtomicAncestorValueString(TYPE); }
}
