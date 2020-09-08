package com.nosliw.uiresource.page.story.design;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.HAPDataType;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPDataTypeManager;
import com.nosliw.data.core.criteria.HAPCriteriaUtility;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.service.interfacee.HAPServiceInterface;
import com.nosliw.data.core.service.interfacee.HAPServiceOutput;
import com.nosliw.data.core.service.interfacee.HAPServiceParm;
import com.nosliw.data.core.service.interfacee.HAPServiceResult;
import com.nosliw.data.core.service.provide.HAPManagerService;
import com.nosliw.data.core.story.HAPIdElement;
import com.nosliw.data.core.story.HAPInfoElement;
import com.nosliw.data.core.story.HAPStory;
import com.nosliw.data.core.story.HAPUtilityConnection;
import com.nosliw.data.core.story.HAPUtilityStory;
import com.nosliw.data.core.story.design.HAPAnswer;
import com.nosliw.data.core.story.design.HAPBuilderStory;
import com.nosliw.data.core.story.design.HAPChangeInfo;
import com.nosliw.data.core.story.design.HAPChangeItem;
import com.nosliw.data.core.story.design.HAPDesignStep;
import com.nosliw.data.core.story.design.HAPDesignStory;
import com.nosliw.data.core.story.design.HAPQuestionGroup;
import com.nosliw.data.core.story.design.HAPQuestionItem;
import com.nosliw.data.core.story.design.HAPRequestDesign;
import com.nosliw.data.core.story.design.HAPResponseDesign;
import com.nosliw.data.core.story.design.HAPStageInfo;
import com.nosliw.data.core.story.design.HAPUtilityChange;
import com.nosliw.data.core.story.design.HAPUtilityDesign;
import com.nosliw.data.core.story.element.connectiongroup.HAPElementGroupBatch;
import com.nosliw.data.core.story.element.connectiongroup.HAPElementGroupSwitch;
import com.nosliw.data.core.story.element.node.HAPStoryNodeConstant;
import com.nosliw.data.core.story.element.node.HAPStoryNodeService;
import com.nosliw.data.core.story.element.node.HAPStoryNodeServiceInput;
import com.nosliw.data.core.story.element.node.HAPStoryNodeServiceInputParm;
import com.nosliw.data.core.story.element.node.HAPStoryNodeServiceOutput;
import com.nosliw.data.core.story.element.node.HAPStoryNodeServiceOutputItem;
import com.nosliw.data.core.story.element.node.HAPStoryNodeVariable;
import com.nosliw.uiresource.page.story.element.HAPStoryNodePage;
import com.nosliw.uiresource.page.story.element.HAPStoryNodeUIData;
import com.nosliw.uiresource.page.story.element.HAPStoryNodeUIHtml;
import com.nosliw.uiresource.page.story.model.HAPUINode;
import com.nosliw.uiresource.page.story.model.HAPUITree;
import com.nosliw.uiresource.page.tag.HAPUITagManager;
import com.nosliw.uiresource.page.tag.HAPUITagQueryResult;
import com.nosliw.uiresource.page.tag.HAPUITageQuery;

public class HAPStoryBuilderPageSimple1 implements HAPBuilderStory{

	public final static String BUILDERID = "pageSimple";
	
	public final static String STAGE_SERVICE = "service";
	public final static String STAGE_UI = "ui";
	public final static String STAGE_END = "end";
	
	private HAPManagerService m_serviceManager;
	private HAPUITagManager m_uiTagManager;
	
	private List<HAPStageInfo> m_stages;
	
	public HAPStoryBuilderPageSimple1(HAPManagerService serviceManager, HAPUITagManager uiTagMan) {
		this.m_serviceManager = serviceManager;
		this.m_uiTagManager = uiTagMan;
		this.m_stages = new ArrayList<HAPStageInfo>();
		this.m_stages.add(new HAPStageInfo(STAGE_SERVICE, STAGE_SERVICE));
		this.m_stages.add(new HAPStageInfo(STAGE_UI, STAGE_UI));
		this.m_stages.add(new HAPStageInfo(STAGE_END, STAGE_END));
	}
	
