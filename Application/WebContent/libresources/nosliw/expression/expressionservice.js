//get/create package
var packageObj = library.getChildPackage("expressionservice");    

(function(packageObj){
	//get used node
	var node_requestProcessor;
	var node_buildServiceProvider;
	var node_COMMONTRIBUTECONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_createServiceRequestInfoSet;
	var node_createServiceRequestInfoService;
//*******************************************   Start Node Definition  ************************************** 	

var node_createExpressionService = function(){
	/**
	 * Request for execute expression
	 */
	var loc_getExecuteExpressionRequest = function(expression, variables, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteExpression", {"expression":expression, "variables":variables}), handlers, requestInfo);
		var variablesInfo = expression[node_COMMONTRIBUTECONSTANT.EXPRESSION_VARIABLES];
		
		//convert variables
		var varsMatchers = expression[node_COMMONTRIBUTECONSTANT.EXPRESSION_VARIABLESMATCHERS];
		var varsMatchRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("MatcherVariable", {"variables":variables, "variablesMatchers":varsMatchers}), 
				{			
					success : function(reqInfo, setResult){
						var matchedVars = {};
						_.each(variables, function(varData, varName, list){
							var matchedVar = setResult[varName];
							if(matchedVar==undefined){
								matchedVar = variables[varName];
							}
							matchedVars[varName] = matchedVar;
						}, this);
						out.setData("variables", matchedData);
					}, 
				}, 
				out);
		_.each(variables, function(varData, varName, list){
			var request = loc_getMatchDataTaskRequest(varData, varsMatchers[varName], {}, requestInfo);
			varsMatchRequest.add(varName, request);
		}, this);

		out.addRequet(varsMatchRequest);
		
		//execute operand
		var executeOperandRequest = loc_getExecuteOperandRequest(expression, expression[node_COMMONTRIBUTECONSTANT.EXPRESSION_OPERAND], out.getData("variables"), {
			success : function(requestInfo, operandResult){
				return operandResult;
			}
		}, out);
		out.addRequet(executeOperandRequest);
		
		return out;
	};

	//execute general operand
	var loc_getExecuteOperandRequest = function(expression, operand, variables, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);

		var out;
		var operandType = operand[node_COMMONTRIBUTECONSTANT.OPERAND_TYPE];
		switch(operandType){
		case CONSTANT:
			out = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteConstantOperand", {"operand":operand, "variables":variables}), 
					function(requestInfo){  return requestInfo.service.operand[node_COMMONTRIBUTECONSTANT.OPERAND_DATA];  }, 
					handlers, requestInfo);
			break;
		case VARIABLE: 
			out = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteVariableOperand", {"operand":operand, "variables":variables}), 
					function(requestInfo){  return requestInfo.service.variable[requestInfo.service.operand[node_COMMONTRIBUTECONSTANT.OPERAND_NAME]];  }, 
					handlers, requestInfo);
			out = node_createServiceRequestInfoService(undefined, loc_calVariable, handlers, requestInfo);
		    break;
		case OPERATION:
			out = loc_getExecuteOperationOperandRequest(operand, variables, handlers, requestInfo);
			break;
		case REFERENCE:
			out = loc_getExecuteExpressionRequest(expression, expression[node_COMMONTRIBUTECONSTANT.OPERAND_REFERENCES][operand[node_COMMONTRIBUTECONSTANT.OPERAND_REFERENCENAME]], variables, handlers, requestInfo);
			break;
		}
	};

	//execute operation operand
	var loc_getExecuteOperationOperandRequest = function(operationOperand, variables, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		
		var out = createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteOperationOperand", {"operationOperand":operationOperand, "variables":variables}), handlers, requestInfo);

		//cal all parms data and base data
		var parmsOperand = operationOperand.parms;
		var baseOperand = operationOperand.baseOperand;
		
		var parmsData = {};
		var parmsOperandRequest = createServiceRequestInfoSequenceSet(new node_ServiceInfo("CalOperationParms", {"parms":parmsOperand}), {
			success : function(requestInfo, setResult){
				parmsData = setResult;
			}
		});
		_.each(parmsOperand, function(parmOperand, parmName, list){
			var parmOperandRequest = loc_getExecuteOperandRequest(parmOperand, variables, {}, parmsOperandRequest);
			parmsOperandRequest.add(parmName, parmOperandRequest);
		}, this);
		out.addRequest(parmsOperandRequest);
		
		var baseData;
		var baseOperandRequest = loc_buildExecuteOperandRequest(baseOperand, variables, {
			success : function(result, requestInfo){
				baseData = result;
			}
		});
		out.addRequest(baseOperandRequest);
		
		//match parms and base
		var matchedParms;
		var parmsMatcherRequest = createServiceRequestInfoSequenceSet(new node_ServiceInfo("MatchOperationParms", {"parmsData":parmsData, "matchers":operationOperand.parmMatchers}), {
			success : function(requestInfo, setResult){
				matchedParms = setResult;
			}
		});
		_.each(operationOperand.parmsMatchers, function(parmMatchers, parmName, list){
			var parmMatchRequest = loc_getMatchDataTaskRequest(parmsData[parmName], parmMatchers, {}, parmsMatcherRequest);
			parmsMatcherRequest.add(parmName, parmMatchRequest);
		}, this);
		out.addRequest(parmsMatcherRequest);
		
		var matchedBase;
		var parmMatchRequest = loc_getMatchDataTaskRequest(baseData, operationOperand.baseMatchers, {
			success : function(requestInfo, data){
				matchedBase = data;
			}
		}, out);
		out.addRequest(parmMatchRequest);
		
		//execute data operation
		var executeOperationRequest = loc_getExecuteOperationRequest(operationOperand.dataTypeId, operationOperand.operandName, matchedParms, matchedBase, {
			success : function(requestInfo, data){
				return data;
			}
		}, out);
		out.addRequest(executeOperationRequest);
		
		return out;
	}

	//execute data operation
	var loc_getExecuteOperationRequest = function(dataTypeId, operation, parms, handlers, requestInfo){
		var dataOperationId;
		var loadResourceRequest = nosliw.getResourceService().getGetResourcesRequest([dataOperationId], {
			success : function(requestInfo, resources){
				var dataOperationResource = resources[dataOperationId];
				var dataOperationFun = dataOperationResource.resourceData;
				
				//build operation context
				var operationContext = {
					resources : resources,
					data : {},
				};
				var dependencys = dataOperationResource.dependency;
				_.each(dependencys, function(dependency, index, list){
					var aliases;
					var dependencyResource;
					_.each(aliases, function(alias, index, list){
						operationContext.data[alias] = dependencyResource.resourceData;
					}, this);
				}, this);
				
				var converterResult = dataOperationFun.call(base, parms, operationContext);
				return converterResult;
			}
		}, requestInfo);
		
		return out;
	};	


	
	//convert individual data according to matchers
	var loc_getMatchDataTaskRequest = function(data, matchers, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);

		var dataType = data[node_COMMONTRIBUTECONSTANT.DATA_DATATYPEID];
		var matcher = matchers[dataType];
		
		if(converter==undefined){
			//if converter does not created, then get it
		}
		else{
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("MatchData", {"data":data, "matcher":matcher}), handlers, requestInfo);
			var matcherSegments = matcher[node_COMMONTRIBUTECONSTANT.DATATYPERELATIONSHIP_PATH];
			var sourceId = matcher[node_COMMONTRIBUTECONSTANT.DATATYPERELATIONSHIP_SOURCE];

			var converters = [];
			for(var i=0; i<matcherSegments.length; i++){
				var sourceId = node_namingConvensionUtility.parseLevel2(matcherSegments[i])[1];
				var targetId;
				if(i<matcherSegments.length-1){
					targetId = node_namingConvensionUtility.parseLevel2(matcherSegments[i+1])[1];
				}
				else{
					targetId = matcher[node_COMMONTRIBUTECONSTANT.DATATYPERELATIONSHIP_TARGET];
				}
				converters.push({
					sourceConverter : "converter;"+sourceId+"from",
					targetId : targetId,
				});
			}
			
			var converterData = data;
			for(var i=0; i<converters.length; i++){
				var converterRequest = loc_getExecuteConverterRequest(converterData, converters[i].sourceConverter, converters[i].targetId, {
					success : function(requestInfo, convertedData){
						converterData = convertedData;
					}
				}, out);
				out.addRequest(converterRequest);
			}

			out.setRequestProcessors({
				success : function(reqInfo, result){
					return converterData;
				}, 
			});
			return out;
		}
	};

	//execute conterter
	var loc_getExecuteConverterRequest = function(data, converterId, targetId, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		var out = node_createServiceRequestInfoService(new node_ServiceInfo("ExecuteConverter", {"converterId":converterId, "targetId":targetId}), handlers, requetInfo);
		var loadResourceRequest = nosliw.getResourceService().getGetResourcesRequest([converterId], {
			success : function(requestInfo, resources){
				var converterFun = resources[converterId].resourceData;
				var converterResult = converterFun.call(data, targetId);
				return converterResult;
			}
		}, requestInfo);
		out.setDependentService(loadResourceRequest);
		return out;
	}
	
	var loc_out = {
		getExecuteExpressionRequest : function(expression, variables, handlers, requester_parent){
			return loc_getExecuteExpressionRequest(expression, variables, handlers, requester_parent);
		},
			
		executeExecuteExpressionRequest : function(expression, variables, handlers, requestInfo){
			var requestInfo = this.getExecuteExpressionRequest(resourceIds, handlers, requestInfo);
			node_requestServiceProcessor.processRequest(requestInfo, false);
		}
	};
	
	loc_out = node_buildServiceProvider(loc_out, "expressionService");
	
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createNode("createExpressionService", node_createExpressionService); 

	var module = {
		start : function(packageObj){
			node_buildServiceProvider = packageObj.getNodeData("request.buildServiceProvider");
			node_requestProcessor = packageObj.getNodeData("request.requestServiceProcessor");
			node_COMMONTRIBUTECONSTANT = packageObj.getNodeData("constant.COMMONTRIBUTECONSTANT");
			node_createServiceRequestInfoSequence = packageObj.getNodeData("request.request.createServiceRequestInfoSequence");
			node_ServiceInfo = packageObj.getNodeData("common.service.ServiceInfo");
			node_createServiceRequestInfoSet = packageObj.getNodeData("request.request.createServiceRequestInfoSet");
			node_createServiceRequestInfoService = packageObj.getNodeData("request.request.createServiceRequestInfoService");
		}
	};
	nosliw.registerModule(module, packageObj);
})(packageObj);
