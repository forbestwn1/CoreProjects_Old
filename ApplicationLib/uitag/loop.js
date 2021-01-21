{
	name : "loop",
	type : "data",
	base : "arrayData",
	description : "",
	attributes : [
		{
			name : "data",
		},
		{
			name : "element",
			defaultValue : "element", 
		},
		{
			name : "index",
			defaultValue : "index", 
		}	
	],
	context: {
		group : {
			private : {
				element : {
					"internal_data": {
						definition : {
							path : "<%=&(nosliwattribute_data)&%>",
							definition : {
								criteria : "test.array;1.0.0"
							}
						},
					}
				},
			},
			protected : {
				element : {
					"<%=&(nosliwattribute_element)&%>" : {
						definition : {
							path : "<%=&(nosliwattribute_data)&%>.element",
						},
						info:{
							instantiate : "manual"
						}
					},
					"<%=&(nosliwattribute_index)&%>" : {
						definition : {
							criteria : "test.integer;1.0.0",
						},
						info:{
							instantiate : "manual"
						}
					}		
				},
			},
		},
		info : {
			inherit : "true",
		}
	},
	event : [
		
	],
	script : function(base){

		var node_createServiceRequestInfoSequence = nosliw.getNodeData("request.request.createServiceRequestInfoSequence");
		var node_createServiceRequestInfoSimple = nosliw.getNodeData("request.request.createServiceRequestInfoSimple");

		var loc_base = base;
		var loc_childViews = [];

		var loc_out = 
		{
//			initViews : function(handlers, requestInfo){
//				loc_view = $('<div id="map" style="height:600px;width:100%;"></div>');	
//			},
			
			createContextForDemo : function(id, parentContext, request) {
				var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
				var node_createData = nosliw.getNodeData("uidata.data.entity.createData");
				var node_createContextElementInfo = nosliw.getNodeData("uidata.context.createContextElementInfo");
				var node_createContext = nosliw.getNodeData("uidata.context.createContext");
				var data = node_createData([1, 2], node_CONSTANT.WRAPPER_TYPE_OBJECT);
				var elementInfosArray = [node_createContextElementInfo("internal_data", data)];
				return node_createContext(id, elementInfosArray, request);
			},
			
			getCreateElementViewRequest : function(id, index, variables, handlers, requestInfo){
				var extendeContextInfo = [];
				_.each(variables, function(variable, name){
					extendeContextInfo.push(loc_base.getEnv().createContextElementInfo(name, variable));
				});
				var eleContext = loc_base.getEnv().createExtendedContext(extendeContextInfo, requestInfo);

				var out = node_createServiceRequestInfoSequence(undefined, handlers, requestInfo);
				out.addRequest(loc_base.getEnv().getCreateUIViewWithIdRequest(id, eleContext, {
					success : function(requestInfo, resourceView){
						if(index==0)	resourceView.insertAfter(loc_base.getEnv().getStartElement());
						else	resourceView.insertAfter(loc_childViews[index-1].getViews());
						loc_childViews.splice(index, 0, resourceView);
						return resourceView;
					}
				}));
				return out;
			},

			deleteElement : function(index, requestInfo){
				var view = loc_childViews[index];
				view.detachViews();
				view.destroy(requestInfo);
				loc_childViews.splice(index, 1);
			}, 

			destroy : function(request){
			},
			
			getDataForDemo : function(){
				return {
					dataTypeId:"test.array;1.0.0",
					value:[
						{
							value:"Hello World1", 
							dataTypeId:"test.string;1.0.0"
						},
						{
							value:"Hello World2", 
							dataTypeId:"test.string;1.0.0"
						},
					], 
				};
			},
		};
		return loc_out;
	}
}
