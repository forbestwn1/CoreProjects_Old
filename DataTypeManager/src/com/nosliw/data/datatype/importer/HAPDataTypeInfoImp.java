package com.nosliw.data.datatype.importer;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.strvalue.HAPStringableValueAtomic;
import com.nosliw.common.strvalue.HAPStringableValueEntity;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPDataTypeInfo;
import com.nosliw.data.HAPDataTypeVersion;

public class HAPDataTypeInfoImp extends HAPStringableValueEntity implements HAPDataTypeInfo{

	public static String ID = "id";
	
	public HAPDataTypeInfoImp(){}
	
	public HAPDataTypeInfoImp(String name, HAPDataTypeVersion version){
		this.updateAtomicChild(NAME, name, HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING, null);
		this.updateAtomicChildValue(VERSION, version);
	}

	public HAPDataTypeInfoImp(String id){
		this.setId(id);
	}

	public HAPDataTypeInfoImp(String id, String name, HAPDataTypeVersion version){
		this.setId(id);
		this.updateAtomicChild(NAME, name, HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING);
		this.updateAtomicChildValue(VERSION, version);
	}

	public String getId(){ return this.getAtomicAncestorValueString(ID); }
	public void setId(String id){		this.updateAtomicChild(ID, id, HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING);	}
	
	@Override
	public String getName() {		return this.getAtomicAncestorValueString(HAPDataTypeInfo.NAME);	}
	@Override
	public HAPDataTypeVersion getVersion() {
		HAPDataTypeVersionImp version = (HAPDataTypeVersionImp)this.getAtomicAncestorValueObject(VERSION, HAPDataTypeVersionImp.class);
		return version;
	}

	@Override
	protected String buildLiterate(){
		HAPDataTypeVersionImp version = (HAPDataTypeVersionImp)this.getVersion();
		String versionLiterate = null;
		if(version!=null){
			versionLiterate = version.toStringValue(HAPSerializationFormat.LITERATE);
		}
		return HAPNamingConversionUtility.cascadeSegments(this.getName(), versionLiterate);
	}

	@Override
	protected void buildObjectByLiterate(String literateValue){	
		String[] segs = HAPNamingConversionUtility.parseSegments(literateValue);
		this.updateAtomicChild(NAME, segs[0], HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING, null);
		if(segs.length>=2){
			HAPStringableValueAtomic versionValue = new HAPStringableValueAtomic(segs[1], HAPConstant.STRINGABLE_ATOMICVALUETYPE_OBJECT, HAPDataTypeVersionImp.class.getName());
			this.updateChild(VERSION, versionValue);
		}
	}
	
	public static String buildStringValue(String name, String version){
		return HAPNamingConversionUtility.cascadeSegments(name, version);
	}
	
	public HAPDataTypeInfoImp clone(){
		HAPDataTypeInfoImp out = new HAPDataTypeInfoImp();
		out.cloneFrom(this);
		return out;
	}
}