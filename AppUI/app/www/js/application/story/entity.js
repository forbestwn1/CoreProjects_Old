//get/create package
var packageObj = library.getChildPackage("entity");    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_storyUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_createUINode = function(nodeId, story){
	
	var loc_story = story;
	var loc_nodeId = nodeId;
	var loc_children = [];
	var loc_storyNode;
	
	var loc_getStoryNode = function(storyNodeId){
		if(loc_storyNode==undefined){
			loc_storyNode = node_storyUtility.getNodeById(story, loc_nodeId);  
		}
		return loc_storyNode;
	};
	
	var loc_out = {
		
		getTagId : function(){
			var storyNode = loc_getStoryNode();
			var nodeEntity = storyNode[node_COMMONATRIBUTECONSTANT.STORYELEMENT_ENTITY];
			return nodeEntity.tag;
		},
			
		addChild : function(uiNode, index){
			loc_children[index] = uiNode;
		},
		
		getChildren : function(){   return loc_children;    },
		
		getBody : function(){
			return loc_out;
		}
	
	};
	return loc_out;
};
	
//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("application.instance.story.utility", function(){node_storyUtility = this.getData();});

//Register Node by Name
packageObj.createChildNode("createUINode", node_createUINode); 

})(packageObj);