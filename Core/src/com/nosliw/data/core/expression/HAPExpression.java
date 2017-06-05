package com.nosliw.data.core.expression;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;

/**
 * Expression object we get after processing HAPExpressionDefinition
 *  
 */
@HAPEntityWithAttribute(baseName="EXPRESSION")
public interface HAPExpression {

	@HAPAttribute
	public static String ID = "id";
	
	@HAPAttribute
	public static String EXPRESSIONDEFINITION = "expressionDefinition";

	@HAPAttribute
	public static String OPERAND = "operand";
	
	@HAPAttribute
	public static String VARIABLES = "variables";
	
	@HAPAttribute
	public static String ERRORMSGS = "errorMsgs";

	@HAPAttribute
	public static String CONVERTERS = "converters";

	@HAPAttribute
	public static String REFERENCES = "references";
	
	//during runtime, every expression has a unique id
	String getId();
	void setId(String id);
	
	//ExpressionDefinition used to define expression
	HAPExpressionDefinition getExpressionDefinition();
	
	//Operand to represent the expression
	HAPOperand getOperand();
	
	//Variables infos
	Map<String, HAPVariableInfo> getVariables();

	//value for each variable, this converter help to convert to internal variable 
	Map<String, HAPMatchers> getVariableMatchers();
	
	//all the referenced expression, it may referenced by referenced in expression
	Map<String, HAPExpression> getReferences();
	
	//error message used to indicate whether the expression is successfully processed
	String[] getErrorMessages();

	//discover variable infor in expression:
	//1 discover internal variable
	//2 discover expect variable
	//3 build converters between expect variable to internal variable
	HAPMatchers discover(Map<String, HAPVariableInfo> expectVariablesInfo, HAPDataTypeCriteria expectCriteria, HAPProcessVariablesContext context,	HAPDataTypeHelper dataTypeHelper);
	
}
