//get/create package
var packageObj = library.getChildPackage("interface");    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

var interfaceAttributeName = "____Interface";

var loc_getInterfaceObject = function(baseObject, createIfNotExist){
	var interfaceObj = baseObject[interfaceAttributeName];
	if(interfaceObj==undefined && createIfNotExist===true){
		interfaceObj = {};
		baseObject[interfaceAttributeName] = interfaceObj;

		//add "getInterfaceObject" method to baseObject
		baseObject.getInterfaceObject = function(name){
			if(name==undefined)   return interfaceObj;
			else   return interfaceObj[name];
		}
	}
	return interfaceObj;
};

var buildInterface = function(baseObject, name, newInterfaceObj){
	var interfaceObj = loc_getInterfaceObject(baseObject, true);
	
	//add "getBaseObject" method to interfaceObject
	newInterfaceObj.getBaseObject = function(){
		return baseObject;
	};
	
	interfaceObj[name] = newInterfaceObj;
	return baseObject;
};

var getInterface = function(baseObject, name){
	var interfaceObj = loc_getInterfaceObject(baseObject, false);
	return interfaceObj[name];
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createNode("buildInterface", buildInterface); 
packageObj.createNode("getInterface", getInterface); 

})(packageObj);
