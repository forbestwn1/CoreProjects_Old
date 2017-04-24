package com.nosliw.data.core.imp.expression;

import java.util.Map;

import com.nosliw.common.strvalue.HAPStringableValueEntity;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPInfo;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPExpressionInfo;
import com.nosliw.data.core.expression.HAPReferenceInfo;

public class HAPExpressionInfoImp extends HAPStringableValueEntity implements HAPExpressionInfo{

	public static String _VALUEINFO_NAME;
	
	public HAPExpressionInfoImp(){	}
	
	@Override
	public String getName() {  return this.getAtomicAncestorValueString(NAME);	}
	public void setName(String name){  this.updateAtomicChildStrValue(NAME, name);  }
	
	@Override
	public String getExpression(){ return this.getAtomicAncestorValueString(EXPRESSION); }

	@Override
	public HAPInfo getInfo() {	return (HAPInfo)this.getEntityAncestorByPath(INFO); }

	@Override
	public Map<String, HAPData> getConstants(){return this.getMapAncestorByPath(CONSTANTS).getMapValue();}

	@Override
	public Map<String, HAPDataTypeCriteria> getVariables() {  return this.getMapAncestorByPath(VARIABLES).getMapValue(); }

	@Override
	public Map<String, HAPReferenceInfo> getReferences() {  return this.getMapAncestorByPath(REFERENCES).getMapValue();  }
}