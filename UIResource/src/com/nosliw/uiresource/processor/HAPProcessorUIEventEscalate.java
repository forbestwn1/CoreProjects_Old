package com.nosliw.uiresource.processor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.script.context.HAPContextEntity;
import com.nosliw.data.core.script.context.HAPUtilityContext;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitTag;
import com.nosliw.uiresource.tag.HAPUITagId;
import com.nosliw.uiresource.tag.HAPUITagManager;

public class HAPProcessorUIEventEscalate {

	public static void process(HAPExecutableUIUnit exeUnit, HAPUITagManager uiTagMan) {

		if(HAPConstant.UIRESOURCE_TYPE_TAG.equals(exeUnit.getType())) {
			HAPExecutableUIUnitTag exeTag = (HAPExecutableUIUnitTag)exeUnit;
			if(HAPUtilityContext.getContextGroupEscalateMode(uiTagMan.getUITagDefinition(new HAPUITagId(exeTag.getUIUnitTagDefinition().getTagName())).getContext())) {
				Map<String, HAPContextEntity> mappedEventDefs = new LinkedHashMap<String, HAPContextEntity>();
				
				Map<String, String> nameMapping = HAPNamingConversionUtility.parsePropertyValuePairs(exeTag.getAttributes().get(HAPConstant.UITAG_PARM_EVENT));
				exeTag.setEventMapping(nameMapping);
				Map<String, HAPContextEntity> exeEventDefs = exeTag.getEventDefinitions();
				for(String eventName : exeEventDefs.keySet()) {
					String mappedName = nameMapping.get(eventName);
					if(mappedName==null)   mappedName = eventName;
					mappedEventDefs.put(mappedName, exeEventDefs.get(eventName));
				}
				escalate(exeTag, mappedEventDefs);
			}
			
		}

		//child tag
		for(HAPExecutableUIUnitTag childTag : exeUnit.getUITags()) {
			process(childTag, uiTagMan);
		}
	}

	private static void escalate(HAPExecutableUIUnit exeUnit, Map<String, HAPContextEntity> eventsDef) {
		HAPExecutableUIUnit parent = exeUnit.getParent();
		if(parent==null) {
			for(String eventName : eventsDef.keySet()) {
				if(exeUnit.getEventDefinition(eventName)==null) {
					exeUnit.addEventDefinition(eventName, eventsDef.get(eventName));
				}
			}
		}
		else {
			escalate(parent, eventsDef);
		}
	}
}