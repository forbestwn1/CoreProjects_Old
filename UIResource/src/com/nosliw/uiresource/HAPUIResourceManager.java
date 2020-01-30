package com.nosliw.uiresource;

import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.component.HAPAttachmentContainer;
import com.nosliw.data.core.component.HAPComponentUtility;
import com.nosliw.data.core.component.HAPManagerResourceDefinition;
import com.nosliw.data.core.component.HAPWithNameMapping;
import com.nosliw.data.core.expressionsuite.HAPExpressionSuiteManager;
import com.nosliw.data.core.process.HAPManagerProcessDefinition;
import com.nosliw.data.core.resource.HAPResourceCache;
import com.nosliw.data.core.resource.HAPResourceDefinition;
import com.nosliw.data.core.resource.HAPResourceDefinitionOrReference;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.resource.HAPResourceManagerRoot;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.service.provide.HAPManagerServiceDefinition;
import com.nosliw.uiresource.application.HAPDefinitionApp;
import com.nosliw.uiresource.application.HAPDefinitionAppEntryWrapper;
import com.nosliw.uiresource.application.HAPExecutableAppEntry;
import com.nosliw.uiresource.application.HAPParseMiniApp;
import com.nosliw.uiresource.application.HAPProcessMiniAppEntry;
import com.nosliw.uiresource.common.HAPIdGenerator;
import com.nosliw.uiresource.module.HAPDefinitionModule;
import com.nosliw.uiresource.module.HAPExecutableModule;
import com.nosliw.uiresource.module.HAPParserModule;
import com.nosliw.uiresource.module.HAPProcessorModule;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIPage;
import com.nosliw.uiresource.page.definition.HAPParserPage;
import com.nosliw.uiresource.page.definition.HAPUtilityPage;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitPage;
import com.nosliw.uiresource.page.processor.HAPProcessorUIPage;
import com.nosliw.uiresource.page.tag.HAPUITagManager;

public class HAPUIResourceManager {

	private HAPResourceCache m_resourceCache;
	
	private HAPExpressionSuiteManager m_expressionMan; 
	
	private HAPResourceManagerRoot m_resourceMan;

	private HAPUITagManager m_uiTagMan;
	
	private HAPRuntime m_runtime;

	private HAPDataTypeHelper m_dataTypeHelper;
	
	private HAPManagerProcessDefinition m_processMan;
	
	private HAPManagerServiceDefinition m_serviceDefinitionManager;
	
	private HAPManagerResourceDefinition m_componentManager;
	
	private HAPIdGenerator m_idGengerator = new HAPIdGenerator(1);

	private HAPParserPage m_uiResourceParser;
	
	private HAPParserModule m_moduleParser;

	private HAPParseMiniApp m_miniAppParser;
	
	public HAPUIResourceManager(
			HAPUITagManager uiTagMan,
			HAPExpressionSuiteManager expressionMan, 
			HAPResourceManagerRoot resourceMan,
			HAPManagerProcessDefinition processMan,
			HAPRuntime runtime, 
			HAPDataTypeHelper dataTypeHelper,
			HAPManagerServiceDefinition serviceDefinitionManager,
			HAPManagerResourceDefinition componentManager){
		this.m_uiTagMan = uiTagMan;
		this.m_expressionMan = expressionMan;
		this.m_resourceMan = resourceMan;
		this.m_processMan = processMan;
		this.m_runtime = runtime;
		this.m_resourceCache = new HAPResourceCache();
		this.m_dataTypeHelper = dataTypeHelper;
		this.m_uiResourceParser = new HAPParserPage(null, m_idGengerator);
		this.m_moduleParser = new HAPParserModule(this.m_processMan.getPluginManager());
		this.m_miniAppParser = new HAPParseMiniApp(this.m_processMan.getPluginManager());
		this.m_serviceDefinitionManager = serviceDefinitionManager;
		this.m_componentManager = componentManager;
	}

	public HAPDefinitionApp getMiniAppDefinition(HAPResourceId appId, HAPAttachmentContainer parentAttachment) {
		//get definition itself
		HAPDefinitionApp appDef = (HAPDefinitionApp)this.m_componentManager.getResourceDefinition(appId);
		
		//merged with parent attachment
		HAPAttachmentContainer attachment = mergeWithParentAttachment(appDef, parentAttachment);

		//resolve attachment mapping
		HAPComponentUtility.solveAttachment(appDef, attachment);
		return appDef;
	}
	
