package com.nosliw.data.core;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.literate.HAPLiterateManager;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPSerializeManager;
import com.nosliw.common.utils.HAPConstant;

public class HAPRelationshipPath extends HAPSerializableImp{

	private HAPDataTypeId m_source;
	private HAPDataTypeId m_target;
	
	protected List<HAPRelationshipPathSegment> m_segments = null;

	public HAPRelationshipPath(){
		this.m_segments = new ArrayList<HAPRelationshipPathSegment>();
	}

	public HAPRelationshipPath(HAPDataTypeId source, HAPDataTypeId target){
		this.m_segments = new ArrayList<HAPRelationshipPathSegment>();
		this.m_source = source;
		this.m_target = target;
	}

	public HAPRelationshipPath reverse(HAPDataTypeId source, HAPDataTypeId target) {
		HAPRelationshipPath out = new HAPRelationshipPath();
		for(int i=this.m_segments.size()-2; i>=0; i--) {
			out.m_segments.add(this.m_segments.get(i));
		}
		out.m_segments.add(new HAPRelationshipPathSegment(source));
		return out;
	}
	
	public List<HAPRelationshipPathSegment> getSegments(){
		return this.m_segments;
	}
	
	public void addSegment(HAPRelationshipPathSegment segment){
		this.m_segments.add(segment);
	}

	public void insert(HAPRelationshipPathSegment segment){
		this.m_segments.add(0, segment);
	}

	public void append(HAPRelationshipPathSegment segment){
		this.m_segments.add(segment);
	}
	
	public void setPath(HAPRelationshipPath path){
		this.m_segments.clear();
		this.m_segments.addAll(path.getSegments());
	}

	@Override
	protected String buildLiterate(){  
		return HAPSerializeManager.getInstance().toStringValue(m_segments, HAPSerializationFormat.LITERATE); 
	}
	
	@Override
	public boolean buildObject(Object value, HAPSerializationFormat format) {
		this.m_segments = (List<HAPRelationshipPathSegment>)HAPLiterateManager.getInstance().stringToValue((String)value, HAPConstant.STRINGABLE_ATOMICVALUETYPE_ARRAY, HAPRelationshipPathSegment.class.getName());
		return true;
	} 

	public HAPRelationshipPath clone(){
		HAPRelationshipPath out = new HAPRelationshipPath();
		out.m_segments.addAll(this.m_segments);
		return out;
	}
	
}
