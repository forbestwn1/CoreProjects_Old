{
	name : "include",
	description : "",
	attributes : [
		{
			name : "source"
		},
		{
			name : "context"
		},
		{
			name : "event"
		}
	],
	context: {
		group : {
			public : {
				element : {
				},
			},
			private : {
				element : {
				},
			},
		},
		info : {
			inherit : "false",
			escalate : "true"
		}
	},
	script : function(env){
		var loc_env = env;
		
		var loc_resourceView;
		
		var loc_out = 
		{
			findFunctionDown : function(name){
				return loc_resourceView.findFunctionDown(name);
			},	
			
			initViews : function(requestInfo){
				loc_resourceView = loc_env.createDefaultUIView(requestInfo);
				return loc_resourceView.getViews();
			},
			
			destroy : function(){	
				loc_resourceView.detachViews();
				loc_resourceView.destroy();
			},
		};
		return loc_out;
	}
}