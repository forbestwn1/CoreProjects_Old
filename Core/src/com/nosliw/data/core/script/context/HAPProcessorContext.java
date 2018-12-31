package com.nosliw.data.core.script.context;

public class HAPProcessorContext {

	public static HAPContextGroup process(HAPContextGroup contextGroup, HAPContextGroup parentContextGroup, HAPConfigureContextProcessor configure, HAPEnvContextProcessor contextProcessorEnv) {
		HAPContextGroup out = processStatic(contextGroup, parentContextGroup, configure, contextProcessorEnv);
		out = processRelative(out, parentContextGroup, configure, contextProcessorEnv);
		return out;
	}
	
	
	//merge child context with parent context
	public static HAPContextGroup processStatic(HAPContextGroup contextGroup, HAPContextGroup parentContextGroup, HAPConfigureContextProcessor configure, HAPEnvContextProcessor contextProcessorEnv) {

		if(configure==null)  configure = new HAPConfigureContextProcessor();
		
		//figure out all constant values in context
		contextGroup = HAPProcessorContextConstant.process(contextGroup, parentContextGroup, configure.inheritMode, contextProcessorEnv);
		
		//solidate name in context  
		contextGroup = HAPProcessorContextSolidate.process(contextGroup, contextProcessorEnv);
		
		//process inheritance
		contextGroup = HAPProcessorContextInheritance.process(contextGroup, parentContextGroup, configure.inheritMode, contextProcessorEnv);
		
		return contextGroup;
		
	}
	
	public static HAPContextGroup processRelative(HAPContextGroup contextGroup, HAPContextGroup parentContextGroup, HAPConfigureContextProcessor configure, HAPEnvContextProcessor contextProcessorEnv) {
		
		if(configure==null)  configure = new HAPConfigureContextProcessor();
		
		//resolve relative context
		contextGroup = HAPProcessorContextRelative.process(contextGroup, parentContextGroup, configure, contextProcessorEnv);
		
		return contextGroup;
		
	}
}
