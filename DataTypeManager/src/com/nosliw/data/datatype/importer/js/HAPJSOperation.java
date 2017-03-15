package com.nosliw.data.datatype.importer.js;

import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.strvalue.HAPStringableValueEntity;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.datatype.importer.HAPDataTypeIdImp;
import com.nosliw.data.datatype.importer.HAPResourceIdImp;

public class HAPJSOperation extends HAPStringableValueEntity{

	public static String DATADEFINITION_NAME = "data.operation.js";
	
	@HAPAttribute
	public static String ID = "id";
	
	@HAPAttribute
	public static String SCRIPT = "script";
	
	@HAPAttribute
	public static String OPERATIONID = "operationId";

	@HAPAttribute
	public static String OPERATIONNAME = "operationName";
	
	@HAPAttribute
	public static String DATATYPENAME = "dataTypeName";
	
	@HAPAttribute
	public static String DEPENDENCY = "dependency";
	
	public HAPJSOperation(String script, String operationId, HAPDataTypeId dataTypeName, String operationName,List<HAPResourceId> dependency){
		this.setScript(script);
		this.setOperationName(operationName);
		this.setDataTypeName(dataTypeName);
		this.setOperationId(operationId);
		this.setDependency(dependency);
	}
	
	public String getId(){  return this.getAtomicAncestorValueString(ID);  }
	public void setId(String id){  this.updateAtomicChildStrValue(ID, id);  }
	
	public String getScript(){  return this.getAtomicAncestorValueString(SCRIPT);  }
	public void setScript(String script){  this.updateAtomicChildStrValue(SCRIPT, script);  }
	
	public String getOperationId(){  return this.getAtomicAncestorValueString(OPERATIONID);  }
	public void setOperationId(String operationId){  this.updateAtomicChildStrValue(OPERATIONID, operationId);  }
	
	public String getOperationName(){  return this.getAtomicAncestorValueString(OPERATIONNAME);  }
	public void setOperationName(String operationName){  this.updateAtomicChildStrValue(OPERATIONNAME, operationName);  }
	
	public HAPDataTypeId getDataTypeName() {	return (HAPDataTypeIdImp)this.getAtomicAncestorValueObject(DATATYPENAME, HAPDataTypeIdImp.class);	}
	public void setDataTypeName(HAPDataTypeId dataTypeName){ this.updateAtomicChildObjectValue(DATATYPENAME, dataTypeName); }
	
	public List<HAPResourceId> getDependency(){  
		return this.getAtomicAncestorValueArray(DEPENDENCY, HAPResourceIdImp.class); 
	}
	public void setDependency(List<HAPResourceId> resourcesId){
		this.updateAtomicChildObjectValue(DEPENDENCY, resourcesId);
	}
}
