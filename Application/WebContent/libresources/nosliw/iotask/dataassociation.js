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
	var node_EndActivityOutput;
	var node_ProcessResult;
	var node_createServiceRequestInfoService;
	var node_DependentServiceRequestInfo;
	var node_requestServiceProcessor;
	var node_IOTaskResult;
	var node_createIODataSet;
	var node_requestServiceProcessor;
	var node_makeObjectWithType;
	var node_getObjectType;
	var node_ioTaskUtility;
	var node_getExecuteMappingDataAssociationRequest;

//*******************************************   Start Node Definition  ************************************** 	
//dataAssociation that has inputIO, dataAssociation and outputIO
//when it is executed, the data from inputIO is mapped to outputIO
//the type of inputIO can be IODataSet or another dataAssociation
var node_createDataAssociation = function(inputIO, dataAssociationDef, outputIODataSet){
	
	var loc_inputIO = node_createIODataSet(inputIO);
	var loc_outputIODataSet = node_createIODataSet(outputIODataSet);
	var loc_dataAssociationDef = dataAssociationDef;

	//execute association
	var loc_executeDataAssociationConvertFun  = function(association, inputDataSet){
		if(association==undefined || association[node_COMMONATRIBUTECONSTANT.EXECUTABLEASSOCIATION_CONVERTFUNCTION]==undefined) return undefined;
		return association[node_COMMONATRIBUTECONSTANT.EXECUTABLEASSOCIATION_CONVERTFUNCTION](inputDataSet, node_objectOperationUtility.assignObjectAttributeByPath);
	};

	//merge extraDataSet with inputIODataSet to create inputDataSet for data association
	var loc_getInputDataSetRequest = function(inputIO, extraDataSet, handlers, request){
		var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
		out.addRequest(inputIO.getGetDataSetValueRequest({
			success : function(request, intputDataSet){
				if(extraDataSet!=undefined){
					_.each(extraDataSet.getDataSet(), function(extraData, setName){
						var inputData = intputDataSet[setName];
						if(inputData==undefined){
							inputData = {};
							intputDataSet[setName] = inputData;
						}
						node_ioTaskUtility.mergeContext(extraData, inputData, loc_dataAssociationDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEDATAASSOCIATION_INPUT][setName]);
					})
				}
				return intputDataSet;
			}
		}));
		return out;
	};

	//execute data association of mirror type
	var loc_getExecuteMirrorDataAssociationRequest = function(inputDataSet, handlers, request){
		var service = new node_ServiceInfo("ExecuteMirrorDataAssociation", {});
		var out = node_createServiceRequestInfoSet(undefined, {
			success : function(request, resultSet){
				return loc_outputIODataSet;
			}
		});

		_.each(inputDataSet, function(inputData, name){
			var inputFlat = loc_dataAssociationDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEDATAASSOCIATION_INPUT][name];
			var outputFlat = loc_dataAssociationDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEDATAASSOCIATION_OUTPUT][name];
			
			var outputData = inputData;
			if(inputFlat!=outputFlat){
				if(inputFlat==true){
					//input is flat and output is not flat
					//put input into public categary
					outputData = {
						public : inputData
					}
				}
				else{
					//input is not flat, output is flat
					//make output flat
					outputData = {};
					_.each(node_ioTaskUtility.getContextTypes(), function(categary, index){
						var context = inputData[categary];
						if(context!=undefined){
							_.each(context, function(value, name){
								outputData[name] = value;
							});
						}
					});
				}
			}
			
			out.addRequest(name, node_ioTaskUtility.outputToDataSetIORequest(loc_outputIODataSet, outputData, name, true));
		});
		
		return out;
	};

	var loc_getExecuteNoneDataAssociationRequest = function(intputDataSet, handlers, request){
		return node_createServiceRequestInfoSimple(undefined, function(request){
			return loc_outputIODataSet;
		}, handlers, request);
	};

	var loc_getExecuteDataAssociationRequest = function(inputIO, extraDataSet, handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteDataAssociation", {}), handlers, request);
		out.addRequest(loc_getInputDataSetRequest(inputIO, extraDataSet, {
			success : function(request, intputDataSet){
				if(loc_dataAssociationDef==undefined)  return loc_getExecuteNoneDataAssociationRequest(intputDataSet);   //if no data association, then nothing happen
				else{
					var type = loc_dataAssociationDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEDATAASSOCIATION_TYPE];
					if(type==node_COMMONCONSTANT.DATAASSOCIATION_TYPE_MAPPING)	return node_getExecuteMappingDataAssociationRequest(intputDataSet, loc_dataAssociationDef, loc_outputIODataSet);
					else if(type==node_COMMONCONSTANT.DATAASSOCIATION_TYPE_MIRROR)		return loc_getExecuteMirrorDataAssociationRequest(intputDataSet);
					else if(type==node_COMMONCONSTANT.DATAASSOCIATION_TYPE_NONE)	return loc_getExecuteNoneDataAssociationRequest(intputDataSet);
				}
			}
		}));
		return out;
	};

	var loc_out = {
		
		getExecuteDataAssociationRequest : function(extraData, handlers, request){
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("ExecuteDataAssociation", {}), handlers, request);
			if(extraData!=undefined)  extraData = node_createIODataSet(extraData);
			var inputIOType = node_getObjectType(loc_inputIO);
			if(inputIOType==node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_IODATASET){
				out.addRequest(loc_getExecuteDataAssociationRequest(loc_inputIO, extraData, {
					success :function(request){
						return loc_outputIODataSet;
					}
				}));
			}
			else if(inputIOType==node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION){
				out.add(loc_inputIO.getExecuteRequest({
					success : function(request, inputIO){
						out.addRequest(loc_getExecuteDataAssociationRequest(inputIO, extraData, {
							success :function(request){
								return loc_outputIODataSet;
							}
						}));
					}
				}));
			}
			return out;
		},
			
		getExecuteRequest : function(handlers, request){
			return this.getExecuteDataAssociationRequest(undefined, handlers, request);
		},

		executeRequest : function(handlers, request){
			var requestInfo = this.getExecuteRequest(handlers, request);
			node_requestServiceProcessor.processRequest(requestInfo);
		},
		
		getExecuteCommandRequest : function(commandName, data, handlers, request){
			if(commandName=="execute"){
				return this.getExecuteDataAssociationRequest(data, handlers, request);
			}
		}
	};
	
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION);

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
nosliw.registerSetNodeDataEvent("process.entity.EndActivityOutput", function(){node_EndActivityOutput = this.getData();	});
nosliw.registerSetNodeDataEvent("process.entity.ProcessResult", function(){node_ProcessResult = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoService", function(){node_createServiceRequestInfoService = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){node_createServiceRequestInfoSet = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.entity.DependentServiceRequestInfo", function(){node_DependentServiceRequestInfo = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.entity.IOTaskResult", function(){node_IOTaskResult = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.entity.createIODataSet", function(){node_createIODataSet = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.ioTaskUtility", function(){node_ioTaskUtility = this.getData();	});
nosliw.registerSetNodeDataEvent("iotask.getExecuteMappingDataAssociationRequest", function(){node_getExecuteMappingDataAssociationRequest = this.getData();	});

//Register Node by Name
packageObj.createChildNode("createDataAssociation", node_createDataAssociation); 

})(packageObj);
