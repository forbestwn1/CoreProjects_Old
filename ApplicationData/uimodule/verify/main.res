{
	"name": "verify",
	"description": "",
	"info": {
		"setting": "application"
	},
	"valuestructure": 
	 {
		"group": {
			"public": {
				"flat": {
					"aaa_module":{
						"definition" : {
							"criteria": "test.string;1.0.0",
						},
						"defaultValue": {
							"dataTypeId": "test.string;1.0.0",
							"value": "This is my world!"
						}
					},

					"bbb_module":{
						"definition" : {
							"criteria": "test.string;1.0.0",
						},
						"defaultValue": {
							"dataTypeId": "test.string;1.0.0",
							"value": "This is my world!"
						}
					},

					"ccc_module":{
						"definition" : {
							"criteria": "test.string;1.0.0",
						},
					},

				}
			},
			"protect": {
				"flat": {
					"eventData_module" : {
						"definition": {
							"criteria": "test.string;1.0.0",
						}
					}
				}
			}
		}
	},
	"ui": [
		{
			"name": "firstPage",
			"type": "list",
			"page": "firstPage",
			"status" : "disabled111",
			"in": [
				{
					"name" : "default",
					"mapping": {
						"aaa": {
							"definition": {
								"path": "aaa_module"
							},
							"info": {
								"relativeConnection": "physical"
							}
						}
					}
				}
			],
			"out": [
			],
			"eventHandler": [
				{
					"eventName" : "event1",
					"dataMapping" : {
						"eventData_module" : "EVENT.data"
					},
					"handler" : [
						{
							"task" : "transitToSecondPage",
						},
						{
							"task" : "initSecondPageData",
						},
					]
				}
			],
		},
		{
			"name": "secondPage",
			"type": "info",
			"page": "secondPage",
			"status" : "disabled111",
			"inputMapping": [
				{
					"name" : "default",
					"mapping": {
						"bbb": {
							"definition": {
								"path": "bbb_module"
							},
						}
					}
				}
			]				
		}
	],
	
	"task" : [
		{
			"name" : "nosliw_INIT_ACTIVE",
			"taskType": "sequence",
			"step": [
				{
					"name" : "transitToFirstPage",
					"taskType" : "activity",
					"activityType" : "transitPage",
					"configuration" : {
						"page" : "firstPage"
					}
				},
			]
		},
		{
			"name" : "event1Handler",
			"taskType": "sequence",
			"step": [
				{
					"name" : "transitToSecondPage",
					"taskType" : "activity",
					"activityType" : "transitPage",
					"configuration" : {
						"page" : "secondPage"
					}
				},
				{
					"name" : "initSecondPageData",
					"taskType" : "activity",
					"activityType" : "exeDA",
					"configuration" : {
						"path" : [
							{
								"entityType" : "dataAssociation",
								"path" : "default"
							}
						]
					}
				},
			]
		},
		{
			"name" : "submitService",
			"taskType": "activity",
			"activityType": "Service_request",
			"configuration" : {
				"serviceUse" : "simpleServiceWithoutInterface"
			}
		},
	
	],
	
	"command" : [
	],
	
	"services":
	[
		{
			"name" : "simpleServiceWithoutInterface",
			"status" : "enable",
			"provider" : "simpleServiceWithInterfaceProvider",
			"info" : {
				"enhanceContext" : "true"
			},
			"dataAssociation" :{
				"in" : {
					"mapping" : {
						"parm1" : {
							"definition" : {
								"path" : "ccc_module"
							}
						},
					}
				},
				"out" : {
					"success" : {
						"mapping" : {
							"aaa" : {
								"definition" : {
									"path" : "simpleOutput2"
								}
							}
						}
					}
				}
			}
		}
	],
	"attachment": {
		"uiResource" : [
			{
				"name": "firstPage",
				"referenceId": {
					"structure" : "local",
					"id" : "firstpage"
				},
			},
			{
				"name": "secondPage",
				"referenceId": {
					"structure" : "local",
					"id" : "secondpage"
				},
			},
		],
		"service" : [
			{
				"name": "simpleServiceWithInterfaceProvider",
				"referenceId" : "simpleoutput_internalinterface"
			},	
		],
	}
}
