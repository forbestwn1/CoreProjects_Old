package com.nosliw.data.core.script.context;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.expression.HAPMatcherUtility;
import com.nosliw.data.core.expression.HAPMatchers;

/**
 * Context element that based on context element on parent
 * When tag has its own variable definition which is different from definition from parent, 
 * we should treat those two variables as different variables 
 * And matcher is needed to do convert between these two variables  
 */
public class HAPContextNodeRootRelative extends HAPContextNodeRootVariable{

	@HAPAttribute
	public static final String PATH = "path";

	//whether related to parent context or just to sibling root
	@HAPAttribute
	public static final String ISTOPARENT = "isToParent";

	@HAPAttribute
	public static final String PARENTCATEGARY = "parentCategary";
	
	@HAPAttribute
	public static final String MATCHERS = "matchers";

	@HAPAttribute
	public static final String REVERSEMATCHERS = "reverseMatchers";
	
	//relative path from parent context
	private HAPContextPath m_path;
	private String m_pathStr;
	
	//context node full name --- matchers
	//used to convert data from parent to data within uiTag
	private Map<String, HAPMatchers> m_matchers;

	private Map<String, HAPMatchers> m_reverseMatchers;
	
	private boolean m_processed = false;
	
	public HAPContextNodeRootRelative() {
		this.m_matchers = new LinkedHashMap<String, HAPMatchers>();
		this.m_reverseMatchers = new LinkedHashMap<String, HAPMatchers>();
	}
	
	@Override
	public String getType() {		return HAPConstant.UIRESOURCE_ROOTTYPE_RELATIVE;	}

	public void processed() {   this.m_processed = true;   }
	public boolean isProcessed() {  return this.m_processed;   }
	
	public void setPath(HAPContextPath path){	
		this.m_path = path;
		this.m_pathStr = null;
	}
	public void setPath(String path) {  
		this.m_pathStr = path;
		this.m_path = null;
	}
	public void setPath(String categary, String path) {  
		this.m_path = new HAPContextPath(categary, path);
		this.m_pathStr = null;
	}
	public void setPath(String categary, String rootNodeName, String path) {  
		this.m_path = new HAPContextPath(categary, rootNodeName, path);    
		this.m_pathStr = null;
	}

	public HAPContextPath getPath() {
		if(this.m_path==null && HAPBasicUtility.isStringNotEmpty(m_pathStr)) {
			this.m_path = new HAPContextPath(this.m_pathStr);
		}
		return this.m_path;
	}
	public String getPathStr() {
		if(this.m_path!=null && HAPBasicUtility.isStringEmpty(m_pathStr)) {
			this.m_pathStr = this.m_path.getFullPath();
		}
		return this.m_pathStr;
	}
	
	public String getParentCategary() {		return this.getPath().getRootElementId().getCategary();	}
	
	public boolean isRelativeToParent() {	return !HAPBasicUtility.isStringEmpty(this.getPath().getRootElementId().getCategary());	}
	
	public void setMatchers(Map<String, HAPMatchers> matchers){
		this.m_matchers.clear();
		this.m_matchers.putAll(matchers);
		this.m_reverseMatchers.clear();
		for(String name : matchers.keySet()) {
			this.m_reverseMatchers.put(name, HAPMatcherUtility.reversMatchers(matchers.get(name)));
		}
	}

	@Override
	public HAPContextNodeRoot cloneContextNodeRoot() {
		HAPContextNodeRootRelative out = new HAPContextNodeRootRelative();
		this.toContextNodeRootVariable(out);
		out.m_pathStr = this.m_pathStr;
		if(this.m_path!=null)	out.m_path = this.m_path.clone();
		
		for(String name : this.m_matchers.keySet()) 	out.m_matchers.put(name, this.m_matchers.get(name).cloneMatchers());
		for(String name : this.m_reverseMatchers.keySet())   out.m_reverseMatchers.put(name, this.m_reverseMatchers.get(name).cloneMatchers());
		return out;
	}
	
	@Override
	public HAPContextNodeRoot toSolidContextNodeRoot(Map<String, Object> constants, HAPEnvContextProcessor contextProcessorEnv) {
		HAPContextNodeRootRelative out = new HAPContextNodeRootRelative();
		this.toSolidContextNode(out, constants, contextProcessorEnv);
		out.m_pathStr = HAPProcessorContextSolidate.getSolidName(this.getPathStr(), constants, contextProcessorEnv);
		out.m_path = null;
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PATH, this.getPath().toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ISTOPARENT, this.isRelativeToParent()+"");
		typeJsonMap.put(ISTOPARENT, Boolean.class);
		if(this.m_matchers!=null && !this.m_matchers.isEmpty()){
			jsonMap.put(MATCHERS, HAPJsonUtility.buildJson(this.m_matchers, HAPSerializationFormat.JSON));
			jsonMap.put(REVERSEMATCHERS, HAPJsonUtility.buildJson(this.m_reverseMatchers, HAPSerializationFormat.JSON));
		}
	}

}
