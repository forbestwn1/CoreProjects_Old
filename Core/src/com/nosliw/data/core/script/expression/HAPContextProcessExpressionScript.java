package com.nosliw.data.core.script.expression;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.common.HAPWithConstantDefinition;
import com.nosliw.data.core.common.HAPWithValueStructure;
import com.nosliw.data.core.expression.HAPDefinitionExpressionSuite;
import com.nosliw.data.core.valuestructure.HAPValueStructure;

public class HAPContextProcessExpressionScript implements HAPWithValueStructure, HAPWithConstantDefinition{

	//all expression container
	private HAPDefinitionExpressionSuite m_expressionSuiteDefinition;

	//all constant
	private Map<String, HAPDefinitionConstant> m_constantsDefinition;

	//context for expression
	private HAPValueStructure m_valueStructure;
	
	public HAPContextProcessExpressionScript() {
		this.m_constantsDefinition = new LinkedHashMap<String, HAPDefinitionConstant>();
	}
	
	public HAPDefinitionExpressionSuite getExpressionDefinitionSuite(){		return this.m_expressionSuiteDefinition;	}
	public void setExpressionDefinitionSuite(HAPDefinitionExpressionSuite expressionSuite) {   this.m_expressionSuiteDefinition = expressionSuite;    }

	@Override
	public Set<HAPDefinitionConstant> getConstantDefinitions() {  return new HashSet<HAPDefinitionConstant>(this.m_constantsDefinition.values()); }

	@Override
	public HAPDefinitionConstant getConstantDefinition(String id) {  return this.m_constantsDefinition.get(id);  }

	@Override
	public void addConstantDefinition(HAPDefinitionConstant constantDef) {  this.m_constantsDefinition.put(constantDef.getId(), constantDef);  }

	@Override
	public HAPValueStructure getValueStructure() {   return this.m_valueStructure;  }

	@Override
	public void setValueStructure(HAPValueStructure valueStructure) {   this.m_valueStructure = valueStructure;  }

	@Override
	public void cloneToWithValueStructure(HAPWithValueStructure withValueStructure) {   withValueStructure.setValueStructure(m_valueStructure);  }  
}
