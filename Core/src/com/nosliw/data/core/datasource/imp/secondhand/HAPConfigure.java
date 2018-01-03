package com.nosliw.data.core.datasource.imp.secondhand;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.strvalue.io.HAPStringableEntityImporterJSON;
import com.nosliw.common.strvalue.valueinfo.HAPValueInfoManager;
import com.nosliw.data.core.expression.HAPExpressionDefinitionSuite;
import com.nosliw.data.core.imp.expression.HAPExpressionDefinitionSuiteImp;

@HAPEntityWithAttribute
public class HAPConfigure extends HAPSerializableImp{

	@HAPAttribute
	public static String DEPENDENTDATASOURCES = "dependentDataSources";
	
	@HAPAttribute
	public static String EXPRESSIONSUITE = "expressionSuite";
	
	
	private Map<String, HAPDependentDataSource> m_dependentDataSources;
	
	private HAPExpressionDefinitionSuite m_expressionSuite;
	
	public HAPConfigure(){
		this.m_dependentDataSources = new LinkedHashMap<String, HAPDependentDataSource>();
	}
	

	public Map<String, HAPDependentDataSource> getDependentDataSources(){
		return this.m_dependentDataSources;
	}

	public HAPExpressionDefinitionSuite getExpressionSuite(){
		return this.m_expressionSuite;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		try{
			JSONObject objJson = (JSONObject)json;
			
			JSONObject dependentResourcesJson = (JSONObject)objJson.opt(DEPENDENTDATASOURCES);
			if(dependentResourcesJson != null){
				Iterator<String> it = dependentResourcesJson.keys();
				while(it.hasNext()){
					String name = it.next();
					JSONObject dependentResourceJson = dependentResourcesJson.getJSONObject(name);
					HAPDependentDataSource dependentResource = new HAPDependentDataSource();
					dependentResource.buildObjectByJson(dependentResourceJson);
					this.m_dependentDataSources.put(name, dependentResource);
				}
			}
			
			JSONObject expressionSuiteJson = (JSONObject)objJson.opt(EXPRESSIONSUITE);
		    this.m_expressionSuite = (HAPExpressionDefinitionSuiteImp)HAPStringableEntityImporterJSON.parseJsonEntity(expressionSuiteJson, HAPExpressionDefinitionSuiteImp._VALUEINFO_NAME, HAPValueInfoManager.getInstance());
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;  
	}
	
}
