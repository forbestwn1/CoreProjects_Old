package com.nosliw.uiresource.page.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.HAPDataType;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPDataTypeManager;
import com.nosliw.data.core.component.HAPManagerResourceDefinition;
import com.nosliw.data.core.criteria.HAPCriteriaUtility;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.expression.HAPManagerExpression;
import com.nosliw.data.core.runtime.HAPRuntime;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;
import com.nosliw.data.core.service.interfacee.HAPServiceInterface;
import com.nosliw.data.core.service.interfacee.HAPServiceParm;
import com.nosliw.data.core.service.provide.HAPManagerService;
import com.nosliw.data.core.service.provide.HAPManagerServiceDefinition;
import com.nosliw.data.core.story.HAPAliasElement;
import com.nosliw.data.core.story.HAPInfoElement;
import com.nosliw.data.core.story.HAPReferenceElement;
import com.nosliw.data.core.story.HAPStory;
import com.nosliw.data.core.story.HAPUtilityConnection;
import com.nosliw.data.core.story.HAPUtilityStory;
import com.nosliw.data.core.story.change.HAPCalculateObjectElementId;
import com.nosliw.data.core.story.change.HAPChangeItem;
import com.nosliw.data.core.story.change.HAPChangeItemNew;
import com.nosliw.data.core.story.change.HAPRequestChange;
import com.nosliw.data.core.story.change.HAPRequestChangeWrapper;
import com.nosliw.data.core.story.change.HAPResultTransaction;
import com.nosliw.data.core.story.change.HAPUtilityChange;
import com.nosliw.data.core.story.design.HAPAnswer;
import com.nosliw.data.core.story.design.HAPBuilderStory;
import com.nosliw.data.core.story.design.HAPDesignStep;
import com.nosliw.data.core.story.design.HAPDesignStory;
import com.nosliw.data.core.story.design.HAPQuestionGroup;
import com.nosliw.data.core.story.design.HAPQuestionItem;
import com.nosliw.data.core.story.design.HAPRequestDesign;
import com.nosliw.data.core.story.design.HAPResponseDesign;
import com.nosliw.data.core.story.design.HAPStageInfo;
import com.nosliw.data.core.story.design.HAPUtilityDesign;
import com.nosliw.data.core.story.element.connectiongroup.HAPElementGroupBatch;
import com.nosliw.data.core.story.element.connectiongroup.HAPElementGroupSwitch;
import com.nosliw.data.core.story.element.node.HAPStoryNodeConstant;
import com.nosliw.data.core.story.element.node.HAPStoryNodeService;
import com.nosliw.data.core.story.element.node.HAPStoryNodeServiceInput;
import com.nosliw.data.core.story.element.node.HAPStoryNodeServiceInputParm;
import com.nosliw.data.core.story.element.node.HAPStoryNodeVariable;
import com.nosliw.uiresource.common.HAPUtilityCommon;
import com.nosliw.uiresource.page.story.element.HAPStoryNodeUIData;
import com.nosliw.uiresource.page.story.element.HAPStoryNodeUIHtml;
import com.nosliw.uiresource.page.story.model.HAPUIDataInfo;
import com.nosliw.uiresource.page.story.model.HAPUINode;
import com.nosliw.uiresource.page.story.model.HAPUITree;
import com.nosliw.uiresource.page.story.model.HAPUtility;
import com.nosliw.uiresource.page.tag.HAPUITagManager;
import com.nosliw.uiresource.page.tag.HAPUITagQueryResult;
import com.nosliw.uiresource.page.tag.HAPUITageQuery;

public class HAPStoryBuilderPageSimple2 implements HAPBuilderStory{

	public final static String BUILDERID = "pageSimple";
	
	public final static String STAGE_SERVICE = "service";
	public final static String STAGE_UI = "ui";
	public final static String STAGE_END = "end";

	private final static HAPAliasElement ELEMENT_SERVICE = new HAPAliasElement("service", false);
	
	private HAPManagerService m_serviceManager;
	private HAPUITagManager m_uiTagManager;
	private HAPRequirementContextProcessor m_contextProcessRequirement;

