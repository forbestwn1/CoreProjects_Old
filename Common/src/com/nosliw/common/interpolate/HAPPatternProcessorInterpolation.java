package com.nosliw.common.interpolate;

import com.nosliw.common.pattern.HAPPatternProcessorImp;

public abstract class HAPPatternProcessorInterpolation extends HAPPatternProcessorImp{

	HAPInterpolateExpressionProcessor m_interpolateExpressionProcessor; 
	
	public HAPPatternProcessorInterpolation(HAPInterpolateExpressionProcessor processor){
		this.m_interpolateExpressionProcessor = processor;
	}

	@Override
	public Object parse(String text, Object data) {
		return this.m_interpolateExpressionProcessor.processExpression(text, data);
	}

	@Override
	public String compose(Object obj, Object data) {
		return null;
	}
}
