package com.nosliw.uiresource.processor;

import java.util.Map;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.expression.HAPVariableInfo;
import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPContextFlat;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPContextNodeRoot;
import com.nosliw.data.core.script.context.HAPContextNodeRootConstant;
import com.nosliw.data.core.script.context.HAPContextPath;
import com.nosliw.data.core.script.context.HAPEnvContextProcessor;
import com.nosliw.data.core.script.context.HAPInfoRelativeContextResolve;
import com.nosliw.data.core.script.context.HAPProcessorContext;
import com.nosliw.data.core.script.context.HAPUtilityContext;
import com.nosliw.uiresource.page.definition.HAPDefinitionUIUnitTag;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnit;
import com.nosliw.uiresource.page.execute.HAPExecutableUIUnitTag;
import com.nosliw.uiresource.tag.HAPUITagDefinitionContext;
import com.nosliw.uiresource.tag.HAPUITagId;
import com.nosliw.uiresource.tag.HAPUITagManager;

public class HAPProcessorUIContext {

	public static void process(HAPExecutableUIUnit uiExe, HAPExecutableUIUnit parentUIExe, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		process1(uiExe, parentUIExe, uiTagMan, contextProcessorEnv);			
		process2(uiExe, parentUIExe, contextProcessorEnv);			
		process3(uiExe);
	}
	
	//process context information
	public static void process1(HAPExecutableUIUnit uiExe, HAPExecutableUIUnit parentUIExe, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		HAPContextGroup parentContext = parentUIExe==null?null:parentUIExe.getContext();
		
		HAPContextGroup contextDef = uiExe.getUIUnitDefinition().getContextDefinition();

		if(uiExe.getType().equals(HAPConstant.UIRESOURCE_TYPE_TAG)) {
			HAPExecutableUIUnitTag uiTagExe = (HAPExecutableUIUnitTag)uiExe;
			//for custom tag, build context for tag first : merge parent context with context definition in tag definition first
			parentContext = buildUITagContext(uiTagExe, parentContext, uiTagMan, contextProcessorEnv);
			uiTagExe.setTagContext(parentContext);
		}
		
		//merge with context defined in tag unit
		HAPContextGroup extContextGroup = HAPProcessorContext.process1(contextDef, parentContext, new HAPConfigureContextProcessor(), contextProcessorEnv);
		uiExe.setContext(extContextGroup);

		//process include
		if(uiExe.getType().equals(HAPConstant.UIRESOURCE_TYPE_TAG)) {
			HAPExecutableUIUnitTag uiTagExe = (HAPExecutableUIUnitTag)uiExe;
			
			String tagName = ((HAPDefinitionUIUnitTag)uiTagExe.getUIUnitDefinition()).getTagName();
			if(HAPConstant.UITAG_NAME_INCLUDE.equals(tagName)) {
				//process include tag
				String includeContextMapping = uiTagExe.getAttributes().get(HAPConstant.UITAG_NAME_INCLUDE_PARM_CONTEXT);
				Map<String, String> contextMapping = HAPNamingConversionUtility.parsePropertyValuePairs(includeContextMapping);
				
				contextDef = uiExe.getContext(); 
//						contextDef.clone();
				HAPContext context = contextDef.getContext(HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC);

				for(String key : context.getElementNames()) {
					if(HAPBasicUtility.isStringEmpty(contextMapping.get(key)))			contextMapping.put(key, key);
				}
				
				for(String key : context.getElementNames()) {
					
					HAPUtilityContext.escalate(contextDef, key, contextMapping.get(key), HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC);
					
					
//					HAPContextNodeRoot rootNode = context.getElement(key);
//					if(rootNode.getType().equals(HAPConstant.UIRESOURCE_ROOTTYPE_ABSOLUTE)) {
//						HAPContextNodeRootAbsolute node = (HAPContextNodeRootAbsolute)rootNode;
//						String mappedName = contextMapping.get(key);
//						
//						Object[] a = elecate(mappedName, uiTagExe.getTagContext(), node);
//						uiTagExe.getTagContext().addElement(key, (HAPContextNodeRoot)a[0], (String)a[1]);
//						
//						HAPContextNodeRoot newRootNode = HAPUtilityContext.createInheritedElement(uiTagExe.getTagContext(), (String)a[1], key);
//						context.addElement(key, newRootNode);
//					}
				}
			}
		}
		
		//child tag
		for(HAPExecutableUIUnitTag childTag : uiExe.getUITags()) {
			process1(childTag, uiExe, uiTagMan, contextProcessorEnv);			
		}
	}

