/**
 * 
 */
//get/create package
var packageObj = library.getChildPackage();    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_ServiceInfo;
	var node_createServiceRequestInfoSequence;
	var node_createServiceRequestInfoSimple;
	var node_createServiceRequestInfoCommon;
	var node_makeObjectWithName;
	var node_makeObjectWithLifecycle;
	var node_getLifecycleInterface;
	var node_createEventObject;
	var node_createNodeElement;
	var node_createConnectionLink;
//*******************************************   Start Node Definition  ************************************** 	
var loc_mduleName = "overview";
	
var node_createModuleOverview = function(parm){

	var loc_root = parm;

	var loc_eventSource = node_createEventObject();
	var loc_eventListener = node_createEventObject();

	var loc_triggerEvent = function(eventName, eventData, request){
		if(request==undefined)  request = node_createServiceRequestInfoCommon();
		loc_eventSource.triggerEvent(eventName, eventData, request);
	};

	var loc_paper;
	var loc_graph;
	
	var loc_nodeElements = {};
	var loc_connectionLinks = {}; 
	
	var loc_story;
	
	var lifecycleCallback = {};
	lifecycleCallback[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT] = function(handlers, requestInfo){
		loc_graph = new joint.dia.Graph;

		 loc_paper = new joint.dia.Paper({
		        el: loc_root,
		        model: loc_graph,
		        width: 1000,
		        height: 1000,
		        gridSize: 1
		    });
		
//		    var rect = new joint.shapes.standard.Rectangle();
//		    rect.position(100, 30);
//		    rect.resize(100, 40);
//		    rect.attr({
//		        body: {
//		            fill: 'blue'
//		    },
//		    label: {
//		        text: 'Hello',
//		        fill: 'white'
//		    }
//		});
//		rect.addTo(loc_graph);
		
//		var rect2 = rect.clone();
//		rect2.translate(300, 0);
//		rect2.attr('label/text', 'World!');
//		rect2.addTo(graph);
		
//		var link = new joint.shapes.standard.Link();
//		link.source(rect);
//		link.target(rect2);
//		link.addTo(graph);
	};

	var loc_out = {
		
		refreshRequest : function(story, handlers, requestInfo){
			var that  = this;
			loc_story = story;
			var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("RefreshOverviewModule", {}), handlers, requestInfo);
			out.addRequest(node_createServiceRequestInfoSimple(new node_ServiceInfo("RefreshOverviewModule", {}), 
				function(requestInfo){
					var nodes = story[node_COMMONATRIBUTECONSTANT.STORY_NODE];
					_.each(nodes, function(storyNode, id){
						var nodeEle = node_createNodeElement(id, that);
						loc_nodeElements[id] = nodeEle; 
						loc_graph.addCells(nodeEle.getElement());
					});

					var connections = story[node_COMMONATRIBUTECONSTANT.STORY_CONNECTION];
					_.each(connections, function(storyConnection, id){
						var connectionLink = node_createConnectionLink(id, that);
						loc_connectionLinks[id] = connectionLink; 
						loc_graph.addCells(connectionLink.getLink());
					});
				})); 
			return out;
		},

		getStory : function(){  return loc_story;  },
		getNodeElementById : function(storyNodeId){  return loc_nodeElements[storyNodeId];   },
		getConnectionLinkById : function(storyConnectionId){  return loc_connectionLinks[storyConnectionId];   },
		
		registerEventListener : function(listener, handler, thisContext){  return loc_eventSource.registerListener(undefined, listener, handler, thisContext); },
		unregisterEventListener : function(listener){	return loc_eventSource.unregister(listener); },

	};
	
	node_makeObjectWithLifecycle(loc_out, lifecycleCallback);
	node_makeObjectWithName(loc_out, loc_mduleName);
	
	return loc_out;
};	
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.service.ServiceInfo", function(){node_ServiceInfo = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSequence", function(){	node_createServiceRequestInfoSequence = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoSimple", function(){	node_createServiceRequestInfoSimple = this.getData();	});
nosliw.registerSetNodeDataEvent("request.request.createServiceRequestInfoCommon", function(){	node_createServiceRequestInfoCommon = this.getData();	});
nosliw.registerSetNodeDataEvent("common.objectwithname.makeObjectWithName", function(){node_makeObjectWithName = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.makeObjectWithLifecycle", function(){node_makeObjectWithLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.getLifecycleInterface", function(){node_getLifecycleInterface = this.getData();});
nosliw.registerSetNodeDataEvent("common.event.createEventObject", function(){node_createEventObject = this.getData();});
nosliw.registerSetNodeDataEvent("application.story.module.overview.createNodeElement", function(){node_createNodeElement = this.getData();});
nosliw.registerSetNodeDataEvent("application.story.module.overview.createConnectionLink", function(){node_createConnectionLink = this.getData();});

//Register Node by Name
packageObj.createChildNode("createModuleOverview", node_createModuleOverview); 
})(packageObj);