	public HAPDefinitionAppEntryWrapper getMiniAppEntryDefinition(HAPResourceId appEntryId, HAPAttachmentContainer parentAttachment) {
		HAPDefinitionAppEntryWrapper appEntryDef = (HAPDefinitionAppEntryWrapper)this.m_componentManager.getResourceDefinition(appEntryId);
		//merged with parent attachment
		HAPAttachmentContainer attachment = mergeWithParentAttachment(appEntryDef.getAppDefinition(), parentAttachment);
		//resolve attachment mapping
		HAPComponentUtility.solveAttachment(appEntryDef.getAppDefinition(), attachment);
		return appEntryDef;
	}

	public HAPExecutableAppEntry getMiniAppEntry(HAPResourceId appEntryId) {
		return this.getEmbededMiniAppEntry(appEntryId, null);
	}
	
	public HAPExecutableAppEntry getEmbededMiniAppEntry(HAPResourceId appEntryId, HAPAttachmentContainer parentAttachment) {
		HAPDefinitionAppEntryWrapper appEntryDef = this.getMiniAppEntryDefinition(appEntryId, parentAttachment);
		HAPProcessTracker processTracker = new HAPProcessTracker(); 
		HAPExecutableAppEntry out = HAPProcessMiniAppEntry.process(appEntryDef.getAppDefinition(), appEntryDef.getEntry(), null, m_processMan, this, m_dataTypeHelper, m_runtime, m_expressionMan, m_serviceDefinitionManager, processTracker);
		return out;
	}
	
	
//	public HAPDefinitionAppEntryUI getMiniAppEntryDefinition(HAPResourceId appId, String entry, HAPAttachmentContainer parentExternalMapping) {
//		HAPDefinitionApp appDef = this.getMiniAppDefinition(appId, parentExternalMapping);
//		//resolve attachment for entry
//		HAPComponentUtility.solveAttachment(appDef.getEntry(entry), appDef.getAttachmentContainer());
//		return appDef.getEntry(entry);
//	}
	
	
	
	
//	public HAPDefinitionApp getMiniAppDefinition(String appId, HAPAttachmentContainer parentExternalMapping) {
//		HAPDefinitionApp miniAppDef = HAPUtilityApp.getAppDefinitionById(appId, this.m_miniAppParser);
//		//resolve attachment for app
//		HAPComponentUtility.solveAttachment(miniAppDef, parentExternalMapping);
//		return miniAppDef;
//	}

//	public HAPDefinitionAppEntryUI getMiniAppEntryDefinition(HAPResourceIdSimple resourceId) {
//		HAPUIAppEntryId appEntryId = new HAPResourceIdUIAppEntry(resourceId).getUIAppEntryId();
//		return getMiniAppEntryDefinition(appEntryId.getAppId(), appEntryId.getEntry(), new HAPAttachmentContainer(resourceId.getSupplement()));
//	}
	
//	public HAPDefinitionAppEntryUI getMiniAppEntryDefinition(String appId, String entry, HAPAttachmentContainer parentExternalMapping) {
//		HAPDefinitionApp appDef = this.getMiniAppDefinition(appId, parentExternalMapping);
//		
//		//resolve attachment for entry
//		HAPComponentUtility.solveAttachment(appDef.getEntry(entry), appDef.getAttachmentContainer());
//		return appDef.getEntry(entry);
//	}

	
//	public HAPExecutableAppEntry getMiniAppEntry(String appId, String entry, HAPAttachmentContainer parentExternalMapping) {
//		HAPDefinitionApp miniAppDef = getMiniAppDefinition(appId, parentExternalMapping);
//		//resolve attachment for entry
//		HAPComponentUtility.solveAttachment(miniAppDef.getEntry(entry), miniAppDef.getAttachmentContainer());
//		
//		HAPProcessTracker processTracker = new HAPProcessTracker(); 
//		HAPExecutableAppEntry out = HAPProcessMiniAppEntry.process(miniAppDef, entry, null, m_processMan, this, m_dataTypeHelper, m_runtime, m_expressionMan, m_serviceDefinitionManager, processTracker);
//		return out;
//	}



	public HAPDefinitionModule getModuleDefinition(HAPResourceId moduleId, HAPAttachmentContainer parentAttachment) {
		//get definition itself
		HAPDefinitionModule moduleDef = (HAPDefinitionModule)this.m_componentManager.getResourceDefinition(moduleId);
		
		//merged with parent attachment
		HAPAttachmentContainer attachment = mergeWithParentAttachment(moduleDef, parentAttachment);

		//resolve attachment mapping
		HAPComponentUtility.solveAttachment(moduleDef, attachment);
		return moduleDef;
	}

