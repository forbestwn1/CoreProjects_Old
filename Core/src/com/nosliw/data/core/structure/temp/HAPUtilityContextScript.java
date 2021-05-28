package com.nosliw.data.core.structure.temp;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.interpolate.HAPStringTemplateUtil;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPJsonTypeScript;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.structure.HAPElement;
import com.nosliw.data.core.structure.HAPElementLeafConstant;
import com.nosliw.data.core.structure.HAPElementLeafRelative;
import com.nosliw.data.core.structure.HAPElementNode;
import com.nosliw.data.core.valuestructure.HAPValueStructureDefinitionFlat;
import com.nosliw.data.core.valuestructure.HAPValueStructureDefinitionGroup;

public class HAPUtilityContextScript {

	public static HAPJsonTypeScript buildContextInitScript(HAPValueStructureDefinitionGroup context) {
		Map<String, String> templateParms = new LinkedHashMap<String, String>();
		//build init output object 
		JSONObject output = HAPUtilityContextScript.buildDefaultJsonObject(context);
		templateParms.put("outputInit", HAPJsonUtility.formatJson(output.toString()));

		InputStream templateStream = HAPFileUtility.getInputStreamOnClassPath(HAPUtilityContextScript.class, "ContextInitFunction.temp");
		String script = HAPStringTemplateUtil.getStringValue(templateStream, templateParms);
		return new HAPJsonTypeScript(script);
	}
	
	//build default value structure for context group
	public static JSONObject buildDefaultJsonObject(HAPValueStructureDefinitionGroup contextGroup) {
		JSONObject out = new JSONObject();
		for(String categary : contextGroup.getCategaries()) {
			out.put(categary, buildDefaultJsonObject(contextGroup.getFlat(categary)));
		}
		return out;
	}
	
	public static JSONObject buildDefaultJsonObject(HAPValueStructureDefinitionFlat context) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		for(String contextEleName : context.getRootNames()) {
			Object value = context.getRoot(contextEleName).getDefaultValue();
			if(value!=null) {
				jsonMap.put(contextEleName, value.toString());
			}
		}
		return new JSONObject(HAPJsonUtility.buildMapJson(jsonMap));
	}

	//build skeleton, it is used for data mapping operation
	public static JSONObject buildSkeletonJsonObject(HAPValueStructureDefinitionFlat context, boolean isFlatRootName) {
		JSONObject output = new JSONObject();
		for(String rootName : context.getRootNames()) {
			if(HAPConstantShared.UIRESOURCE_CONTEXTINFO_RELATIVECONNECTION_PHYSICAL.equals(HAPUtilityContextInfo.getRelativeConnectionValue(context.getRoot(rootName).getInfo()))) {
				HAPElement contextDefEle = context.getRoot(rootName).getDefinition();
				Object contextEleJson = buildJsonValue(contextDefEle);

				if(contextEleJson!=null) {
					JSONObject parentJsonObj = output;
					if(isFlatRootName) {
						//if flat root name, just use it
						parentJsonObj.put(rootName, contextEleJson);
					}
					else {
						//not flat, parse categary and name from root name
						HAPIdContextDefinitionRoot rootId = new HAPIdContextDefinitionRoot(rootName);
						String categary = rootId.getCategary();
						if(HAPBasicUtility.isStringNotEmpty(categary)) {
							//ignore those that don't have categary
							parentJsonObj = output.optJSONObject(categary);
							if(parentJsonObj==null) {
								parentJsonObj = new JSONObject();
								output.put(categary, parentJsonObj);
							}
						}
						parentJsonObj.put(rootId.getName(), contextEleJson);
					}
				}
			}
		}
		return output;
	}
	
	private static Object buildJsonValue(HAPElement contextDefEle) {
		switch(contextDefEle.getType()) {
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT:
		{
			HAPElementLeafConstant constantEle = (HAPElementLeafConstant)contextDefEle;
			return constantEle.getValue();
		}
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_NODE:
		{
			HAPElementNode nodeEle = (HAPElementNode)contextDefEle;
			JSONObject out = new JSONObject();
			for(String childName : nodeEle.getChildren().keySet()) {
				Object childJsonValue = buildJsonValue(nodeEle.getChild(childName));
				if(childJsonValue!=null) {
					out.put(childName, childJsonValue);
				}
			}
			return out;
		}
		case HAPConstantShared.CONTEXT_ELEMENTTYPE_RELATIVE:
		{
			HAPElementLeafRelative relativeEle = (HAPElementLeafRelative)contextDefEle;
			return new JSONObject();
		}
		default:
			return null;
		}
	}
}