package com.nosliw.data.core;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.common.HAPDefinitionConstant;
import com.nosliw.data.core.component.attachment.HAPAttachment;
import com.nosliw.data.core.component.attachment.HAPAttachmentContainer;
import com.nosliw.data.core.component.attachment.HAPAttachmentEntity;

public class HAPUtilityDataComponent {

	public static Set<HAPDefinitionConstant> buildConstantDefinition(HAPAttachmentContainer attContainer){
		Set<HAPDefinitionConstant> out = new HashSet<HAPDefinitionConstant>();
		Map<String, HAPAttachment> attrs = attContainer.getAttachmentByType(HAPConstant.RUNTIME_RESOURCE_TYPE_DATA);
		for(String id : attrs.keySet()) {
			HAPDefinitionConstant constantDef = new HAPDefinitionConstant();
			HAPAttachmentEntity attr = (HAPAttachmentEntity)attrs.get(id);
			attr.cloneToEntityInfo(constantDef);
			constantDef.setValue(attr.getEntity());
			out.add(constantDef);
		}
		return out;
	}

	public static Set<HAPDefinitionConstant> buildDataConstantDefinition(HAPAttachmentContainer attContainer){
		Set<HAPDefinitionConstant> out = new HashSet<HAPDefinitionConstant>();
		Set<HAPDefinitionConstant> constants = buildConstantDefinition(attContainer);
		for(HAPDefinitionConstant constant : constants) {
			if(constant.isData()) {
				out.add(constant);
			}
		}
		return out;
	}

	public static Map<String, Object> buildConstantValue(HAPAttachmentContainer attContainer){
		Map<String, Object> out = new LinkedHashMap<String, Object>();
		Set<HAPDefinitionConstant> constants = buildConstantDefinition(attContainer);
		for(HAPDefinitionConstant constant : constants) {
			out.put(constant.getId(), constant.getValue());
		}
		return out;
	}
	
	public static Map<String, HAPData> buildConstantData(HAPAttachmentContainer attContainer){
		Map<String, HAPData> out = new LinkedHashMap<String, HAPData>();
		Set<HAPDefinitionConstant> constants = buildDataConstantDefinition(attContainer);
		for(HAPDefinitionConstant constant : constants) {
			out.put(constant.getId(), constant.getData());
		}
		return out;
	}	
}
