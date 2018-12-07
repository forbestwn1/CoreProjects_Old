package com.nosliw.data.core.script.context;

//store result for resolve reference path
public class HAPInfoRelativeContextResolve{
	//parent reference path
	public HAPContextPath path;
	//parent root node
	public HAPContextDefinitionRoot rootNode;
	//original refered node
	public HAPContextDefinitionElement referedNode;
	//unmatched path part
	public String remainPath;
	//apply unmatched path, find the resolvedNode
	public HAPContextDefinitionElement resolvedNode;
}
