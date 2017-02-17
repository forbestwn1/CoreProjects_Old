package com.nosliw.common.strvalue.mode;

import com.nosliw.common.strvalue.HAPStringableValueEntity;

public class HAPStringableValueMode extends HAPStringableValueEntity{

	public final static String CATEGARY = "categary";

	
	public void setTmeplateCategary(String categary){
		this.updateAtomicChildStrValue(CATEGARY, categary);
	}
	
	public String getTemplateCategary(){
		return this.getAtomicAncestorValueString(CATEGARY);
	}
	
}