	private static Object[] elecate(String name, HAPContextGroup contextGroup, HAPContextNodeRoot original) {
		
		Object[] out = new Object[2];
		HAPInfoRelativeContextResolve resolveInfo = HAPUtilityContext.resolveReferencedParentContextNode(new HAPContextPath(name), contextGroup, null, HAPConfigureContextProcessor.VALUE_RESOLVEPARENTMODE_FIRST);
		if(resolveInfo!=null) {
			//find matched one
			HAPContextNodeRoot newRootNode = HAPUtilityContext.createInheritedElement(resolveInfo.rootNode, resolveInfo.path.getRootElementId().getCategary(), resolveInfo.path.getRootElementId().getName());
			out[0] = newRootNode;
			out[1] = resolveInfo.path.getRootElementId().getCategary();
			return out;
		}
		else {
			//not find
			if(isEnd1(contextGroup)){
				HAPContextNodeRoot newRootNode = original.cloneContextNodeRoot();
				contextGroup.addElement(name, newRootNode, HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC);
//				out[0] = newRootNode;
//				out[1] = HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC;
				return out;
			}
			else {
				HAPContextGroup parent = contextGroup.getParent();
				Object[] a = elecate(name, parent, original);
				
				String categary;
				HAPContextGroup group;
				if(a[0]!=null) {
					contextGroup.addElement(name, (HAPContextNodeRoot)a[0], (String)a[1]);
					group = contextGroup;
					categary = (String)a[1];
				}
				else {
					group = parent;
					categary = HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC;
				}
				

				HAPContextNodeRoot newRootNode = HAPUtilityContext.createInheritedElement(group, categary, name);
				out[0] = newRootNode;
				out[1] = categary;
				return out;
			}
		}
	}
	
	
	private static Object[] processPublicChild(String name, HAPExecutableUIUnit uiUnitExe, HAPContextNodeRoot original, HAPUITagManager uiTagMan) {
		Object[] out = new Object[2];
		HAPInfoRelativeContextResolve resolveInfo = HAPUtilityContext.resolveReferencedParentContextNode(new HAPContextPath(name), uiUnitExe.getContext(), null, HAPConfigureContextProcessor.VALUE_RESOLVEPARENTMODE_FIRST);
		if(resolveInfo!=null) {
			//find matched one
			HAPContextNodeRoot newRootNode = HAPUtilityContext.createInheritedElement(resolveInfo.rootNode, resolveInfo.path.getRootElementId().getCategary(), resolveInfo.path.getRootElementId().getName());
			out[0] = newRootNode;
			out[1] = resolveInfo.path.getRootElementId().getCategary();
			return out;
		}
		else {
			//not find
			if(isEnd(uiUnitExe, uiTagMan)){
				HAPContextNodeRoot newRootNode = original.cloneContextNodeRoot();
//				uiUnitExe.getContext().addElement(name, newRootNode, HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC);
				out[0] = newRootNode;
				out[1] = HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC;
				return out;
			}
			else {
				HAPExecutableUIUnit parent = uiUnitExe.getParent();
				Object[] a = processPublicChild(name, parent, original, uiTagMan);
				
				if(parent.getType().equals(HAPConstant.UIRESOURCE_TYPE_TAG)) {
					((HAPExecutableUIUnitTag)parent).getTagContext().addElement(name, (HAPContextNodeRoot)a[0], (String)a[1]);
					parent.getContext().addElement(name, HAPUtilityContext.createInheritedElement((HAPContextNodeRoot)a[0], (String)a[1], name), (String)a[1]);
				}
				else{
					parent.getContext().addElement(name, (HAPContextNodeRoot)a[0], (String)a[1]);
				}

				out[0] = HAPUtilityContext.createInheritedElement((HAPContextNodeRoot)a[0], (String)a[1], name);
				out[1] = HAPConstant.UIRESOURCE_CONTEXTTYPE_PUBLIC;
				return out;
			}
		}
		
	}

	private static boolean isEnd1(HAPContextGroup contextGroup) {
		HAPContextGroup parent = contextGroup.getParent();
		if(parent==null)   return true;
		return !HAPUtilityContext.getContextGroupEscalateMode(parent);
	}
	
