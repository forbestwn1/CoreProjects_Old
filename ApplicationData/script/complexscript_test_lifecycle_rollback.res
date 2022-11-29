function(parms, configure){

	var node_createServiceRequestInfoSimple = nosliw.getNodeData("request.request.createServiceRequestInfoSimple");
	var node_createErrorData = nosliw.getNodeData("error.entity.createErrorData");

	var loc_selectionView;
	
	var loc_parms = parms;
	var loc_configure = configure;

	var loc_OPTION_NONE = "None";
	var loc_OPTION_ERROR = "Error";
	var loc_OPTION_EXCEPTION = "Exception";

	var loc_getUpdateRuntimeContextRequest = function(runtimeContext){
		var rootViewWrapper = $('<div/>');
		$(runtimeContext.view).append(rootViewWrapper);

		loc_selectionView = $('<select/>');
		rootViewWrapper.append(loc_selectionView);
		
		loc_selectionView.append($('<option>'+loc_OPTION_NONE+'</option>'));
		loc_selectionView.append($('<option>'+loc_OPTION_ERROR+'</option>'));
		loc_selectionView.append($('<option>'+loc_OPTION_EXCEPTION+'</option>'));

	};

	var loc_out = {
		
		getUpdateRuntimeContextRequest : function(runtimeContext, handlers, request){		
			return loc_getUpdateRuntimeContextRequest(runtimeContext);	
		},
		
		//lifecycle handler
		getLifeCycleRequest : function(transitName, handlers, request){
			if(!transitName.startsWith("_")){
				var value = loc_selectionView.val();
				if(value==loc_OPTION_ERROR){
					return node_createErrorData("code", "message", "data");
				}
				else if(value==loc_OPTION_EXCEPTION){
					var k = aaa.bbb.ccc;
				}
			}
		},
		
	};
	return loc_out;
}
