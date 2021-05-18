package com.nosliw.data.core.operand;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.data.HAPDataTypeHelper;
import com.nosliw.data.core.data.criteria.HAPCriteriaUtility;
import com.nosliw.data.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.data.criteria.HAPInfoCriteria;
import com.nosliw.data.core.matcher.HAPMatchers;

public class HAPOperandVariable extends HAPOperandImp{
 
	@HAPAttribute
	public final static String VARIABLENAME = "variableName";
	
	@HAPAttribute
	public final static String VARIABLEID = "variableId";
	
	protected String m_variableName;
	
	protected String m_variableId;
	
	private HAPOperandVariable(){}
	
	public HAPOperandVariable(String name){
		super(HAPConstantShared.EXPRESSION_OPERAND_VARIABLE);
		this.m_variableName = name;
	}
	
	public String getVariableName(){  return this.m_variableName;  }
	public void setVariableName(String name){   this.m_variableName = name;  }
	
	public String getVariableId(){  return this.m_variableId;  }
	public void setVariableId(String id){   this.m_variableId = id;  }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VARIABLENAME, m_variableName);
		jsonMap.put(VARIABLEID, m_variableId);
	}

	@Override
	public HAPMatchers discover(
			HAPContainerVariableCriteriaInfo variablesInfo,
			HAPDataTypeCriteria expectCriteria, 
			HAPProcessTracker processTracker,
			HAPDataTypeHelper dataTypeHelper) {
		
		HAPInfoCriteria variableInfo = variablesInfo.getVariableCriteriaInfo(this.getVariableId());
		
//		if(variableInfo==null){
//			//found a new variable
//			variableInfo = HAPInfoCriteria.buildUndefinedCriteriaInfo();
//			variablesInfo.addVariableCriteriaInfo(variableInfo, this.getVariableName());
//		}
		
		HAPMatchers matchers = HAPCriteriaUtility.mergeVariableInfo(variableInfo, expectCriteria, dataTypeHelper);
		
		//set output criteria
		this.setOutputCriteria(variableInfo.getCriteria());

		//cal converter
		return matchers;
	}
	
	@Override
	public HAPOperand cloneOperand() {
		HAPOperandVariable out = new HAPOperandVariable();
		this.cloneTo(out);
		return out;
	}
	
	protected void cloneTo(HAPOperandVariable operand){
		super.cloneTo(operand);
		operand.m_variableName = this.m_variableName;
	}
	
}
