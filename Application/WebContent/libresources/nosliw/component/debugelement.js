//get/create package
var packageObj = library.getChildPackage("debug");    


(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_getComponentLifecycleInterface;
	var node_getComponentManagementInterface;
	var node_createServiceRequestInfoSimple;
	var node_createIODataSet;
	var node_createDynamicIOData;
	var node_requestServiceProcessor;
	var node_createEventObject;
	var node_getComponentManagementInterface;
	
//*******************************************   Start Node Definition  ************************************** 	
//load component and init it with inputValue
var node_createComponentResetView = function(resetCallBack, resourceType, resourceId, inputValue){
	var loc_resourceType = resourceType;
	var loc_resourceId = resourceId;
	var loc_inputIODataSet = node_createIODataSet();
	var loc_resetCallBack = resetCallBack;
	
	var loc_view = $('<div>Component Input: </div>');
	var loc_resourceTypeView = $('<textinput></textinput><br>');
	var loc_resourceIdView = $('<textinput></textinput><br>');
	var loc_inputValueView = $('<textarea rows="5" cols="150" style="resize: none;" data-role="none"></textarea>');
	var loc_submitView = $('<button>Reset</button>');
	loc_view.append(loc_resourceTypeView);
	loc_view.append(loc_resourceIdView);
	loc_view.append(loc_inputValueView);
	loc_view.append(loc_submitView);
	
	var loc_init = function(resourceType, resourceId, inputValue){
		if(resourceType!=undefined)   	loc_resourceTypeView.val(resourceType);
		if(resourceId!=undefined)  		loc_resourceIdView.val(resourceId);
		if(inputValue!=undefined)		loc_inputValueView.val(JSON.stringify(inputValue));
		
		loc_inputIODataSet.setData(undefined, node_createDynamicIOData(
			function(handlers, request){
				return node_createServiceRequestInfoSimple(undefined, function(request){
					var content = loc_inputValueView.val();
					if(content=='')  return;
					return JSON.parse(content); 
				}, handlers, request); 
			} 
		));

		loc_submitView.on('click', function(){
			loc_resetCallBack();
		});
	};
	
	var loc_out = {

		getView : function(){  return loc_view;   },
		
		getInputIODataSet : function(){	return loc_inputIODataSet;	},
		
		getResourceType : function(){  return loc_resourceTypeView.val();  },
		
		getResourceId : function(){  return loc_resourceIdView.val();  }
	};
	
	loc_init(resourceType, resourceId, inputValue);
	
	return loc_out;
};

//display component context value
var node_createComponentDataView = function(){
	var loc_comInterface;
	
	var loc_view = $('<div>Component Data: </div>');
	var loc_textView = $('<textarea rows="5" cols="150" style="resize: none;" data-role="none"></textarea>');
	loc_view.append(loc_textView);

	var loc_listener = node_createEventObject();

	var loc_clearup = function(){
		if(loc_comInterface!=undefined){
			loc_comInterface.unregisterContextDataChangeEventListener(loc_listener);
			loc_comInterface = undefined;
		}
	};

	var loc_showDataSet = function(dataSet){	loc_textView.val(JSON.stringify(dataSet, null, 4));	};
	
	var loc_setup = function(component, request){
		loc_comInterface = node_getComponentManagementInterface(component);
		loc_comInterface.registerContextDataChangeEventListener(loc_listener, function(eventName, dataSet){
			loc_showDataSet(dataSet);
		});
		node_requestServiceProcessor.processRequest(loc_comInterface.getContextDataSetValueRequest({
			success : function(request, dataSet){
				loc_showDataSet(dataSet);
			}
		}, request));
	};
	
	var loc_out = {
		getView : function(){  return loc_view;   },
		
		setComponent : function(component, request){
			loc_clearup();
			loc_setup(component, request);
		}
	};
	
	return loc_out;
};

//display component event
var node_createComponentEventView = function(){
	var loc_comInterface;
	var loc_view = $('<div>Component Event: </div>');
	var loc_textView = $('<textarea rows="5" cols="150" style="resize: none;" data-role="none"></textarea>');
	loc_view.append(loc_textView);

	var loc_clearup = function(){};
	
	var loc_setup = function(component){
		loc_comInterface = node_getComponentManagementInterface(component);
		loc_comInterface.registerEventListener(undefined, function(eventName, eventData, request){
			var content = loc_textView.val();
			content = content + "\n\n*****************************************\n\n";
			content = content + JSON.stringify({
				eventName : eventName,
				eventData : eventData
			}, null, 4);
			
			loc_textView.val(content);
		});
	};
	
	var loc_out = {
		getView : function(){  return loc_view;   },
		
		setComponent : function(component){
			loc_clearup();
			loc_setup(component);
		}
	};
	return loc_out;
};

//component lifecycle 
var node_createComponentLifeCycleDebugView = function(){

	var loc_view = $('<div></div>');
	
	var loc_stateView = {};
	var loc_commandView = {};
	
	var loc_lifecycle;
	var loc_stateMachine;

	var loc_updateCandidateView = function(all, candidates, views){
		if(candidates==undefined)  candidates = [];
		_.each(all, function(ele, i){
			if(candidates.includes(ele)){
				views[ele].css('color', 'green');
			}
			else{
				views[ele].css('color', 'red');
			}
		});		
	};

	var loc_setup = function(component){
		loc_view.empty();
		loc_stateView = {};
		loc_commandView = {};
		
		loc_lifecycle = node_getComponentLifecycleInterface(component);
		loc_stateMachine = loc_lifecycle.getStateMachine();
		
		var allStatesView = $('<div>All States : </div>');
		_.each(loc_stateMachine.getAllStates(), function(state, i){
			var stateView = $('<a>'+state+'</a>');
			allStatesView.append(stateView);
			allStatesView.append($('<span>&nbsp;&nbsp;</span>'));
			stateView.on('click', function(){
				event.preventDefault();
				loc_lifecycle.transit([state]);
			});
			loc_stateView[state] = stateView;
		});
		loc_view.append(allStatesView);

		var allCommandsView = $('<div>All Commands : </div>');
		_.each(loc_stateMachine.getAllCommands(), function(command, i){
			var commandView = $('<br><a>'+command+'</a>');
			allCommandsView.append(commandView);
			allCommandsView.append($('<span>&nbsp;&nbsp;</span>'));
			commandView.on('click', function(){
				event.preventDefault();
//				loc_lifecycle.command(command);
				loc_lifecycle.executeTransitRequest(command, {
					success : function(request){
						console.log('aaa');
					}
				});
			});
			loc_commandView[command] = commandView;
		});
		loc_view.append(allCommandsView);
		
		var stateHistoryBlockView = $('<div>State History : </div>');
		var currentStateBlockView = $('<div>Current State : </div>');
		var stateHistoryView = $('<span></span>');
		stateHistoryBlockView.append(stateHistoryView);
		var currentStateView = $('<span></span>');
		currentStateBlockView.append(currentStateView);
		loc_view.append(stateHistoryBlockView);
		loc_view.append(currentStateBlockView);
		stateHistoryView.text(loc_stateMachine.getCurrentState());
		currentStateView.text(loc_stateMachine.getCurrentState());
		loc_updateCandidateView(loc_stateMachine.getAllStates(), loc_stateMachine.getNextStateCandidates(), loc_stateView);
		loc_updateCandidateView(loc_stateMachine.getAllCommands(), loc_stateMachine.getCommandCandidates(), loc_commandView);
		loc_lifecycle.registerEventListener(undefined, function(eventName, eventData, request){
			if(eventName==node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_FINISHTRANSITION){
				stateHistoryView.text(stateHistoryView.text() + " -- " + loc_stateMachine.getCurrentState());
			}
			else if(eventName==node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_FAILTRANSITION){
				stateHistoryView.text(stateHistoryView.text() + " XX " + loc_stateMachine.getCurrentState());
			}
			else if(eventName==node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_NOTRANSITION){
				stateHistoryView.text(stateHistoryView.text() + " XX " + loc_stateMachine.getCurrentState());
			}
			currentStateView.text(loc_stateMachine.getCurrentState());
			loc_updateCandidateView(loc_stateMachine.getAllStates(), loc_stateMachine.getNextStateCandidates(), loc_stateView);
			loc_updateCandidateView(loc_stateMachine.getAllCommands(), loc_stateMachine.getCommandCandidates(), loc_commandView);
		});
		
	};
	
	var loc_out = {
		
		getView : function(){   return loc_view;   },
		
		setComponent : function(component){
			loc_setup(component);
		}
	};
	
	return loc_out;
};
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentLifecycleInterface", function(){node_getComponentLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentInterface", function(){node_getComponentManagementInterface = this.getData();});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){node_createServiceRequestInfoSimple = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.entity.createIODataSet", function(){node_createIODataSet = this.getData();});
nosliw.registerSetNodeDataEvent("iotask.entity.createDynamicData", function(){node_createDynamicIOData = this.getData();});
nosliw.registerSetNodeDataEvent("request.requestServiceProcessor", function(){node_requestServiceProcessor = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("component.getComponentManagementInterface", function(){node_getComponentManagementInterface = this.getData();});


//Register Node by Name
packageObj.createChildNode("createComponentLifeCycleDebugView", node_createComponentLifeCycleDebugView); 
packageObj.createChildNode("createComponentDataView", node_createComponentDataView); 
packageObj.createChildNode("createComponentEventView", node_createComponentEventView); 
packageObj.createChildNode("createComponentResetView", node_createComponentResetView); 

})(packageObj);
