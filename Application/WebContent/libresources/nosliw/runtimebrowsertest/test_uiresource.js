	nosliw.registerNodeEvent("runtime", "active", function(eventName, nodeName){
		
		nosliw.runtime.getUIResourceService().executeCreateUIResourceViewRequest("Example1", 
				{
					success : function(requestInfo, uiResourceView){
						nosliw.logging.info(JSON.stringify(uiResourceView));
						
						uiResourceView.appendTo($('#testDiv'));
						
//						var views = uiResourceView.getViews();
//						$('#testDiv').append(views.children());

					}
				}
		);
	});