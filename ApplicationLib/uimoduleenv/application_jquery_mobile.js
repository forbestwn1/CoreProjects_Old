function(uiModule){
	var node_createServiceRequestInfoCommon = nosliw.getNodeData("request.request.createServiceRequestInfoCommon");
	var node_createServiceRequestInfoSimple = nosliw.getNodeData("request.request.createServiceRequestInfoSimple");
	var node_createServiceRequestInfoSet = nosliw.getNodeData("request.request.createServiceRequestInfoSet");
	var node_ServiceRequestExecuteInfo = nosliw.getNodeData("request.entity.ServiceRequestExecuteInfo");
	var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
	var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
	var node_ServiceInfo = nosliw.getNodeData("common.service.ServiceInfo");
	var node_COMMONCONSTANT = nosliw.getNodeData("constant.COMMONCONSTANT");

	var loc_uiModule = uiModule;
	var loc_uiStacks = []; 
	
	
	var loc_getUpdateBackIconsRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSet(undefined, handlers, request);
		_.each(loc_uiStacks, function(ui, index){
			var displayStyle = "inline";
			if(index==0){
				displayStyle = "none";
			}
			out.addRequest(ui.getName(), ui.getUpdateContextRequest({
				backIconDisplay : displayStyle
			}));
		});
		return out;
	};

	var loc_getTransferToRequest = function(uiName, mode, handlers, requestInfo){
		$.mobile.changePage($("#"+uiName));
		loc_uiStacks.push(loc_uiModule.getUI(uiName));
		return loc_getUpdateBackIconsRequest(handlers, requestInfo);
	};
	
	var loc_transferBack = function(){
		loc_uiStacks.pop();
		$.mobile.back();
	};

	var loc_processUIEvent = function(eventName, uiName, eventData, request){
		if(eventName=="transferBack"){
			loc_transferBack();
		}
	};

	
	var loc_out = {
			getPresentUIRequest : function(uiName, mode, handlers, requestInfo){
				return loc_getTransferToRequest(uiName, mode, handlers, requestInfo);
			},
			
			getExecuteCommandRequest : function(uiName, commandName, commandData, handlers, requestInfo){
				return loc_uiModule.getUI(uiName).getExecuteCommandRequest(commandName, commandData, handlers, requestInfo);
			},
			
			processUIEvent : function(eventName, uiName, eventData, request){
				loc_processUIEvent(eventName, uiName, eventData, request);
			},
			
			//runtime execute request through this method, so that ui can do something (for instance, spinning circle)
			processRequest : function(request){     node_requestServiceProcessor.processRequest(request);   },
			
			getInitRequest :function(handlers, requestInfo){
				var out = node_createServiceRequestInfoCommon(undefined, handlers, requestInfo);
				out.setRequestExecuteInfo(new node_ServiceRequestExecuteInfo(function(){
					//put ui together
					_.each(loc_uiModule.getUIs(), function(ui, index){
						ui.getPage().appendTo(loc_uiModule.getView());
					});

					$(document).bind("mobileinit", function() {
						out.executeSuccessHandler();
					});
					//load jquery mobile
					nosliw.runtime.getResourceService().executeGetResourceDataByTypeRequest(["external.jQuery_Mobile;1.4.5"], node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_JSLIBRARY);

				}));
				return out;
			},

	};
	return loc_out;
}
