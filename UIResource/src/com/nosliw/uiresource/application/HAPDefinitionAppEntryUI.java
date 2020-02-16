package com.nosliw.uiresource.application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.component.HAPAttachmentContainer;
import com.nosliw.data.core.component.HAPAttachmentReference;
import com.nosliw.data.core.component.HAPChildrenComponentId;
import com.nosliw.data.core.component.HAPChildrenComponentIdContainer;
import com.nosliw.data.core.component.HAPComponentImp;
import com.nosliw.data.core.component.HAPComponentUtility;
import com.nosliw.data.core.process.HAPDefinitionProcessSuiteElementEntity;
import com.nosliw.data.core.process.plugin.HAPManagerActivityPlugin;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionWrapperTask;
import com.nosliw.uiresource.module.HAPDefinitionModuleUI;

@HAPEntityWithAttribute
public class HAPDefinitionAppEntryUI  extends HAPComponentImp implements HAPDefinitionAppEntry{

	@HAPAttribute
	public static final String MODULE = "module";

	@HAPAttribute
	public static final String PROCESS = "process";

	//all modules in this entry
	private List<HAPDefinitionAppModule> m_modules;

	private Map<String, HAPDefinitionWrapperTask<HAPDefinitionProcessSuiteElementEntity>> m_processes;

	public HAPDefinitionAppEntryUI(String id, HAPManagerActivityPlugin activityPluginMan) {
		super(id, activityPluginMan);
		this.m_modules = new ArrayList<HAPDefinitionAppModule>();
		this.m_processes = new LinkedHashMap<String, HAPDefinitionWrapperTask<HAPDefinitionProcessSuiteElementEntity>>();
	}
	
	public List<HAPDefinitionAppModule> getModules(){  return this.m_modules;  }
	public void addModules(List<HAPDefinitionAppModule> modules) {   if(modules!=null)   this.m_modules.addAll(modules);   }
	public void addModule(HAPDefinitionAppModule module) {  this.m_modules.add(module);  }
	
	@Override
	public HAPDefinitionWrapperTask<HAPDefinitionProcessSuiteElementEntity> getProcess(String name) {  return this.m_processes.get(name);   }
	public Map<String, HAPDefinitionWrapperTask<HAPDefinitionProcessSuiteElementEntity>> getProcesses(){   return this.m_processes;  }
	public void addProcess(String name, HAPDefinitionWrapperTask<HAPDefinitionProcessSuiteElementEntity> process) {  this.m_processes.put(name, process);    }

	@Override
	public HAPChildrenComponentIdContainer getChildrenComponentId() {
		HAPChildrenComponentIdContainer out = new HAPChildrenComponentIdContainer();

		//module part
		for(HAPDefinitionAppModule module : this.getModules()) {
			if(!HAPDefinitionModuleUI.STATUS_DISABLED.equals(module.getStatus())) {
				HAPAttachmentContainer mappedParentAttachment = HAPComponentUtility.buildNameMappedAttachment(this.getAttachmentContainer(), module);
				HAPAttachmentReference moduleAttachment = (HAPAttachmentReference)this.getAttachmentContainer().getElement(HAPConstant.RUNTIME_RESOURCE_TYPE_UIMODULE, module.getModule());
				out.addChildCompoentId(new HAPChildrenComponentId(module.getName(), moduleAttachment.getId(), module.getInfo()), mappedParentAttachment);
			}
		}
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(MODULE, HAPJsonUtility.buildJson(this.m_modules, HAPSerializationFormat.JSON));
		jsonMap.put(PROCESS, HAPJsonUtility.buildJson(this.m_processes, HAPSerializationFormat.JSON));
	}
}
