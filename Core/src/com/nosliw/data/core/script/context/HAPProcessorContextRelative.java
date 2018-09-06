package com.nosliw.data.core.script.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.expression.HAPMatchers;

public class HAPProcessorContextRelative {

	public static HAPContextGroup process(HAPContextGroup contextGroup, HAPContextGroup parentContextGroup, HAPConfigureContextProcessor configure, HAPEnvContextProcessor contextProcessorEnv) {
		HAPContextGroup out = new HAPContextGroup(contextGroup.getInfo());
		for(String categary : HAPContextGroup.getAllContextTypes()){
			Map<String, HAPContextNodeRoot> eles = contextGroup.getElements(categary);
			for(String eleName : eles.keySet()) {
				HAPContextNodeRoot node = eles.get(eleName);
				if(node.getType().equals(HAPConstant.UIRESOURCE_ROOTTYPE_RELATIVE)) {
					out.addElement(eleName, processRelativeContextDefinitionElement((HAPContextNodeRootRelative)node, parentContextGroup, configure, contextProcessorEnv), categary);
				}
				else {
					out.addElement(eleName, node, categary);
				}
			}
		}
		return out;
	}
	
	private static HAPContextNodeRootRelative processRelativeContextDefinitionElement(HAPContextNodeRootRelative defContextElementRelative, HAPContextGroup parentContext, HAPConfigureContextProcessor configure, HAPEnvContextProcessor contextProcessorEnv){
		if(defContextElementRelative.isProcessed())   return (HAPContextNodeRootRelative)defContextElementRelative.cloneContextNodeRoot();
		
		if(parentContext==null)  throw new RuntimeException();
		List<String> categaryes = new ArrayList<String>();
		if(defContextElementRelative.getParentCategary()!=null) categaryes.add(defContextElementRelative.getParentCategary());
		else if(configure.parentCategary==null)   categaryes.addAll(Arrays.asList(HAPContextGroup.getVisibleContextTypes()));
		else   categaryes.addAll(Arrays.asList(configure.parentCategary));
		return processRelativeContextDefinitionElement(defContextElementRelative, parentContext, categaryes.toArray(new String[0]), configure.relativeResolveMode, contextProcessorEnv);
	}
	
	private static HAPContextNodeRootRelative processRelativeContextDefinitionElement(HAPContextNodeRootRelative defContextElementRelative, HAPContextGroup parentContext, String[] categaryes, String mode, HAPEnvContextProcessor contextProcessorEnv){
		HAPContextNodeRootRelative out = new HAPContextNodeRootRelative();
		HAPContextPath path = defContextElementRelative.getPath(); 
		
		HAPInfoRelativeContextResolve resolveInfo = HAPUtilityContext.resolveReferencedParentContextNode(path, parentContext, categaryes, mode);
		path.getRootElementId().setCategary(resolveInfo.contextPath.getRootElementId().getCategary());
		out.setPath(path);
		
		HAPContextNode parentNode = resolveInfo.resolvedNode; 
		if(parentNode!=null){
			Map<String, HAPMatchers> matchers = new LinkedHashMap<String, HAPMatchers>();
			merge(parentNode, defContextElementRelative, out, matchers, null, contextProcessorEnv);
			//remove all the void matchers
			Map<String, HAPMatchers> noVoidMatchers = new LinkedHashMap<String, HAPMatchers>();
			for(String p : matchers.keySet()){
				HAPMatchers match = matchers.get(p);
				if(!match.isVoid()){
					noVoidMatchers.put(p, match);
				}
			}
			out.setMatchers(noVoidMatchers);
		}
		else{
			//not find parent node, throw exception
			throw new RuntimeException();
		}
		return out;
	}

	//merge parent context def with child context def to another context out
	//also generate matchers from parent to child
	private static void merge(HAPContextNode parent, HAPContextNode def, HAPContextNode out, Map<String, HAPMatchers> matchers, String path, HAPEnvContextProcessor contextProcessorEnv){
		HAPContextNodeCriteria parentDefinition = parent.getDefinition();
		if(parentDefinition!=null){
			HAPContextNodeCriteria defDefinition = null;
			if(def!=null)			defDefinition = def.getDefinition();
			if(defDefinition==null)   out.setDefinition(parentDefinition);
			else{
				out.setDefinition(defDefinition);
				//cal matchers
				HAPMatchers matcher = contextProcessorEnv.dataTypeHelper.convertable(parentDefinition.getValue(), defDefinition.getValue());
				matchers.put(path, matcher);
			}
		}
		else{
			for(String name : parent.getChildren().keySet()){
				HAPContextNode outChild = new HAPContextNode();
				out.addChild(name, outChild);
				HAPContextNode defChild = null;
				if(def!=null && def.getChildren().get(name)!=null){
					defChild = def.getChildren().get(name);
				}
				merge(parent.getChildren().get(name), defChild, outChild, matchers, HAPNamingConversionUtility.cascadePath(path, name), contextProcessorEnv);
			}
		}
	}
}