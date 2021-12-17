//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

/**
 * 
 */
var COMMONATRIBUTECONSTANT=

{
  "REQUEST_GETUIRESOURCE_NAME": "name",
  "REQUEST_GETENTITYDEFINITIONBYNAMES_NAMES": "names",
  "REQUEST_GETDATATYPES_EXISTINGARRAY": "existingArray",
  "REQUEST_GETDATATYPES_REQUESTARRAY": "requestArray",
  "ENTITYNAME_ATTRIBUTENAME": "ENTITYNAME_ATTRIBUTENAME",
  "SERVICEDATA_CODE": "code",
  "SERVICEDATA_MESSAGE": "message",
  "SERVICEDATA_DATA": "data",
  "SERVICEDATA_METADATA": "metaData",
  "DATATYPEINFO_CATEGARY": "categary",
  "DATATYPEINFO_TYPE": "type",
  "DATATYPEINFO_KEY": "key",
  "DATATYPEINFO_ENTITYGROUPS": "entityGroups",
  "DATATYPEINFO_VERSION": "version",
  "DATATYPEINFO_CHILD_CATEGARY": "childCategary",
  "DATATYPEINFO_CHILD_TYPE": "childTYPE",
  "DATATYPEINFO_CHILD_ENTITYGROUPS": "childEntityGroups",
  "DATATYPE_DATATYPEINFO": "dataTypeInfo",
  "DATATYPE_PARENT": "parent",
  "DATATYPE_OPERATIONINFOS": "operationInfos",
  "DATATYPE_NEWOPERATIONINFOS": "newOperationInfos",
  "DATATYPE_OPERATIONSCRIPTS": "operationScripts",
  "DATAOPERATIONINFO_NAME": "name",
  "DATAOPERATIONINFO_DESCRIPTION": "description",
  "DATAOPERATIONINFO_CONVERTPATH": "convertPath",
  "DATAOPERATIONINFO_OUT": "out",
  "DATAOPERATIONINFO_INS": "ins",
  "DATAOPERATIONINFO_DEPENDENTDATATYPES": "dependentDataTypes",
  "DATA_DATATYPEINFO": "dataTypeInfo",
  "DATA_VALUE": "value",
  "WRAPER_DATA": "data",
  "WRAPER_DATATYPE": "dataType",
  "WRAPER_INFO": "info",
  "OPERATIONINFO_OPERATIONID": "operationId",
  "OPERATIONINFO_OPERATION": "operation",
  "OPERATIONINFO_AUTOCOMMIT": "isAutoCommit",
  "OPERATIONINFO_VALIDATION": "isValidation",
  "OPERATIONINFO_SCOPE": "scope",
  "OPERATIONINFO_ROOTOPERATION": "rootOperation",
  "OPERATIONINFO_SUBMITABLE": "submitable",
  "OPERATIONINFO_EXTRA": "extra",
  "OPERATIONINFO_ENTITYID": "entityId",
  "OPERATIONINFO_ATTRIBUTEPATH": "attributePath",
  "OPERATIONINFO_DATA": "data",
  "OPERATIONINFO_TRANSACTIONID": "transactionId",
  "OPERATIONINFO_REFERENCEPATH": "referencePath",
  "OPERATIONINFO_WRAPER": "wraper",
  "OPERATIONINFO_ENTITYDEFINITION": "entityDefinition",
  "OPERATIONINFO_ATTRIBUTEDEFINITION": "attributeDefinition",
  "OPERATIONINFO_ENTITYTYPE": "entityType",
  "OPERATIONINFO_ELEMENTID": "elementId",
  "OPERATIONINFO_QUERYNAME": "queryName",
  "OPERATIONINFO_QUERYENTITYWRAPER": "queryEntityWraper",
  "OPERATIONINFO_VALUE": "value",
  "OPERATIONINFO_REFENTITYID": "refEntityID",
  "OPERATIONINFO_PARMS": "parms",
  "OPERATIONINFO_ENTITYOPERATIONS": "entityOperations",
  "ENTITYATTRDEF_NAME": "name",
  "ENTITYATTRDEF_FULLNAME": "fullName",
  "ENTITYATTRDEF_CRITICALVALUE": "criticalValue",
  "ENTITYATTRDEF_DESCRIPTION": "description",
  "ENTITYATTRDEF_ISEMPTYONINIT": "isEmptyOnInit",
  "ENTITYATTRDEF_DATATYPEDEFINFO": "dataTypeDefInfo",
  "ENTITYATTRDEF_VALIDATION": "validation",
  "ENTITYATTRDEF_RULES": "rules",
  "ENTITYATTRDEF_OPTIONS": "options",
  "ENTITYATTRDEF_EVENTS": "events",
  "ENTITYATTRDEF_ISCRITICAL": "isCritical",
  "ENTITYATTRDEF_DEFAULTVALUE": "defaultValue",
  "ENTITYATTRDEF_ELEMENT": "element",
  "ENTITYID_ID": "id",
  "ENTITYID_ENTITYTYPE": "entityType",
  "ENTITYID_KEY": "key",
  "DATAWRAPER_CHILDDATATYPE": "childDataType",
  "DATAWRAPER_ID": "id",
  "DATAWRAPER_ENTITYID": "entityID",
  "DATAWRAPER_ATTRPATH": "attrPath",
  "DATAWRAPER_PARENTENTITYID": "parentEntityID",
  "DATAWRAPER_ATTRCONFIGURE": "attrConfigure",
  "ENTITYPATH_PATH": "path",
  "ENTITYPATH_ENTITYID": "entityID",
  "ENTITYPATH_EXPECTPATH": "expectPath",
  "ENTITYPATHINFO_ENTITYPATH": "entityPath",
  "ENTITYPATHINFO_PATHSEGMENTS": "pathSegments",
  "ENTITYPATHINFO_DATA": "data",
  "QUERYENTITYATTRIBUTEWRAPER_ENTITYPATH": "entityPath",
  "QUERYDEFINITION_NAME": "name",
  "QUERYDEFINITION_EXPRESSIONINFO": "expressionInfo",
  "QUERYDEFINITION_PROJECTATTRIBUTES": "projectAttributes",
  "QUERYPROJECTATTRIBUTE_ENTITYNAME": "entityName",
  "QUERYPROJECTATTRIBUTE_ATTRIBUTE": "attribute",
  "ENTITYMANAGER_PERSISTANT": "persistent",
  "ENTITYMANAGER_TRANSACTIONS": "transactions",
  "CONSTANTGROUP_TYPE": "type",
  "CONSTANTGROUP_FILEPATH": "filepath",
  "CONSTANTGROUP_CLASSNAME": "classname",
  "CONSTANTGROUP_PACKAGENAME": "packagename",
  "CONSTANTGROUP_DEFINITIONS": "definitions",
  "DISPLAYRESOURCELEAF_VALUE": "value",
  "DISPLAYRESOURCENODE_CHILDREN": "children",
  "SERVICEDATA_SERVICEDATA_CODE": "code",
  "SERVICEDATA_SERVICEDATA_MESSAGE": "message",
  "SERVICEDATA_SERVICEDATA_DATA": "data",
  "SERVICEDATA_SERVICEDATA_METADATA": "metaData",
  "ENTITYINFO_ID": "id",
  "ENTITYINFO_NAME": "name",
  "ENTITYINFO_STATUS": "status",
  "ENTITYINFO_DISPLAYNAME": "displayName",
  "ENTITYINFO_DESCRIPTION": "description",
  "ENTITYINFO_INFO": "info",
  "COMPLEXPATH_ROOT": "root",
  "COMPLEXPATH_PATH": "path",
  "STRINGABLEVALUE_STRUCTURE": "structure",
  "STRINGABLEVALUE_TYPE": "type",
  "STRINGABLEVALUE_SUBTYPE": "subType",
  "STRINGABLEVALUE_STRINGVALUE": "stringValue",
  "STRINGABLEVALUE_RESOLVED": "resolved",
  "STRINGABLEVALUE_VALUE": "atomic_value",
  "STRINGABLEVALUE_ELEMENTS": "elements",
  "STRINGABLEVALUE_PROPERTIES": "properties",
  "STRINGABLEVALUE_ID": "id",
  "USERINFO_USER": "user",
  "USERINFO_ENTITIES": "entities",
  "DEFINITIONACTIVITY_ACTIVITYTYPE": "activityType",
  "DEFINITIONACTIVITY_COFIGURATION": "configuration",
  "DEFINITIONACTIVITY_INPUT": "input",
  "DEFINITIONACTIVITY_RESULT": "result",
  "DEFINITIONRESULTACTIVITY_OUTPUT": "output",
  "EXECUTABLEACTIVITY_TYPE": "type",
  "EXECUTABLEACTIVITY_INPUTMAPPING": "inputMapping",
  "EXECUTABLEACTIVITY_RESULT": "result",
  "EXECUTABLERESULTACTIVITY_DATAASSOCIATION": "dataAssociation",
  "PLUGINACTIVITY_TYPE": "type",
  "PLUGINACTIVITY_SCRIPT": "script",
  "PLUGINACTIVITY_DEFINITION": "definition",
  "PLUGINACTIVITY_PROCESSOR": "processor",
  "DEFINITIONACTIVITY_PATH": "path",
  "EXECUTABLEACTIVITY_PATH": "path",
  "DEFINITIONACTIVITY_TASK": "task",
  "DEFINITIONACTIVITY_DATAMAPPING": "dataMapping",
  "EXECUTABLEACTIVITY_PROCESS": "process",
  "DEFINITIONACTIVITY_EVENTNAME": "eventName",
  "EXECUTABLEACTIVITY_EVENT": "event",
  "DEFINITIONACTIVITY_SCRIPT": "script",
  "DEFINITIONACTIVITY_VALUESTRUCTURE": "valueStructure",
  "EXECUTABLEACTIVITY_SCRIPTEXPRESSION": "scriptExpression",
  "EXECUTABLEACTIVITY_SCRIPTEXPRESSIONSCRIPT": "scriptExpressionScript",
  "DEFINITIONACTIVITY_CONTAINERNAME": "containerName",
  "DEFINITIONACTIVITY_ELEMENTNAME": "elementName",
  "DEFINITIONACTIVITY_INDEXNAME": "indexName",
  "DEFINITIONACTIVITY_STEP": "step",
  "DEFINITIONACTIVITY_PROCESS": "process",
  "EXECUTABLEACTIVITY_STEP": "step",
  "EXECUTABLEACTIVITY_CONTAINERDATAPATH": "containerDataPath",
  "EXECUTABLEACTIVITY_ELEMENTNAME": "elementName",
  "EXECUTABLEACTIVITY_INDEXNAME": "indexName",
  "DEFINITIONACTIVITY_SERVICEUSE": "serviceUse",
  "EXECUTABLEACTIVITY_SERVICE": "service",
  "EXECUTABLEACTIVITY_PROVIDER": "provider",
  "DEFINITIONACTIVITY_OUTPUT": "output",
  "EXECUTABLEACTIVITY_RESULTNAME": "resultName",
  "DEFINITIONACTIVITY_FLOW": "flow",
  "EXECUTABLEACTIVITY_FLOW": "flow",
  "CODETABLE_DATASET": "dataSet",
  "GATEWAYCODETABLE_COMMAND_GETCODETABLE": "getCodeTable",
  "GATEWAYCODETABLE_PARMS_GETCODETABLE_ID": "id",
  "ENTITYINFO_VALUE": "value",
  "_ELEMENT": "element",
  "_VALUESTRUCTURE": "valueStructure",
  "_VALUETYPE": "valueType",
  "_ADAPTOR": "adaptor",
  "ENTITYINFO_ENTITY": "entity",
  "ATTACHMENTREFERENCE_REFERENCEID": "referenceId",
  "ATTACHMENTREFERENCEEXTERNAL_ADAPTOR": "adaptor",
  "CONTAINERATTACHMENT_ELEMENT": "element",
  "_DATATYPE": "dataType",
  "_NAME": "name",
  "ENTITYINFO_TASK": "task",
  "EXECUTABLECOMMAND_TASK": "task",
  "EXECUTABLECOMMAND_DATAASSOCIATION": "dataAssociation",
  "EXECUTABLECOMMAND_COMMAND": "command",
  "_COMMAND": "command",
  "DEFINITIONHANDLEREVENT_EVENTNAME": "eventName",
  "DEFINITIONHANDLEREVENT_HANDLER": "handler",
  "DEFINITIONHANDLEREVENT_IN": "in",
  "EXECUTABLEEVENT_VALUESTRUCTURE": "valueStructure",
  "EXECUTABLEEVENT_DATAASSOCIATION": "dataAssociation",
  "EXECUTABLEHANDLEREVENT_EVENTNAME": "eventName",
  "EXECUTABLEHANDLEREVENT_IN": "in",
  "EXECUTABLEHANDLEREVENT_HANDLER": "handler",
  "_EVENT": "event",
  "_CATEGARY": "categary",
  "ENTITYINFO_NAMEMAPPING": "nameMapping",
  "ENTITYINFO_IN": "in",
  "ENTITYINFO_OUT": "out",
  "ENTITYINFO_EVENTHANDLER": "eventHandler",
  "ENTITYINFO_REFERENCE": "reference",
  "EXECUTABLECOMPONENT_VALUESTRUCTURE": "valueStructure",
  "EXECUTABLECOMPONENT_INITSCRIPT": "initScript",
  "EXECUTABLECOMPONENT_TASK": "task",
  "EXECUTABLECOMPONENT_EVENT": "event",
  "EXECUTABLECOMPONENT_COMMAND": "command",
  "EXECUTABLECOMPONENT_SERVICE": "service",
  "EXECUTABLEEMBEDEDCOMPONENT_COMPONENT": "component",
  "EXECUTABLEEMBEDEDCOMPONENT_IN": "in",
  "EXECUTABLEEMBEDEDCOMPONENT_OUT": "out",
  "EXECUTABLEEMBEDEDCOMPONENT_EVENTHANDLER": "eventHandler",
  "PATHTOCHILDELEMENT_ELEMENTTYPE": "elementType",
  "PATHTOCHILDELEMENT_ELEMENTID": "elementId",
  "PATHTOELEMENT_SEGMENT": "segment",
  "_ATTACHMENT": "attachment",
  "_EVENTHANDLER": "eventHandler",
  "_NAMEMAPPING": "nameMapping",
  "WITHSERVICE_SERVICE": "service",
  "_TASK": "task",
  "ENTITYINFO_SCHEDULE": "schedule",
  "ENTITYINFO_END": "end",
  "_TYPE": "type",
  "_SCHEDULETYPE": "scheduleType",
  "_START": "start",
  "_INTERVAL": "interval",
  "DATATYPECRITERIA_TYPE": "type",
  "DATATYPECRITERIA_CHILDREN": "children",
  "DATATYPECRITERIA_DATATYPEID": "dataTypeId",
  "DATATYPECRITERIA_ELEMENTDATATYPECRITERIA": "elementDataTypeCriteria",
  "DATATYPECRITERIA_IDSCRITERIA": "idsCriteria",
  "DATATYPECRITERIA_DATATYPEFROM": "dataTypeFrom",
  "DATATYPECRITERIA_DATATYPETO": "dataTypeTo",
  "_CRITERIA": "criteria",
  "_STATUS": "status",
  "DATA_DATATYPEID": "dataTypeId",
  "DATA_INFO": "info",
  "DATATYPE_INFO": "info",
  "DATATYPE_NAME": "name",
  "DATATYPE_PARENTSINFO": "parentsInfo",
  "DATATYPE_LINKEDVERSION": "linkedVersion",
  "DATATYPE_ISCOMPLEX": "isComplex",
  "DATATYPEID_NAME": "name",
  "DATATYPEID_VERSION": "version",
  "DATATYPEID_PARMS": "parms",
  "DATATYPEID_FULLNAME": "fullName",
  "DATATYPEOPERATION_OPERATIONINFO": "operationInfo",
  "DATATYPEOPERATION_TARGETDATATYPE": "targetDataType",
  "DATATYPEVERSION_MAJOR": "major",
  "DATATYPEVERSION_MINOR": "minor",
  "DATATYPEVERSION_REVISION": "revision",
  "DATATYPEVERSION_NAME": "name",
  "DATA_VALUEFORMAT": "valueFormat",
  "DATAOPERATIONINFO_TYPE": "type",
  "DATAOPERATIONINFO_PAMRS": "parms",
  "DATAOPERATIONINFO_OUTPUT": "output",
  "DATAOPERATIONINFO_INFO": "info",
  "DATATYPEID_OPERATION": "operation",
  "DATAOPERATIONOUTPUTINFO_DESCRIPTION": "description",
  "DATAOPERATIONOUTPUTINFO_CRITERIA": "criteria",
  "OPERATIONPARM_NAME": "name",
  "OPERATIONPARM_DATA": "data",
  "DATAOPERATIONPARMINFO_NAME": "name",
  "DATAOPERATIONPARMINFO_ISBASE": "isBase",
  "DATATYPERELATIONSHIP_PATH": "path",
  "DATATYPERELATIONSHIP_TARGET": "target",
  "DATATYPERELATIONSHIP_SOURCE": "source",
  "DATARULE_RULETYPE": "ruleType",
  "DATARULE_PATH": "path",
  "DATARULEENUMCODE_ENUMCODE": "enumCode",
  "DATARULEENUMDATA_DATASET": "dataSet",
  "DATARULEEXPRESSION_EXPRESSION": "expression",
  "DATARULEEXPRESSION_EXPRESSIONEXECUTE": "expressionExecute",
  "DATARULEEXPRESSION_VARIABLENAME": "data",
  "DATARULEJSSCRIPT_SCRIPT": "script",
  "VARIABLEDATAINFO_CRITERIA": "criteria",
  "VARIABLEDATAINFO_RULE": "rule",
  "VARIABLEDATAINFO_RULEMATCHERS": "ruleMatchers",
  "VARIABLEDATAINFO_RULECRITERIA": "ruleCriteria",
  "VARIABLEINFO_DATAINFO": "dataInfo",
  "VARIABLEINFO_REFERENCE": "reference",
  "VARIABLEINFO_DEFAULTVALUE": "defaultValue",
  "_OUT": "out",
  "_IN": "in",
  "EXECUTABLEDATAASSOCIATION_TYPE": "type",
  "EXECUTABLEDATAASSOCIATION_INPUT": "input",
  "EXECUTABLEDATAASSOCIATION_OUTPUT": "output",
  "EXECUTABLEGROUPDATAASSOCIATIONFORCOMPONENT_ELEMENT": "element",
  "EXECUTABLEGROUPDATAASSOCIATIONFORTASK_OUT": "out",
  "EXECUTABLEGROUPDATAASSOCIATIONFORTASK_IN": "in",
  "EXECUTABLEWRAPPERTASK_TASK": "task",
  "ENTITYINFO_TARGET": "target",
  "EXECUTABLEDATAASSOCIATION_ASSOCIATION": "association",
  "EXECUTABLEDATAASSOCIATION_INPUTDEPENDENCY": "inputDependency",
  "EXECUTABLEMAPPING_OUTPUTMATCHERS": "outputMatchers",
  "EXECUTABLEMAPPING_CONVERTFUNCTION": "convertFunction",
  "REFERENCEROOTINMAPPING_NAME": "name",
  "_MAPPING": "mapping",
  "EXECUTABLEDATAASSOCIATION_OUTPUT1": "output",
  "GATEWAYERRORLOGGER_COMMAND_LOGERRRO": "logError",
  "GATEWAYERRORLOGGER_PARMS_ERROR": "error",
  "_CONTEXT": "context",
  "_POLLTASK": "pollTask",
  "_POLLSCHEDULE": "pollSchedule",
  "_END": "end",
  "_INPUT": "input",
  "ENTITYINFO_EXPRESSION": "expression",
  "EXECUTABLEEXPRESSION_OPERAND": "operand",
  "EXECUTABLEEXPRESSION_OUTPUTMATCHERS": "outputMatchers",
  "EXECUTABLEEXPRESSION_VARIABLEINFOS": "variableInfos",
  "EXPRESSIONGROUP_VARIABLEINFOS": "variableInfos",
  "EXPRESSIONGROUP_EXPRESSIONS": "expressions",
  "EXECUTABLEHANDLER_STEPS": "steps",
  "HANDLER_STEPS": "steps",
  "HANDLERSTEP_STEPTYPE": "stepType",
  "OUTPUTINTERACTIVE_CRITERIA": "criteria",
  "OUTPUTINTERACTIVE_REFERENCE": "reference",
  "OUTPUTINTERACTIVE_DATA": "data",
  "RESULTINTERACTIVE_OUTPUT": "output",
  "_REQUEST": "request",
  "_RESULT": "result",
  "MATCHER_REVERSE": "reverse",
  "MATCHER_DATATYPEID": "dataTypeId",
  "MATCHER_RELATIONSHIP": "relationship",
  "MATCHER_SUBMATCHERS": "subMatchers",
  "MATCHERSCOMBO_MATCHERS": "matchers",
  "MATCHERSCOMBO_REVERSEMATCHERS": "reverseMatchers",
  "CONTAINERVARIABLECRITERIAINFO_VARIABLES": "variables",
  "OPERAND_TYPE": "type",
  "OPERAND_STATUS": "status",
  "OPERAND_CHILDREN": "children",
  "OPERAND_DATATYPEINFO": "dataTypeInfo",
  "OPERAND_CONVERTERS": "converters",
  "OPERAND_OUTPUTCRITERIA": "outputCriteria",
  "OPERAND_NAME": "name",
  "OPERAND_DATA": "data",
  "OPERAND_DATATYPEID": "dataTypeId",
  "OPERAND_OPERATION": "operation",
  "OPERAND_PARMS": "parms",
  "OPERAND_BASE": "base",
  "OPERAND_MATCHERSPARMS": "matchersParms",
  "OPERAND_REFERENCE": "reference",
  "OPERAND_EXPRESSION": "expression",
  "OPERAND_ELEMENTNAME": "elementName",
  "OPERAND_VARMATCHERS": "varMatchers",
  "OPERAND_VARMAPPING": "varMapping",
  "OPERAND_VARIABLENAME": "variableName",
  "OPERAND_VARIABLEID": "variableId",
  "DEFINITIONACTIVITY_INPUTMAPPING": "inputMapping",
  "DEFINITIONACTIVITY_BRANCH": "branch",
  "_INPUTMAPPING": "inputMapping",
  "DEFINITIONRESULTACTIVITYBRANCH_FLOW": "flow",
  "DEFINITIONRESULTACTIVITYBRANCH_DATA": "data",
  "DEFINITIONRESULTACTIVITYNORMAL_FLOW": "flow",
  "DEFINITIONRESULTACTIVITYNORMAL_OUTPUT": "output",
  "DEFINITIONSEQUENCEFLOW_TARGET": "target",
  "_PROCESS": "process",
  "EXECUTABLEACTIVITY_CATEGARY": "categary",
  "EXECUTABLEACTIVITY_ID": "id",
  "EXECUTABLEACTIVITY_DEFINITION": "definition",
  "EXECUTABLEACTIVITY_BRANCH": "branch",
  "EXECUTABLEPROCESS_DEFINITION": "definition",
  "EXECUTABLEPROCESS_ID": "id",
  "EXECUTABLEPROCESS_ACTIVITY": "activity",
  "EXECUTABLEPROCESS_STARTACTIVITYID": "startActivityId",
  "EXECUTABLEPROCESS_CONTEXT": "context",
  "EXECUTABLEPROCESS_RESULT": "result",
  "EXECUTABLEPROCESS_INITSCRIPT": "initScript",
  "EXECUTABLERESULTACTIVITYBRANCH_FLOW": "flow",
  "EXECUTABLERESULTACTIVITYBRANCH_DATA": "data",
  "EXECUTABLERESULTACTIVITYNORMAL_FLOW": "flow",
  "EXECUTABLERESULTACTIVITYNORMAL_DATAASSOCIATION": "dataAssociation",
  "RESULTPROCESS_RESULTNAME": "resultName",
  "RESULTPROCESS_OUTPUT": "output",
  "ENTITYINFO_DATA": "data",
  "RESOURCE_ID": "id",
  "RESOURCE_RESOURCEDATA": "resourceData",
  "RESOURCE_INFO": "info",
  "ENTITYINFO_RESOURCEID": "resourceId",
  "ENTITYINFO_LOCALREFERENCEBASE": "localReferenceBase",
  "RESOURCEDEPENDENCY_ALIAS": "alias",
  "RESOURCEDEPENDENCY_ID": "id",
  "RESOURCEID_RESOURCETYPE": "resourceType",
  "RESOURCEID_ID": "id",
  "RESOURCEID_STRUCUTRE": "structure",
  "RESOURCEID_SUP": "supliment",
  "RESOURCEID_BUILDER": "builder",
  "RESOURCEID_PARMS": "parms",
  "RESOURCEID_PARENT": "parent",
  "RESOURCEID_PATH": "path",
  "RESOURCEID_BASEPATH": "basePath",
  "RESOURCEID_NAME": "name",
  "RESOURCEINFO_ID": "id",
  "RESOURCEINFO_INFO": "info",
  "RESOURCEINFO_DEPENDENCY": "dependency",
  "RESOURCEINFO_CHILDREN": "children",
  "ENTITYINFO_CONTEXT": "context",
  "INFORUNTIMETASKEXPRESSION_EXPRESSION": "expression",
  "INFORUNTIMETASKEXPRESSION_ITEMNAME": "itemName",
  "INFORUNTIMETASKEXPRESSION_VARIABLESVALUE": "variablesValue",
  "INFORUNTIMETASKTASK_TASKSUITE": "taskSuite",
  "INFORUNTIMETASKTASK_ITEMNAME": "itemName",
  "INFORUNTIMETASKTASK_INPUTVALUE": "inputValue",
  "RUNTIMETASKEXECUTECONVERTER_DATAT": "data",
  "RUNTIMETASKEXECUTECONVERTER_MATCHERS": "matchers",
  "RUNTIMETASKEXECUTEDATAOPERATION_DATATYPEID": "dataTypeId",
  "RUNTIMETASKEXECUTEDATAOPERATION_OPERATION": "operation",
  "RUNTIMETASKEXECUTEDATAOPERATION_PARMS": "parms",
  "RUNTIMETASKEXECUTEPROCESS_PROCESS": "process",
  "RUNTIMETASKEXECUTEPROCESS_INPUT": "input",
  "RUNTIMETASKEXECUTEPROCESSEMBEDED_PROCESS": "process",
  "RUNTIMETASKEXECUTEPROCESSEMBEDED_PARENTCONTEXT": "parentContext",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION": "loadTestExpression",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION_ELEMENT_SUITE": "suite",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION_ELEMENT_EXPRESSIONNAME": "expressionName",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION_ELEMENT_VARIABLES": "variables",
  "_COMMAND_GETCHILDCRITERIA": "getChildCriteria",
  "_COMMAND_GETCHILDCRITERIA_CRITERIA": "criteria",
  "_COMMAND_GETCHILDCRITERIA_CHILDNAME": "childName",
  "_COMMAND_GETCHILDRENCRITERIA": "getChildrenCriteria",
  "_COMMAND_GETCHILDRENCRITERIA_CRITERIA": "criteria",
  "_COMMAND_ADDCHILDCRITERIA": "addChildCriteria",
  "_COMMAND_ADDCHILDCRITERIA_CRITERIA": "criteria",
  "_COMMAND_ADDCHILDCRITERIA_CHILDNAME": "childName",
  "_COMMAND_ADDCHILDCRITERIA_CHILD": "child",
  "GATEWAYRESOURCE_COMMAND_DISCOVERRESOURCES": "requestDiscoverResources",
  "GATEWAYRESOURCE_COMMAND_DISCOVERRESOURCES_RESOURCEIDS": "resourceIds",
  "GATEWAYRESOURCE_COMMAND_DISCOVERANDLOADRESOURCES": "requestDiscoverAndLoadResources",
  "GATEWAYRESOURCE_COMMAND_DISCOVERANDLOADRESOURCES_RESOURCEIDS": "resourceIds",
  "GATEWAYRESOURCE_COMMAND_LOADRESOURCES": "requestLoadResources",
  "GATEWAYRESOURCE_COMMAND_LOADRESOURCES_RESOURCEINFOS": "resourceInfos",
  "GATEWAYRESOURCEDEFINITION_COMMAND_LOADRESOURCEDEFINITION": "requestLoadResourceDefinition",
  "GATEWAYRESOURCEDEFINITION_COMMAND_LOADRESOURCEDEFINITION_ID": "resourceId",
  "GATEWAYOUTPUT_SCRIPTS": "scripts",
  "GATEWAYOUTPUT_DATA": "data",
  "JSSCRIPTINFO_NAME": "name",
  "JSSCRIPTINFO_FILE": "file",
  "JSSCRIPTINFO_SCRIPT": "script",
  "_VALUE": "value",
  "RUNTIME_NODENAME_GATEWAY": "gatewayObj",
  "RUNTIME_GATEWAY_CODETABLE": "codeTable",
  "RUNTIME_GATEWAY_RESOURCE": "resources",
  "RUNTIME_GATEWAY_RESOURCEDEFINITION": "resourceDefinition",
  "RUNTIME_GATEWAY_CRITERIA": "criteria",
  "RUNTIME_GATEWAY_ERRORLOG": "errorLog",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERRESOURCES": "requestDiscoverResources",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERRESOURCES_RESOURCEIDS": "resourceIds",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERANDLOADRESOURCES": "requestDiscoverAndLoadResources",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERANDLOADRESOURCES_RESOURCEIDS": "resourceIds",
  "RUNTIMEGATEWAYJS_REQUEST_LOADRESOURCES": "requestLoadResources",
  "RUNTIMEGATEWAYJS_REQUEST_LOADRESOURCES_RESOURCEINFOS": "resourceInfos",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS": "getExpressions",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_EXPRESSIONS": "expressions",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_ELEMENT_SUITE": "suite",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_ELEMENT_EXPRESSIONNAME": "expressionName",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_ELEMENT_VARIABLES": "variables",
  "_VERSION": "version",
  "_DATATYPENAME": "dataTypeName",
  "_GATEWAY": "gateway",
  "RESOURCEDATAJSLIBRARY_URIS": "uris",
  "_OPERATIONID": "operationId",
  "_OPERATIONNAME": "operationName",
  "_SCRIPTEXPRESSION": "scriptExpression",
  "_VARIABLESVALUE": "variablesValue",
  "ENTITYINFO_SCRIPT": "script",
  "ENTITYINFO_TYPE": "type",
  "ENTITYINFO_REFERENCEMAPPING": "referenceMapping",
  "_SCRIPTTYPE": "scriptType",
  "_ID": "id",
  "EXECUTABLESCRIPTENTITY_SCRIPTFUNCTION": "scriptFunction",
  "EXECUTABLESCRIPTENTITY_SUPPORTFUNCTION": "supportFunction",
  "EXECUTABLESCRIPTENTITY_VARIABLESINFO": "variablesInfo",
  "EXECUTABLESCRIPTENTITY_VARIABLES": "variables",
  "EXECUTABLESCRIPTENTITY_EXPRESSIONREF": "expressionRef",
  "EXECUTABLESCRIPTGROUP_EXPRESSIONGROUP": "expressionGroup",
  "EXECUTABLESCRIPTGROUP_ELEMENT": "element",
  "_SCRIPT": "script",
  "_CONSTANTNAME": "constantName",
  "_ELEMENTS": "elements",
  "_VARIABLENAME": "variableName",
  "_TEXT": "text",
  "DEFINITIONSEQUENCE_STEP": "step",
  "EXECUTABLESEQUENCE_STEP": "step",
  "DEFINITIONSERVICE_STATIC": "static",
  "DEFINITIONSERVICE_RUNTIME": "runtime",
  "_SUITE": "suite",
  "GATEWAYSERVICE_COMMAND_REQUEST": "request",
  "GATEWAYSERVICE_COMMAND_REQUEST_QUERY": "query",
  "GATEWAYSERVICE_COMMAND_REQUEST_PARMS": "parms",
  "GATEWAYSERVICE_COMMAND_SEARCHDEFINITION": "searchDefinition",
  "GATEWAYSERVICE_COMMAND_SEARCHDEFINITION_QUERY": "query",
  "_IMPLEMENTATION": "implementation",
  "_CONFIGURE": "configure",
  "INFOSERVICESTATIC_TAG": "tag",
  "INFOSERVICESTATIC_INTERFACE": "interface",
  "QUERYSERVICE_SERVICEID": "serviceId",
  "QUERYSERVICE_INTERFACE": "interface",
  "QUERYSERVICEDEFINITION_KEYWORDS": "keywords",
  "RESULTSERVICE_RESULTNAME": "resultName",
  "RESULTSERVICE_RESULTVALUE": "resultValue",
  "INFOSERVICEINTERFACE_TAG": "tag",
  "INFOSERVICEINTERFACE_INTERFACE": "interface",
  "INFOSERVICEINTERFACE_DISPLAY": "display",
  "DEFINITIONMAPPINGSERVICE_PARMMAPPING": "parmMapping",
  "DEFINITIONMAPPINGSERVICE_RESULTMAPPING": "resultMapping",
  "DEFINITIONSERVICEINENTITY_USE": "use",
  "DEFINITIONSERVICEINENTITY_PROVIDER": "provider",
  "DEFINITIONSERVICEPROVIDER_SERVICEID": "serviceId",
  "DEFINITIONSERVICEPROVIDER_SERVICEINTERFACE": "serviceInterface",
  "DEFINITIONSERVICEUSE_INTERFACE": "interface",
  "DEFINITIONSERVICEUSE_PROVIDER": "provider",
  "DEFINITIONSERVICEUSE_DATAASSOCIATION": "dataAssociation",
  "EXECUTABLEPROVIDERTOUSE_PARMMAPPING": "parmMapping",
  "EXECUTABLEPROVIDERTOUSE_RESULTMAPPING": "resultMapping",
  "EXECUTABLESERVICEUSE_NAME": "name",
  "EXECUTABLESERVICEUSE_INFO": "info",
  "EXECUTABLESERVICEUSE_SERVICEUSE": "serviceUse",
  "EXECUTABLESERVICEUSE_PROVIDERMAPPING": "providerMapping",
  "EXECUTABLESERVICEUSE_PROVIDERID": "providerId",
  "ENTITYINFO_DATAMAPPING": "dataMapping",
  "_SERVICE": "service",
  "CHANGEDATAGROUPELEMENT_IDINDEX": "idIndex",
  "CHANGEDATAGROUPELEMENT_ELEMENTINFO": "elementInfo",
  "CHANGEITEM_CHANGETYPE": "changeType",
  "CHANGEITEM_REVERTCHANGES": "revertChanges",
  "CHANGEITEM_REVERTABLE": "revertable",
  "CHANGEITEM_EXTENDFROM": "extendFrom",
  "CHANGEITEM_EXTENDED": "extended",
  "CHANGEITEM_ALIAS": "alias",
  "CHANGEITEM_ELEMENTID": "elementId",
  "CHANGEITEM_TARGETELEMENTREF": "targetElementRef",
  "CHANGEITEM_ELEMENT": "element",
  "CHANGEITEM_PATH": "path",
  "CHANGEITEM_VALUE": "value",
  "CHANGEITEM_INFONAME": "infoName",
  "CHANGEITEM_INFOVALUE": "infoValue",
  "ANSWER_CHANGES": "changes",
  "ANSWER_QUESTIONID": "questionId",
  "DESIGNSTEP_CHANGES": "changes",
  "DESIGNSTEP_QUESTIONAIRE": "questionaire",
  "DESIGNSTORY_DIRECTORID": "directorId",
  "DESIGNSTORY_STORY": "story",
  "DESIGNSTORY_CHANGEHISTORY": "changeHistory",
  "QUESTION_QUESTION": "question",
  "QUESTION_TYPE": "type",
  "QUESTION_CHILDREN": "children",
  "QUESTION_TARGETREF": "targetRef",
  "QUESTION_ISMANDATORY": "isMandatory",
  "QUESTIONNAIRE_QUESTIONS": "questions",
  "QUESTIONNAIRE_ANSWERS": "answers",
  "RESPONSEDESIGN_ANSWER": "answer",
  "RESPONSEDESIGN_ANSWEREXTEND": "answerExtend",
  "RESPONSEDESIGN_STEP": "step",
  "CONNECTIONCONTAIN_CHILDID": "childId",
  "CONNECTIONDATAIO_PATH1": "path1",
  "CONNECTIONDATAIO_PATH2": "path2",
  "ELEMENTGROUPSWITCH_CHOICE": "choice",
  "STORYNODECONSTANT_DATA": "data",
  "STORYNODECONSTANT_DATATYPE": "dataType",
  "STORYNODECONSTANT_ISMANDATORY": "isMandatory",
  "STORYNODESERVICE_REFERENCEID": "referenceId",
  "STORYNODESERVICEINPUTPARM_PARMDEFINITION": "parmDefinition",
  "STORYNODESERVICEOUTPUTITEM_OUTPUTITEM": "outputItem",
  "STORYNODEVARIABLE_VARAIBLEINFO": "variableInfo",
  "STORYNODEVARIABLE_INITDATA": "initData",
  "ALIASELEMENT_NAME": "name",
  "ALIASELEMENT_TEMPORARY": "temporary",
  "CONNECTION_END1": "end1",
  "CONNECTION_END2": "end2",
  "CONNECTIONEND_CONNECTIONID": "connectionId",
  "CONNECTIONEND_NODEREF": "nodeRef",
  "CONNECTIONEND_PROFILE": "profile",
  "ELEMENTGROUP_ELEMENTS": "elements",
  "ELEMENTGROUP_ELEMENT": "element",
  "IDELEMENT_CATEGARY": "categary",
  "IDELEMENT_ID": "id",
  "IDELEMENTINFO_CATEGARY": "categary",
  "IDELEMENTINFO_ID": "id",
  "IDELEMENTINFO_TYPE": "type",
  "INFOELEMENT_ELEMENTREF": "elementRef",
  "STORY_DIRECTOR": "director",
  "STORY_SHOWTYPE": "showType",
  "STORY_NODE": "node",
  "STORY_CONNECTION": "connection",
  "STORY_ELEMENTGROUP": "elementGroup",
  "STORY_ALIAS": "alias",
  "STORYELEMENT_CATEGARY": "categary",
  "STORYELEMENT_TYPE": "type",
  "STORYELEMENT_ENABLE": "enable",
  "STORYELEMENT_ELEMENTSTATUS": "eleStatus",
  "STORYELEMENT_DISPLAYRESOURCE": "displayResource",
  "STORYELEMENT_EXTRA": "extra",
  "STORYNODE_CONNECTIONS": "connection",
  "ELEMENTSTRUCTURE_TYPE": "type",
  "ELEMENTSTRUCTURE_PROCESSED": "processed",
  "ELEMENTSTRUCTURELEAFCONSTANT_VALUE": "value",
  "ELEMENTSTRUCTURE_CONSTANT": "constant",
  "ELEMENTSTRUCTURE_CRITERIA": "criteria",
  "ELEMENTSTRUCTURE_PATH": "path",
  "ELEMENTSTRUCTURE_RESOLVEDPATH": "resolvedPath",
  "ELEMENTSTRUCTURE_PARENTCATEGARY": "parentCategary",
  "ELEMENTSTRUCTURE_DEFINITION": "definition",
  "ELEMENTSTRUCTURE_SOLIDNODEREF": "solidNodeRef",
  "ELEMENTSTRUCTURE_MATCHERS": "matchers",
  "ELEMENTSTRUCTURE_REVERSEMATCHERS": "reverseMatchers",
  "ELEMENTSTRUCTURE_CHILD": "child",
  "INFOPATHTOSOLIDROOT_ROOTNODEID": "rootNodeId",
  "INFOPATHTOSOLIDROOT_PATH": "path",
  "REFERENCEELEMENTINSTRUCTURE_ROOTREFERENCE": "rootReference",
  "REFERENCEELEMENTINSTRUCTURE_PATH": "path",
  "REFERENCEROOTINSTRUCUTRE_STRUCTURETYPE": "structureType",
  "ROOTSTRUCTURE_LOCALID": "localId",
  "ROOTSTRUCTURE_GLOBALID": "globalId",
  "ROOTSTRUCTURE_DEFINITION": "definition",
  "ROOTSTRUCTURE_DEFAULT": "defaultValue",
  "REFERENCEELEMENTINSTRUCTURECOMPLEX_REFERENCEPATH": "referencePath",
  "REFERENCEELEMENTINSTRUCTURECOMPLEX_PARENT": "parent",
  "DEFINITIONTASK_TASKTYPE": "taskType",
  "EXECUTABLETASK_TASKTYPE": "taskType",
  "EXECUTABLETASKSUITE_ID": "id",
  "EXECUTABLETASKSUITE_TASK": "task",
  "EXECUTABLETASKSUITE_INITSCRIPT": "initScript",
  "_STRUCTURE": "structure",
  "EXECUTABLEVALUESTRUCTURE_ROOT": "root",
  "EXECUTABLEVALUESTRUCTURE_NAME2ID": "name2Id",
  "REFERENCEROOTINEXECUTABLE_ID": "id",
  "REFERENCEROOTINEXECUTABLE_NAME": "name",
  "REFERENCEROOTINFLAT_NAME": "name",
  "REFERENCEROOTINGROUP_CATEGARY": "categary",
  "REFERENCEROOTINGROUP_NAME": "name",
  "REFERENCEROOTINGROUP_PATHFORMAT": "pathFormat",
  "_INFO": "info",
  "VALUESTRUCTUREDEFINITIONFLAT_FLAT": "flat",
  "VALUESTRUCTUREDEFINITIONGROUP_GROUP": "group",
  "VALUESTRUCTUREDEFINITIONGROUP_INFO_INHERIT": "inherit",
  "VALUESTRUCTUREDEFINITIONGROUP_INFO_POPUP": "popup",
  "VALUESTRUCTUREDEFINITIONGROUP_INFO_ESCALATE": "escalate",
  "VARIABLEINFOINSTRUCTURE_NAMESBYVARID": "namesByVarId",
  "VARIABLEINFOINSTRUCTURE_VARIDBYNAME": "varIdByName",
  "ENTITYINFO_ROOT": "root",
  "_CRONJOB": "cronJob",
  "DATATYPE_OPERATIONS": "operations",
  "STRINGABLEVALUE_DESCRIPTION": "description",
  "STRINGABLEVALUE_COMPLEX": "complex",
  "DATAOPERATIONINFO_OPERATIONID": "operationId",
  "_SOURCE": "source",
  "_RELATIONSHIPS": "relationship",
  "DATAOPERATIONINFO_BASEPARM": "baseParm",
  "DATAOPERATIONINFO_DATATYPNAME": "dataTypeName",
  "STRINGABLEVALUE_DATATYPEID": "dataTypeId",
  "STRINGABLEVALUE_OPERATIONID": "operationId",
  "STRINGABLEVALUE_SOURCEDATATYPE": "sourceDataType",
  "STRINGABLEVALUE_TARGETDATATYPE": "targetDataType",
  "STRINGABLEVALUE_PATH": "path",
  "STRINGABLEVALUE_TARGETTYPE": "targetType",
  "RUNTIME_GATEWAY_LOADLIBRARIES": "loadLibraries",
  "RUNTIME_GATEWAY_TESTEXPRESSION": "testExpression",
  "RUNTIME_GATEWAY_SERVICE": "service",
  "RUNTIME_GATEWAY_UITAG": "uiTag",
  "STRINGABLEVALUE_RESOURCEID": "resourceId",
  "STRINGABLEVALUE_DEPENDENCY": "dependency",
  "RESOURCEMANAGERJSOPERATION_INFO_OPERATIONINFO": "operationInfo",
  "DEFINITIONAPP_ID": "id",
  "DEFINITIONAPP_APPLICATIONDATA": "applicationData",
  "DEFINITIONAPPELEMENTUI_MODULE": "module",
  "DEFINITIONAPPMODULE_MODULE": "module",
  "DEFINITIONAPPMODULE_ROLE": "role",
  "DEFINITIONAPPMODULE_STATUS": "status",
  "EXECUTABLEAPPENTRY_ID": "id",
  "EXECUTABLEAPPENTRY_MODULE": "module",
  "EXECUTABLEAPPENTRY_CONTEXT": "context",
  "EXECUTABLEAPPENTRY_PROCESS": "process",
  "EXECUTABLEAPPENTRY_APPLICATIONDATA": "applicationData",
  "EXECUTABLEAPPENTRY_INITSCRIPT": "initScript",
  "EXECUTABLEAPPMODULE_MODULEDEFID": "moduleDefId",
  "EXECUTABLEAPPMODULE_ROLE": "role",
  "EXECUTABLEAPPMODULE_MODULE": "module",
  "EXECUTABLEAPPMODULE_INPUTMAPPING": "inputMapping",
  "EXECUTABLEAPPMODULE_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEAPPMODULE_DATADEPENDENCY": "dataDependency",
  "EXECUTABLEAPPMODULE_EVENTHANDLER": "eventHandler",
  "COMPONENTWITHCONFIGURATION_PAGEINFO": "pageInfo",
  "COMPONENTWITHCONFIGURATION_SERVICE": "service",
  "DEFINITIONDECORATION_SHARE": "share",
  "DEFINITIONDECORATION_PARTS": "parts",
  "EXECUTABLEEVENTHANDLER_PROCESS": "process",
  "INFODECORATION_ID": "id",
  "INFODECORATION_CONFIGURE": "configure",
  "INFOPAGE_ID": "id",
  "INFOPAGE_DECORATION": "decoration",
  "DEFINITIONACTIVITY_PARTID": "partId",
  "DEFINITIONACTIVITY_COMMAND": "command",
  "EXECUTABLEACTIVITY_PARTID": "partId",
  "EXECUTABLEACTIVITY_COMMAND": "command",
  "DEFINITIONACTIVITY_PAGE": "page",
  "DEFINITIONACTIVITY_SETTING": "setting",
  "EXECUTABLEACTIVITY_UI": "ui",
  "EXECUTABLEACTIVITY_SETTING": "setting",
  "DEFINITIONMODULEUI_PAGE": "page",
  "DEFINITIONMODULEUI_UIDECORATION": "uiDecoration",
  "DEFINITIONMODULEUI_TYPE": "type",
  "EXECUTABLEMODULE_UI": "ui",
  "EXECUTABLEMODULEUI_PAGE": "page",
  "EXECUTABLEMODULEUI_UIDECORATION": "uiDecoration",
  "EXECUTABLEMODULEUI_PAGENAME": "pageName",
  "DEFINITIONUICOMMAND_PARM": "parm",
  "DEFINITIONUICOMMAND_RESULT": "result",
  "DEFINITIONUIEVENT_DATA": "data",
  "ELEMENTEVENT_UIID": "uiId",
  "ELEMENTEVENT_EVENT": "event",
  "ELEMENTEVENT_FUNCTION": "function",
  "ELEMENTEVENT_SELECTION": "selection",
  "EXECUTABLESTYLE_ID": "id",
  "EXECUTABLESTYLE_DEFINITION": "definition",
  "EXECUTABLESTYLE_CHILDREN": "children",
  "EXECUTABLECOMPONENT_TAGNAME": "tagName",
  "EXECUTABLECOMPONENT_TAGVALUESTRUCTURE": "tagValueStructure",
  "EXECUTABLECOMPONENT_EVENTMAPPING": "eventMapping",
  "EXECUTABLECOMPONENT_CONTEXTMAPPING": "contextMapping",
  "EXECUTABLECOMPONENT_COMMANDMAPPING": "commandMapping",
  "EXECUTABLECOMPONENT_SERVICEMAPPING": "serviceMapping",
  "EXECUTABLEUIUNIT_SCRIPTEXPRESSIONSINCONTENT": "scriptExpressionsInContent",
  "EXECUTABLEUIUNIT_SCRIPTEXPRESSIONINATTRIBUTES": "scriptExpressionInAttributes",
  "EXECUTABLEUIUNIT_SCRIPTEXPRESSIONINTAGATTRIBUTES": "scriptExpressionTagAttributes",
  "EXECUTABLEUIUNIT_SCRIPTGROUP": "scriptGroup",
  "EXECUTABLEUIUNIT_SCRIPT": "script",
  "EXECUTABLEUIUNIT_HTML": "html",
  "EXECUTABLEUIUNIT_ELEMENTEVENTS": "elementEvents",
  "EXECUTABLEUIUNIT_TAGEVENTS": "tagEvents",
  "EXECUTABLEUIUNIT_UITAGS": "uiTags",
  "EXECUTABLEUIUNIT_UITAGLIBS": "uiTagLibs",
  "EXECUTABLEUIUNIT_CONSTANTS": "constants",
  "EXECUTABLEUIUNIT_EXPRESSIONS": "expressions",
  "EXECUTABLEUIUNIT_HANDLERS": "handlers",
  "EXECUTABLEUIUNIT_SERVICEPROVIDERS": "serviceProviders",
  "EXECUTABLEUIUNIT1_ID": "id",
  "EXECUTABLEUIUNIT1_TYPE": "type",
  "EXECUTABLEUIUNIT1_ATTRIBUTES": "attributes",
  "EXECUTABLEUIUNIT1_BODYUNIT": "bodyUnit",
  "EXECUTABLEUIUNIT_STYLE": "style",
  "UIEMBEDEDSCRIPTEXPRESSION_UIID": "uiId",
  "UIEMBEDEDSCRIPTEXPRESSION_SCRIPT": "script",
  "UIEMBEDEDSCRIPTEXPRESSION_ATTRIBUTE": "attribute",
  "STORYNODEUI_DATASTRUCTURE": "dataStructure",
  "STORYNODEUIDATA_DATAINFO": "dataInfo",
  "STORYNODEUIDATA_MATCHERS": "matchers",
  "STORYNODEUIDATA_ATTRIBUTE_DATAFLOW": "dataflow",
  "STORYNODEUIHTML_HTML": "html",
  "STORYNODEUITAG_TAGNAME": "tagName",
  "STORYNODEUITAG_ATTRIBUTES": "attributes",
  "UIDATASTRUCTUREINFO_CONTEXT": "context",
  "UIDATAINFO_DATATYPE": "dataType",
  "UIDATAINFO_IDPATH": "idPath",
  "UIDATAINFO_ROOTREFERENCE": "rootReference",
  "GATEWAYUITAG_COMMAND_GETDEFAULTTAG": "getDefaultTag",
  "GATEWAYUITAG_COMMAND_GETDEFAULTTAG_CRITERIA": "criteria",
  "GATEWAYUITAG_COMMAND_QUERYTAG": "queryTag",
  "GATEWAYUITAG_COMMAND_QUERYTAG_CRITERIA": "criteria",
  "UITAGDEFINITION_TYPE": "type",
  "UITAGDEFINITION_BASE": "base",
  "UITAGDEFINITION_SCRIPT": "script",
  "UITAGDEFINITION_ATTRIBUTES": "attributes",
  "UITAGDEFINITION_VALUESTRUCTURE": "valueStructure",
  "UITAGDEFINITION_VALUESTRUCTUREEXE": "valueStructureExe",
  "UITAGDEFINITION_REQUIRES": "requires",
  "UITAGDEFINITION_EVENT": "event",
  "UITAGDEFINITIONATTRIBUTE_DEFAULTVALUE": "defaultValue",
  "UITAGDEFINITION_DATATYPECRITERIA": "dataTypeCriteria",
  "UITAGEQUERYDATA_DATATYPECRITERIA": "dataTypeCriteria",
  "UITAGINFO_ATTRIBUTES": "attributes",
  "UITAGINFO_MATCHERS": "matchers",
  "UITAGQUERYRESULT_UITAGINFO": "uiTagInfo",
  "UITAGQUERYRESULT_SCORE": "weight",
  "UITAGQUERYRESULTSET_ITEMS": "items",
  "_RESOURCEID": "resourceId",
  "_URL": "url",
  "_ISVALID": "isValid",
  "_CHILDREN": "children",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP": "loadMiniApp",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP_APPID": "appId",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP_USERID": "userId",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP_ENTRY": "entry",
  "MINIAPPSERVLET_COMMAND_CREATEDATA": "createData",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_USERID": "userId",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_APPID": "appId",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_DATANAME": "dataName",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_DATAINFO": "dataInfo",
  "MINIAPPSERVLET_COMMAND_UPDATEDATA": "updateData",
  "MINIAPPSERVLET_COMMAND_UPDATEDATA_ID": "id",
  "MINIAPPSERVLET_COMMAND_UPDATEDATA_DATAINFO": "dataInfo",
  "MINIAPPSERVLET_COMMAND_DELETEDATA": "deleteData",
  "MINIAPPSERVLET_COMMAND_DELETEDATA_DATATYPE": "dataType",
  "MINIAPPSERVLET_COMMAND_DELETEDATA_ID": "id",
  "NEWDESIGN_DESIGNID": "designId",
  "NEWDESIGN_DIRECTOR": "director",
  "NEWDESIGN_STORYID": "storyId",
  "NEWDESIGN_STORY": "story",
  "STORYBUILDSERVLET_COMMAND_GETDESIGN": "getDesign",
  "STORYBUILDSERVLET_COMMAND_GETDESIGN_ID": "id",
  "STORYBUILDSERVLET_COMMAND_NEWDESIGN": "newDesign",
  "STORYBUILDSERVLET_COMMAND_NEWDESIGN_DIRECTORID": "directorId",
  "STORYBUILDSERVLET_COMMAND_DESIGN": "design",
  "STORYBUILDSERVLET_COMMAND_DESIGN_DESIGNID": "designId",
  "STORYBUILDSERVLET_COMMAND_DESIGN_ANSWER": "answer",
  "STORYBUILDSERVLET_COMMAND_DESIGN_EXTRACHANGES": "extraChanges",
  "STORYBUILDSERVLET_COMMAND_DESIGN_STEPCUSOR": "stepCursor",
  "STORYBUILDSERVLET_COMMAND_CONVERTTOSHOW": "convertToShow",
  "STORYBUILDSERVLET_COMMAND_CONVERTTOSHOW_DESIGNID": "designId",
  "STORYBUILDSERVLET_COMMAND_SHOW": "show",
  "STORYBUILDSERVLET_COMMAND_SHOW_DESIGNID": "designId",
  "GATEWAYSERVLET_COMMAND_PARM_RUNTIMEINFO": "runtimeInfo",
  "SERVICESERVLET_COMMAND_LOGIN": "login",
  "REQUESTINFO_CLIENTID": "clientId",
  "REQUESTINFO_COMMAND": "command",
  "REQUESTINFO_PARMS": "parms",
  "SERVICEINFO_SERVICE_COMMAND": "command",
  "SERVICEINFO_SERVICE_PARMS": "parms",
  "SERVICESERVLET_REQUEST_TYPE": "type",
  "SERVICESERVLET_REQUEST_SERVICE": "service",
  "SERVICESERVLET_REQUEST_MODE": "mode",
  "SERVICESERVLET_REQUEST_CHILDREN": "children",
  "PLAYERINFO_EMAIL": "email",
  "_USERID": "userId",
  "PLAYERLINEUP_WAITINGLIST": "waitingList",
  "PLAYERLINEUP_LINEUP": "lineUp",
  "PLAYERLINEUP_VACANT": "vacant",
  "RESPONSEPLAYERLINEUP_WAITINGLIST": "waitingList",
  "RESPONSEPLAYERLINEUP_LINEUP": "lineUp",
  "RESPONSESPOT_PLAYERS": "players",
  "RESPONSESPOT_VACANT": "vacant",
  "SPOT_PLAYERS": "players",
  "APPDATAINFO_ID": "id",
  "APPDATAINFO_NAME": "name",
  "APPDATAINFO_OWNERINFO": "ownerInfo",
  "APPDATAINFO_DATA": "data",
  "APPDATAINFOCONTAINER_APPDATAINFOS": "appDataInfos",
  "MINIAPP_CATEGARY": "dataOwnerType",
  "OWNERINFO_USERID": "userId",
  "OWNERINFO_COMPONENTID": "componentId",
  "OWNERINFO_COMPONENTTYPE": "componentType",
  "USERGROUPMINIAPP_GROUP": "group",
  "USERGROUPMINIAPP_MINIAPP": "miniApp",
  "_GROUP": "group",
  "_MINIAPP": "miniApp",
  "_GROUPMINIAPP": "groupMiniApp",
  "GATEWAYAPPDATA_GATEWAY_APPDATA": "appData",
  "GATEWAYAPPDATA_COMMAND_GETOWNERAPPDATA": "getOwnerAppData",
  "GATEWAYAPPDATA_COMMAND_GETOWNERAPPDATA_OWNER": "owner",
  "GATEWAYAPPDATA_COMMAND_GETAPPDATA": "getAppData",
  "GATEWAYAPPDATA_COMMAND_GETAPPDATA_INFOS": "infos",
  "GATEWAYAPPDATA_COMMAND_UPDATEAPPDATA": "updateAppData",
  "GATEWAYAPPDATA_COMMAND_UPDATEAPPDATA_INFOS": "infos"
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createChildNode("COMMONATRIBUTECONSTANT", COMMONATRIBUTECONSTANT); 

})(packageObj);