	private static boolean isEnd(HAPExecutableUIUnit uiUnitExe, HAPUITagManager uiTagMan) {
		HAPExecutableUIUnit parent = uiUnitExe.getParent();
		if(parent==null)   return true;
		HAPExecutableUIUnitTag tag = (HAPExecutableUIUnitTag)parent;
		HAPUITagDefinitionContext tagDefinitionContext = uiTagMan.getUITagDefinition(new HAPUITagId(tag.getUIUnitTagDefinition().getTagName())).getContext();
		String inheritMode = HAPUtilityContext.getContextGroupInheritMode(tagDefinitionContext);
		return HAPConfigureContextProcessor.VALUE_INHERITMODE_NONE.equals(inheritMode);
	}
	
	private static void process2(HAPExecutableUIUnit uiExe, HAPExecutableUIUnit parentUIExe, HAPEnvContextProcessor contextProcessorEnv){
		HAPContextGroup parentContext = parentUIExe==null?null:parentUIExe.getContext();
		
		if(uiExe.getType().equals(HAPConstant.UIRESOURCE_TYPE_TAG)) {
			HAPExecutableUIUnitTag uiTagExe = (HAPExecutableUIUnitTag)uiExe;
			
			HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
			uiTagExe.setTagContext(HAPProcessorContext.process2(uiTagExe.getTagContext(), parentContext, configure, contextProcessorEnv));
			
			parentContext = uiTagExe.getTagContext();
		}
		
		//merge with context defined in tag unit
		uiExe.setContext(HAPProcessorContext.process2(uiExe.getContext(), parentContext, new HAPConfigureContextProcessor(), contextProcessorEnv));

		//child tag
		for(HAPExecutableUIUnitTag childTag : uiExe.getUITags()) {
			process2(childTag, uiExe, contextProcessorEnv);			
		}
		
	}	
	
	private static void process3(HAPExecutableUIUnit uiExe){
		
		if(uiExe.getType().equals(HAPConstant.UIRESOURCE_TYPE_TAG)) {
			//flat it
			HAPExecutableUIUnitTag uiTagExe = (HAPExecutableUIUnitTag)uiExe;
			uiTagExe.setFlatTagContext(HAPUtilityContext.buildFlatContext(uiTagExe.getTagContext()));
		}
		
		//build flat context
		HAPContextFlat flatContext = HAPUtilityContext.buildFlatContext(uiExe.getContext());
		uiExe.setFlatContext(flatContext);

		Map<String, Object> constantsValue = flatContext.getConstantValue();
		for(String name : constantsValue.keySet()) {
			Object constantValue = constantsValue.get(name);
			uiExe.addConstantValue(name, constantValue);
		}
		
		//build variables
		Map<String, HAPVariableInfo> varsInfo = HAPUtilityContext.discoverDataVariablesInContext(flatContext.getContext());
		for(String varName : varsInfo.keySet()) {
			uiExe.getExpressionContext().addVariable(varName, varsInfo.get(varName));
		}
		
		//child tag
		for(HAPExecutableUIUnitTag childTag : uiExe.getUITags()) {
			process3(childTag);			
		}
	}
	
	
	//build context for ui Tag
	private static HAPContextGroup buildUITagContext(HAPExecutableUIUnitTag uiTag, HAPContextGroup parentContext, HAPUITagManager uiTagMan, HAPEnvContextProcessor contextProcessorEnv){
		//get contextDef 
		HAPUITagDefinitionContext tagDefinitionContext = uiTagMan.getUITagDefinition(new HAPUITagId(uiTag.getUIUnitTagDefinition().getTagName())).getContext();

		//add attribute constant as part of tagContext
		Map<String, String> constants = uiTag.getAttributes();
		HAPContextGroup tagContext = tagDefinitionContext.clone();
		for(String cstName : constants.keySet()) {
			HAPContextNodeRootConstant cstRootNode = new HAPContextNodeRootConstant(constants.get(cstName));
			tagContext.addElement(cstName, cstRootNode, HAPConstant.UIRESOURCE_CONTEXTTYPE_INTERNAL);
		}
		
		HAPConfigureContextProcessor configure = new HAPConfigureContextProcessor();
		configure.inheritMode = HAPUtilityContext.getContextGroupInheritMode(tagDefinitionContext);
		HAPContextGroup out = HAPProcessorContext.process1(tagContext, parentContext, configure, contextProcessorEnv);
		return out;
	}
}
