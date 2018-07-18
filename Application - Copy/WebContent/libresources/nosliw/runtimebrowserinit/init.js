//
var libNames = [
	"nosliw.constant",
	"nosliw.logging",
	"nosliw.common",
	"nosliw.expression",
	"nosliw.request",
	"nosliw.id",
	"nosliw.resource",
	"nosliw.uidata",
	"nosliw.remoteservice",
	"nosliw.error",
	"nosliw.runtime",
	"nosliw.runtimebrowser",
	"nosliw.uiexpression",
	"nosliw.uiresource",
	"nosliw.miniapp",
	"nosliw.runtimebrowsertest",
];


var requestLoadLibraryResources = function(resourceIds, callBackFunction){
	var data = {
		command : "requestLoadLibraryResources",
		data : {
			"resourceIds" : resourceIds
		}
	};
	
	$.ajax({
		url : "loadlib",
		type : "POST",
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		data : JSON.stringify(data),
		async : true,
		success : function(serviceData, status){
			var result = serviceData.data.data;
			
			//temperary solution for map
//			result.push("https://maps.googleapis.com/maps/api/js?key=AIzaSyBCcQbzlVgvSWsXexpcAMXkjPgtfVbPiBE");
			
			var fileNumber = result.length;
			var count = 0;
			
			
			var loadScriptInOrder = function(){
				var url = result[count];
				
				var script = document.createElement('script');
				script.setAttribute('src', url);
				script.setAttribute('defer', "defer");
				script.setAttribute('type', 'text/javascript');

				script.onload = callBack;
				document.getElementsByTagName("head")[0].appendChild(script);
			};
			
			var callBack = function(){
				count++;
				if(count==fileNumber){
					callBackFunction.call();
				}
				else{
					loadScriptInOrder();
				}
			};
			
			loadScriptInOrder();
		},
		error: function(obj, textStatus, errorThrown){
		},
	});
};

//set runtime name first
nosliw.createNode("runtime.name", "browser");

var libResources = [];
for(var i in libNames){
	libResources.push({
		"id" : libNames[i],
		"type" : "jslibrary"
	});
}

requestLoadLibraryResources(libResources, function(){
	  var runtime = nosliw.getNodeData("runtime.createRuntime")(nosliw.runtimeName);
	  runtime.interfaceObjectLifecycle.init();

});