	public HAPExecutableModule getUIModule(HAPResourceId moduleId) {
		HAPExecutableModule out = (HAPExecutableModule)this.m_resourceCache.getResource(moduleId);
		if(out==null) {
			out = getEmbededUIModule(moduleId, null, null);
		}
		return out;
	}

	public HAPExecutableModule getEmbededUIModule(HAPResourceDefinitionOrReference defOrRef, HAPAttachmentContainer parentAttachment, HAPWithNameMapping withNameMapping) {
		HAPDefinitionModule moduleDef = null;
		String id = null;
		HAPAttachmentContainer attachmentEx = null;
		if(defOrRef instanceof HAPResourceId) {
			HAPResourceId moduleId = (HAPResourceId)defOrRef;
			attachmentEx = HAPComponentUtility.buildInternalAttachment(moduleId, parentAttachment, withNameMapping);
			moduleDef = getModuleDefinition(moduleId, attachmentEx);
			id = moduleId.getIdLiterate();
		}
		else if(defOrRef instanceof HAPResourceDefinition) {
			moduleDef = (HAPDefinitionModule)defOrRef;
			attachmentEx = HAPComponentUtility.buildNameMappedAttachment(parentAttachment, withNameMapping);
		}
		return HAPProcessorModule.process(moduleDef, id, attachmentEx, null, m_processMan, this, m_dataTypeHelper, m_runtime, m_expressionMan, m_serviceDefinitionManager);
	}

	
	public HAPExecutableUIUnitPage getUIPage(HAPResourceId pageResourceId){
		HAPExecutableUIUnitPage out = (HAPExecutableUIUnitPage)this.m_resourceCache.getResource(pageResourceId);
		if(out==null) {
			out = getEmbededUIPage(pageResourceId, pageResourceId.getIdLiterate(), null, null, null, null);
		}
		return out;
	}
	
	public HAPDefinitionUIPage getUIPageDefinition(HAPResourceId pageResourceId, HAPAttachmentContainer parentAttachment) {
		//get definition itself
		HAPDefinitionUIPage pageDefinition = (HAPDefinitionUIPage)this.m_componentManager.getResourceDefinition(pageResourceId);

		//merged with parent attachment
		HAPAttachmentContainer attachment = mergeWithParentAttachment(pageDefinition, parentAttachment);

		//process include tag
		pageDefinition = HAPUtilityPage.processInclude(pageDefinition, this.m_uiResourceParser, this, this.m_componentManager);

		//resolve attachment
		HAPUtilityPage.solveExternalMapping(pageDefinition, attachment, this.m_uiTagMan);

		//resolve service provider
		HAPUtilityPage.solveServiceProvider(pageDefinition, null, m_serviceDefinitionManager);
		return pageDefinition;
	}

	public HAPExecutableUIUnitPage getEmbededUIPage(HAPResourceDefinitionOrReference defOrRef, String id, HAPContextGroup context, HAPContextGroup parentContext, HAPAttachmentContainer parentAttachment, HAPWithNameMapping withNameMapping){
		HAPDefinitionUIPage pageDef = null;
		HAPAttachmentContainer attachmentEx = null;
		if(defOrRef instanceof HAPResourceId) {
			HAPResourceId pageId = (HAPResourceId)defOrRef;
			attachmentEx = HAPComponentUtility.buildInternalAttachment(pageId, parentAttachment, withNameMapping);
			pageDef = getUIPageDefinition(pageId, attachmentEx);
		}
		
		//compile it
		HAPExecutableUIUnitPage out = HAPProcessorUIPage.processUIResource(pageDef, id, context, parentContext, null, this, m_dataTypeHelper, m_uiTagMan, m_runtime, m_expressionMan, m_resourceMan, this.m_uiResourceParser, this.m_serviceDefinitionManager, m_idGengerator);
		return out;
	}

	public HAPParserModule getModuleParser() {    return this.m_moduleParser;   }
	public HAPParserPage getUIResourceParser() {    return this.m_uiResourceParser;  }
	public HAPParseMiniApp getMinitAppParser() {    return this.m_miniAppParser;     }

	//
	private HAPAttachmentContainer mergeWithParentAttachment(HAPResourceDefinition resourceDefinition, HAPAttachmentContainer parentAttachment) {
		HAPAttachmentContainer out = new HAPAttachmentContainer(resourceDefinition.getResourceId().getSupplement());
		out.merge(parentAttachment, HAPConfigureContextProcessor.VALUE_INHERITMODE_CHILD);
		return out;
	}
	
}
