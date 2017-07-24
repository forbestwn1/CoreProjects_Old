//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_makeObjectWithName;
	var node_makeObjectWithLifecycle;
	var node_createIdService;
	var node_createLoggingService;
	var node_createResourceManager;
	var node_createResourceService;
	var node_createExpressionService;
	var node_CONSTANT;
	var node_COMMONCONSTANT;
	var node_runtimeGateway;
//*******************************************   Start Node Definition  ************************************** 	

	var loc_mduleName = "runtime";
	
/**
 * 
 */
var node_createRuntime = function(name){
	
	var loc_name = name;
	
	var loc_idService;
	
	var loc_resourceService;
	
	var loc_resourceManager;
	
	var loc_expressionService;
	
	var loc_out = {
		
		start : function(){
			
		},
		
		getIdService(){
			return loc_idService;
		},
		
		getResourceService(){
			return loc_resourceService;
		},
		
		getExpressionService(){
			return loc_expressionService;
		},
			
		getName(){
			return loc_name;
		},
		
		getGateway(){
			return node_runtimeGateway;
		}
		
		
	};
	
	var lifecycleCallback = {};
	lifecycleCallback[node_CONSTANT.LIFECYCLE_RESOURCE_EVENT_INIT] = function(){
		loc_idService = node_createIdService();
		loc_resourceManager = node_createResourceManager();
		loc_resourceService = node_createResourceService(loc_resourceManager);
		loc_expressionService = node_createExpressionService();
		
		//set sortcut for object
		 nosliw.runtime = loc_out;
		 nosliw.generateId = loc_out.getIdService().generateId;
		 
		 //create node for runtime object
		 nosliw.createNode("runtime", loc_out);
		 
		 nosliw.triggerNodeEvent("runtime", "active");
		
		return true;
	};
	
	node_makeObjectWithLifecycle(loc_out, lifecycleCallback);
	node_makeObjectWithName(loc_out, loc_mduleName);
	
	return loc_out;
};


//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithname.makeObjectWithName", function(){node_makeObjectWithName = this.getData();});
nosliw.registerSetNodeDataEvent("common.lifecycle.makeObjectWithLifecycle", function(){node_makeObjectWithLifecycle = this.getData();});
nosliw.registerSetNodeDataEvent("service.idservice.createIdService", function(){node_createIdService = this.getData();});
nosliw.registerSetNodeDataEvent("resource.createResourceManager", function(){node_createResourceManager = this.getData();});
nosliw.registerSetNodeDataEvent("expression.service.createExpressionService", function(){node_createExpressionService = this.getData();});
nosliw.registerSetNodeDataEvent("resource.createResourceService", function(){node_createResourceService = this.getData();});
nosliw.registerSetNodeDataEvent("runtime.gateway", function(){node_runtimeGateway = this.getData();});

//Register Node by Name
packageObj.createChildNode("createRuntime", node_createRuntime); 

})(packageObj);
