//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_buildServiceProvider;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSet;
	var node_ServiceInfo;
	var node_objectOperationUtility;
	var node_NormalActivityResult;
	var node_NormalActivityOutput;
	var node_EndActivityOutput;
	var node_ProcessResult;
	var node_createServiceRequestInfoService;
	var node_DependentServiceRequestInfo;
	var node_requestServiceProcessor;

//*******************************************   Start Node Definition  ************************************** 	
var node_createProcessRuntimeFactory = function(){
	var loc_out = {
		createProcessRuntime : function(envObj){
			if(envObj==undefined)  envObj = {};
			envObj.buildOutputVarialbeName = function(varName){
				return "nosliw_"+varName;
			}; 
			
			return node_createProcessService(envObj);
		}
	};
	return loc_out;
};
	
	
var node_createProcessService = function(envObj){

	//activity plugin entity 
	var loc_activityPlugins = {};
	
	var loc_envObj = envObj; 
	
	var loc_getActivityPluginRequest = function(pluginName, handlers, request){
		var service = new node_ServiceInfo("getActivityPlugin", {"pluginName":pluginName})
		var plugin = loc_activityPlugins[pluginName];
		if(plugin!=null){
			return node_createServiceRequestInfoSimple(service, function(requestInfo){	return plugin;}, handlers, request);
		}
		else{
			var out = node_createServiceRequestInfoSequence(service, handlers, request);
			out.addRequest(nosliw.runtime.getResourceService().getGetResourceDataByTypeRequest([pluginName], node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_ACTIVITYPLUGIN, {
				success : function(request, resourceData){
					var plugin = resourceData[pluginName][node_COMMONATRIBUTECONSTANT.PLUGINACTIVITY_SCRIPT](nosliw, loc_envObj);
					loc_activityPlugins[pluginName] = plugin;
					return plugin;
				}
			}));
			return out;
		}
	};
	
	var loc_dyanimicValueBuild = function(output, outputPathSegs, input, intpuPathSegs){
		var inputValue = node_objectOperationUtility.getObjectAttributeByPathSegs(input, intpuPathSegs);
		node_objectOperationUtility.operateObjectByPathSegs(output, outputPathSegs, node_CONSTANT.WRAPPER_OPERATION_SET, inputValue);
		return output;
	};
	
	var loc_getGenerateDataAssociationOutputRequest = function(dataAssociation, input, handlers, request){
		var out = node_createServiceRequestInfoSimple(new node_ServiceInfo("generateDataAssociationOutputRequest", {"dataAssociation":dataAssociation, "input":input}), 
			function(requestInfo){
				return dataAssociation[node_COMMONATRIBUTECONSTANT.EXECUTABLEDATAASSOCIATIONGROUP_CONVERTFUNCTION](input, loc_dyanimicValueBuild);
			}, 
			handlers, request);
		return out;
	};
	
	var loc_getBackToGlobalRequest = function(data, backToGlobalConfig, handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("BackToGlobal", {"data":data, "backToGlobalConfig":backToGlobalConfig}), handlers, request);

		var activityResultDataAssociation = backToGlobalConfig[node_COMMONATRIBUTECONSTANT.BACKTOGLOBALCONTEXT_OUTPUTASSOCIATION];
		out.addRequest(loc_getGenerateDataAssociationOutputRequest(activityResultDataAssociation, data, {
			success : function(request, globalData){
				//process matchers here
				//kkkk
				var matchersByPathRequest = node_createServiceRequestInfoSet();
				var matchersByPath = backToGlobalConfig[node_COMMONATRIBUTECONSTANT.BACKTOGLOBALCONTEXT_OUTPUTMATCHERS];
				_.each(matchersByPath, function(matchers, path){
					var valueByPath = node_objectOperationUtility.getObjectAttributeByPath(globalData, path);
					
				});
				
				return globalData;
			}
		}));
		return out;
	};

	var loc_getExecuteNormalActivityRequest = function(normalActivity, context, handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteNormalActivity", {"activity":normalActivity, "context":context}), handlers, request);
		//calculate input for activity first
		out.addRequest(loc_getGenerateDataAssociationOutputRequest(normalActivity[node_COMMONATRIBUTECONSTANT.EXECUTABLEACTIVITY_INPUT], context, {
			success : function(requestInfo, input){
				//get activity plugin 
				return loc_getActivityPluginRequest(normalActivity[node_COMMONATRIBUTECONSTANT.EXECUTABLEACTIVITY_TYPE], {
					success : function(requestInfo, activityPlugin){
						//execute activity plugin
						return activityPlugin.getExecuteActivityRequest(normalActivity, input, loc_envObj, {
							success : function(requestInfo, activityResult){  //get activity results (result name + result value map)
								//calculate variable output
								var activityResultConfig = normalActivity[node_COMMONATRIBUTECONSTANT.EXECUTABLEACTIVITY_RESULT][activityResult.resultName];
								return loc_getBackToGlobalRequest(activityResult.resultValue, activityResultConfig, {
									success :function(request, activityOutput){
										//build new context
										_.each(activityOutput, function(ele, name){
											context[name] = ele;
										});
										return new node_NormalActivityOutput(activityResultConfig[node_COMMONATRIBUTECONSTANT.EXECUTABLERESULTACTIVITYNORMAL_FLOW][node_COMMONATRIBUTECONSTANT.DEFINITIONSEQUENCEFLOW_TARGET],
												context);
									}
								});
							}
						});
					}
				});
			}
		}));
		return out;
	};

	var loc_getExecuteStartActivityRequest = function(startActivity, context, handlers, request){
		out = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteStartActivity", {"activity":activity, "context":context}), 
				function(requestInfo){
					return new node_ActivityOutput(activityResultConfig[node_COMMONATRIBUTECONSTANT.EXECUTABLERESULTACTIVITYNORMAL_FLOW][node_COMMONATRIBUTECONSTANT.DEFINITIONSEQUENCEFLOW_TARGET], context);
				}, 
				handlers, requestInfo);
	};

	var loc_getExecuteEndActivityRequest = function(endActivity, context, handlers, request){
		out = node_createServiceRequestInfoSimple(new node_ServiceInfo("generateInput", {"activity":activity, "context":constants}), 
				function(requestInfo){
					var output = process[result][activity[result]];
					return new ProcessResult(activity[result], output.inputScript(context, utilFun));
				}, 
				handlers, requestInfo);
	};

	var loc_getExecuteActivitySequenceRequest = function(activityId, activities, context, handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteActivity", {"activityId":activityId, "activities":activities, "context":context}), handlers, request);
		var activitExecuteRequest;
		var activity = activities[activityId];
		var activityType = activity[node_COMMONATRIBUTECONSTANT.EXECUTABLEACTIVITY_TYPE];
		if(activityType==node_COMMONCONSTANT.ACTIVITY_TYPE_START){
			activitExecuteRequest = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteStartActivity", {"activity":activity, "context":context}), 
					function(requestInfo){
						var nextActivityId = activity[node_COMMONATRIBUTECONSTANT.EXECUTABLERESULTACTIVITYNORMAL_FLOW][node_COMMONATRIBUTECONSTANT.DEFINITIONSEQUENCEFLOW_TARGET];
						return loc_getExecuteActivitySequenceRequest(nextActivityId, activities, context); 
					});
		}
		else if(activityType==node_COMMONCONSTANT.ACTIVITY_TYPE_END){
			activitExecuteRequest = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteEndActivity", {"activity":activity, "context":context}), 
					function(requestInfo){
						return new node_EndActivityOutput(activity[node_COMMONATRIBUTECONSTANT.EXECUTABLEACTIVITY_RESULTNAME], context);
					}, 
					handlers, request);
		}
		else{
			activitExecuteRequest = loc_getExecuteNormalActivityRequest(activity, context, {
				success : function(requestInfo, normalActivityOutput){
					return loc_getExecuteActivitySequenceRequest(normalActivityOutput.next, activities, normalActivityOutput.context);
				}
			}, request);
		}
		out.addRequest(activitExecuteRequest);
		return out;
	};

	var loc_getExecuteProcessRequest = function(process, input, handlers, request){
		//init, build context
		var context = process[node_COMMONATRIBUTECONSTANT.EXECUTABLEPROCESS_INITSCRIPT](input);

		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteProcess", {"process":process, "context":context}), handlers, request);

		var startActivityId = process[node_COMMONATRIBUTECONSTANT.EXECUTABLEPROCESS_STARTACTIVITYID];
		var activities = process[node_COMMONATRIBUTECONSTANT.EXECUTABLEPROCESS_ACTIVITY];
		out.addRequest(loc_getExecuteActivitySequenceRequest(startActivityId, activities, context, {
			success : function(requestInfo, endActivityOutput){
				return loc_getGenerateDataAssociationOutputRequest(
						process[node_COMMONATRIBUTECONSTANT.EXECUTABLEPROCESS_RESULT][endActivityOutput.resultName],
						endActivityOutput.context, {
							success : function(requestInfo, processOutputValue){
								return new node_ProcessResult(endActivityOutput.resultName, processOutputValue);
							}
						});
			}
		}));
		return out;
	};
	
	var loc_out = {

		getExecuteProcessRequest : function(id, input, handlers, requester_parent){
			var requestInfo = loc_out.getRequestInfo(requester_parent);
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteProcessResource", {"id":id, "input":input}), handlers, requestInfo);

			out.addRequest(nosliw.runtime.getResourceService().getGetResourceDataByTypeRequest([id], node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_PROCESS, {
				success : function(requestInfo, processes){
					var process = processes[id];
					return loc_getExecuteProcessRequest(process, input);
				}
			}));
			
			return out;
		},
			
		executeProcessRequest : function(id, input, handlers, requester_parent){
			var requestInfo = this.getExecuteProcessRequest(id, input, handlers, requester_parent);
			node_requestServiceProcessor.processRequest(requestInfo);
		},
			
	};

	loc_out = node_buildServiceProvider(loc_out, "processService");
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.buildServiceProvider", function(){node_buildServiceProvider = this.getData();});

nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("common.utility.objectOperationUtility", function(){node_objectOperationUtility = this.getData();	});
nosliw.registerSetNodeDataEvent("process.entity.NormalActivityResult", function(){node_NormalActivityResult = this.getData();	});
nosliw.registerSetNodeDataEvent("process.entity.NormalActivityOutput", function(){node_NormalActivityOutput = this.getData();	});
nosliw.registerSetNodeDataEvent("process.entity.EndActivityOutput", function(){node_EndActivityOutput = this.getData();	});
nosliw.registerSetNodeDataEvent("process.entity.ProcessResult", function(){node_ProcessResult = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoService", function(){node_createServiceRequestInfoService = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){node_createServiceRequestInfoSet = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.entity.DependentServiceRequestInfo", function(){node_DependentServiceRequestInfo = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});

//Register Node by Name
packageObj.createChildNode("createProcessRuntimeFactory", node_createProcessRuntimeFactory); 

})(packageObj);
