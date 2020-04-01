package com.nosliw.data.core.runtime;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.expression.HAPExecutableExpression;

@HAPEntityWithAttribute
public abstract class HAPRuntimeTaskExecuteExpression extends HAPRuntimeTask{

	final public static String TASK = "ExecuteExpression"; 
	
	@HAPAttribute
	public static String EXPRESSION = "expression";

	@HAPAttribute
	public static String ITEMNAME = "itemName";

	@HAPAttribute
	public static String VARIABLESVALUE = "variablesValue";

	
	private HAPExecutableExpression m_expression;
	
	private String m_itemName;
	
	private Map<String, HAPData> m_variablesValue;

	private Map<String, HAPData> m_referencesValue;
	
	public HAPRuntimeTaskExecuteExpression(HAPExecutableExpression expression, String itemName, Map<String, HAPData> variablesValue, Map<String, HAPData> referencesValue){
		this.m_expression = expression;
		this.m_itemName = itemName;
		if(HAPBasicUtility.isStringEmpty(this.m_itemName))   this.m_itemName = HAPConstant.NAME_DEFAULT;
		this.m_variablesValue = variablesValue; 
		this.m_referencesValue = referencesValue;
	}
	
	public HAPExecutableExpression getExpression(){return this.m_expression;}
	
	public String getItemName() {    return this.m_itemName;   }
	
	public Map<String, HAPData> getVariablesValue(){  return this.m_variablesValue;  }

	public Map<String, HAPData> getReferencesValue(){  return this.m_referencesValue;  }
	
	public HAPData getExpressionDataResult(){ return (HAPData)this.getResult(); }

	@Override
	public String getTaskType(){  return TASK; }

}
