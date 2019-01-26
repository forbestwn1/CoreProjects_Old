
nosliw.runtime.getResourceService().importResource({"id":{"id":"debug",
"type":"uiTag"
},
"children":[],
"dependency":{},
"info":{}
}, {"name":"debug",
"context":{"group":{"public":{"element":{}
},
"protected":{"element":{}
},
"internal":{"element":{}
},
"private":{"element":{}
}
},
"info":{}
},
"attributes":{},
"event":[],
"script":
function (env) {
    var node_createServiceRequestInfoSet = nosliw.getNodeData("request.request.createServiceRequestInfoSet");
    var node_requestProcessor = nosliw.getNodeData("request.requestServiceProcessor");
    var node_createContextVariablesGroup = nosliw.getNodeData("uidata.context.createContextVariablesGroup");
    var node_createContextVariableInfo = nosliw.getNodeData("uidata.context.createContextVariableInfo");
    var loc_env = env;
    var loc_view;
    var loc_viewData;
    var loc_viewVariableTree;
    var loc_contextVariableGroup = {};
    var loc_getVariableTreeInfo = function (eleVar, childInfo) {
        var out = {};
        out.id = eleVar.prv_id;
        if (childInfo != undefined) {
            out.path = childInfo.path;
            out.normal = childInfo.isNormal;
        }
        out.children = [];
        var childrenInfo = eleVar.prv_getChildren();
        if (childrenInfo != undefined) {
            _.each(childrenInfo, function (childVarInfo, id) {
                out.children.push(loc_getVariableTreeInfo(childVarInfo.variable, childVarInfo));
            });
        }
        return out;
    };
    var loc_updateView = function (requestInfo) {
        var contextContent = {};
        var setRequest = node_createServiceRequestInfoSet({}, {success: function (requestInfo, result) {
            _.each(result.getResults(), function (contextData, name) {
                contextContent[name] = contextData.value;
            });
            loc_viewData.val(JSON.stringify(contextContent, null, 4));
        }}, requestInfo);
        var eleVars = loc_contextVariableGroup.getVariables();
        _.each(eleVars, function (eleVar, eleName) {
            setRequest.addRequest(eleName, loc_env.getDataOperationRequestGet(eleVar));
        });
        node_requestProcessor.processRequest(setRequest, false);
        var varTree = {};
        _.each(eleVars, function (eleVar, eleName) {
            varTree[eleName] = loc_getVariableTreeInfo(eleVar.prv_getVariable());
        });
        loc_viewVariableTree.val(JSON.stringify(varTree, null, 4));
    };
    var loc_out = {preInit: function (requestInfo) {
        loc_contextVariableGroup = node_createContextVariablesGroup(loc_env.getContext(), undefined, function (request) {
            loc_updateView(request);
        });
        _.each(loc_env.getContext().getElementsName(), function (eleName, index) {
            loc_contextVariableGroup.addVariable(node_createContextVariableInfo(eleName));
        });
    }, initViews: function (requestInfo) {
        loc_view = $("<div/>");
        loc_viewData = $("<textarea rows=\"15\" cols=\"150\" id=\"aboutDescription\" style=\"resize: none;\" data-role=\"none\"></textarea>");
        loc_viewVariableTree = $("<textarea rows=\"15\" cols=\"150\" id=\"aboutDescription\" style=\"resize: none;\" data-role=\"none\"></textarea>");
        loc_view.append(loc_viewData);
        loc_view.append(loc_viewVariableTree);
        return loc_view;
    }, postInit: function (requestInfo) {
        loc_updateView(requestInfo);
    }};
    return loc_out;
}

}, {"loadPattern":"file"
});
