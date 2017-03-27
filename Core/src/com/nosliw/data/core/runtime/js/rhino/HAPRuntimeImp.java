package com.nosliw.data.core.runtime.js.rhino;

import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.expression.HAPExpression;
import com.nosliw.data.core.runtime.HAPResource;
import com.nosliw.data.core.runtime.HAPResourceId;
import com.nosliw.data.core.runtime.HAPResourceManager;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;

public class HAPRuntimeImp implements HAPRuntime{

	private HAPResourceManager m_resourceMan;
	
	@Override
	public HAPRuntimeInfo getRuntimeInfo() {
		return new HAPRuntimeInfo(HAPConstant.RUNTIME_LANGUAGE_JS, HAPConstant.RUNTIME_ENVIRONMENT_RHINO);
	}

	@Override
	public HAPData executeExpression(HAPExpression expression) {
		Set<HAPResourceId> resourcesId = this.getResourceManager().discoverResourceRequirement(expression);
		
		Set<HAPResourceId> missedResourceId = this.findMissedResources(resourcesId);
		this.loadResources(missedResourceId);
		
		HAPData out = this.execute(expression);
		
		return out;
	}

	@Override
	public HAPResourceManager getResourceManager() {
		return this.m_resourceMan;
	}

	private Scriptable initScope(HAPExpression expression){
		Scriptable out = null;
		Context context = Context.enter();
	    try {
	        Scriptable scope = this.initEsencialScope(context, null);
	        scope = this.initRelatedResource(expression, context, scope);
	        
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    return out;
	}
	
	/**
	 * Init resources related with expression
	 * @param context
	 * @param parent
	 * @return
	 */
	private Scriptable initRelatedResource(HAPExpression expression, Context context, Scriptable parent){
		return null;
	}
	
	/**
	 * Init essencial object, include base, all the library for expression and all basic data type
	 * @param context
	 * @param parent
	 * @return
	 */
	private Scriptable initEsencialScope(Context context, Scriptable parent){
		Scriptable out = context.initStandardObjects(null);
		
		//library
		
		//data type
		
		return out;
	}
	
	private Set<HAPResourceId> findMissedResources(Set<HAPResourceId> resourcesId){
		return null;
	}
	
	private Map<HAPResourceId, HAPResource> loadResources(Set<HAPResourceId> resourcesId){
		return null;
	}
	
	private HAPData execute(HAPExpression expression){
		return null;
	}
	
}