	private List<HAPStageInfo> m_stages;
	
	public HAPStoryBuilderPageSimple2(
			HAPManagerService serviceManager, 
			HAPUITagManager uiTagMan, 
			HAPManagerResourceDefinition resourceDefMan,
			HAPDataTypeHelper dataTypeHelper, 
			HAPRuntime runtime, 
			HAPManagerExpression expressionMan,
			HAPManagerServiceDefinition serviceDefinitionManager) {
		this.m_serviceManager = serviceManager;
		this.m_uiTagManager = uiTagMan;
		this.m_contextProcessRequirement = HAPUtilityCommon.getDefaultContextProcessorRequirement(resourceDefMan, dataTypeHelper, runtime, expressionMan, serviceDefinitionManager);
		this.m_stages = new ArrayList<HAPStageInfo>();
		this.m_stages.add(new HAPStageInfo(STAGE_SERVICE, STAGE_SERVICE));
		this.m_stages.add(new HAPStageInfo(STAGE_UI, STAGE_UI));
		this.m_stages.add(new HAPStageInfo(STAGE_END, STAGE_END));
	}
	
	@Override
	public void initDesign(HAPDesignStory design) {
		HAPStory story = design.getStory();

		story.setShowType(HAPConstant.RUNTIME_RESOURCE_TYPE_UIRESOURCE);
		HAPUtilityDesign.setDesignAllStages(design, m_stages);
		
		story.startTransaction();
		HAPRequestChange changeRequest = new HAPRequestChange();
		changeRequest.addChange(new HAPChangeItemNew(HAPUtility.buildPageStoryNode(story)));
		changeRequest.addChange(new HAPChangeItemNew(new HAPStoryNodeService(), ELEMENT_SERVICE));
		story.change(changeRequest);
		HAPResultTransaction transactionResult = story.commitTransaction();
		
		HAPDesignStep step = design.newStep();
		step.addChanges(transactionResult.getChanges());
		
		//extra info
		HAPQuestionGroup rootQuestionGroup = new HAPQuestionGroup("Please select service.");
		HAPQuestionItem serviceItemExtraInfo = new HAPQuestionItem("select service", ELEMENT_SERVICE);
		rootQuestionGroup.addChild(serviceItemExtraInfo);
		step.setQuestion(rootQuestionGroup);
		
		//stage
		HAPUtilityDesign.setChangeStage(step, STAGE_SERVICE);

		design.addStep(step);
	}

	@Override
	public HAPServiceData buildStory(HAPDesignStory storyDesign, HAPRequestDesign answer) {
		HAPServiceData out = null;
		String stage = HAPUtilityDesign.getDesignStage(storyDesign);
		if(stage.equals(STAGE_SERVICE)) {
			out = this.processServiceStage(storyDesign, answer);
		}
		else if(stage.equals(STAGE_UI)) {
			out = this.processUIChangeStage(storyDesign, answer);
		}
		else if(stage.equals(STAGE_END)) {
			
		}
		
		return out;
	}
	
	private HAPServiceData validateServiceAnswer(HAPDesignStory design, HAPRequestDesign answerRequest) {
		HAPStory story = design.getStory();

		HAPRequestChange changeRequest = new HAPRequestChange(false);
		for(HAPAnswer answer : answerRequest.getAnswers()){		changeRequest.addChanges(answer.getChanges());	}
		for(HAPChangeItem change : answerRequest.getExtraChanges()) {   changeRequest.addChange(change);    }
		story.change(changeRequest);
		
		HAPStoryNodeService serviceStoryNode = (HAPStoryNodeService)HAPUtilityStory.getAllStoryNodeByType(story, HAPConstant.STORYNODE_TYPE_SERVICE).iterator().next();
		if(!HAPBasicUtility.isStringEmpty(serviceStoryNode.getReferenceId())){
			//valid
			//add answer to step
			HAPDesignStep latestStep = design.getLatestStep();
			latestStep.addAnswers(answerRequest.getAnswers());
			return HAPServiceData.createSuccessData();
		}
		else {
			//failure
			//revert answer changes
			story.rollbackTransaction();
			//
			String[] errorMsg = {"Service should not be empty!!"};
			return HAPServiceData.createFailureData(errorMsg, "Validation Fail!!");
		}
	}
	
