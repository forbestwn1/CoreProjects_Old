package com.nosliw.data.core.expression.resource;

import java.util.Map;
import java.util.Set;

import com.nosliw.data.core.complex.HAPDefinitionEntityComplex;
import com.nosliw.data.core.complex.HAPDefinitionEntityContainer;
import com.nosliw.data.core.expression.HAPDefinitionExpressionGroup1;
import com.nosliw.data.core.expression.HAPDefinitionExpressionSuite1;
import com.nosliw.data.core.resource.HAPEntityResourceDefinition;
import com.nosliw.data.core.resource.HAPResourceDefinition1;
import com.nosliw.data.core.resource.HAPResourceDefinitionOrId;

public class HAPResourceEntityExpressionSuite 
				extends HAPDefinitionEntityContainer<HAPElementContainerResourceDefinitionEntityExpressionSuite> 
				implements HAPDefinitionExpressionSuite1{

	public HAPResourceEntityExpressionSuite() {
	}

	@Override
	public Set<HAPDefinitionExpressionGroup1> getEntityElements() {		return (Set)this.getContainerElements();	}

	@Override
	public HAPDefinitionExpressionGroup1 getEntityElement(String id) {  return this.getContainerElement(id); }

	@Override
	public void addEntityElement(HAPDefinitionExpressionGroup1 entityElement) {  this.addContainerElement((HAPElementContainerResourceDefinitionEntityExpressionSuite)entityElement); }

	@Override
	public HAPResourceDefinition1 getElementResourceDefinition(String eleId) {  return new HAPResourceEntityExpressionGroup(this, eleId);  }

	@Override
	public HAPResourceDefinitionOrId getChild(String path) {   return new HAPResourceEntityExpressionGroup(this, path);    }

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		return true;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

	@Override
	public HAPDefinitionEntityContainer<HAPElementContainerResourceDefinitionEntityExpressionSuite> cloneResourceDefinitionContainer() {
		HAPDefinitionEntityContainer<HAPElementContainerResourceDefinitionEntityExpressionSuite> out = new HAPResourceEntityExpressionSuite();
		this.cloneToResourceDefinitionContainer(out);
		return out;
	}

	@Override
	public HAPDefinitionExpressionSuite1 cloneExpressionSuiteDefinition() {
		HAPResourceEntityExpressionSuite out = new HAPResourceEntityExpressionSuite();
		this.cloneToResourceDefinitionContainer(out);
		return out;
	}

	@Override
	public HAPDefinitionEntityComplex cloneComplexEntityDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPEntityResourceDefinition getChildResourceEntity(String child) {
		// TODO Auto-generated method stub
		return null;
	}

}
