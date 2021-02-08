package com.nosliw.data.core.script.expression.literate;

import java.util.List;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.expression.HAPDefinitionExpressionGroup;
import com.nosliw.data.core.expression.HAPDefinitionReference;
import com.nosliw.data.core.script.expression.HAPDefinitionScriptEntity;
import com.nosliw.data.core.script.expression.HAPScript;
import com.nosliw.data.core.script.expression.expression.HAPProcessorScriptExpression;

public class HAPProcessorScriptLiterate {

	public static HAPExecutableScriptLiterate process(
		String id,
		HAPDefinitionScriptEntity scriptDef,
		Map<String, HAPDefinitionReference> references,
		Map<String, Object> constantValues,
		HAPDefinitionExpressionGroup expressionDef
	) {
		HAPExecutableScriptLiterate out = new HAPExecutableScriptLiterate(id);
		scriptDef.cloneToEntityInfo(out);
		List<HAPScript> scriptSegs = HAPUtilityScriptLiterate.parseScriptLiterate(scriptDef.getScript().getScript());
		for(int j=0; j<scriptSegs.size(); j++) {
			HAPScript scriptSeg = scriptSegs.get(j);
			String scriptType = scriptSeg.getType();
			String scriptId = id+"_"+j;
			if(HAPConstantShared.SCRIPT_TYPE_SEG_EXPRESSIONSCRIPT.equals(scriptType)) {
				HAPExecutableScriptSegExpressionScript expressionScriptSegExe = new HAPExecutableScriptSegExpressionScript(scriptId);
				expressionScriptSegExe.addSegments(HAPProcessorScriptExpression.process(scriptId, scriptSeg, references, constantValues, expressionDef));
				out.addSegment(expressionScriptSegExe);
			}
			else if(HAPConstantShared.SCRIPT_TYPE_SEG_TEXT.equals(scriptType)) {
				out.addSegment(new HAPExecutableScriptSegText(scriptId, scriptSeg.getScript())); 
			}
		}
		return out;
	}
	
}
