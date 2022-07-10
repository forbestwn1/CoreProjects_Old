//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
	var node_CONSTANT;
	var node_COMMONCONSTANT;
	var node_COMMONATRIBUTECONSTANT;
	var node_makeObjectWithType;
	var nod_createVariableDomain;
	
//*******************************************   Start Node Definition  ************************************** 	

var node_createBundleCore = function(globalComplexEntitId, configure){

	var loc_globalComplexEntitId = globalComplexEntitId;
	
	var loc_runtimeEnv;

	var loc_parentView;
	
	var loc_bundleDef;
	
	var loc_mainComplexEntity;
	
	//variable domain for this bundle
	var loc_variableDomain;
	
	var loc_getInitBundleRequest = function(handlers, request){
		var out = node_createServiceRequestInfoSequence(new node_ServiceInfo("createBundleRuntime"), handlers, request);

		//load related resources
		var resourceId = globalComplexEntitId[node_COMMONATRIBUTECONSTANT.IDCOMPLEXENTITYINGLOBAL_ROOTRESOURCEID];
		out.addRequest(nosliw.runtime.getResourceService().getGetResourcesRequest(resourceId, {
			success : function(requestInfo, resourceTree){
				//get bundle definition
				loc_bundleDef = node_resourceUtility.getResourceFromTree(resourceTree, resourceId).resourceData;
				loc_variableDomain = nod_createVariableDomain(loc_bundleDef[node_COMMONATRIBUTECONSTANT.EXECUTABLEBUNDLECOMPLEXRESOURCE_EXECUTABLEENTITYDOMAIN][node_COMMONATRIBUTECONSTANT.DOMAINENTITYEXECUTABLERESOURCECOMPLEX_VALUESTRUCTUREDOMAIN]);
				
				return nosliw.runtime.getPackageService().getCreateComponentRuntimeRequest(globalComplexEntitId[node_COMMONATRIBUTECONSTANT.IDCOMPLEXENTITYINGLOBAL_ENTITYIDINDOMAIN], undefined, bundleCore, configure, runtimeContext, {
					success : function(request, mainComplexEntity){
						loc_mainComplexEntity = mainComplexEntity;
						return bundleRuntime;
					}
				});
 			}
		}));
		return out;
	};

	var loc_out = {

		getBundleDefinition : function(){		return loc_bundleDef;	},
		
		getVariableDomain : function(){		return loc_variableDomain;	},

		getUpdateRuntimeContextRequest : function(runtimeContext, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				loc_parentView = runtimeContext.view;
			}, handlers, request);
		},
			
		getUpdateRuntimeEnvRequest : function(runtimeEnv, handlers, request){
			return node_createServiceRequestInfoSimple(undefined, function(request){
				loc_runtimeEnv = runtimeEnv;
			}, handlers, request);
		},
		
		getLifeCycleRequest : function(transitName, handlers, request){
			var out = node_createServiceRequestInfoSequence(undefined, handlers, request);
			if(transitName==node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DESTROY){
			}
			else{
				if(transitName==node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_INIT){
					out.addRequest(loc_getInitBundleRequest());
				}
				else if(transitName==node_CONSTANT.LIFECYCLE_COMPONENT_TRANSIT_DEACTIVE){
				}
			}
			return out;
		},
	};
	
	loc_out = node_makeObjectWithType(loc_out, node_CONSTANT.TYPEDOBJECT_TYPE_BUNDLE);

	return loc_out;
};

//*******************************************   End Node Definition  ************************************** 	

//populate dependency node data
nosliw.registerSetNodeDataEvent("constant.CONSTANT", function(){node_CONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONCONSTANT", function(){node_COMMONCONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("constant.COMMONATRIBUTECONSTANT", function(){node_COMMONATRIBUTECONSTANT = this.getData();});
nosliw.registerSetNodeDataEvent("common.objectwithtype.makeObjectWithType", function(){node_makeObjectWithType = this.getData();});
nosliw.registerSetNodeDataEvent("package.createVariableDomain", function(){nod_createVariableDomain = this.getData();});

//Register Node by Name
packageObj.createChildNode("createBundleCore", node_createBundleCore); 

})(packageObj);
