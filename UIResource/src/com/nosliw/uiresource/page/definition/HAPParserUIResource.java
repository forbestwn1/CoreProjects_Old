package com.nosliw.uiresource.page.definition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.nosliw.common.configure.HAPConfigure;
import com.nosliw.common.serialization.HAPScript;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPSegmentParser;
import com.nosliw.data.core.script.context.HAPContextEntity;
import com.nosliw.data.core.script.context.HAPParserContext;
import com.nosliw.data.core.script.expressionscript.HAPDefinitionEmbededScript;
import com.nosliw.data.core.script.expressionscript.HAPScriptExpressionUtility;
import com.nosliw.uiresource.HAPIdGenerator;
import com.nosliw.uiresource.HAPUIResourceManager;

/*
 * This is a utility class that process ui resource file and create ui resource object
 * the id index start with 1 every processing start so that for same ui resource, we would get same result
 */
public class HAPParserUIResource {

	public static final String EVENT = "events";
	public static final String SERVICE = "services";
	public static final String CONTEXT = "contexts";
	public static final String COMMAND = "commands";
	public static final String EXPRESSION = "expressions";
	public static final String SCRIPT = "scripts";
	
	//for creating ui id
	private HAPIdGenerator m_idGenerator;
	//configuration object
	private HAPConfigure m_setting;
	
	public HAPParserUIResource(HAPConfigure setting, HAPIdGenerator idGenerator){
		this.m_idGenerator = idGenerator;
		this.m_setting = setting;
	}
	
