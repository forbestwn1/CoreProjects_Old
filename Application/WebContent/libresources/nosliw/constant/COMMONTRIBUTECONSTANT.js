//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

/**
 * 
 */
var COMMONTRIBUTECONSTANT=

{"SERVLETPARMS_COMMAND":"command",
"SERVLETPARMS_SERVICE":"service",
"SERVLETPARMS_CLIENTID":"clientId",
"SERVLETPARMS_PARMS":"parms",
"REQUEST_TYPE":"type",
"REQUEST_SERVICE":"service",
"REQUEST_MODE":"mode",
"REQUEST_CHILDREN":"children",
"SERVICE_COMMAND":"command",
"SERVICE_PARMS":"parms",
"REQUEST_GETUIRESOURCE_NAME":"name",
"REQUEST_GETENTITYDEFINITIONBYNAMES_NAMES":"names",
"REQUEST_GETDATATYPES_EXISTINGARRAY":"existingArray",
"REQUEST_GETDATATYPES_REQUESTARRAY":"requestArray",
"ENTITYNAME_ATTRIBUTENAME":"ENTITYNAME_ATTRIBUTENAME",
"SERVICEDATA_CODE":"code",
"SERVICEDATA_MESSAGE":"message",
"SERVICEDATA_DATA":"data",
"SERVICEDATA_METADATA":"metaData",
"UIRESOURCECONTEXTINFO_NAME":"name",
"UIRESOURCECONTEXTINFO_TYPE":"type",
"UIRESOURCECONTEXTINFO_CONFIGURE":"configure",
"CONTEXTVARIABLE_NAME":"name",
"CONTEXTVARIABLE_PATH":"path",
"DATABINDING_NAME":"name",
"DATABINDING_VARIABLE":"variable",
"UIRESOURCE_ID":"id",
"UIRESOURCE_TYPE":"type",
"UIRESOURCE_CONTEXTINFOS":"contextInfos",
"UIRESOURCE_EXPRESSIONCONTENTS":"expressionContents",
"UIRESOURCE_EXPRESSIONATTRIBUTES":"expressionAttributes",
"UIRESOURCE_EXPRESSIONTAGATTRIBUTES":"expressionTagAttributes",
"UIRESOURCE_ELEMENTEVENTS":"elementEvents",
"UIRESOURCE_TAGEVENTS":"tagEvents",
"UIRESOURCE_UITAGS":"uiTags",
"UIRESOURCE_ATTRIBUTES":"attributes",
"UIRESOURCE_HTML":"html",
"UIRESOURCE_SCRIPTFACTORYNAME":"scriptFactoryName",
"UIRESOURCE_TAGNAME":"tagName",
"UIRESOURCE_DATABINDINGS":"dataBindings",
"UIRESOURCE_UITAGLIBS":"uiTagLibs",
"UIRESOURCE_CONSTANT":"constant",
"ELEMENTEVENT_UIID":"uiId",
"ELEMENTEVENT_EVENT":"event",
"ELEMENTEVENT_FUNCTION":"function",
"ELEMENTEVENT_SELECTION":"selection",
"UIEXPRESSIONCONTENT_UIID":"uiId",
"UIEXPRESSIONCONTENT_UIEXPRESSIONELEMENTS":"uiExpressionElements",
"UIEXPRESSIONCONTENT_ATTRIBUTE":"attribute",
"UIRESOURCEEXPRESSION_EXPRESSIONID":"expressionId",
"UIRESOURCEEXPRESSION_FUNCTIONSCRIPT":"functionScript",
"UIRESOURCEEXPRESSION_EXPRESSIONUNITS":"expressionUnits",
"UIRESOURCEEXPRESSION_CONTEXTVARIABLES":"contextVariables",
"UIRESOURCEEXPRESSION_EXPRESSIONOBJECT":"expressionObject",
"OPERAND_TYPE":"type",
"OPERAND_SCRIPTRUNNALBE":"scriptRunnable",
"OPERAND_OUTDATATYPEINFO":"outDataTypeInfo",
"OPERAND_VARIABLE_VARNAME":"varName",
"OPERAND_CONSTANT_DATA":"data",
"OPERAND_DATAOPERATION_BASEDATA":"baseData",
"OPERAND_OPERATION_PARAMETERS":"parameters",
"OPERAND_OPERATION_OPERATION":"operation",
"OPERAND_OPERATION_BASEDATATYPEINFO":"baseDataTypeInfo",
"OPERAND_OPERATION_ATTRIBUTE":"attribute",
"OPERAND_PATH_PATH":"path",
"OPERAND_PATH_BASEDATA":"baseData",
"DATATYPEINFO_CATEGARY":"categary",
"DATATYPEINFO_TYPE":"type",
"DATATYPEINFO_KEY":"key",
"DATATYPEINFO_ENTITYGROUPS":"entityGroups",
"DATATYPEINFO_VERSION":"version",
"DATATYPEINFO_CHILD_CATEGARY":"childCategary",
"DATATYPEINFO_CHILD_TYPE":"childTYPE",
"DATATYPEINFO_CHILD_ENTITYGROUPS":"childEntityGroups",
"DATATYPE_DATATYPEINFO":"dataTypeInfo",
"DATATYPE_PARENT":"parent",
"DATATYPE_OPERATIONINFOS":"operationInfos",
"DATATYPE_NEWOPERATIONINFOS":"newOperationInfos",
"DATATYPE_OPERATIONSCRIPTS":"operationScripts",
"DATAOPERATIONINFO_NAME":"name",
"DATAOPERATIONINFO_DESCRIPTION":"description",
"DATAOPERATIONINFO_CONVERTPATH":"convertPath",
"DATAOPERATIONINFO_OUT":"out",
"DATAOPERATIONINFO_INS":"ins",
"DATAOPERATIONINFO_DEPENDENTDATATYPES":"dependentDataTypes",
"DATA_DATATYPEINFO":"dataTypeInfo",
"DATA_VALUE":"value",
"WRAPER_DATA":"data",
"WRAPER_DATATYPE":"dataType",
"WRAPER_INFO":"info",
"OPERATIONINFO_OPERATIONID":"operationId",
"OPERATIONINFO_OPERATION":"operation",
"OPERATIONINFO_AUTOCOMMIT":"isAutoCommit",
"OPERATIONINFO_VALIDATION":"isValidation",
"OPERATIONINFO_SCOPE":"scope",
"OPERATIONINFO_ROOTOPERATION":"rootOperation",
"OPERATIONINFO_SUBMITABLE":"submitable",
"OPERATIONINFO_EXTRA":"extra",
"OPERATIONINFO_ENTITYID":"entityId",
"OPERATIONINFO_ATTRIBUTEPATH":"attributePath",
"OPERATIONINFO_DATA":"data",
"OPERATIONINFO_TRANSACTIONID":"transactionId",
"OPERATIONINFO_REFERENCEPATH":"referencePath",
"OPERATIONINFO_WRAPER":"wraper",
"OPERATIONINFO_ENTITYDEFINITION":"entityDefinition",
"OPERATIONINFO_ATTRIBUTEDEFINITION":"attributeDefinition",
"OPERATIONINFO_ENTITYTYPE":"entityType",
"OPERATIONINFO_ELEMENTID":"elementId",
"OPERATIONINFO_QUERYNAME":"queryName",
"OPERATIONINFO_QUERYENTITYWRAPER":"queryEntityWraper",
"OPERATIONINFO_VALUE":"value",
"OPERATIONINFO_REFENTITYID":"refEntityID",
"OPERATIONINFO_PARMS":"parms",
"OPERATIONINFO_ENTITYOPERATIONS":"entityOperations",
"ENTITYATTRDEF_NAME":"name",
"ENTITYATTRDEF_FULLNAME":"fullName",
"ENTITYATTRDEF_CRITICALVALUE":"criticalValue",
"ENTITYATTRDEF_DESCRIPTION":"description",
"ENTITYATTRDEF_ISEMPTYONINIT":"isEmptyOnInit",
"ENTITYATTRDEF_DATATYPEDEFINFO":"dataTypeDefInfo",
"ENTITYATTRDEF_VALIDATION":"validation",
"ENTITYATTRDEF_RULES":"rules",
"ENTITYATTRDEF_OPTIONS":"options",
"ENTITYATTRDEF_EVENTS":"events",
"ENTITYATTRDEF_ISCRITICAL":"isCritical",
"ENTITYATTRDEF_DEFAULTVALUE":"defaultValue",
"ENTITYATTRDEF_ELEMENT":"element",
"ENTITYID_ID":"id",
"ENTITYID_ENTITYTYPE":"entityType",
"ENTITYID_KEY":"key",
"DATAWRAPER_CHILDDATATYPE":"childDataType",
"DATAWRAPER_ID":"id",
"DATAWRAPER_ENTITYID":"entityID",
"DATAWRAPER_ATTRPATH":"attrPath",
"DATAWRAPER_PARENTENTITYID":"parentEntityID",
"DATAWRAPER_ATTRCONFIGURE":"attrConfigure",
"ENTITYPATH_PATH":"path",
"ENTITYPATH_ENTITYID":"entityID",
"ENTITYPATH_EXPECTPATH":"expectPath",
"ENTITYPATHINFO_ENTITYPATH":"entityPath",
"ENTITYPATHINFO_PATHSEGMENTS":"pathSegments",
"ENTITYPATHINFO_DATA":"data",
"QUERYENTITYATTRIBUTEWRAPER_ENTITYPATH":"entityPath",
"QUERYDEFINITION_NAME":"name",
"QUERYDEFINITION_EXPRESSIONINFO":"expressionInfo",
"QUERYDEFINITION_PROJECTATTRIBUTES":"projectAttributes",
"QUERYPROJECTATTRIBUTE_ENTITYNAME":"entityName",
"QUERYPROJECTATTRIBUTE_ATTRIBUTE":"attribute",
"ENTITYMANAGER_PERSISTANT":"persistent",
"ENTITYMANAGER_TRANSACTIONS":"transactions",
"CONSTANTGROUP_TYPE":"type",
"CONSTANTGROUP_FILEPATH":"filepath",
"CONSTANTGROUP_CLASSNAME":"classname",
"CONSTANTGROUP_PACKAGENAME":"packagename",
"CONSTANTGROUP_DEFINITIONS":"definitions",
"STRINGABLEVALUE_STRUCTURE":"structure",
"STRINGABLEVALUE_TYPE":"type",
"STRINGABLEVALUE_SUBTYPE":"subType",
"STRINGABLEVALUE_STRINGVALUE":"stringValue",
"STRINGABLEVALUE_RESOLVED":"resolved",
"STRINGABLEVALUE_VALUE":"atomic_value",
"STRINGABLEVALUE_ELEMENTS":"elements",
"STRINGABLEVALUE_PROPERTIES":"properties",
"STRINGABLEVALUE_ID":"id",
"DATATYPECRITERIA_TYPE":"type",
"DATATYPECRITERIA_ELEMENTS":"elements",
"DATATYPECRITERIA_DATATYPEID":"dataTypeId",
"DATATYPECRITERIA_DATATYPEIDS":"dataTypeids",
"DATATYPECRITERIA_DATATYPEFROM":"dataTypeFrom",
"DATATYPECRITERIA_DATATYPETO":"dataTypeTo",
"EXPRESSION_NAME":"name",
"EXPRESSION_EXPRESSIONDEFINITION":"expressionDefinition",
"EXPRESSION_OPERAND":"operand",
"EXPRESSION_VARIABLEINFOS":"variableInfos",
"EXPRESSION_ERRORMSGS":"errorMsgs",
"EXPRESSION_CONVERTERS":"converters",
"EXPRESSION_REFERENCES":"references",
"EXPRESSIONDEFINITION_NAME":"name",
"EXPRESSIONDEFINITION_INFO":"info",
"EXPRESSIONDEFINITION_EXPRESSION":"expression",
"EXPRESSIONDEFINITION_CONSTANTS":"constants",
"EXPRESSIONDEFINITION_VARIABLECRITERIAS":"variableCriterias",
"EXPRESSIONDEFINITION_REFERENCES":"references",
"_TYPE":"type",
"_STATUS":"status",
"_CHILDREN":"children",
"_DATATYPEINFO":"dataTypeInfo",
"_CONVERTERS":"converters",
"_REFERENCE":"reference",
"_VARIABLESMAP":"variablesMap",
"VARIABLEINFO_CRITERIA":"criteria",
"VARIABLEINFO_STATUS":"status",
"DATA_DATATYPEID":"dataTypeId",
"DATATYPE_INFO":"info",
"DATATYPE_NAME":"name",
"DATATYPE_PARENTSINFO":"parentsInfo",
"DATATYPE_LINKEDVERSION":"linkedVersion",
"DATATYPEID_NAME":"name",
"DATATYPEID_VERSION":"version",
"DATATYPEID_FULLNAME":"fullName",
"DATATYPEOPERATION_OPERATIONINFO":"operationInfo",
"DATATYPEOPERATION_TARGETDATATYPE":"targetDataType",
"DATATYPEVERSION_MAJOR":"major",
"DATATYPEVERSION_MINOR":"minor",
"DATATYPEVERSION_REVISION":"revision",
"DATATYPEVERSION_NAME":"name",
"DATA_WAPPER_TYPE":"wapperType",
"DATAOPERATIONINFO_TYPE":"type",
"DATAOPERATIONINFO_PAMRS":"parms",
"DATAOPERATIONINFO_OUTPUT":"output",
"DATAOPERATIONINFO_INFO":"info",
"DATATYPEID_OPERATION":"operation",
"DATAOPERATIONOUTPUTINFO_DESCRIPTION":"description",
"DATAOPERATIONOUTPUTINFO_CRITERIA":"criteria",
"DATAOPERATIONPARMINFO_NAME":"name",
"DATAOPERATIONPARMINFO_ISBASE":"isBase",
"_PATH":"path",
"_TARGET":"target",
"_SOURCE":"source",
"RESOURCE_ID":"id",
"RESOURCE_RESOURCEDATA":"resourceData",
"RESOURCE_INFO":"info",
"_ALIAS":"alias",
"_ID":"id",
"RESOURCEID_ID":"id",
"RESOURCEID_TYPE":"type",
"_INFO":"info",
"_DEPENDENCY":"dependency",
"_NAME":"name",
"_VERSION":"version",
"_DATATYPENAME":"dataTypeName",
"_CONVERTERTYPE":"converterType",
"RESOURCEDATAJSLIBRARY_URIS":"uris",
"_OPERATIONID":"operationId",
"_OPERATIONNAME":"operationName",
"_VALUE":"value",
"DATATYPE_OPERATIONS":"operations",
"STRINGABLEVALUE_DESCRIPTION":"description",
"STRINGABLEVALUE_OPERATIONID":"operationId",
"_RELATIONSHIPS":"relationship",
"STRINGABLEVALUE_DATATYPNAME":"dataTypeName",
"STRINGABLEVALUE_DATATYPEID":"dataTypeId",
"STRINGABLEVALUE_SOURCEDATATYPE":"sourceDataType",
"STRINGABLEVALUE_TARGETDATATYPE":"targetDataType",
"STRINGABLEVALUE_PATH":"path",
"STRINGABLEVALUE_TARGETTYPE":"targetType",
"STRINGABLEVALUE_RESOURCEID":"resourceId",
"STRINGABLEVALUE_DEPENDENCY":"dependency"
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createNode("COMMONTRIBUTECONSTANT", COMMONTRIBUTECONSTANT); 

	var module = {
		start : function(packageObj){
		}
	};
	nosliw.registerModule(module, packageObj);

})(packageObj);