	@Override
	public void initDesign(HAPDesignStory design) {
		design.getStory().setShowType(HAPConstant.RUNTIME_RESOURCE_TYPE_UIRESOURCE);

		HAPUtilityDesign.setDesignAllStages(design, m_stages);
		
		HAPDesignStep step = new HAPDesignStep();

		//new page node
		HAPChangeInfo newPageNodeChange = HAPUtilityChange.buildChangeNewAndApply(design.getStory(), new HAPStoryNodePage(), step.getChanges());

		//new service node
		HAPChangeInfo newServiceNodeChange = HAPUtilityChange.buildChangeNewAndApply(design.getStory(), new HAPStoryNodeService(), step.getChanges());
		
		//extra info
		HAPQuestionGroup rootQuestionGroup = new HAPQuestionGroup("Please select service.");
		HAPQuestionItem serviceItemExtraInfo = new HAPQuestionItem("select service", newServiceNodeChange.getStoryElement().getElementId());
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

		List<HAPChangeItem> answerChanges = new ArrayList<HAPChangeItem>();
		for(HAPAnswer answer : answerRequest.getAnswers()){		answerChanges.addAll(answer.getChanges());	}
		
		//apply answer changes to story
		HAPUtilityChange.applyChange(story, answerChanges);
		
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
			HAPUtilityChange.revertChange(story, answerChanges);
			//
			String[] errorMsg = {"Service should not be empty!!"};
			return HAPServiceData.createFailureData(errorMsg, "Validation Fail!!");
		}
	}
	
	private HAPServiceData processServiceStage(HAPDesignStory design, HAPRequestDesign answer) {
		HAPStory story = design.getStory();
		
		HAPServiceData validateResult = validateServiceAnswer(design, answer);
		if(validateResult.isFail())   return validateResult;
		else {
			//new step
			HAPDesignStep step = new HAPDesignStep();

			//question
			HAPQuestionGroup rootQuestionGroup = new HAPQuestionGroup("Please select ui.");
			step.setQuestion(rootQuestionGroup);

			//get page node
			HAPStoryNodePage pageStoryNode = (HAPStoryNodePage)HAPUtilityStory.getAllStoryNodeByType(story, HAPConstant.STORYNODE_TYPE_PAGE).iterator().next();
			//page layout node
			HAPChangeInfo pageLayoutNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(HAPFileUtility.readFile(HAPStoryBuilderPageSimple1.class, "page_html.tmp")), step.getChanges());
			HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(pageStoryNode.getId(), pageLayoutNewChange.getStoryElement().getId(), null), step.getChanges());

			//get service node
			HAPStoryNodeService serviceStoryNode = (HAPStoryNodeService)HAPUtilityStory.getAllStoryNodeByType(story, HAPConstant.STORYNODE_TYPE_SERVICE).iterator().next();
			HAPServiceInterface serviceInterface = this.m_serviceManager.getServiceDefinitionManager().getDefinition(serviceStoryNode.getReferenceId()).getStaticInfo().getInterface();
			
			{
				//service input
				HAPChangeInfo serviceInputNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeServiceInput(), step.getChanges());
				HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(serviceStoryNode.getId(), serviceInputNewChange.getStoryElement().getId(), HAPConstant.SERVICE_CHILD_INPUT), step.getChanges());
				
				//parms
				for(String parmName : serviceInterface.getParmNames()) {
					
					HAPQuestionGroup parmQuestionGroup = new HAPQuestionGroup("Configure Input Parm " + parmName);
					rootQuestionGroup.addChild(parmQuestionGroup);
					
					//parm and connection to input
					HAPServiceParm parmDef = serviceInterface.getParm(parmName);
					HAPChangeInfo parmNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeServiceInputParm(parmDef), step.getChanges());
					HAPChangeInfo parmConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(serviceInputNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), parmName), step.getChanges());

					//constant path and group
					HAPElementGroupBatch constantBatchGroup = new HAPElementGroupBatch(story);
					HAPChangeInfo parmConstantProviderNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeConstant(parmDef.getCriteria()), step.getChanges(), constantBatchGroup);
					HAPChangeInfo parmConstantProviderConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmConstantProviderNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), null, null), step.getChanges(), constantBatchGroup);
					HAPChangeInfo constantProviderGroupNewChange = HAPUtilityChange.buildChangeNewAndApply(story, constantBatchGroup, step.getChanges());
					
					//variable path and group
					HAPElementGroupBatch variableBatchGroup = new HAPElementGroupBatch(story);
					HAPChangeInfo parmVariableProviderNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeVariable(parmName, parmDef.getCriteria()), step.getChanges(), variableBatchGroup);
					HAPChangeInfo parmVariableProviderConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmVariableProviderNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), null, null), step.getChanges(), variableBatchGroup);

					//ui
					HAPUINode dataUITreeNode = buildDataUINode(parmDef.getCriteria(), parmName, story, step);
					HAPUITree dataUITree = new HAPUITree(story, dataUITreeNode);

					HAPIdElement uiDataEleId = dataUITree.searchNodeByType(HAPConstant.STORYELEMENT_CATEGARY_NODE, HAPConstant.STORYNODE_TYPE_UIDATA).get(0);
					dataUITree.realize(step.getChanges());
					HAPChangeInfo layoutToUIContainerConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(pageLayoutNewChange.getStoryElement().getId(), dataUITree.getRootElementId().getId(), "input"), step.getChanges(), variableBatchGroup);

					//variable to ui connection
					HAPChangeInfo parmParmUIConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(uiDataEleId.getId(), parmVariableProviderNewChange.getStoryElement().getId(), null, null), step.getChanges(), variableBatchGroup);
					
					//variable group
					for(HAPIdElement eleId : dataUITree.getAllElements()) 	variableBatchGroup.addElement(new HAPInfoElement(eleId));
					
					HAPChangeInfo variableProviderGroupNewChange = HAPUtilityChange.buildChangeNewAndApply(story, variableBatchGroup, step.getChanges());

					//switch group
					HAPElementGroupSwitch group = new HAPElementGroupSwitch(story);
					//add constant to group
					HAPInfoElement constantGroupEle = new HAPInfoElement(constantProviderGroupNewChange.getStoryElement().getElementId());
					constantGroupEle.setName("Constant");
					group.addElement(constantGroupEle);
					//add variable to group
					HAPInfoElement varGroupEle = new HAPInfoElement(variableProviderGroupNewChange.getStoryElement().getElementId());
					varGroupEle.setName("Variable");
					group.addElement(varGroupEle);
					HAPChangeInfo groupNewChange = HAPUtilityChange.buildChangeNewAndApply(story, group, step.getChanges());

					//set switch group choice
					HAPUtilityChange.buildChangePatchAndApply(story, groupNewChange.getStoryElement().getElementId(), HAPElementGroupSwitch.CHOICE, variableProviderGroupNewChange.getStoryElement().getElementId().toStringValue(HAPSerializationFormat.LITERATE), step.getChanges());

					//question item
					HAPQuestionItem groupQuestion = new HAPQuestionItem("select import for parm", groupNewChange.getStoryElement().getElementId());
					parmQuestionGroup.addChild(groupQuestion);
					
					HAPQuestionItem constantQuestion = new HAPQuestionItem("select constant", parmConstantProviderNewChange.getStoryElement().getElementId());
					parmQuestionGroup.addChild(constantQuestion);
					
					HAPQuestionItem uiDataQuestion = new HAPQuestionItem("select ui tag", uiDataEleId);
					parmQuestionGroup.addChild(uiDataQuestion);
				}
			}
			
			{
				HAPChangeInfo serviceOutputNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeServiceOutput(), step.getChanges());
				HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(serviceStoryNode.getId(), serviceOutputNewChange.getStoryElement().getId(), HAPConstant.SERVICE_CHILD_RESULT), step.getChanges());
				
				HAPServiceResult successResult = serviceInterface.getResult("success");
				Map<String, HAPServiceOutput> output = successResult.getOutput(); 
				for(String parmName : output.keySet()) {
					HAPQuestionGroup parmQuestionGroup = new HAPQuestionGroup("Configure Output Parm " + parmName);
					rootQuestionGroup.addChild(parmQuestionGroup);
					
					//parm and connection to input
					HAPServiceOutput parmDef = output.get(parmName);
					HAPChangeInfo parmNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeServiceOutputItem(parmDef), step.getChanges());
					HAPChangeInfo parmConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(serviceOutputNewChange.getStoryElement().getId(), parmNewChange.getStoryElement().getId(), parmName), step.getChanges());

					//variable path and group
					HAPChangeInfo parmVariableNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeVariable(parmName, parmDef.getCriteria()), step.getChanges());
					HAPChangeInfo parmVariableConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmNewChange.getStoryElement().getId(), parmVariableNewChange.getStoryElement().getId(), null, null), step.getChanges());

					//ui container
					HAPChangeInfo parmParmUIContainerNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(HAPFileUtility.readFile(HAPStoryBuilderPageSimple1.class, "uiData.tmp")), step.getChanges());
					HAPChangeInfo layoutToUIContainerConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(pageLayoutNewChange.getStoryElement().getId(), parmParmUIContainerNewChange.getStoryElement().getId(), "output"), step.getChanges());
					HAPChangeInfo parmParmUILabelNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(parmName), step.getChanges());
					HAPChangeInfo uiContainerToUILabelConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(parmParmUIContainerNewChange.getStoryElement().getId(), parmParmUILabelNewChange.getStoryElement().getId(), "label"), step.getChanges());

					//ui for variable, add to page, connect with variable
					HAPUITagQueryResult uiTagInfo = this.m_uiTagManager.getDefaultUITag(new HAPUITageQuery(parmDef.getCriteria()));
					HAPChangeInfo parmParmUINewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIData(uiTagInfo.getTag(), parmDef.getCriteria()), step.getChanges());
					HAPChangeInfo parmParmUIConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionOnewayDataIO(parmVariableNewChange.getStoryElement().getId(), parmParmUINewChange.getStoryElement().getId(), null, null), step.getChanges());
					
