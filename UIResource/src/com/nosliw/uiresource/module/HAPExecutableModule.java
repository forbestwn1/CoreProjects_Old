package com.nosliw.uiresource.module;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImpWrapper;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPScript;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.resource.HAPResourceData;
import com.nosliw.data.core.resource.HAPResourceDependent;
import com.nosliw.data.core.runtime.HAPExecutable;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPResourceDataFactory;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPUtilityContextScript;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableWrapperTask;

@HAPEntityWithAttribute
public class HAPExecutableModule extends HAPEntityInfoImpWrapper implements HAPExecutable{

	@HAPAttribute
	public static String ID = "id";

	@HAPAttribute
	public static String CONTEXT = "context";
	
	@HAPAttribute
	public static String UI = "ui";
	
	@HAPAttribute
	public static String PROCESS = "process";

	@HAPAttribute
	public static String INITSCRIPT = "initScript";

	private HAPDefinitionModule m_moduleDefinition;
	
	private String m_id;

	// hook up with real data during runtime
	private HAPContextGroup m_context;

	//processes (used for lifecycle, module command)
	private Map<String, HAPExecutableWrapperTask<HAPExecutableProcess>> m_processes;
	
	private List<HAPExecutableModuleUI> m_uis;

	public HAPExecutableModule(HAPDefinitionModule moduleDefinition, String id) {
		super(moduleDefinition);
		this.m_processes = new LinkedHashMap<String, HAPExecutableWrapperTask<HAPExecutableProcess>>();
		this.m_uis = new ArrayList<HAPExecutableModuleUI>();
		this.m_moduleDefinition = moduleDefinition;
		this.m_id = id;
	}

	public HAPDefinitionModule getDefinition() {   return this.m_moduleDefinition;  }
	
	public HAPContextGroup getContext() {   return this.m_context;   }

	public void setContextGroup(HAPContextGroup contextGroup) { 	this.m_context = contextGroup;	}
	
	public void addProcess(String name, HAPExecutableWrapperTask<HAPExecutableProcess> process) {		this.m_processes.put(name, process);	}
	
	public void addModuleUI(HAPExecutableModuleUI ui) {  this.m_uis.add(ui);   }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ID, this.m_id);
		jsonMap.put(CONTEXT, HAPJsonUtility.buildJson(this.m_context, HAPSerializationFormat.JSON));
		jsonMap.put(UI, HAPJsonUtility.buildJson(this.m_uis, HAPSerializationFormat.JSON));
		jsonMap.put(PROCESS, HAPJsonUtility.buildJson(this.m_processes, HAPSerializationFormat.JSON));
	}
	
	@Override
	public HAPResourceData toResourceData(HAPRuntimeInfo runtimeInfo) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
		this.buildFullJsonMap(jsonMap, typeJsonMap);

		List<String> uiJsonList = new ArrayList<String>();
		for(HAPExecutableModuleUI ui :this.m_uis) {
			uiJsonList.add(ui.toResourceData(runtimeInfo).toString());
		}
		jsonMap.put(UI, HAPJsonUtility.buildArrayJson(uiJsonList.toArray(new String[0])));
		
		Map<String, String> processJsonMap = new LinkedHashMap<String, String>();
		for(String processName :this.m_processes.keySet()) {
			processJsonMap.put(processName, this.m_processes.get(processName).toResourceData(runtimeInfo).toString());
		}
		jsonMap.put(PROCESS, HAPJsonUtility.buildMapJson(processJsonMap));
		
		jsonMap.put(INITSCRIPT, HAPUtilityContextScript.buildContextInitScript(this.getContext()).getScript());
		typeJsonMap.put(INITSCRIPT, HAPScript.class);
		
		return HAPResourceDataFactory.createJSValueResourceData(HAPJsonUtility.buildMapJson(jsonMap, typeJsonMap));
	}

	@Override
	public List<HAPResourceDependent> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		List<HAPResourceDependent> out = new ArrayList<HAPResourceDependent>();
		
		for(HAPExecutableModuleUI ui : this.m_uis) {
			out.addAll(ui.getResourceDependency(runtimeInfo));
		}
		
		for(HAPExecutableWrapperTask process : this.m_processes.values()) {
			out.addAll(process.getResourceDependency(runtimeInfo));
		}
		
		return out;
	}
}
