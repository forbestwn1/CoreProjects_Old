package com.nosliw.data.core.process;

import java.util.Map;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.component.HAPChildrenComponentIdContainer;
import com.nosliw.data.core.component.HAPResourceDefinitionContainer;
import com.nosliw.data.core.component.HAPResourceDefinitionContainerElement;
import com.nosliw.data.core.resource.HAPResourceDefinition;

//application that contains multiple tasks
@HAPEntityWithAttribute
public class HAPDefinitionProcessSuite extends HAPResourceDefinitionContainer<HAPResourceDefinitionContainerElement>{

	public HAPDefinitionProcessSuite() {
	}

	@Override
	public String getResourceType() {   return HAPConstant.RUNTIME_RESOURCE_TYPE_PROCESSSUITE;  }

	@Override
	public HAPResourceDefinition getElementResourceDefinition(String eleName) {	return new HAPDefinitionProcess(this, eleName);	}

	@Override
	public HAPChildrenComponentIdContainer getChildrenComponentId() {
		return null;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
	
	public HAPDefinitionProcessSuite cloneProcessSuiteDefinition() {
		HAPDefinitionProcessSuite out = new HAPDefinitionProcessSuite();
		this.cloneToResourceDefinitionContainer(out);
		return out;
	}

	@Override
	public HAPResourceDefinitionContainer cloneResourceDefinitionContainer() {	return this.cloneProcessSuiteDefinition();	}

}
