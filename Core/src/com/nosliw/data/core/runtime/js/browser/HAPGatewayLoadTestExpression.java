package com.nosliw.data.core.runtime.js.browser;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.criteria.HAPDataTypeCriteriaWrapperLiterate;
import com.nosliw.data.core.expression.HAPExpression;
import com.nosliw.data.core.expression.HAPExpressionDefinitionSuiteForTest;
import com.nosliw.data.core.expression.HAPExpressionManager;
import com.nosliw.data.core.runtime.js.HAPGatewayImp;

@HAPEntityWithAttribute
public class HAPGatewayLoadTestExpression extends HAPGatewayImp{

	@HAPAttribute
	public static final String COMMAND_LOADTESTEXPRESSION = "loadTestExpression";
	@HAPAttribute
	final public static String COMMAND_LOADTESTEXPRESSION_ELEMENT_SUITE = "suite";
	@HAPAttribute
	final public static String COMMAND_LOADTESTEXPRESSION_ELEMENT_EXPRESSIONNAME = "expressionName";
	@HAPAttribute
	final public static String COMMAND_LOADTESTEXPRESSION_ELEMENT_VARIABLES = "variables";
	
	private HAPExpressionManager m_expressionManager;

	public HAPGatewayLoadTestExpression(HAPExpressionManager expressionManager){
		this.m_expressionManager = expressionManager;
	}
	
	@Override
	public HAPServiceData command(String command, JSONObject parms) throws Exception {
		HAPServiceData out = null;
		switch(command){
		case COMMAND_LOADTESTEXPRESSION:
			
			String suite = parms.optString(COMMAND_LOADTESTEXPRESSION_ELEMENT_SUITE);
			String expressionName = parms.optString(COMMAND_LOADTESTEXPRESSION_ELEMENT_EXPRESSIONNAME);
			JSONObject variablesObject = (JSONObject)parms.optJSONObject(COMMAND_LOADTESTEXPRESSION_ELEMENT_VARIABLES);
			
			Map<String, HAPDataTypeCriteria> variableCriterias = new LinkedHashMap<String, HAPDataTypeCriteria>();
			if(variablesObject!=null){
				Iterator varNamesIt = variablesObject.keys();
				while(varNamesIt.hasNext()){
					String varName = (String)varNamesIt.next();
					String criteria = variablesObject.optString(varName);
					HAPDataTypeCriteriaWrapperLiterate criteriaObj = new HAPDataTypeCriteriaWrapperLiterate();
					criteriaObj.buildObject(criteria, HAPSerializationFormat.LITERATE);
					variableCriterias.put(varName, criteriaObj);
				}
			}
			
			Map<String, HAPData> varDatas = ((HAPExpressionDefinitionSuiteForTest)this.m_expressionManager.getExpressionDefinitionSuite(suite)).getVariableData();
			HAPExpression expression = this.m_expressionManager.processExpression(null, suite, expressionName, variableCriterias);
			HAPResponseGatewayLoadTestExpression response = new HAPResponseGatewayLoadTestExpression(expression, varDatas);
			out = this.createSuccessWithObject(response);
			break;
		}
		
		return out;
	}

}