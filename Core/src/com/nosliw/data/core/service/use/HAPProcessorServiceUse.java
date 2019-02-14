package com.nosliw.data.core.service.use;

import java.util.Map;

import com.nosliw.data.core.script.context.HAPConfigureContextProcessor;
import com.nosliw.data.core.script.context.HAPContext;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.script.context.dataassociation.HAPDataAssociationIO;
import com.nosliw.data.core.script.context.dataassociation.HAPDefinitionDataAssociationGroup;
import com.nosliw.data.core.script.context.dataassociation.HAPExecutableDataAssociationGroupWithTarget;
import com.nosliw.data.core.script.context.dataassociation.HAPProcessorDataAssociation;
import com.nosliw.data.core.service.interfacee.HAPServiceInterface;

public class HAPProcessorServiceUse {

	public static HAPExecutableServiceUse process(
			HAPDefinitionServiceUse definition,
			HAPServiceInterface providerInterface,
			HAPDataAssociationIO globalContext, 
			HAPConfigureContextProcessor configure, 
			HAPRequirementContextProcessor contextProcessRequirement) {
		HAPExecutableServiceUse out = new HAPExecutableServiceUse(definition);
		
		HAPDefinitionMappingService serviceMapping = definition.getServiceMapping();
		
		//process parm mapping
		HAPExecutableDataAssociationGroupWithTarget processedParms = HAPProcessorDataAssociation.processDataAssociation(globalContext, serviceMapping.getParms(), HAPUtilityServiceUse.buildContextFromServiceParms(providerInterface), false, contextProcessRequirement);
		out.setParmMapping(processedParms);
		 
		//process result mapping
		Map<String, HAPDefinitionDataAssociationGroup> resultMapping = serviceMapping.getResultMapping();
		for(String result :resultMapping.keySet()) {
			HAPContext outputContext = HAPUtilityServiceUse.buildContextFromResultServiceOutputs(providerInterface, result); 
			HAPExecutableDataAssociationGroupWithTarget processedResult = HAPProcessorDataAssociation.processDataAssociation(outputContext, resultMapping.get(result), globalContext, false, contextProcessRequirement);
			out.addResultMapping(result, processedResult);
		}
		return out;
	}
	
}