package com.nosliw.data.core.structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.info.HAPInfo;
import com.nosliw.common.path.HAPComplexPath;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.data.core.data.criteria.HAPInfoCriteria;
import com.nosliw.data.core.valuestructure.HAPContainerVariableCriteriaInfo;
import com.nosliw.data.core.valuestructure.HAPElementContextStructureValueExecutable;
import com.nosliw.data.core.valuestructure.HAPValueStructureDefinition;
import com.nosliw.data.core.valuestructure.HAPValueStructureDefinitionFlat;
import com.nosliw.data.core.valuestructure.HAPValueStructureDefinitionGroup;
import com.nosliw.data.core.valuestructure.HAPValueStructureExecutable;

public class HAPUtilityContext {

	public static Map<String, Object> discoverContantsValueFromContextStructure(HAPValueStructureDefinition contextStructure) {
		HAPValueStructureExecutable flatContext = buildFlatContextFromContextStructure(contextStructure);
		return flatContext.getConstantValue();
	}
	
	public static HAPValueStructureExecutable buildFlatContextFromContextStructure(HAPValueStructureDefinition contextStructure) {
		HAPValueStructureExecutable out = null;
		String type = contextStructure.getType();
		if(type.equals(HAPConstantShared.CONTEXTSTRUCTURE_TYPE_NOTFLAT)) {
			out = buildFlatContextFromContextGroup((HAPValueStructureDefinitionGroup)contextStructure);
		}
		else if(type.equals(HAPConstantShared.CONTEXTSTRUCTURE_TYPE_FLAT)) {
			out = buildFlatContextFromContext((HAPValueStructureDefinitionFlat)contextStructure);
		}
		return out;
	}

	public static HAPValueStructureExecutable buildFlatContextFromContext(HAPValueStructureDefinitionFlat context) {
		HAPValueStructureExecutable out = new HAPValueStructureExecutable();
		for(String name : context.getRootNames()) {
			out.addRoot(context.getRoot(name), name);
		}
		return out;
	}
	
	public static HAPValueStructureExecutable buildFlatContextFromContextGroup(HAPValueStructureDefinitionGroup context) {
		HAPValueStructureExecutable out = new HAPValueStructureExecutable();
		
		List<String> categarys = Arrays.asList(HAPValueStructureDefinitionGroup.getAllCategariesWithPriority());
		Collections.reverse(categarys);
		for(String categary : categarys) {
			Map<String, HAPRoot> eles = context.getRootsByCategary(categary);
			for(String localName : eles.keySet()) {
				String globalName = new HAPIdContextDefinitionRoot(categary, localName).getFullName();
				Set<String> aliases = new HashSet<String>();
				aliases.add(localName);
				aliases.add(globalName);
				out.addRoot(new HAPElementContextStructureValueExecutable(eles.get(localName), categary), globalName, aliases);
			}
		}
		return out;
	}
	
	public static HAPElement getDescendant(HAPValueStructureDefinition context, HAPReferenceElement path) {
		if(context.getType().equals(HAPConstantShared.CONTEXTSTRUCTURE_TYPE_NOTFLAT)) {
			return getDescendant((HAPValueStructureDefinitionGroup)context, path.getFullPath());
		}
		else {
			return getDescendant((HAPValueStructureDefinitionFlat)context, path.getPath());
		}
	}
	
	public static HAPElement getDescendant(HAPValueStructureDefinitionFlat context, String path) {
		HAPElement out = null;
		HAPComplexPath complexPath = new HAPComplexPath(path);
		HAPRoot root = context.getRoot(complexPath.getRootName());
		if(root!=null) {
			out = getDescendant(root.getDefinition(), complexPath.getPathStr());
		}
		return out;
	}
	
	public static HAPElement getDescendant(HAPValueStructureDefinitionGroup contextGroup, String categary, String path) {
		HAPElement out = null;
		HAPValueStructureDefinitionFlat context = contextGroup.getFlat(categary);
		if(context!=null)   out = getDescendant(context, path);
		return out;
	}

	public static HAPElement getDescendant(HAPValueStructureDefinitionGroup contextGroup, String path) {
		HAPReferenceElement contextPath = new HAPReferenceElement(path);
		return getDescendant(contextGroup, contextPath.getRootReference().getCategary(), contextPath.getPath());
	}
	
	public static void updateDataDescendant(HAPValueStructureDefinitionGroup contextGroup, String categary, String path, HAPElementLeafData dataEle) {
		updateDataDescendant(contextGroup.getFlat(categary), path, dataEle);
	}

	public static void updateDataDescendant(HAPValueStructureDefinitionFlat context, String path, HAPElementLeafData dataEle) {
		setDescendant(context, path, dataEle);
//		HAPComplexPath cpath = new HAPComplexPath(path);
//		HAPContextDefinitionRoot root = context.getElement(cpath.getRootName());
//		if(cpath.getPathSegs().length==0 && root!=null && root.getDefinition().getType().equals(HAPConstant.CONTEXT_ELEMENTTYPE_DATA)) {
//			//for data root replacement, not replace whole, just replace criteria
//			((HAPContextDefinitionLeafData)root.getDefinition()).setCriteria(dataEle.getCriteria());
//		}
//		else {
//			setDescendant(context, path, dataEle);
//		}
	}

	public static void setDescendant(HAPValueStructureDefinition contextStructure, HAPReferenceElement contextPath, HAPElement ele) {
		HAPRoot targetRoot = contextStructure.getRoot(contextPath.getRootReference().getFullName(), true);

	}
	
	public static void setDescendant(HAPValueStructureDefinitionGroup contextGroup, String categary, String path, HAPElement ele) {
//		setDescendant(contextGroup.getContext(categary), path, ele);
		setDescendant(contextGroup, new HAPReferenceElement(categary, path), ele);
	}

