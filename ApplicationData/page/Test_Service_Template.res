<!DOCTYPE html>
<html>
<body>
	<br>
	<br><a href='' nosliw-event="click:refreshService:">Refresh</a><br>
	
	serviceParm1:<nosliw-textinput data="service1_parm_serviceParm1"/>  
	<br>
	<br>
	outputInService:<nosliw-textinput data="service1_result_success_outputInService"/>  
	<br>
	<br>

</body>

	<scripts>
	{
		refreshService : function(info, env){

			event.preventDefault();

			var node_CONSTANT = nosliw.getNodeData("constant.CONSTANT");
			var node_requestServiceProcessor = nosliw.getNodeData("request.requestServiceProcessor");
			var node_createBatchUIDataOperationRequest = nosliw.getNodeData("uidata.uidataoperation.createBatchUIDataOperationRequest");
			var node_UIDataOperation = nosliw.getNodeData("uidata.uidataoperation.UIDataOperation");
			
			var requestInfo = env.getServiceRequest("service1", {
				success : function(request){
				}
			});
			node_requestServiceProcessor.processRequest(requestInfo, false);
		},

	}
	</scripts>

	
		<!-- This part can be used to define context (variable)
				it describle data type criteria for each context element and its default value
		-->
	<contexts>
	{
		"group" : {
			"public" : {
				"element" : {
					"service1_parm_serviceParm1" : {
						"definition" : {
							"criteria" : "test.string;1.0.0"
						}
					},
					"service1_result_success_outputInService" : {
						"definition" : {
							"criteria" : "test.string;1.0.0"
						}
					},
				}
			}
		}
	}
	</contexts>
	
	<services>
	[
		{
			"name" : "service1",
			"provider" : "service1",
			"serviceMapping" :{
				"inputMapping" : {
					"element" : {
						"serviceParm1" : {
							"definition" : {
								"path" : "service1_parm_serviceParm1"
							}
						}
					}
				},
				"outputMapping" : {
					"success" : {
						"element" : {
							"service1_result_success_outputInService" : {
								"definition" : {
									"path" : "outputInService"
								}
							}
						}
					}
				}
			}
		}
	]
	</services>

	<attachment>
	{
		"service" : [
			{
				"name": "service1",
				"referenceId" : "TestTemplateService"
			}	
		]
	}
	</attachment>

	<events>
	[
	]
	</events>
	
</html>