	//resourceUnit : target ui resource object
	//content : html content
	public void parseContent(HAPDefinitionUIUnit resourceUnit, String content){
		try{
			Document doc = Jsoup.parse(content, "UTF-8");
			parseUIDefinitionUnit(resourceUnit, doc.body(), null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public HAPDefinitionUIUnitResource parseFile(String fileName){
		HAPDefinitionUIUnitResource resource = null;
		try{
			File input = new File(fileName);
			//use file name as ui resource id
			String resourceId = HAPFileUtility.getFileName(input);
			String source = HAPFileUtility.readFile(input);
			resource = this.parseContent(resourceId, source);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resource;
	}

	public HAPDefinitionUIUnitResource parseContent(String resourceId, String content){
		HAPDefinitionUIUnitResource resource = new HAPDefinitionUIUnitResource(resourceId, content);
		try{
			Document doc = Jsoup.parse(content, "UTF-8");
			this.parseUIDefinitionUnit(resource, doc.body(), null);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return resource;
	}

	public HAPDefinitionUIUnitResource processInclude(HAPDefinitionUIUnitResource uiResourceDef, HAPUIResourceManager uiResourceMan) {
		Set<HAPDefinitionUIUnitTag> includeTags = new HashSet<HAPDefinitionUIUnitTag>();
		HAPUIDefinitionUnitUtility.getUITagByName(uiResourceDef, HAPConstant.UITAG_NAME_INCLUDE, includeTags);
		for(HAPDefinitionUIUnitTag includeTagResource : includeTags){
			//include resource
			String includeResourceName = includeTagResource.getAttributes().get(HAPConstant.UITAG_NAME_INCLUDE_PARM_SOURCE);
			HAPDefinitionUIUnitResource uiResource = uiResourceMan.getUIResourceDefinitionById(includeResourceName);
			this.parseContent(includeTagResource, uiResource.getSource());
		}
		return uiResourceDef; 
	}

	//parse unit (resource, tag)
	private void parseUIDefinitionUnit(HAPDefinitionUIUnit uiUnit, Element unitEle, HAPDefinitionUIUnit parentUIUnit){
		//process script block
		this.parseUnitScriptBlocks(unitEle, uiUnit);
		//process context block
		this.parseUnitContextBlocks(unitEle, uiUnit);
		//process expression block
		this.parseUnitExpressionBlocks(unitEle, uiUnit);
		
		//parse event definition block
		this.parseUnitEventBlocks(unitEle, uiUnit);
		//parse service definition block
		this.parseUnitServiceBlocks(unitEle, uiUnit);
		//parse command definition block
		this.parseChildCommandBlocks(unitEle, uiUnit);
		
		//process key attribute
		if(HAPConstant.UIRESOURCE_TYPE_TAG.equals(uiUnit.getType()))   parseKeyAttributeOnTag(unitEle, parentUIUnit, true);

		//process unit element's attribute that have expression value 
		if(HAPConstant.UIRESOURCE_TYPE_TAG.equals(uiUnit.getType()))	parseScriptExpressionInTagAttribute(unitEle, parentUIUnit, true);
		
		//process element's normal attribute
		parseUIUnitAttribute(unitEle, uiUnit);
		
		if(HAPConstant.UIRESOURCE_TYPE_TAG.equals(uiUnit.getType())) {
			//add placeholder element to the customer tag's postion and then remove the original tag from html structure 
			String uiId = uiUnit.getId();
			unitEle.after("<"+HAPConstant.UIRESOURCE_TAG_PLACEHOLDER+" style=\"display:none;\" "+HAPConstant.UIRESOURCE_ATTRIBUTE_UIID+"="+ uiId +HAPConstant.UIRESOURCE_CUSTOMTAG_WRAPER_END_POSTFIX+"></"+HAPConstant.UIRESOURCE_TAG_PLACEHOLDER+">");
			unitEle.after("<"+HAPConstant.UIRESOURCE_TAG_PLACEHOLDER+" style=\"display:none;\" "+HAPConstant.UIRESOURCE_ATTRIBUTE_UIID+"="+ uiId +HAPConstant.UIRESOURCE_CUSTOMTAG_WRAPER_START_POSTFIX+"></"+HAPConstant.UIRESOURCE_TAG_PLACEHOLDER+">");
			unitEle.remove();
		}
		
		//process contents within customer ele
		parseChildScriptExpressionInContent(unitEle, uiUnit);
		parseDescendantTags(unitEle, uiUnit);
		
		HAPUtilityUIResourceParser.addSpanToText(unitEle);
		
		uiUnit.postRead();
		
		uiUnit.setContent(unitEle.html());
	}

	/*
	 * process all the descendant tags under element
	 */
	private void parseDescendantTags(Element ele, HAPDefinitionUIUnit parentUnit){
		List<Element> removes = new ArrayList<Element>();
		Elements eles = ele.children();
		for(Element e : eles){
			if(HAPBasicUtility.isStringEmpty(HAPUtilityUIResourceParser.getUIIdInElement(e))){
				//if tag have no ui id, then create ui id for it
				String id = this.m_idGenerator.createId();
				e.attr(HAPConstant.UIRESOURCE_ATTRIBUTE_UIID, id);
			}
			
			boolean ifRemove = parseTag(e, parentUnit);
			if(ifRemove)  removes.add(e);
		}
		
		for(Element remove : removes){
			remove.remove();
		}
	}
	
	/*
	 * process a tag element 
	 * return true : this element should be removed after processing
	 * 		  false : this element should not be removed after processiong
	 */
	private boolean parseTag(Element ele, HAPDefinitionUIUnit parentUnit){
		String customTag = HAPUtilityUIResourceParser.isCustomTag(ele);
		if(customTag!=null){
			//process custome tag
			String uiId = HAPUtilityUIResourceParser.getUIIdInElement(ele); 
			HAPDefinitionUIUnitTag uiTag = new HAPDefinitionUIUnitTag(customTag, uiId);
			parseUIDefinitionUnit(uiTag, ele, parentUnit);
			parentUnit.addUITag(uiTag);
			return false;
		}
		else{
			//process regular tag
			parseChildScriptExpressionInContent(ele, parentUnit);
			//process key attribute
			parseKeyAttributeOnTag(ele, parentUnit, false);
			//process elements's attribute that have expression value 
			parseScriptExpressionInTagAttribute(ele, parentUnit, false);
			//process all descendant tags under this elment
			parseDescendantTags(ele, parentUnit);
			return false;
		}
	}

	
	private void parseUnitEventBlocks(Element ele, HAPDefinitionUIUnit resourceUnit) {
		List<Element> childEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, EVENT);
		for(Element childEle : childEles){
			try {
				JSONArray eventListJson = new JSONArray(childEle.html());
				for(int i=0; i<eventListJson.length(); i++) {
					JSONObject eventJson = eventListJson.getJSONObject(i);
					HAPContextEntity eventDef = new HAPContextEntity();
					eventDef.buildObject(eventJson, HAPSerializationFormat.JSON);
					resourceUnit.addEventDefinition(eventDef);
				}
				break;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		for(Element childEle : childEles)  childEle.remove();
	}

	private void parseUnitServiceBlocks(Element ele, HAPDefinitionUIUnit resourceUnit) {
		List<Element> childEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, SERVICE);
		for(Element childEle : childEles){
			try {
				JSONArray serviceListJson = new JSONArray(childEle.html());
				for(int i=0; i<serviceListJson.length(); i++) {
					JSONObject serviceJson = serviceListJson.getJSONObject(i);
					HAPContextEntity serviceDef = new HAPContextEntity();
					serviceDef.buildObject(serviceJson, HAPSerializationFormat.JSON);
					resourceUnit.addServiceDefinition(serviceDef);
				}
				break;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		for(Element childEle : childEles)  childEle.remove();
	}

	private void parseChildCommandBlocks(Element ele, HAPDefinitionUIUnit resourceUnit) {
		List<Element> childEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, COMMAND);
		for(Element childEle : childEles){
			try {
				JSONArray commandListJson = new JSONArray(childEle.html());
				for(int i=0; i<commandListJson.length(); i++) {
					JSONObject commandJson = commandListJson.getJSONObject(i);
					HAPContextEntity commandDef = new HAPContextEntity();
					commandDef.buildObject(commandJson, HAPSerializationFormat.JSON);
					resourceUnit.addCommandDefinition(commandDef);
				}
				break;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		for(Element childEle : childEles)  childEle.remove();
	}
	
	private void parseUnitContextBlocks(Element ele, HAPDefinitionUIUnit resourceUnit){
		List<Element> childEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, CONTEXT);
		
		for(Element childEle : childEles){
			try {
				HAPParserContext.parseContextGroup(new JSONObject(StringEscapeUtils.unescapeHtml(childEle.html())), resourceUnit.getContextDefinition());
				break;
			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println(childEle.html());
			}
		}
		
		for(Element childEle : childEles)  childEle.remove();
	}
	
	private void parseUnitExpressionBlocks(Element ele, HAPDefinitionUIUnit resource){
		List<Element> childEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, EXPRESSION);

		for(Element childEle : childEles){
			try {
				String content = childEle.html();
				JSONObject defsJson = new JSONObject(content);
				Iterator<String> defNames = defsJson.keys();
				while(defNames.hasNext()){
					String defName = defNames.next();
					resource.addExpressionDefinition(defName, defsJson.optString(defName));
				}
				break;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}		

		for(Element childEle : childEles)   childEle.remove();
	}
	
	/*
	 * process all script blocks under unit
	 */
	private void parseUnitScriptBlocks(Element ele, HAPDefinitionUIUnit resource){
		List<Element> scirptEles = new ArrayList<Element>();
		
		scirptEles = HAPUtilityUIResourceParser.getChildElementsByTag(ele, SCRIPT);
		for(Element scriptEle : scirptEles){
			HAPScript jsBlock = new HAPScript(scriptEle.html());
			resource.setJSBlock(jsBlock);
			break;
		}
		
		for(Element scriptEle : scirptEles)  scriptEle.remove();
	}

	/*
	 * process expression in child text content within element 
	 */
	private void parseChildScriptExpressionInContent(Element ele, HAPDefinitionUIUnit resource){
		List<TextNode> textNodes = ele.textNodes();
		for(TextNode textNode : textNodes){
			String text = textNode.text();
			List<Object> segments = HAPScriptExpressionUtility.discoverEmbededScript(text);
			StringBuffer newText = new StringBuffer();
			for(Object segment : segments){
				if(segment instanceof String){
					newText.append((String)segment);
				}
				else if(segment instanceof HAPDefinitionEmbededScript){
					HAPDefinitionEmbededScript scriptExpression = (HAPDefinitionEmbededScript)segment;
					HAPDefinitionUIEmbededScriptExpressionInContent expressionContent = new HAPDefinitionUIEmbededScriptExpressionInContent(this.m_idGenerator.createId(), scriptExpression);
					newText.append("<span "+HAPConstant.UIRESOURCE_ATTRIBUTE_UIID+"="+expressionContent.getUIId()+"></span>");
					resource.addScriptExpressionInContent(expressionContent);
				}
			}
			
			textNode.after(newText.toString());
			textNode.remove();
		}
	}
	
	
	/*
	 * process attribute of ui unit(UI resource or custom tag)
	 */
	private void parseUIUnitAttribute(Element unitEle, HAPDefinitionUIUnit uiUnit){
		Attributes eleAttrs = unitEle.attributes();
		for(Attribute eleAttr : eleAttrs){
			uiUnit.addAttribute(eleAttr.getKey(), eleAttr.getValue());
		}
	}
	
	/*
	 * process element's attribute that have script expression value
	 */
	private void parseScriptExpressionInTagAttribute(Element ele, HAPDefinitionUIUnit resource, boolean isCustomerTag){
		String uiId = HAPUtilityUIResourceParser.getUIIdInElement(ele); 
		
		//read attributes
		Attributes eleAttrs = ele.attributes();
		for(Attribute eleAttr : eleAttrs){
			String eleAttrKey = eleAttr.getKey();
			//replace express attribute value with; create ExpressEle object
			HAPDefinitionUIEmbededScriptExpressionInAttribute eAttr = new HAPDefinitionUIEmbededScriptExpressionInAttribute(eleAttrKey, uiId, eleAttr.getValue());
			if(eAttr.containsScriptExpression()){
				if(isCustomerTag)  resource.addScriptExpressionInTagAttribute(eAttr);
				else  resource.addScriptExpressionInAttribute(eAttr);
				ele.attr(eleAttrKey, "");
			}
		}
	}
	
	/*
	 * process key attribute within element 
	 * key attribute means attribute that have predefined meaning within ui resource
	 * isCustomertag : whether this element is a customer tag
	 */
	private void parseKeyAttributeOnTag(Element ele, HAPDefinitionUIUnit resource, boolean isCustomerTag){
		String uiId = HAPUtilityUIResourceParser.getUIIdInElement(ele); 
		Attributes eleAttrs = ele.attributes();
		for(Attribute eleAttr : eleAttrs){
			String eleAttrValue = eleAttr.getValue();
			String eleAttrName = eleAttr.getKey();
			String keyAttrName = HAPUtilityUIResourceParser.isKeyAttribute(eleAttrName);
			
			if(keyAttrName!=null){
				if(keyAttrName.contains(HAPConstant.UIRESOURCE_ATTRIBUTE_EVENT)){
					//process event key attribute
					HAPSegmentParser events = new HAPSegmentParser(eleAttrValue, HAPConstant.SEPERATOR_ELEMENT);
					while(events.hasNext()){
						String event = events.next();
						if(isCustomerTag){
							//this attribute belong to customer tag
							HAPElementEvent tagEvent = new HAPElementEvent(uiId, event);
							resource.addTagEvent(tagEvent);
						}
						else{
							//this attribute blong to regular tag
							HAPElementEvent eleEvent = new HAPElementEvent(uiId, event);
							resource.addElementEvent(eleEvent);
						}
					}
					//remove this attribute from element
					ele.removeAttr(eleAttrName);
				}
			}
		}
	}
}
