//get/create package
var packageObj = library.getChildPackage("entity");    

(function(packageObj){
	//get used node
	var node_COMMONATRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_buildServiceProvider;
//*******************************************   Start Node Definition  ************************************** 	

var node_createModuleUIRequest = function(moduleUI, externalContext, env, handler, request){
	
	//build context
	
	//generate uiView
	
	nosliw.runtime.getUIResourceService().getGenerateUIResourceViewRequest(moduleUI.page,
			
	
	
};
	
var node_createModuleUI = function(uiView, context, env){
	
	var uiView;
	
	var context;
	
	var loc_out = {
		//take command
		executeCommandRequest : function(commandName, parms, handlers, request){
			
		},
		
		//
		registerListener : function(listener){
			
		},
		
		getView : function(){
			return uiView;
		},
		
		updateContext : function(contextValue){
			
		}
			
			
	};
	
	return loc_out;
	
};
	
	

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("request.buildServiceProvider", function(){node_buildServiceProvider = this.getData();});

//Register Node by Name
packageObj.createChildNode("NormalActivityResult", node_NormalActivityResult); 
packageObj.createChildNode("NormalActivityOutput", node_NormalActivityOutput); 
packageObj.createChildNode("EndActivityOutput", node_EndActivityOutput); 
packageObj.createChildNode("ProcessResult", node_ProcessResult); 

})(packageObj);
