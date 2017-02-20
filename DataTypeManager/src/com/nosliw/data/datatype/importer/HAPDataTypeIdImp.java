package com.nosliw.data.datatype.importer;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.strvalue.HAPStringableValueAtomic;
import com.nosliw.common.strvalue.HAPStringableValueEntity;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPDataTypeId;
import com.nosliw.data.HAPDataTypeVersion;

public class HAPDataTypeIdImp extends HAPStringableValueEntity implements HAPDataTypeId{

	@HAPAttribute
	public static String FULLNAME = "fullName";
	
	public HAPDataTypeIdImp(){}
	
	public HAPDataTypeIdImp(String name, HAPDataTypeVersion version){
		this.updateAtomicChildStrValue(NAME, name, HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING, null);
		this.updateAtomicChildObjectValue(VERSION, version);
		this.updateAtomicChildStrValue(FULLNAME, this.buildLiterate(), HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING, null);
	}

	public HAPDataTypeIdImp(String fullName){
		this.setFullName(fullName);
	}
	
	public String getFullName(){  return this.buildLiterate();  }
	public void setFullName(String fullName){
		this.updateAtomicChildStrValue(FULLNAME, fullName, HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING, null);
		buildObjectByLiterate(fullName);
	}
	
	public int getVersionMajor(){  return this.getVersion().getMajor(); }
	public int getVersionMinor(){  return this.getVersion().getMinor(); }
	public String getVersionRevision(){  return this.getVersion().getRevision();  }
	
	@Override
	public String getName() {		return this.getAtomicAncestorValueString(HAPDataTypeId.NAME);	}
	@Override
	public HAPDataTypeVersion getVersion() {
		HAPDataTypeVersionImp version = (HAPDataTypeVersionImp)this.getAtomicAncestorValueObject(VERSION, HAPDataTypeVersionImp.class);
		return version;
	}

	public String getVersionFullName(){ return ((HAPDataTypeVersionImp)this.getVersion()).getName(); }
	
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
		this.updateAtomicChildStrValue(NAME, segs[0], HAPConstant.STRINGABLE_ATOMICVALUETYPE_STRING, null);
		if(segs.length>=2){
			HAPStringableValueAtomic versionValue = new HAPStringableValueAtomic(segs[1], HAPConstant.STRINGABLE_ATOMICVALUETYPE_OBJECT, HAPDataTypeVersionImp.class.getName());
			this.updateChild(VERSION, versionValue);
		}
	}
	
	public static String buildStringValue(String name, String version){
		return HAPNamingConversionUtility.cascadeSegments(name, version);
	}
	
	public HAPDataTypeIdImp clone(){
		HAPDataTypeIdImp out = new HAPDataTypeIdImp();
		out.cloneFrom(this);
		return out;
	}
}
