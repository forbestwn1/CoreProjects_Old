//run seperately
//		which data type (name + version) belong to
//      dependency
//		each operation (operation name, script, dependency)

var dataTypeDefition = nosliw.getDataTypeDefinition("test.string");

//define what this data type globlely requires (operation, datatype, library)
dataTypeDefition.requires = {
};

//define what operations in this page requires (operation, datatype, library)
dataTypeDefition.localRequires = {
	"operation" : { 
//		op: "base.text;1.0.0;operation"
	},
	"datatype" : {
	},
	"library" : {
	},
	"helper" : {
		globalHelper : {
			a : 123,
			b : "bb",
			c : "cc",
			d : function(parm1){
				return parm1 = parm1 + "fff";
			},
			e : [1, 2, 3, 4],
			f : true,
			h : [true, false, true]
		}
	}
};


//define operation
dataTypeDefition.operations['subString'] = {
		//defined operation
		//in operation can access all the required resources by name through context
		operation : function(parms, context){
//			var to = context.operation("test.integer;1.0.0", "add", [{name:"parm1", value:parms.getParm("from")}, {name:"parm2", value:parms.getParm("to")}]).value;
			
			var from = parms.getParm("from").value;
			var to = parms.getParm("to").value;
			var outStr = this.value.substring(from, to);
			outStr = context.getResourceDataByName("globalHelper").d(outStr);
			return {
				dataTypeId : "test.string;1.0.0",
				value : outStr,
			}
		},

		requires:{
			"operation" : { 
				op1: "test.integer;1.0.0;add",
			},
//			"datatype1" : {
//				integer : "test.integer"
//			},
			"helper" : {
//				operation : "operationHelper"
			}
		},
};

//define operation
dataTypeDefition.operations['length'] = {
		//defined operation
		//in operation can access all the required resources by name through context
		operation : function(parms, context){
			
			var baseData = parms[base];
//			var out = context.buildFromLiterate("test.integer", baseData.data.length);
			return out;
		}, 

//define required resources for operation
	requires:{
		"operation" : { 
			op1: "test.integer;new",
		},
//		"datatype1" : {
//			integer : "test.integer"
//		},
		"helper" : {
//			operation : "operationHelper"
		}
	},
	
	info : {
	}
	
};

nosliw.addDataTypeDefinition(dataTypeDefition);
