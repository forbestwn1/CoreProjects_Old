//get/create package
var packageObj = library.getChildPackage("service");    

(function(packageObj){
	//get used node
	var node_resourceUtility;
	var node_requestServiceProcessor;
	var node_buildServiceProvider;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_basicUtility;
	var node_createServiceRequestInfoSet;
	var node_createServiceRequestInfoService;
	var node_createServiceRequestInfoSimple;
	var node_OperationContext;
	var node_OperationParm;
	var node_OperationParms;
	var node_DependentServiceRequestInfo;
	var node_expressionUtility;
	var node_dataUtility;
	var node_namingConvensionUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createExpressionService = function(){
	/**
	 * Request for execute expression
	 */
	var loc_getExecuteExpressionRequest = function(expression, variables, constants, references, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteExpression", {"expression":expression, "variables":variables}), handlers, requestInfo);
		var variablesInfo = expression[node_COMMONATRIBUTECONSTANT.EXPRESSION_VARIABLEINFOS];
			
		//if have variables, convert variables
		var varsMatchers = expression[node_COMMONATRIBUTECONSTANT.EXPRESSION_VARIABLESMATCHERS];
		if(varsMatchers==undefined)  varsMatchers = {};
		var varsMatchRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("MatcherVariable", {"variables":variables, "variablesMatchers":varsMatchers}), 
				{			
					success : function(reqInfo, setResult){
						var variables = reqInfo.getData();
						var matchedVars = {};
						_.each(variables, function(varData, varName, list){
							var matchedVar = setResult.getResult(varName);
							if(matchedVar==undefined){
								matchedVar = variables[varName];
							}
							matchedVars[varName] = matchedVar;
						}, this);
						reqInfo.setData(matchedVars);
						
						//execute operand
						var executeOperandRequest = loc_getExecuteOperandRequest(expression, expression[node_COMMONATRIBUTECONSTANT.EXPRESSION_OPERAND], reqInfo.getData(), constants, references, {
							success : function(requestInfo, operandResult){
								return operandResult;
							}
						}, null);
						return executeOperandRequest;
					}, 
				}, 
				null).withData(variables);
			
			_.each(variables, function(varData, varName, list){
				var varMatchers = varsMatchers[varName];
				if(varMatchers!=undefined){
					var request = loc_getMatchDataTaskRequest(varData, varMatchers, {}, null);
					varsMatchRequest.addRequest(varName, request);
				}
			}, this);

			out.addRequest(varsMatchRequest);
		
		return out;
	};

	//execute general operand
	var loc_getExecuteOperandRequest = function(expression, operand, variables, constants, references, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);

		var out;
		var operandType = operand[node_COMMONATRIBUTECONSTANT.OPERAND_TYPE];
		switch(operandType){
		case node_COMMONCONSTANT.EXPRESSION_OPERAND_CONSTANT:
			out = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteConstantOperand", {"operand":operand, "constants":constants}), 
					function(requestInfo){
						var constantName = requestInfo.getService().parms.operand[node_COMMONATRIBUTECONSTANT.OPERAND_NAME];
						var constantData = requestInfo.getService().parms.operand[node_COMMONATRIBUTECONSTANT.OPERAND_DATA];
						if(constantData==undefined)  constantData = requestInfo.getService().parms.constants[constantName];
						return constantData;
					}, 
					handlers, requestInfo);
			break;
		case node_COMMONCONSTANT.EXPRESSION_OPERAND_VARIABLE: 
			out = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteVariableOperand", {"operand":operand, "variables":variables}), 
					function(requestInfo){  
						var varName = requestInfo.getService().parms.operand[node_COMMONATRIBUTECONSTANT.OPERAND_VARIABLENAME];
						return requestInfo.getService().parms.variables[varName];  
					}, 
					handlers, requestInfo);
		    break;
		case node_COMMONCONSTANT.EXPRESSION_OPERAND_OPERATION:
			out = loc_getExecuteOperationOperandRequest(expression, operand, variables, constants, references, handlers, requestInfo);
			break;
		case node_COMMONCONSTANT.EXPRESSION_OPERAND_REFERENCE:
			out = node_createServiceRequestInfoSimple(new node_ServiceInfo("ExecuteReferenceOperand", {"operand":operand, "references":references}), 
					function(requestInfo){
						var refName = requestInfo.getService().parms.operand[node_COMMONATRIBUTECONSTANT.OPERAND_REFERENCENAME];
						return requestInfo.getService().parms.references[refName];
					}, 
					handlers, requestInfo);
			break;
		case node_COMMONCONSTANT.EXPRESSION_OPERAND_DATASOURCE:
			out = loc_getExecuteDataSourceOperandRequest(expression, operand, variables, handlers, requestInfo);
			break;
		case node_COMMONCONSTANT.EXPRESSION_OPERAND_ATTRIBUTEOPERATION:
			break;
		}
		return out;
	};

	
	//execute operation operand
	var loc_getExecuteDataSourceOperandRequest = function(expression, dataSourceOperand, variables, handlers, requester_parent){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteDataSourceOperand", {"dataSourceOperand":dataSourceOperand}), handlers, requester_parent);

		var getParmsValueRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("GetDataSourceParmsValue", {}), {
			success : function(requestInfo, setResult){
				var gatewayParms = {};
				gatewayParms[node_COMMONATRIBUTECONSTANT.GATEWAYDATASOURCE_COMMAND_GETDATA_NAME] = dataSourceOperand[node_COMMONATRIBUTECONSTANT.OPERAND_DATASOURCE_NAME];
				gatewayParms[node_COMMONATRIBUTECONSTANT.GATEWAYDATASOURCE_COMMAND_GETDATA_PARMS] = setResult.getResults(); 
				return nosliw.runtime.getGatewayService().getExecuteGatewayCommandRequest(
						node_COMMONATRIBUTECONSTANT.DATASOURCEMANAGER_GATEWAY_DATASOURCE, 
						node_COMMONATRIBUTECONSTANT.GATEWAYDATASOURCE_COMMAND_GETDATA, 
						gatewayParms);
			}
		});
		var dataSourceDefinition = dataSourceOperand[node_COMMONATRIBUTECONSTANT.OPERAND_DATASOURCE_DEFINITION];
		_.each(dataSourceDefinition[node_COMMONATRIBUTECONSTANT.DATASOURCEDEFINITION_PARMS], function(parmDef, parmName){
			//get parm value from constant first
			var getParmValueRequest;
			var parmValue = dataSourceOperand[node_COMMONATRIBUTECONSTANT.OPERAND_DATASOURCE_CONSTANT][parmName];
			if(parmValue!=undefined){
				//get from constant
				getParmValueRequest = node_createServiceRequestInfoSimple({}, function(requestInfo){	return parmValue;});
			}
			else{
				//try from variable
				var mappedVarName = dataSourceOperand[node_COMMONATRIBUTECONSTANT.OPERAND_DATASOURCE_VARCONFIGURE][parmName];
				if(mappedVarName==undefine)  mappedVarName = parmName;    //no mapping, use original name to find varible

				//get from variable
				var varValue = variables[mappedVarName];
				if(varValue!=undefined){
					var varMatchers = dataSourceOperand[node_COMMONATRIBUTECONSTANT.OPERAND_DATASOURCE_PARMMATCHERS][parmName];
					//convert according to matchers
					getParmValueRequest = loc_getMatchDataTaskRequest(varValue, varMatchers);
				}
			}
			
			if(getParmValueRequest!=undefined)		getParmsValueRequest.addRequest(parmName, getParmValueRequest);
		});
		
		out.addRequest(getParmsValueRequest);
		return out;
	};
	
	//execute operation operand
	var loc_getExecuteOperationOperandRequest = function(expression, operationOperand, variables, constants, references, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteOperationOperand", {"operationOperand":operationOperand, "variables":variables}), handlers, requestInfo);

		//cal all parms
		var parmsOperand = operationOperand[node_COMMONATRIBUTECONSTANT.OPERAND_PARMS];
		var parmsOperandRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("CalOperationParms", {"parms":parmsOperand}), {
			success : function(requestInfo, setResult){
				var parmsData = setResult.getResults();
				
				//match parms and base
				var parmsMatcherRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("MatchOperationParms", {"parmsData":parmsData, "matchers":operationOperand[node_COMMONATRIBUTECONSTANT.OPERAND_MATCHERSPARMS]}), {
					success : function(requestInfo, parmMatchResult){
						var parmsData = requestInfo.getData();
						var parmMatchedData = parmMatchResult.getResults();
						_.each(parmMatchedData, function(parmValue, parmName, list){
							parmsData[parmName] = parmValue;
						}, this);
						out.setData(parmsData);

						//build parms for operation
						var operationParms = [];
						_.each(parmsData, function(parmData, parmName, list){
							operationParms.push(new node_OperationParm(parmData, parmName));
						}, this);

						//execute data operation
						var dataTypeId = operationOperand[node_COMMONATRIBUTECONSTANT.OPERAND_DATATYPEID];
						var executeOperationRequest = loc_getExecuteOperationRequest(dataTypeId, operationOperand[node_COMMONATRIBUTECONSTANT.OPERAND_OPERATION], operationParms, {
							success : function(requestInfo, data){
								return data;
							}
						});
						return executeOperationRequest;
					}
				}).withData(parmsData);
				_.each(operationOperand[node_COMMONATRIBUTECONSTANT.OPERAND_MATCHERSPARMS], function(parmMatchers, parmName, list){
					var parmMatchRequest = loc_getMatchDataTaskRequest(parmsData[parmName], parmMatchers, {});
					parmsMatcherRequest.addRequest(parmName, parmMatchRequest);
				}, this);
				return parmsMatcherRequest;
			}
		});
		_.each(parmsOperand, function(parmOperand, parmName, list){
			var parmOperandRequest = loc_getExecuteOperandRequest(expression, parmOperand, variables, constants, references, {
				success :function(request, parmValue){
					return parmValue;
				}
			});
			parmsOperandRequest.addRequest(parmName, parmOperandRequest);
		}, this);
		out.addRequest(parmsOperandRequest);
		
		return out;
	}

	
	//convert individual data according to matchers
	var loc_getMatchDataTaskRequest = function(data, matchers, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		var service = new node_ServiceInfo("MatchData", {"data":data, "matcher":matchers});
		
		var dataTypeId = data[node_COMMONATRIBUTECONSTANT.DATA_DATATYPEID];
		var matcher;
		if(matchers!=undefined){
			matcher = matchers[dataTypeId];
		}
		
		var out;
		if(matcher==undefined){
			//if converter does not created, then get it
			nosliw.error("no matches for data type: " + dataTypeId);
		}
		else{
			var relationship = matcher[node_COMMONATRIBUTECONSTANT.MATCHER_RELATIONSHIP];
			var subMatchers = matcher[node_COMMONATRIBUTECONSTANT.MATCHER_SUBMATCHERS];
			var sourceDataTypeId = relationship[node_COMMONATRIBUTECONSTANT.DATATYPERELATIONSHIP_SOURCE];
			var targetDataTypeId = relationship[node_COMMONATRIBUTECONSTANT.DATATYPERELATIONSHIP_TARGET];
			
			if(sourceDataTypeId==targetDataTypeId){
				if(node_basicUtility.isEmptyObject(subMatchers)==true){
					//no need to convert
					out = node_createServiceRequestInfoSimple(service, function(requestInfo){
						return requestInfo.getService().parms.data;
					}, handlers, requestInfo);
				}
				else{
					out = node_createServiceRequestInfoService(service, handlers, requestInfo);
					var resourceRequestDependency = new node_DependentServiceRequestInfo(loc_getSubMatchDataTaskRequest(data, subMatchers), {
						success : function(requestInfo, convertedSubData){
							return convertedSubData;
						}
					});
					out.setDependentService(resourceRequestDependency);
				}
			}
			else{
				out = node_createServiceRequestInfoSequence(service, handlers, requestInfo);
				var matcherSegments = relationship[node_COMMONATRIBUTECONSTANT.DATATYPERELATIONSHIP_PATH];

				var targets = [];
				var sourceId = relationship[node_COMMONATRIBUTECONSTANT.DATATYPERELATIONSHIP_SOURCE];
				var targetId;
				for(var i=0; i<matcherSegments.length; i++){
					targetId = node_namingConvensionUtility.parseLevel2(matcherSegments[i])[1];
					targets.push(targetId);
					sourceId = targetId;
				}
				
				var converterData = data;
				for(var i=0; i<targets.length; i++){
					var converterRequest = loc_getExecuteConverterToRequest(converterData, targets[i], matcher.reverse, {
						success : function(requestInfo, convertedData){
							converterData = convertedData;
						}
					}, out);
					out.addRequest(converterRequest);
				}

				//convert sub data
				if(!node_basicUtility.isEmptyObject(subMatchers)==true){
					out.addRequest(loc_getSubMatchDataTaskRequest(converterData, subMatchers, {
						success : function(requestInfo, convertedData){
							converterData = convertedData;
						}
					}));
				}
				
				out.setRequestProcessors({
					success : function(reqInfo, result){
						return converterData;
					}, 
				});
			}
			return out;
		}
	};

	//convert data according to sub matchers
	var loc_getSubMatchDataTaskRequest = function(data, submatchers, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);

		//get all subNames involved in match
		var subNames = [];
		_.each(submatchers, function(submatcher, name){subNames.push(name)});

		var subDatas = {};
		
		//get all sub data
		var getSubDatasRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("GetSubDatas", {"data":data, "subNames":subNames}), {
			success : function(requestInfo, subDatasResult){
				subDatas = subDatasResult.getResults();
			}
		});
		
		_.each(subNames, function(name){
			//get each sub data request
			getSubDatasRequest.addRequest(name, loc_out.getExecuteOperationRequest(data[node_COMMONATRIBUTECONSTANT.DATA_DATATYPEID], "getSubData", [{"name":node_dataUtility.createStringData("name")}], {}));
		});

		//convert all sub data
		var convertSubDatasRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("MatchSubDatas", {"data":subDatas, "subNames":subNames}), {
			success : function(requestInfo, subDatasResult){
				subDatas = subDatasResult.getResults();
			}
		});
		
		//convert each sub data
		_.each(subNames, function(name){
			convertSubDatasRequest.addRequest(name, loc_getMatchDataTaskRequest(subDatas[name], submatchers[name], {}));
		});
		
		//set all sub data
		var setSubDatasRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("SetSubDatas", {"subDatas":subDatas, "subNames":subNames}), {
			success : function(requestInfo, subValuesResult){
			}
		});
			
		_.each(subNames, function(name){
			setSubDatasRequest.addRequest(name, loc_out.getExecuteOperationRequest(data[node_COMMONATRIBUTECONSTANT.DATA_DATATYPEID], "setSubData", [{"name":node_dataUtility.createStringData("name")}, {"data":subDatas[name]}], {}));
		});
			
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("SubMatchers", {"data":data, "submatchers":submatchers}), handlers, requestInfo);
		out.addRequest(getSubDatasRequest);
		out.addRequest(convertSubDatasRequest);
		out.addRequest(setSubDatasRequest);
		return out;
	};
	
	//execute conterter
	var loc_getExecuteConverterToRequest = function(data, targetDataTypeId, reverse, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteConverter", {"data":data, "targetDataTypeId":targetDataTypeId}), handlers, requestInfo);

		var dataTypeId;
		if(reverse){
			dataTypeId = targetDataTypeId;
		}
		else{
			dataTypeId = data[node_COMMONATRIBUTECONSTANT.DATA_DATATYPEID];
		}
		
		var converterResourceId = node_resourceUtility.createConverterResourceId(dataTypeId);
		var getResourceRequest = nosliw.runtime.getResourceService().getGetResourcesRequest([converterResourceId], {
			success : function(requestInfo, resourcesTree){
				var dataTypeId;
				if(reverse){
					dataTypeId = data[node_COMMONATRIBUTECONSTANT.DATA_DATATYPEID];
				}
				else{
					dataTypeId = targetDataTypeId;
				}
				return node_expressionUtility.executeConvertResource(converterResourceId, data, dataTypeId, reverse, resourcesTree);
			}
		});
		out.addRequest(getResourceRequest);
		return out;
	};
	
	//execute data operation
	var loc_getExecuteOperationRequest = function(dataTypeId, operation, parmArray, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteOperation", {"dataType":dataTypeId, "operation":operation, "parms":parmArray}), handlers, requestInfo);

		var dataOperationResourceId = node_resourceUtility.createOperationResourceId(dataTypeId, operation);
		var getResourceRequest = nosliw.runtime.getResourceService().getGetResourcesRequest([dataOperationResourceId], {
			success : function(requestInfo, resourcesTree){
				var opResult = node_expressionUtility.executeOperationResource(dataOperationResourceId, parmArray, resourcesTree);
				return opResult;
			}
		});
		out.addRequest(getResourceRequest);
		return out;
	};	

	var loc_getExecuteScriptRequest = function(script, expressions, variables, scriptConstants, handlers, requester_parent){
		var requestInfo = loc_out.getRequestInfo(requester_parent);
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExpressionService_ExecuteScript", {"script":script, "expressions":expressions, "variables":variables}), handlers, requestInfo);

		//calculate multiple expression
		var executeMultipleExpressionRequest = node_createServiceRequestInfoSet(new node_ServiceInfo("ExecuteMultipleExpression", {"expressions":expressions, "variables":variables}), {
			success : function(requestInfo, expressionsResult){
				var expressionsData = expressionsResult.getResults();
				return script.call(undefined, expressionsData, scriptConstants, variables);
			}
		});
		_.each(expressions, function(expression, name){
			//find variable value only for this expression
			var expVariables = {};
			_.each(expression[node_COMMONATRIBUTECONSTANT.EXPRESSION_VARIABLEINFOS], function(varInfo, name){
				expVariables[name] = variables[name];
			});
			executeMultipleExpressionRequest.addRequest(name, loc_getExecuteExpressionRequest(expression, expVariables, {}, {}));
		});
		
		out.addRequest(executeMultipleExpressionRequest);
		return out;
	};

	var loc_out = {
		
		getExecuteOperationRequest : function(dataTypeId, operation, parmsArray, handlers, requester_parent){
			return loc_getExecuteOperationRequest(dataTypeId, operation, parmsArray, handlers, requester_parent);
		},
			
		executeExecuteOperationRequest : function(dataTypeId, operation, parmsArray, handlers, requester_parent){
			var requestInfo = this.getExecuteOperationRequest(dataTypeId, operation, parmsArray, handlers, requester_parent);
			node_requestServiceProcessor.processRequest(requestInfo);
		},

		getExecuteExpressionRequest : function(expression, variables, constants, references, handlers, requester_parent){
			return loc_getExecuteExpressionRequest(expression, variables, constants, references, handlers, requester_parent);
		},
			
		executeExecuteExpressionRequest : function(expression, variables, constants, references, handlers, requester_parent){
			var requestInfo = this.getExecuteExpressionRequest(expression, variables, constants, references, handlers, requester_parent);
			node_requestServiceProcessor.processRequest(requestInfo);
		},

		getMatchDataRequest : function(data, matchers, handlers, requester_parent){
			return loc_getMatchDataTaskRequest(data, matchers, handlers, requester_parent);
		},

		executeMatchDataRequest : function(data, matchers, handlers, requester_parent){
			var requestInfo = this.getMatchDataRequest(data, matchers, handlers, requester_parent);
			node_requestServiceProcessor.processRequest(requestInfo);
		},
		
		/**
		 * Execute script expression
		 * 		script : function with parameter map (name : expression result)
		 * 		expressions : map (name : expression)
		 * 		variables : variables for expression
		 * 		scriptConstants : constants in script
		 */
		getExecuteScriptRequest : function(script, expressions, variables, scriptConstants, handlers, requester_parent){
			return loc_getExecuteScriptRequest(script, expressions, variables, scriptConstants, handlers, requester_parent);
		},
	
		executeExecuteScriptExpressionRequest : function(script, expressions, variables, scriptConstants, handlers, requester_parent){
			var requestInfo = this.getExecuteScriptRequest(script, expressions, variables, scriptConstants, handlers, requester_parent);
			node_requestServiceProcessor.processRequest(requestInfo);
		},
	};
	
	loc_out = node_buildServiceProvider(loc_out, "expressionService");
	
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("resource.utility", function(){node_resourceUtility = this.getData();});
nosliw.registerSetNodeDataEvent("request.buildServiceProvider", function(){node_buildServiceProvider = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("common.utility.basicUtility", function(){node_basicUtility = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){node_createServiceRequestInfoSet = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoService", function(){node_createServiceRequestInfoService = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("expression.entity.OperationContext", function(){node_OperationContext = this.getData();});
nosliw.registerSetNodeDataEvent("expression.entity.OperationParm", function(){node_OperationParm = this.getData();});
nosliw.registerSetNodeDataEvent("expression.entity.OperationParms", function(){node_OperationParms = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.entity.DependentServiceRequestInfo", function(){node_DependentServiceRequestInfo = this.getData();});
nosliw.registerSetNodeDataEvent("expression.utility", function(){node_expressionUtility = this.getData();});
nosliw.registerSetNodeDataEvent("expression.dataUtility", function(){node_dataUtility = this.getData();});
nosliw.registerSetNodeDataEvent("common.namingconvension.namingConvensionUtility", function(){node_namingConvensionUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createExpressionService", node_createExpressionService); 
	
})(packageObj);