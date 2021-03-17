package com.nosliw.data.core.script.context;

//store result for resolve reference path
public class HAPInfoContextElementReferenceResolve{
	//parent reference path
	public HAPContextPath path;
	//parent root node
	public HAPContextDefinitionRoot rootNode;
	//original refered node
	public HAPContextDefinitionElement referedNode;
	//refered solid node
	public HAPContextDefinitionElement referedSolidNode;
	//unmatched path part
	public String remainPath;
	//apply unmatched path,
	public HAPContextDefinitionElement resolvedNode;
	//after apply remain path, data criteria
//	HAPDataTypeCriteria resolvedCriteria;
}