package com.nosliw.uiresource.context;

import java.util.Iterator;

import org.json.JSONObject;

import com.nosliw.data.core.criteria.HAPCriteriaParser;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContextElementAbsolute;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContextElment;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContextElmentRelative;

public class HAPContextParser {

	public static HAPContextNodeRootAbsolute parseContextRootElementInUIResource(JSONObject eleDefJson){
		HAPContextNodeRootAbsolute contextEle = new HAPContextNodeRootAbsolute();
		buildContextRootElementAbsolute(eleDefJson, contextEle);
		return contextEle;
	}
	
	public static HAPUITagDefinitionContextElment parseContextRootElementInTagDefinition(JSONObject eleDefJson){
		HAPUITagDefinitionContextElment out = null;
		String path = (String)eleDefJson.opt(HAPUITagDefinitionContextElmentRelative.PATH);
		if(path!=null){
			//relative
			out = new HAPUITagDefinitionContextElmentRelative();
			parseContextNodeFromJson(eleDefJson, (HAPUITagDefinitionContextElmentRelative)out);
		}
		else{
			//absolute
			out = new HAPUITagDefinitionContextElementAbsolute();
			buildContextRootElementAbsolute(eleDefJson, (HAPUITagDefinitionContextElementAbsolute)out);
		}
		
		return out;
	}
	
	private static void buildContextRootElementAbsolute(JSONObject eleDefJson, HAPContextNodeRootAbsolute contextEle){
		Object d = eleDefJson.opt(HAPContextNodeRootAbsolute.DEFAULT);
		if(d!=null)		contextEle.setDefaultValue(d);

		Object defObj = eleDefJson.opt(HAPContextNode.DEFINITION);
		parseContextNodeFromJson(defObj, contextEle);
	}
	
	public static void parseContextNodeFromJson(Object json, HAPContextNode contextNode){
		if(json instanceof String){
			contextNode.setDefinition(new HAPContextNodeCriteria(HAPCriteriaParser.getInstance().parseCriteria((String)json)));
		}
		else if(json instanceof JSONObject){
			JSONObject childrenObj = (JSONObject)json;
			Iterator<String> names = childrenObj.keys();
			while(names.hasNext()){
				String name = names.next();
				HAPContextNode childNode = new HAPContextNode();
				parseContextNodeFromJson(childrenObj.opt(name), childNode);
				contextNode.addChild(name, childNode);
			}
		}
	}
	
	
}
