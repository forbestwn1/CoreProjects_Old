package com.nosliw.data.core.component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.script.context.HAPContextGroup;

abstract public class HAPComponentImp extends HAPEntityInfoWritableImp implements HAPComponent{

	private HAPResourceId m_resourceId;
	
	private String m_id;

	//context definition within this component
	private HAPContextGroup m_context;
	
	//lifecycle definition
	private Set<HAPHandlerLifecycle> m_lifecycleAction;
	
	//event handlers
	private Set<HAPHandlerEvent> m_eventHandlers;

	private HAPAttachmentContainer m_attachmentContainer;

	public HAPComponentImp() {
		this.m_context = new HAPContextGroup();
		this.m_lifecycleAction = new HashSet<HAPHandlerLifecycle>();
		this.m_eventHandlers = new HashSet<HAPHandlerEvent>();
		this.m_attachmentContainer = new HAPAttachmentContainer();
	}

	public HAPComponentImp(String id) {
		this();
		this.m_id = id;
	}
	
	@Override
	public void setResourceId(HAPResourceId resourceId) {  this.m_resourceId = resourceId;   }
	@Override
	public HAPResourceId getResourceId() {   return this.m_resourceId;   }
	
	@Override
	public String getId() {   return this.m_id;   }
	@Override
	public void setId(String id) {  this.m_id = id;   }
 	 
	@Override
	public HAPContextGroup getContext() {  return this.m_context;   }
	@Override
	public void setContext(HAPContextGroup context) {  
		this.m_context = context;
		if(this.m_context ==null)  this.m_context = new HAPContextGroup();
	}
	
	@Override
	public Set<HAPHandlerLifecycle> getLifecycleAction(){    return this.m_lifecycleAction;    }
	@Override
	public void addLifecycleAction(HAPHandlerLifecycle lifecycleAction) {    this.m_lifecycleAction.add(lifecycleAction);    }
 	
	@Override
	public Set<HAPHandlerEvent> getEventHandlers(){   return this.m_eventHandlers;   }
	@Override
	public void addEventHandler(HAPHandlerEvent eventHandler) {  this.m_eventHandlers.add(eventHandler);   }

	@Override
	public HAPAttachmentContainer getAttachmentContainer() {		return this.m_attachmentContainer;	}

	@Override
	public Map<String, HAPAttachment> getAttachmentsByType(String type) {
		return this.m_attachmentContainer.getAttachmentByType(type);
	}

	@Override
	public void mergeBy(HAPWithAttachment parent, String mode) {
		this.m_attachmentContainer.merge(parent.getAttachmentContainer(), mode);
	}
 
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ID, this.m_id);
		jsonMap.put(CONTEXT, HAPJsonUtility.buildJson(this.m_context, HAPSerializationFormat.JSON));
		jsonMap.put(LIFECYCLE, HAPJsonUtility.buildJson(this.m_lifecycleAction, HAPSerializationFormat.JSON));
		jsonMap.put(EVENTHANDLER, HAPJsonUtility.buildJson(this.m_eventHandlers, HAPSerializationFormat.JSON));
	}
}
