package com.nosliw.data.core.expression;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.component.HAPManagerResourceDefinition;
import com.nosliw.data.core.component.attachment.HAPAttachmentContainer;
import com.nosliw.data.core.resource.HAPEntityWithResourceContext;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.service.provide.HAPManagerServiceDefinition;


public class HAPManagerExpression {
	private HAPManagerResourceDefinition m_resourceDefManager;

	private HAPRequirementContextProcessor m_contextProcessRequirement;

	public HAPManagerExpression(
			HAPManagerResourceDefinition resourceDefManager,
			HAPDataTypeHelper dataTypeHelper,
			HAPRuntime runtime,
			HAPManagerServiceDefinition serviceDefinitionManager
			) {
		this.m_resourceDefManager = resourceDefManager;
		this.m_contextProcessRequirement = new HAPRequirementContextProcessor(this.m_resourceDefManager, dataTypeHelper, runtime, this, serviceDefinitionManager, null);
	}
	
	public HAPResourceDefinitionExpressionSuite getExpressionSuiteDefinition(HAPResourceId suiteId, HAPAttachmentContainer parentAttachment) {
		HAPResourceDefinitionExpressionSuite suiteDef = (HAPResourceDefinitionExpressionSuite)this.m_resourceDefManager.getAdjustedComplextResourceDefinition(suiteId, parentAttachment);
		return suiteDef;
	}

	public HAPResourceDefinitionExpression getExpressionDefinition(HAPResourceId expressionId, HAPAttachmentContainer parentAttachment) {
		HAPResourceDefinitionExpression expressionDef = (HAPResourceDefinitionExpression)this.m_resourceDefManager.getAdjustedComplextResourceDefinition(expressionId, parentAttachment);
		return expressionDef;
	}
	
	public HAPEntityWithResourceContext getExpressionDefinitionWithContext(HAPResourceId processId, HAPAttachmentContainer parentAttachment) {
		HAPResourceDefinitionExpression expressionDef = this.getExpressionDefinition(processId, parentAttachment);
		HAPEntityWithResourceContext out = new HAPEntityWithResourceContext(expressionDef, HAPContextExpression.createContext(expressionDef.getSuite(), this.m_resourceDefManager));
		return out;
	}

	public HAPExecutableExpression getExpression(HAPResourceId expressionId, HAPContextExpression context, Map<String, String> configure) {
		if(context==null)  context = HAPContextExpression.createContext(this.m_resourceDefManager);
		HAPEntityWithResourceContext resourceDefWithContext = context.getResourceDefinition(expressionId);
		
		if(configure==null) {
			//build configure from definition info
			HAPResourceDefinitionExpression expressionDef = (HAPResourceDefinitionExpression)resourceDefWithContext.getEntity();
			configure = new LinkedHashMap<String, String>();
			for(String n : expressionDef.getInfo().getNames()) {
				configure.put(n, (String)expressionDef.getInfo().getValue(n)); 
			}
		}
		
		HAPExecutableExpression out = HAPProcessorExpression.process(
				expressionId.toStringValue(HAPSerializationFormat.LITERATE), 
				resourceDefWithContext, 
				null, 
				null, 
				this, 
				configure, 
				m_contextProcessRequirement,
				new HAPProcessTracker());
		return out;
	}
}
