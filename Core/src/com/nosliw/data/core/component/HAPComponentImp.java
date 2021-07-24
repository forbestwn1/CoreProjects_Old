package com.nosliw.data.core.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.handler.HAPHandler;
import com.nosliw.data.core.service.use.HAPDefinitionServiceUse;

abstract public class HAPComponentImp extends HAPResourceDefinitionComplexImp implements HAPComponent{

	//lifecycle definition
	private Set<HAPHandlerLifecycle> m_lifecycleAction;
	
	//event handlers
	private Set<HAPHandler> m_eventHandlers;

	//used service
	private Map<String, HAPDefinitionServiceUse> m_serviceUse;
	
	private List<HAPDefinitionEvent> m_events;
	
	private List<HAPDefinitionCommand> m_commands;
	
	public HAPComponentImp() {
		this.m_lifecycleAction = new HashSet<HAPHandlerLifecycle>();
		this.m_eventHandlers = new HashSet<HAPHandler>();
		this.m_serviceUse = new LinkedHashMap<String, HAPDefinitionServiceUse>();
		this.m_commands = new ArrayList<HAPDefinitionCommand>();
		this.m_events = new ArrayList<HAPDefinitionEvent>();
	}

	public HAPComponentImp(String id) {
		this();
		this.setId(id);
	}
	
	@Override
	public String getEntityOrReferenceType() {   return HAPConstantShared.ENTITY;    }

	@Override
	public Set<HAPHandlerLifecycle> getLifecycleAction(){    return this.m_lifecycleAction;    }
	@Override
	public void addLifecycleAction(HAPHandlerLifecycle lifecycleAction) {    this.m_lifecycleAction.add(lifecycleAction);    }
 	
	@Override
	public Set<HAPHandler> getEventHandlers(){   return this.m_eventHandlers;   }
	@Override
	public void addEventHandler(HAPHandler eventHandler) {  this.m_eventHandlers.add(eventHandler);   }
 
	@Override
	public HAPDefinitionServiceUse getService(String name) {   return this.m_serviceUse.get(name);   }
	
	@Override
	public Set<String> getAllServices(){   return this.m_serviceUse.keySet();     }
	
	@Override
	public void addService(HAPDefinitionServiceUse service) {   this.m_serviceUse.put(service.getName(), service);     }
	
	@Override
	public List<HAPDefinitionEvent> getEvents(){   return this.m_events;  }
	@Override
	public void addEvent(HAPDefinitionEvent event) {   this.m_events.add(event);   }

	@Override
	public List<HAPDefinitionCommand> getCommands(){   return this.m_commands;     }
	@Override
	public void addCommand(HAPDefinitionCommand command) {    this.m_commands.add(command);     }

	protected void cloneToComponent(HAPComponent component, boolean cloneValueStructure) {
		component.setId(this.getId());
		this.cloneToComplexResourceDefinition(component, cloneValueStructure);
		for(HAPHandlerLifecycle handler : this.m_lifecycleAction) {
			component.addLifecycleAction(handler.cloneLifecycleHander());
		}
		for(HAPHandler handler : this.m_eventHandlers) {
			component.addEventHandler(handler.cloneHandler());
		}
		for(HAPDefinitionCommand command : this.m_commands) {
			component.addCommand(command.cloneCommandDefinition());
		}
		for(HAPDefinitionEvent event : this.m_events) {
			component.addEvent(event.cloneEventDefinition());
		}
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(LIFECYCLE, HAPJsonUtility.buildJson(this.m_lifecycleAction, HAPSerializationFormat.JSON));
		jsonMap.put(EVENTHANDLER, HAPJsonUtility.buildJson(this.m_eventHandlers, HAPSerializationFormat.JSON));
		jsonMap.put(COMMAND, HAPJsonUtility.buildJson(this.m_commands, HAPSerializationFormat.JSON));
		jsonMap.put(EVENT, HAPJsonUtility.buildJson(this.m_events, HAPSerializationFormat.JSON));
	}
}
