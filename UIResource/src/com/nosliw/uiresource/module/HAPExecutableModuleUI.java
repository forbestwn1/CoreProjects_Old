package com.nosliw.uiresource.module;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.component.HAPExecutableEmbededComponent;
import com.nosliw.data.core.resource.HAPResourceData;
import com.nosliw.data.core.resource.HAPResourceDependency;
import com.nosliw.data.core.resource.HAPResourceManagerRoot;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPResourceDataFactory;
import com.nosliw.uiresource.common.HAPInfoDecoration;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitPage;

@HAPEntityWithAttribute
public class HAPExecutableModuleUI extends HAPExecutableEmbededComponent{

	@HAPAttribute
	public static String PAGE = "page";

	@HAPAttribute
	public static String UIDECORATION = "uiDecoration";

	@HAPAttribute
	public static String PAGENAME = "pageName";
	
	private HAPDefinitionModuleUI m_moduleUIDefinition;
	
	private HAPExecutableUIUnitPage m_page;
	
	private List<HAPInfoDecoration> m_uiDecoration;
	
	public HAPExecutableModuleUI(HAPDefinitionModuleUI moduleUIDefinition, String id) {
		super(moduleUIDefinition);
		this.m_uiDecoration = new ArrayList<HAPInfoDecoration>();
		this.m_moduleUIDefinition = moduleUIDefinition;
		this.m_id = id;
	}

	public void setPage(HAPExecutableUIUnitPage page) {  this.m_page = page;   }
	public HAPExecutableUIUnitPage getPage() {  return this.m_page;   }
	
	public void addUIDecoration(List<HAPInfoDecoration> dec) {   this.m_uiDecoration.addAll(dec);   }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PAGE, HAPJsonUtility.buildJson(this.m_page, HAPSerializationFormat.JSON));
		jsonMap.put(UIDECORATION, HAPJsonUtility.buildJson(this.m_uiDecoration, HAPSerializationFormat.JSON));
		jsonMap.put(PAGENAME, this.m_moduleUIDefinition.getPage());
	}
	
	@Override
	public HAPResourceData toResourceData(HAPRuntimeInfo runtimeInfo) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
		this.buildFullJsonMap(jsonMap, typeJsonMap);

		jsonMap.put(PAGE, this.m_page.toResourceData(runtimeInfo).toString());
		
		return HAPResourceDataFactory.createJSValueResourceData(HAPJsonUtility.buildMapJson(jsonMap, typeJsonMap));
	}

	@Override
	public List<HAPResourceDependency> getResourceDependency(HAPRuntimeInfo runtimeInfo, HAPResourceManagerRoot resourceManager) {
		List<HAPResourceDependency> out = new ArrayList<HAPResourceDependency>();
		out.addAll(super.getResourceDependency(runtimeInfo, resourceManager));
		out.addAll(this.m_page.getResourceDependency(runtimeInfo, resourceManager));
		return out;
	}
}