//					HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(pageStoryNode.getId(), parmParmUINewChange.getStoryElement().getId(), parmName), step.getChanges());
					HAPChangeInfo uiContainerToUIDataTagConnectionNewChange = HAPUtilityChange.buildChangeNewAndApply(story, HAPUtilityConnection.newConnectionContain(parmParmUIContainerNewChange.getStoryElement().getId(), parmParmUINewChange.getStoryElement().getId(), "uiData"), step.getChanges());
					
					//variable group
					HAPElementGroupBatch variableBatchGroup = new HAPElementGroupBatch(story);
					variableBatchGroup.addElement(new HAPInfoElement(parmVariableNewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(parmVariableConnectionNewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(parmParmUINewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(parmParmUIConnectionNewChange.getStoryElement().getElementId()));

					variableBatchGroup.addElement(new HAPInfoElement(parmParmUIContainerNewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(layoutToUIContainerConnectionNewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(parmParmUILabelNewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(uiContainerToUILabelConnectionNewChange.getStoryElement().getElementId()));
					variableBatchGroup.addElement(new HAPInfoElement(uiContainerToUIDataTagConnectionNewChange.getStoryElement().getElementId()));

					HAPChangeInfo variableGroupNewChange = HAPUtilityChange.buildChangeNewAndApply(story, variableBatchGroup, step.getChanges());
					
				}
			}
			
			design.addStep(step);

			//stage
			HAPUtilityDesign.setChangeStage(step, STAGE_UI);

			return HAPServiceData.createSuccessData(new HAPResponseDesign(answer.getAnswers(), step));
		}
	}

	
	private HAPUINode buildDataUINode(HAPDataTypeCriteria dataCriteria, String label, HAPStory story, HAPDesignStep step) {
		//ui container
		HAPChangeInfo parmParmUIContainerNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(HAPFileUtility.readFile(HAPStoryBuilderPageSimple1.class, "uiData.tmp")), step.getChanges());
		HAPUINode rootNode = new HAPUINode(parmParmUIContainerNewChange.getStoryElement().getId(), story);

		//label
		HAPChangeInfo parmParmUILabelNewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIHtml(label), step.getChanges());
		rootNode.addChildNode(parmParmUILabelNewChange.getStoryElement().getId(), "label");

		//data tag
		HAPDataTypeHelper dataTypeHelper = null;
		Set<HAPDataTypeId> dataTypeIds = dataCriteria.getValidDataTypeId(dataTypeHelper);
		HAPDataTypeId dataTypeId = dataTypeIds.iterator().next();
		HAPDataTypeManager dataTypeMan = null;
		HAPDataType dataType = dataTypeMan.getDataType(dataTypeId);
		boolean isComplex = dataType.getIsComplex();
		if(isComplex) {
			if(dataTypeId.getFullName().contains("array")){
				//array
				HAPChangeInfo parmParmUINewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIData("loop", dataCriteria), step.getChanges());
				HAPUINode uiDataNode = rootNode.addChildNode(parmParmUINewChange.getStoryElement().getId(), "uiData");

				//array child
				HAPDataTypeCriteria childCriteria = HAPCriteriaUtility.getChildCriteria(dataCriteria, "element");
				HAPUINode childNode = buildDataUINode(childCriteria, null, story, step);
				uiDataNode.addChildNode(childNode, childId);
			}
			else if(dataTypeId.getFullName().contains("map")) {
				//map
				//array
				HAPChangeInfo parmParmUINewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIData("loop", dataCriteria), step.getChanges());
				HAPUINode uiDataNode = rootNode.addChildNode(parmParmUINewChange.getStoryElement().getId(), "uiData");

				List<String> names = HAPCriteriaUtility.getCriteriaChildrenNames(dataCriteria);
				for(String name : names) {
					HAPDataTypeCriteria childCriteria = HAPCriteriaUtility.getChildCriteria(dataCriteria, name);
					HAPUINode childNode = buildDataUINode(childCriteria, null, story, step);
					uiDataNode.addChildNode(childNode, name);
				}
			}
		}
		else {
			//simple
			HAPUITagQueryResult uiTagInfo = this.m_uiTagManager.getDefaultUITag(new HAPUITageQuery(dataCriteria));
			HAPChangeInfo parmParmUINewChange = HAPUtilityChange.buildChangeNewAndApply(story, new HAPStoryNodeUIData(uiTagInfo.getTag(), dataCriteria), step.getChanges());
			HAPUINode uiDataNode = rootNode.addChildNode(parmParmUINewChange.getStoryElement().getId(), "uiData");
		}
		
		return rootNode;		
	}
	
	private HAPServiceData processUIChangeStage(HAPDesignStory design, HAPRequestDesign answerRequest) {
		HAPStory story = design.getStory();
		
		List<HAPChangeItem> answerChanges = new ArrayList<HAPChangeItem>();
		for(HAPAnswer answer : answerRequest.getAnswers()){		answerChanges.addAll(answer.getChanges());	}

		//apply answer to story
		HAPUtilityChange.applyChange(story, answerChanges);
				
		//new step
		HAPDesignStep step = new HAPDesignStep();

		//question
		HAPQuestionGroup rootQuestionGroup = new HAPQuestionGroup("Finish.");
		step.setQuestion(rootQuestionGroup);

		design.addStep(step);

		//stage
		HAPUtilityDesign.setChangeStage(step, STAGE_END);

		return HAPServiceData.createSuccessData(new HAPResponseDesign(answerRequest.getAnswers(), step));
	}
}
