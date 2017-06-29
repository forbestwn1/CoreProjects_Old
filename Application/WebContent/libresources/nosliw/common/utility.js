//get/create package
var packageObj = library.getChildPackage("utility");    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

var node_basicUtility = 
{
		/*
		 * create an value with meaning of empty
		 */
		createEmptyValue : function(){return "alkfjalsdkfjsafjoweiurwerwelkjdlsjdf";},
		
		isEmptyValue : function(value){ return value== "alkfjalsdkfjsafjoweiurwerwelkjdlsjdf";},
		
		emptyStringIfUndefined : function(value){ 
			if(value==undefined)  return "";
			return value;
		},
		
		/*
		 * merge two object and create a new one
		 * specificOne will override the defaultone object
		 */
		mergeObjects : function(defaultOne, specificOne){
			
			var out = {};
			_.each(defaultOne, function(attr, name, list){
				if(specificOne===undefined || specificOne[name]===undefined){
					out[name] = attr;
				}
				else{
					out[name] = specificOne[name];
				}
			});
			return out;
		},
		
		cloneObject : function(object){
			var newObject = jQuery.extend(true, {}, object);	
			return newObject;
		},
		
		isStringEmpty : function(str){
			if(str==undefined || str+''=='')  return true;
			else false;
		},

		htmlDecode : function(input){
			var e = document.createElement('div');
			e.innerHTML = input;
			return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
		},

		isAtomData : function(data){
			if(_.isString(data) || _.isNumber(data) || _.isBoolean(data))  return true;
		},
		
		capitalizeFirstLetter : function(string) {
		    return string.charAt(0).toUpperCase() + string.slice(1);
		},
		
		isEmptyObject :function (obj) {
			if(obj==undefined)  return true;
			for(var prop in obj) {
			    if (Object.prototype.hasOwnProperty.call(obj, prop)) {
			    	return false;
				}
			}
			return true;
		}
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createNode("basicUtility", node_basicUtility); 

	var module = {
		start : function(packageObj){
		}
	};
	nosliw.registerModule(module, packageObj);

})(packageObj);