	private HAPServiceData processServiceStage(HAPDesignStory design, HAPRequestDesign answer) {
		HAPStory story = design.getStory();
		
		story.startTransaction();
		
		HAPUITree uiTree = HAPUtility.buildUITree(story, this.m_contextProcessRequirement, this.m_uiTagManager);
		
		HAPServiceData validateResult = validateServiceAnswer(design, answer);
		if(validateResult.isFail())   return validateResult;
		else {
			HAPRequestChangeWrapper changeRequest = new HAPRequestChangeWrapper(story);

			//new step
			HAPDesignStep step = design.newStep();

			//question
			HAPQuestionGroup rootQuestionGroup = new HAPQuestionGroup("Please select ui.");
			step.setQuestion(rootQuestionGroup);

			//page layout node
			HAPUINode pageLayoutUINode = uiTree.newChildNode(new HAPStoryNodeUIHtml(HAPFileUtility.readFile(HAPStoryBuilderPageSimple2.class, "page_html.tmp")), null, changeRequest, m_contextProcessRequirement, m_uiTagManager);
			
			//get service node
			HAPStoryNodeService serviceStoryNode = (HAPStoryNodeService)HAPUtilityStory.getAllStoryNodeByType(story, HAPConstant.STORYNODE_TYPE_SERVICE).iterator().next();
			HAPServiceInterface serviceInterface = this.m_serviceManager.getServiceDefinitionManager().getDefinition(serviceStoryNode.getReferenceId()).getStaticInfo().getInterface();
			
			{
				//service input
				HAPAliasElement serviceInputNodeName = changeRequest.addNewChange(new HAPStoryNodeServiceInput());
//				HAPChangeInfo serviceInputNewChange = HAPUtilityChange.applyNew(story, new HAPStoryNodeServiceInput(), step.getChanges());
				changeRequest.addNewChange(HAPUtilityConnection.newConnectionContain(serviceStoryNode.getElementId(), serviceInputNodeName, HAPConstant.SERVICE_CHILD_INPUT));
//				changeRequest.addChange(new HAPChangeItemNew(HAPUtilityConnection.newConnectionContain(serviceStoryNode.getElementId(), serviceInputNodeName, HAPConstant.SERVICE_CHILD_INPUT)));
//				HAPUtilityChange.applyNew(story, HAPUtilityConnection.newConnectionContain(serviceStoryNode.getId(), serviceInputNewChange.getStoryElement().getId(), HAPConstant.SERVICE_CHILD_INPUT), step.getChanges());
				
				//parms
				for(String parmName : serviceInterface.getParmNames()) {
					
					HAPQuestionGroup parmQuestionGroup = new HAPQuestionGroup("Configure Input Parm " + parmName);
					rootQuestionGroup.addChild(parmQuestionGroup);
					
					//parm and connection to input
					HAPServiceParm parmDef = serviceInterface.getParm(parmName);
					
					HAPAliasElement parmNodeName = changeRequest.addNewChange(new HAPStoryNodeServiceInputParm(parmDef));
//					HAPChangeInfo parmNewChange = HAPUtilityChange.applyNew(story, new HAPStoryNodeServiceInputParm(parmDef), step.getChanges());
//					HAPChangeInfo parmConnectionNewChange = HAPUtilityChange.applyNew(story, HAPUtilityConnection.newConnectionContain(serviceInputNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), parmName), step.getChanges());
					changeRequest.addNewChange(HAPUtilityConnection.newConnectionContain(serviceInputNodeName, parmNodeName, parmName));

					HAPAliasElement constantNodeName = changeRequest.addNewChange(new HAPStoryNodeConstant(parmDef.getCriteria()));
//					HAPChangeInfo parmConstantProviderNewChange = HAPUtilityChange.applyNew(story, new HAPStoryNodeConstant(parmDef.getCriteria()), step.getChanges(), constantBatchGroup);
					
					HAPAliasElement constantConnectionNodeName = changeRequest.addNewChange(HAPUtilityConnection.newConnectionOnewayDataIO(constantNodeName, parmNodeName, null, null));
//					HAPChangeInfo parmConstantProviderConnectionNewChange = HAPUtilityChange.applyNew(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmConstantProviderNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), null, null), step.getChanges(), constantBatchGroup);
//					HAPChangeInfo constantProviderGroupNewChange = HAPUtilityChange.applyNew(story, constantBatchGroup, step.getChanges());

					//constant path and group
					HAPElementGroupBatch constantBatchGroup = new HAPElementGroupBatch();
					constantBatchGroup.addElement(new HAPInfoElement(constantNodeName));
					constantBatchGroup.addElement(new HAPInfoElement(constantConnectionNodeName));
					HAPAliasElement constantGroupName = changeRequest.addNewChange(constantBatchGroup);

					
					//variable path and group
					String variableName = parmName;
					HAPAliasElement variableNodeName = changeRequest.addNewChange(new HAPStoryNodeVariable(variableName, parmDef.getCriteria()));
//					HAPChangeInfo parmVariableProviderNewChange = HAPUtilityChange.applyNew(story, new HAPStoryNodeVariable(variableName, parmDef.getCriteria()), step.getChanges(), variableBatchGroup);

					HAPAliasElement variableConnectionNodeName = changeRequest.addNewChange(HAPUtilityConnection.newConnectionOnewayDataIO(variableNodeName, parmNodeName, null, null));
//					HAPChangeInfo parmVariableProviderConnectionNewChange = HAPUtilityChange.applyNew(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmVariableProviderNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), null, null), step.getChanges(), variableBatchGroup);

					HAPElementGroupBatch variableBatchGroup = new HAPElementGroupBatch();
					variableBatchGroup.addElement(new HAPInfoElement(variableNodeName));
					variableBatchGroup.addElement(new HAPInfoElement(variableConnectionNodeName));
					HAPAliasElement variableGroupName = changeRequest.addNewChange(variableBatchGroup);

					//ui
					HAPUINode dataUINode = buildDataUINode(pageLayoutUINode, "input", variableName, parmName, changeRequest);
					
					//variable group
					for(HAPReferenceElement eleRef : dataUINode.getAllStoryElements())   variableBatchGroup.addElement(new HAPInfoElement(eleRef));
					
//					HAPChangeInfo variableProviderGroupNewChange = HAPUtilityChange.applyNew(story, variableBatchGroup, step.getChanges());

					//switch group
					HAPElementGroupSwitch group = new HAPElementGroupSwitch();
					//add constant to group
					HAPInfoElement constantGroupEle = new HAPInfoElement(constantGroupName);
					constantGroupEle.setName("Constant");
					group.addElement(constantGroupEle);
					//add variable to group
					HAPInfoElement varGroupEle = new HAPInfoElement(variableGroupName);
					varGroupEle.setName("Variable");
					group.addElement(varGroupEle);
					HAPAliasElement switchGroupName = changeRequest.addNewChange(group);
//					HAPChangeInfo groupNewChange = HAPUtilityChange.applyNew(story, group, step.getChanges());

					//set switch group choice
					changeRequest.addPatchChange(switchGroupName, HAPElementGroupSwitch.CHOICE, new HAPCalculateObjectElementId(variableGroupName, story));
//					HAPUtilityChange.applyPatch(story, groupNewChange.getStoryElement().getElementId(), HAPElementGroupSwitch.CHOICE, variableProviderGroupNewChange.getStoryElement().getElementId().toStringValue(HAPSerializationFormat.LITERATE), step.getChanges());

					//question item
					HAPQuestionItem groupQuestion = new HAPQuestionItem("select import for parm", switchGroupName);
					parmQuestionGroup.addChild(groupQuestion);
					
					HAPQuestionItem constantQuestion = new HAPQuestionItem("select constant", constantNodeName);
					parmQuestionGroup.addChild(constantQuestion);
					
					HAPQuestionItem uiDataQuestion = new HAPQuestionItem("select ui tag", dataUINode.getStoryNodeRef());
					parmQuestionGroup.addChild(uiDataQuestion);
				}
			}
			
			{
//				HAPChangeInfo serviceOutputNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeServiceOutput(), step.getChanges());
//				HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(serviceStoryNode.getId(), serviceOutputNewChange.getStoryElement().getId(), HAPConstant.SERVICE_CHILD_RESULT), step.getChanges());
//				
//				HAPServiceResult successResult = serviceInterface.getResult("success");
//				Map<String, HAPServiceOutput> output = successResult.getOutput(); 
//				for(String parmName : output.keySet()) {
//					HAPQuestionGroup parmQuestionGroup = new HAPQuestionGroup("Configure Output Parm " + parmName);
//					rootQuestionGroup.addChild(parmQuestionGroup);
//					
//					//parm and connection to input
//					HAPServiceOutput parmDef = output.get(parmName);
//					HAPChangeInfo parmNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeServiceOutputItem(parmDef), step.getChanges());
//					HAPChangeInfo parmConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(serviceOutputNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), parmName), step.getChanges());
//
//					//variable path and group
//					HAPChangeInfo parmVariableNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeVariable(parmName, parmDef.getCriteria()), step.getChanges());
//					HAPChangeInfo parmVariableConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmNewChange.getStoryElement().getId(), parmVariableNewChange.getStoryElement().getId(), null, null), step.getChanges());
//
//					//ui container
//					HAPChangeInfo parmParmUIContainerNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(HAPFileUtility.readFile(HAPStoryBuilderPageSimple.class, "uiData.tmp")), step.getChanges());
//					HAPChangeInfo layoutToUIContainerConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(pageLayoutNewChange.getStoryElement().getId(), parmParmUIContainerNewChange.getStoryElement().getId(), "output"), step.getChanges());
//					HAPChangeInfo parmParmUILabelNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(parmName), step.getChanges());
//					HAPChangeInfo uiContainerToUILabelConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(parmParmUIContainerNewChange.getStoryElement().getId(), parmParmUILabelNewChange.getStoryElement().getId(), "label"), step.getChanges());
//
//					//ui for variable, add to page, connect with variable
//					HAPUITagQueryResult uiTagInfo = this.m_uiTagManager.getDefaultUITag(new HAPUITageQuery(parmDef.getCriteria()));
//					HAPChangeInfo parmParmUINewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIData(uiTagInfo.getTag(), parmDef.getCriteria()), step.getChanges());
//					HAPChangeInfo parmParmUIConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmVariableNewChange.getStoryElement().getId(), parmParmUINewChange.getStoryElement().getId(), null, null), step.getChanges());
//					
////					HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(pageStoryNode.getId(), parmParmUINewChange.getStoryElement().getId(), parmName), step.getChanges());
//					HAPChangeInfo uiContainerToUIDataTagConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(parmParmUIContainerNewChange.getStoryElement().getId(), parmParmUINewChange.getStoryElement().getId(), "uiData"), step.getChanges());
//					
//					//variable group
//					HAPElementGroupBatch variableBatchGroup = new HAPElementGroupBatch(story);
//					variableBatchGroup.addElement(new HAPInfoElement(parmVariableNewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(parmVariableConnectionNewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(parmParmUINewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(parmParmUIConnectionNewChange.getStoryElement().getElementId()));
//
//					variableBatchGroup.addElement(new HAPInfoElement(parmParmUIContainerNewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(layoutToUIContainerConnectionNewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(parmParmUILabelNewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(uiContainerToUILabelConnectionNewChange.getStoryElement().getElementId()));
//					variableBatchGroup.addElement(new HAPInfoElement(uiContainerToUIDataTagConnectionNewChange.getStoryElement().getElementId()));
//
//					HAPChangeInfo variableGroupNewChange = HAPUtilityChange.buildChangeNewAndApply(story, variableBatchGroup, step.getChanges());
//					
//				}
			}
			
			HAPResultTransaction transactionResult = story.commitTransaction();
			step.getChanges().addAll(transactionResult.getChanges());
			step.getChanges().add(HAPUtilityChange.newStoryIndexChange(story));
			
			design.addStep(step);

			//stage
			HAPUtilityDesign.setChangeStage(step, STAGE_UI);

			return HAPServiceData.createSuccessData(new HAPResponseDesign(answer.getAnswers(), step));
		}
	}

	
	private HAPUINode buildDataUINode(HAPUINode parent, Object childId, String varName, String label, HAPRequestChangeWrapper changeRequest) {
		
		HAPUINode layoutUINode = parent.newChildNode(new HAPStoryNodeUIHtml(HAPFileUtility.readFile(HAPStoryBuilderPageSimple2.class, "uiData.tmp")), childId, changeRequest, m_contextProcessRequirement, m_uiTagManager);

		HAPUINode labelUINode = layoutUINode.newChildNode(new HAPStoryNodeUIHtml(label), "label", changeRequest, m_contextProcessRequirement, m_uiTagManager);
		
		//data tag
		HAPUINode dataUINode = null;
		HAPUIDataInfo uiDataInfo = layoutUINode.getDataInfo(varName);
		HAPDataTypeCriteria dataTypeCriteria = uiDataInfo.getDataTypeCriteria();
		HAPDataTypeHelper dataTypeHelper = null;
		Set<HAPDataTypeId> dataTypeIds = dataTypeCriteria.getValidDataTypeId(dataTypeHelper);
		HAPDataTypeId dataTypeId = dataTypeIds.iterator().next();
		HAPDataTypeManager dataTypeMan = null;
		HAPDataType dataType = dataTypeMan.getDataType(dataTypeId);
		boolean isComplex = dataType.getIsComplex();
		if(isComplex) {
			if(dataTypeId.getFullName().contains("array")){
				//array
				HAPStoryNodeUIData uiDataStoryNode = new HAPStoryNodeUIData("loop", uiDataInfo);
				uiDataStoryNode.addAttribute("data", varName);
				dataUINode = layoutUINode.newChildNode(uiDataStoryNode, "uiData", changeRequest, m_contextProcessRequirement, m_uiTagManager);
				HAPUINode elementUINode = buildDataUINode(dataUINode, null, "element", null, changeRequest);
			}
			else if(dataTypeId.getFullName().contains("map")) {
				//map
				List<String> names = HAPCriteriaUtility.getCriteriaChildrenNames(dataTypeCriteria);
				for(String name : names) {
					buildDataUINode(parent, name, varName+"."+name, name, changeRequest);
				}
			}
		}
		else {
			//simple
			HAPUITagQueryResult uiTagInfo = this.m_uiTagManager.getDefaultUITag(new HAPUITageQuery(dataTypeCriteria));
			HAPStoryNodeUIData uiDataStoryNode = new HAPStoryNodeUIData(uiTagInfo.getTag(), uiDataInfo);
			uiDataStoryNode.addAttribute("data", varName);
			dataUINode = layoutUINode.newChildNode(uiDataStoryNode, "uiData", changeRequest, m_contextProcessRequirement, m_uiTagManager);
		}
		return layoutUINode;
	}
	
	private HAPServiceData processUIChangeStage(HAPDesignStory design, HAPRequestDesign answerRequest) {
		HAPStory story = design.getStory();
		
		List<HAPChangeItem> answerChanges = new ArrayList<HAPChangeItem>();
		for(HAPAnswer answer : answerRequest.getAnswers()){		answerChanges.addAll(answer.getChanges());	}

		//apply answer to story
//		HAPUtilityChange.applyChange(story, answerChanges);
				
		//new step
		HAPDesignStep step = design.newStep();

		//question
		HAPQuestionGroup rootQuestionGroup = new HAPQuestionGroup("Finish.");
		step.setQuestion(rootQuestionGroup);

		design.addStep(step);

		//stage
		HAPUtilityDesign.setChangeStage(step, STAGE_END);

		return HAPServiceData.createSuccessData(new HAPResponseDesign(answerRequest.getAnswers(), step));
	}
}
