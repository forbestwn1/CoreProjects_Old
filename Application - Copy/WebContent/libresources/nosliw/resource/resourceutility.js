//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_COMMONTRIBUTECONSTANT;
	var node_COMMONCONSTANT;
	var node_namingConvensionUtility;
//*******************************************   Start Node Definition  ************************************** 	

var node_resourceUtility = 
{
		buildResourceTree : function(tree, resource){
			var resourceId = resource.resourceInfo[node_COMMONTRIBUTECONSTANT.RESOURCEINFO_ID];
			var type = resourceId[node_COMMONTRIBUTECONSTANT.RESOURCEID_TYPE];
			var id = resourceId[node_COMMONTRIBUTECONSTANT.RESOURCEID_ID];
			var typeResources = tree[type];
			if(typeResources==undefined){
				typeResources = {};
				tree[type] = typeResources;
			}
			typeResources[id] = resource; 
		},

		getResourceFromTree : function(tree, resourceId){
			var type = resourceId[node_COMMONTRIBUTECONSTANT.RESOURCEID_TYPE];
			var id = resourceId[node_COMMONTRIBUTECONSTANT.RESOURCEID_ID];
			var typeResources = tree[type];
			if(typeResources==undefined)  return undefined;
			return typeResources[id];
		},
		
		createOperationResourceId : function(dataTypeId, operation){
			var out = {};
			out[node_COMMONTRIBUTECONSTANT.RESOURCEID_ID] = node_namingConvensionUtility.cascadeLevel1(dataTypeId, operation); 
			out[node_COMMONTRIBUTECONSTANT.RESOURCEID_TYPE] = node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_OPERATION; 
			return out;
		},

		createConverterToResourceId : function(dataTypeId){
			var out = {};
			out[node_COMMONTRIBUTECONSTANT.RESOURCEID_ID] = node_namingConvensionUtility.cascadeLevel1(dataTypeId, node_COMMONCONSTANT.DATAOPERATION_TYPE_CONVERTTO); 
			out[node_COMMONTRIBUTECONSTANT.RESOURCEID_TYPE] = node_COMMONCONSTANT.RUNTIME_RESOURCE_TYPE_CONVERTER; 
			return out;
		},
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createNode("resourceUtility", node_resourceUtility); 

	var module = {
		start : function(packageObj){
			node_COMMONTRIBUTECONSTANT = packageObj.getNodeData("constant.COMMONTRIBUTECONSTANT");
			node_COMMONCONSTANT = packageObj.getNodeData("constant.COMMONCONSTANT");
			node_namingConvensionUtility = packageObj.getNodeData("common.namingconvension.namingConvensionUtility"); 
		}
	};
	nosliw.registerModule(module, packageObj);

})(packageObj);
