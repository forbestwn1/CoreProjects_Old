//get/create package
var packageObj = library.getChildPackage("entity");    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_CONSTANT;
	var node_makeObjectWithType;
	var node_getObjectType;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoSequence;
	var node_ServiceInfo;
	var node_createServiceRequestInfoSet;
	var node_ioTaskUtility;
	var node_createEventObject;
//*******************************************   Start Node Definition  ************************************** 	

//task result 
//  resultName : name of the result
//  result: result value map (value name / value)
var node_IOTaskResult = function(resultName, resultValue){
	this.resultName = resultName;
	this.resultValue = resultValue; 
};

var node_ExternalMapping = function(dataIO, dataAssociationDef){
	this.dataIO = dataIO;
	this.dataAssociationDef = dataAssociationDef;
	node_makeObjectWithType(this, node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_EXTERNALMAPPING);
};

var node_createDynamicData = function(getValueRequestFun, setValueRequestFun){
	var loc_getValueRequestFun = getValueRequestFun;
	var loc_setValueRequestFun = setValueRequestFun;
	
	var loc_out = {
		getGetValueRequest : function(handlers, request){
			return loc_getValueRequestFun(handlers, request);
		},
		
		getSetValueRequest : function(value, handlers, request){
			return loc_setValueRequestFun(value, handlers, request);
		}
	};
	
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_DYNAMICDATA);
	
	return loc_out;
};

var node_createIODataSet = function(value){
	
	var loc_eventSource = node_createEventObject();
	var loc_eventListener = node_createEventObject();
	
	if(value!=undefined&&node_getObjectType(value)==node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_DATASET){
		return value;
	}

	var loc_init = function(value){
		//value is default value
		if(value!=undefined) loc_out.prv_dataSet[node_COMMONCONSTANT.DATAASSOCIATION_RELATEDENTITY_DEFAULT] = value;
	};
	
	var loc_trigueEvent = function(eventName, eventData, requestInfo){loc_eventSource.triggerEvent(eventName, eventData, requestInfo); };

	var loc_out = {
		
		prv_dataSet : {},
		prv_id : nosliw.generateId(),
			
		setData : function(name, data, request){  
			if(name==undefined)  name = node_COMMONCONSTANT.DATAASSOCIATION_RELATEDENTITY_DEFAULT;
			loc_out.prv_dataSet[name] = data;   
			loc_trigueEvent(node_CONSTANT.IODATASET_EVENT_CHANGE, undefined, request);
		},
		
		getData : function(name){
			if(name==undefined)  name = node_COMMONCONSTANT.DATAASSOCIATION_RELATEDENTITY_DEFAULT;
			var out = loc_out.prv_dataSet[name];
			if(out==undefined){
				out = {};
				loc_out.prv_dataSet[name] = out;
			}
			return out;
		},

		generateDataEle : function(name){
			return node_createDynamicData(
				function(handlers, request){
					return loc_out.getGetDataValueRequest(name, handlers, request);
				},
				function(value, handlers, request){
					return loc_out.getSetDataValueRequest(name, value, handlers, request);
				}
			);
		},
		
		getDataSet : function(){   return loc_out.prv_dataSet;   },
		
		getGetDataValueRequest : function(name, handlers, request){
			var dataEle = this.getData(name);
			var dataEleType = node_getObjectType(dataEle);
			if(dataEleType==node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_DYNAMICDATA){
				return dataEle.getGetValueRequest(handlers, request);
			}
			else{
				return node_createServiceRequestInfoSimple(undefined, function(request){
					return dataEle;
				}, handlers, request);
			}
		},

		getGetDataSetValueRequest : function(handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			var getDataItemRequest = node_createServiceRequestInfoSet(undefined, {
				success : function(request, resultSet){
					var dataSetValue = {};
					_.each(resultSet.getResults(), function(value, name){
						dataSetValue[name] = value;
					});
					return dataSetValue;
				}
			});
			
			_.each(loc_out.prv_dataSet, function(dataSetEle, name){
				getDataItemRequest.addRequest(name, loc_out.getGetDataValueRequest(name));
			});
			out.addRequest(getDataItemRequest);
			return out;
		},

		getSetDataValueRequest : function(name, value, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			var dataEle = this.getData(name);
			var dataEleType = node_getObjectType(dataEle);
			if(dataEleType==node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_DYNAMICDATA){
				return loc_out.getData(name).getSetValueRequest(value, {
					success : function(request, data){
						loc_trigueEvent(node_CONSTANT.IODATASET_EVENT_CHANGE, undefined, request);
						return data;
					}
				});
			}
			else{
				out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
					loc_out.setData(name, value, request);
					return value;
				}));
			}
			return out;
		},
		
		getMergeDataValueRequest : function(name, value, isDataFlat, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			var dataEle = this.getData(name);
			var dataEleType = node_getObjectType(dataEle);
			if(dataEleType==node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_DYNAMICDATA){
				out.addRequest(loc_out.getGetDataValueRequest(name, {
					success : function(request, value){
						var output = node_ioTaskUtility.mergeContext(request.getData('value'), value, isDataFlat);
						return loc_out.getData(request.getData('name')).getSetValueRequest(output, {
							success : function(request, data){
								loc_trigueEvent(node_CONSTANT.IODATASET_EVENT_CHANGE, undefined, request);
								return data;
							}
						});
					}
				}).withData(name, 'name').withData(value, 'value'));
			}
			else{
				out.addRequest(node_createServiceRequestInfoSimple(undefined, function(request){
					var data = node_ioTaskUtility.mergeContext(request.getData('value'), loc_out.getData(request.getData('name')), isDataFlat);
					loc_trigueEvent(node_CONSTANT.IODATASET_EVENT_CHANGE, undefined, request);
					return data;
				}).withData(name, 'name').withData(value, 'value'));
			}
			return out;
		},
		
		registerEventListener : function(listener, handler, thisContext){  return loc_eventSource.registerListener(undefined, listener, handler, thisContext); },
		unregisterEventListener : function(listener){	return loc_eventSource.unregister(listener); },
		
	};
	
	loc_init(value);
	
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_DATAASSOCIATION_DATASET);
	
	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.getObjectType", function(){node_getObjectType = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSet", function(){node_createServiceRequestInfoSet = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.ioTaskUtility", function(){node_ioTaskUtility = this.getData();	});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});

//Register Node by Name
packageObj.createChildNode("IOTaskResult", node_IOTaskResult); 
packageObj.createChildNode("createIODataSet", node_createIODataSet); 
packageObj.createChildNode("createDynamicData", node_createDynamicData); 

})(packageObj);
