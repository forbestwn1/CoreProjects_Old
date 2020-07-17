var libsInfo = nosliwApplication.info.application.libs;
for(var i in nosliwApplication.info.application.modulesConfigure){
	var moduleConfigure = nosliwApplication.info.application.modulesConfigure[i];
	var moduleName = moduleConfigure.name;
	var moduleFoler = nosliwApplication.utility.getModuleFolder(moduleName);

	//lib for application
	libsInfo.push({
		basePath : nosliwApplication.info.application.appFolder,
		libs : [
			"entity.js",
			"utilitystory.js",
			"utilitystoryui.js",
			"utilitystorychange.js",
		]
	});
	
	if(moduleName=='page'){
		libsInfo.push({
			basePath : moduleFoler,
			libs : [
				"0_package.js",
				"module.js",
				"uinode.js",
			]
		});
	}
	else if(moduleName=='overview'){
		libsInfo.push({
			basePath : moduleFoler,
			libs : [
				"0_package.js",
				"module.js",
				"node.js",
				"connection.js",
			]
		});
	}
	else if(moduleName=='ui'){
		libsInfo.push({
			basePath : moduleFoler,
			libs : [
				"0_package.js",
				"module.js",
			]
		});
	}
	else if(moduleName=='builder'){
		libsInfo.push({
			basePath : moduleFoler,
			libs : [
				"0_package.js",
				"module.js",
			]
		});
	}
}

