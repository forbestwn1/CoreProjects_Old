{
	"name": "test1",
	"description": "test1",
	"context": {
		"element": {
			business : {
				definition: {
					child : {
						a : {
							child : {
								aa : {criteria:"test.string;1.0.0"},
							}
						}
					}
				},
				defaultValue: {
					a : {
						aa : {
							dataTypeId: "test.string;1.0.0",
							value: "This is my world!"
						}
					}
				}
			},
			mybusiness : {
				definition: {
					child : {
						a : {
							child : {
								aa : {criteria:"test.string;1.0.0"},
							}
						}
					}
				},
				defaultValue: {
					a : {
						aa : {
							dataTypeId: "test.string;1.0.0",
							value: "This is my world!"
						}
					}
				}
			},
			"baseVar": {
				"definition":{
					criteria: "test.string",
				},
				"defaultValue": {
					"dataTypeId": "test.string",
					"value": "This is my world!"
				}
			},
			"fromVar" : {
				"definition": {
					criteria : "test.integer",
				},
				"defaultValue": {
					"dataTypeId": "test.integer;1.0.0",
					"value": 3
				}
			},
			"toVar" : {
				"definition": {
					criteria: "test.integer",
				},
				"defaultValue": {
					"dataTypeId": "test.integer;1.0.0",
					"value": 7
				}
			},
			"fromConstant" : {
				definition: {
					value : {
						"dataTypeId": "test.integer;1.0.0",
						"value": 3
					}
				}
			},
			"constantValueString" : {
				definition: {
					value : "How are you",
				}
			},
			"constantValueObject" : {
				definition: {
					value : {
						child : "How are you"
					}
				}
			},
			
		}
	},
	"attachment": {
		"data" : [
			{
				"name": "constantValueInteger",
				"entity": 123
			},
			{
				"name": "constantValueString",
				"entity": "How are you"
			},
			{
				"name": "fromConstant",
				"entity": {
					"dataTypeId": "test.integer;1.0.0",
					"value": 12345
				}
			},
			{
				"name": "constantValueObject",
				"entity": {
					value : {
						child : "How are you"
					}
				}
			},
		],
		"testData" : [
			{
				"name": "testData1",
				"entity": {
					"business.a.aa": {
						"dataTypeId": "test.string;1.0.0",
						"value": "This is my world!"
					},
					"mybusiness.a.aa": {
						"dataTypeId": "test.string;1.0.0",
						"value": "Hello This is my world!"
					},
					"baseVar": {
						"dataTypeId": "test.string;1.0.0",
						"value": "This is my world!"
					},
					"fromVar": {
						"dataTypeId": "test.integer;1.0.0",
						"value": 1
					},
					"toVar": {
						"dataTypeId": "test.integer;1.0.0",
						"value": 7
					}
				}
			},
		],
		"expression" : [
			{
				"name": "expression1",
				"description": "standard",
				"entity" : {
					"expression" : "!(test.string)!.subString(?(baseVar)?,from:?(fromVar)?,to:?(toVar)?)",
				}
			},		
		]
	},	
	"element": [
		{
			"id": "test1",
			"info": {
				"disable" : ""
			},
			"name": "test1",
			"type": "expression",
			"description": "without expression",
			"script" : "#|?(business)?.a.aa.subString(from:?(fromVar)?,to:?(toVar)?)|#.value=='s isfff'?'red':'blue'",
		},
		{
			"id": "test2",
			"info": {
				"disable" : ""
			},
			"name": "test2",
			"type": "expression",
			"description": "with referred expression",
			"script" : "#|!(test.string)!.subString(<(expression1)>,from:?(fromVar)?,to:?(toVar)?)|#.value=='s isfff'?'red':'blue'",
		},
		{
			"id": "test3",
			"info": {
				"disable" : ""
			},
			"name": "test3",
			"type": "expression",
			"description": "with constant data in expression",
			"script" : "#|!(test.string)!.subString(?(business)?.a.aa,from:&(fromConstant)&,to:?(toVar)?)|#.value=='s isfff'?'red':'blue'+&(fromConstant)&.value",
		},
		{
			"id": "test4",
			"name": "test4",
			"type": "expression",
			"description": "script only",
			"script" : "(&(fromConstant)&.value+&(constantValueInteger)&)+&(constantValueString)&",
		},
	]
}
