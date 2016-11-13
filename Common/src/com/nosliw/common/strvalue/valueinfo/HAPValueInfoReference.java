package com.nosliw.common.strvalue.valueinfo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.strvalue.HAPStringableValue;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;

public class HAPValueInfoReference extends HAPValueInfo{

	public static final String ENTITY_PROPERTY_REFERENCE = "reference";
	public static final String ENTITY_PROPERTY_DEFAULT = "default";
	
	private HAPValueInfo m_solidValueInfo;
	
	private Map<String, Object> m_defaultValues;

	public static HAPValueInfoReference build(){
		HAPValueInfoReference out = new HAPValueInfoReference();
		out.init();
		return out;
	}

	@Override
	public void init(){
		super.init();
		this.m_defaultValues = new LinkedHashMap<String, Object>();
		this.updateBasicChild(TYPE, HAPConstant.STRINGALBE_VALUEINFO_REFERENCE);
	}

	@Override
	public void afterBuild(){
		String defaultValues = this.getBasicAncestorValueString(ENTITY_PROPERTY_DEFAULT);
		if(HAPBasicUtility.isStringNotEmpty(defaultValues)){
			String[] valuesDef = HAPNamingConversionUtility.parseElements(defaultValues);
			for(String valueDef : valuesDef){
				Map<String, Object> defaultValuesMap = this.m_defaultValues;
				String[] valueDefSegs = HAPNamingConversionUtility.parseProperty(valueDef);
				String name = valueDefSegs[0];
				String value = valueDefSegs[1];
				HAPPath path = new HAPPath(name);
				String[] nameSegs = path.getPathSegs(); 
				for(int i=0; i<nameSegs.length; i++){
					String nameSeg = nameSegs[i];
					if(i+1>=nameSegs.length){
						defaultValuesMap.put(nameSeg, value);
					}
					else{
						Map<String, Object> childMap = null;
						Object o = defaultValuesMap.get(nameSeg);
						if(o==null){
							childMap = new LinkedHashMap<String, Object>();
							defaultValuesMap.put(nameSeg, childMap);
						}
						defaultValuesMap = childMap;
					}
				}
			}
		}
	}

	
	
	@Override
	public String getCategary() {
		return HAPConstant.STRINGALBE_VALUEINFO_REFERENCE;
	}
	
	@Override
	public String getValueDataType(){
		return this.getSolidValueInfo().getValueDataType();
	}
	
	@Override
	public HAPValueInfo getSolidValueInfo(){
		if(this.m_solidValueInfo==null){
			this.m_solidValueInfo = this.getValueInfoManager().getValueInfo(this.getReferencedName()).getSolidValueInfo().clone();
			this.buildDefaultValue(m_solidValueInfo, m_defaultValues);
		}
		return this.m_solidValueInfo;
	}
	
	private void buildDefaultValue(HAPValueInfo valueInfo, Object defaultValue){
		if(defaultValue==null)  return;
		
		String categary = valueInfo.getCategary();
		if(HAPConstant.STRINGALBE_VALUEINFO_ATOMIC.equals(categary)){
			valueInfo.updateBasicChild(HAPValueInfoBasic.ENTITY_PROPERTY_DEFAULTVALUE, (String)defaultValue);
		}
		else if(HAPConstant.STRINGALBE_VALUEINFO_ENTITY.equals(categary)){
			HAPValueInfoEntity valueInfoEntity = (HAPValueInfoEntity)valueInfo;
			for(String property : valueInfoEntity.getProperties()){
				Object propDefaultValue = ((Map<String, Object>)defaultValue).get(property);
				if(propDefaultValue!=null){
					buildDefaultValue(valueInfoEntity.getPropertyInfo(property), propDefaultValue);
				}
			}
		}
		else if(HAPConstant.STRINGALBE_VALUEINFO_LIST.equals(categary)){
			HAPValueInfoList valueInfoList = (HAPValueInfoList)valueInfo;
			this.buildDefaultValue(valueInfoList.getChildValueInfo(), defaultValue);
		}
		else if(HAPConstant.STRINGALBE_VALUEINFO_MAP.equals(categary)){
			HAPValueInfoMap valueInfoMap = (HAPValueInfoMap)valueInfo;
			this.buildDefaultValue(valueInfoMap.getChildValueInfo(), defaultValue);
		}
		else if(HAPConstant.STRINGALBE_VALUEINFO_ENTITYOPTIONS.equals(categary)){
			HAPValueInfoEntityOptions valueInfoEntityOptions = (HAPValueInfoEntityOptions)valueInfo;
			for(String key : valueInfoEntityOptions.getOptionsKey()){
				Object keyDefaultValue = ((Map<String, Object>)defaultValue).get(key);
				if(keyDefaultValue!=null){
					this.buildDefaultValue(valueInfoEntityOptions.getOptionsValueInfo(key), keyDefaultValue);
				}
			}
		}
	}
	
	private String getReferencedName(){
		return this.getBasicAncestorValueString(ENTITY_PROPERTY_REFERENCE);
	}

	@Override
	public HAPValueInfoReference clone(){
		HAPValueInfoReference out = new HAPValueInfoReference();
		out.cloneFrom(this);
		return out;
	}

	@Override
	public HAPStringableValue buildDefault() {		return null;	}
}