	public static void setDescendant(HAPValueStructureDefinitionFlat context, String path, HAPElement ele) {
		setDescendant(context, new HAPReferenceElement((String)null, path), ele);
	}
	
	public static boolean isContextDefinitionElementConstant(HAPElement ele) {   return HAPConstantShared.CONTEXT_ELEMENTTYPE_CONSTANT.equals(ele.getType());   }
	
	//context root name can be like a.b.c and a.b.d
	//these two root name can be consolidated to one root name with a and child of b.c and b.d
	public static HAPValueStructureDefinitionFlat consolidateContextRoot(HAPValueStructureDefinitionFlat context) {
		HAPValueStructureDefinitionFlat out = new HAPValueStructureDefinitionFlat();
		
		for(String rootName : context.getRootNames()) {
			HAPElement def = context.getRoot(rootName).getDefinition();
			HAPUtilityContext.setDescendant(out, rootName, def);
		}
		return out;
	}
	
	public static String getContextGroupInheritMode(HAPInfo info) {  
		String out = HAPConstant.INHERITMODE_CHILD;
		if("false".equals(info.getValue(HAPValueStructureDefinitionGroup.INFO_INHERIT)))  out = HAPConstant.INHERITMODE_NONE;
		return out;				
	}
 
	public static void setContextGroupInheritModeNone(HAPInfo info) {		info.setValue(HAPValueStructureDefinitionGroup.INFO_INHERIT, "false");	}
	public static void setContextGroupInheritModeChild(HAPInfo info) {		info.setValue(HAPValueStructureDefinitionGroup.INFO_INHERIT, "true");	}
	
	public static boolean getContextGroupPopupMode(HAPInfo info) {  
		boolean out = true;
		if("false".equals(info.getValue(HAPValueStructureDefinitionGroup.INFO_POPUP)))  out = false;
		return out;				
	} 
 
	public static boolean getContextGroupEscalateMode(HAPInfo info) {  
		boolean out = false;
		if("true".equals(info.getValue(HAPValueStructureDefinitionGroup.INFO_ESCALATE)))  out = true;
		return out;				
	} 
	
	public static HAPValueStructureDefinitionGroup buildContextGroupFromContext(HAPValueStructureDefinitionFlat context) {
		HAPValueStructureDefinitionGroup out = new HAPValueStructureDefinitionGroup();
		for(String rootName : context.getRootNames()) {
			HAPRoot root = context.getRoot(rootName);
			HAPIdContextDefinitionRoot rootId = new HAPIdContextDefinitionRoot(rootName);
			out.addRoot(rootId.getName(), root, rootId.getCategary());
		}
		return out;
	}

	public static HAPContainerVariableCriteriaInfo discoverDataVariablesInContext(HAPValueStructureExecutable context) {
		HAPContainerVariableCriteriaInfo out = new HAPContainerVariableCriteriaInfo();
		Map<String, HAPInfoCriteria> dataVarsInfoByIdPath = discoverDataVariablesInStructure(context.getContext());
		for(String idPath : dataVarsInfoByIdPath.keySet()) {
			HAPComplexPath path = new HAPComplexPath(idPath);
			String id = path.getRootName();
			Set<String> aliases = context.getAliasById(id);
			Set<String> aliasesPath = new HashSet<String>();
			for(String alias : aliases) {
				HAPComplexPath aliasPath = new HAPComplexPath(alias, path.getPathStr());
				aliasesPath.add(aliasPath.getFullName());
			}
			out.addVariableCriteriaInfo(dataVarsInfoByIdPath.get(idPath), aliasesPath);
		}
		return out;
	}
	
	//build interited node from parent
	public static HAPRoot createRelativeContextDefinitionRoot(HAPValueStructureDefinitionGroup parentContextGroup, String contextCategary, String refPath, Set<String> excludedInfo) {
		return createRootWithRelativeElement(parentContextGroup.getElement(contextCategary, refPath), contextCategary, refPath, excludedInfo);
	}

	public static HAPInfoReferenceResolve resolveReferencedContextElement(HAPReferenceElement contextPath, HAPValueStructureDefinition parentContext){
		if(parentContext==null)   return null;
		HAPInfoReferenceResolve out = null;
		String contextType = parentContext.getType();
		if(contextType.equals(HAPConstantShared.CONTEXTSTRUCTURE_TYPE_NOTFLAT)) {
			out = analyzeElementReference(contextPath, (HAPValueStructureDefinitionGroup)parentContext, null, null);
		}
		else {
			out = ((HAPValueStructureDefinitionFlat)parentContext).discoverChild(contextPath.getRootReference().getName(), contextPath.getSubPath());
			//process remaining path
			out = processContextElementRefResolve(out);
		}
		
		return out;
	}
	
	public static HAPValueStructureDefinition hardMerge(HAPValueStructureDefinition child, HAPValueStructureDefinition parent) {
		if(child==null) return parent.cloneStructure();
		if(parent==null)  return child.cloneStructure();
		
		String type1 = child.getType();
		String type2 = parent.getType();
		if(!type1.equals(type2))  throw new RuntimeException();
		
		HAPValueStructureDefinition out = null;
		out = child.cloneStructure();
		out.hardMergeWith(parent);
		return out;
	}

	
	public static HAPValueStructureDefinitionGroup hardMerge(HAPValueStructureDefinitionGroup child, HAPValueStructureDefinitionGroup parent) {
		HAPValueStructureDefinitionGroup out = null;
		if(child==null) out = parent.cloneContextGroup();
		else {
			out = child.cloneContextGroup();
			out.hardMergeWith(parent);;
		}
		return out;
	}
	
	


}
