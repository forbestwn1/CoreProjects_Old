package com.nosliw.data.core.script.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.criteria.HAPVariableInfo;

public abstract class HAPExecutableScriptWithSegmentImp implements HAPExecutableScriptWithSegment{

	private String m_id;
	
	private List<HAPExecutableScript> m_segs;

	public HAPExecutableScriptWithSegmentImp(String id) {
		this.m_segs = new ArrayList<HAPExecutableScript>();
		this.m_id = id;
	}
	
	@Override
	public String getId() {    return this.m_id;    }
	
	@Override
	public void addSegment(HAPExecutableScript segment) {    this.m_segs.add(segment);   }
	@Override
	public void addSegments(List<HAPExecutableScript> segments) {   this.m_segs.addAll(segments);    }
	
	@Override
	public List<HAPExecutableScript> getSegments(){    return this.m_segs;     }

	@Override
	public Set<HAPVariableInfo> getVariablesInfo() {
		Map<String, HAPVariableInfo> out = new LinkedHashMap<String, HAPVariableInfo>();
		for(HAPExecutableScript seg : this.m_segs) {
			for(HAPVariableInfo varInfo : seg.getVariablesInfo()) {
				HAPUtilityScriptExpression.addVariableInfo(out, varInfo);
			}
		}
		return new HashSet<HAPVariableInfo>(out.values());  
	}

	@Override
	public Set<HAPDefinitionConstant> getConstantsDefinition() {
		Map<String, HAPDefinitionConstant> out = new LinkedHashMap<String, HAPDefinitionConstant>();
		for(HAPExecutableScript seg : this.m_segs) {
			for(HAPDefinitionConstant constantDef : seg.getConstantsDefinition()) {
				HAPUtilityScriptExpression.addConstantDefinition(out, constantDef);
			}
		}
		return new HashSet<HAPDefinitionConstant>(out.values());  
	}

	@Override
	public void updateConstant(Map<String, Object> value) {
		for(HAPExecutableScript seg : this.m_segs) {
			seg.updateConstant(value);
		}
	}
}
