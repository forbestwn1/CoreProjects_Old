
if(typeof nosliw!='undefined' && nosliw.runtime!=undefined && nosliw.runtime.getResourceService()!=undefined) nosliw.runtime.getResourceService().importResource({"id":{"id":"Decoration_application_header_framework7",
"type":"uiResource"
},
"children":[],
"dependency":{},
"info":{}
}, {"id":"Decoration_application_header_framework7",
"type":"resource",
"context":{"local2Global":{"nosliw_module_application_ui_status":"nosliw_module_application_ui_status___public",
"nosliw_ui_info":"nosliw_ui_info___public"
},
"context":{"element":{"nosliw_module_application_ui_status___public":{"name":"",
"description":"",
"info":{},
"definition":{"type":"node",
"processed":"true",
"child":{"index":{"type":"value",
"processed":"true"
}
}
}
},
"nosliw_module_application_ui_status":{"info":{},
"definition":{"type":"relative",
"processed":"true",
"path":{"rootEleName":"nosliw_module_application_ui_status___public"
},
"parent":"self",
"definition":{"type":"node",
"processed":"true",
"child":{"index":{"type":"value",
"processed":"true"
}
}
}
}
},
"nosliw_ui_info___public":{"name":"",
"description":"",
"info":{},
"definition":{"type":"node",
"processed":"true",
"child":{"id":{"type":"value",
"processed":"true"
},
"title":{"type":"value",
"processed":"true"
}
}
}
},
"nosliw_ui_info":{"info":{},
"definition":{"type":"relative",
"processed":"true",
"path":{"rootEleName":"nosliw_ui_info___public"
},
"parent":"self",
"definition":{"type":"node",
"processed":"true",
"child":{"id":{"type":"value",
"processed":"true"
},
"title":{"type":"value",
"processed":"true"
}
}
}
}
}
}
}
},
"elementEvents":[{"uiId":"2148",
"event":"click",
"function":"transferBack"
},{"uiId":"2152",
"event":"click",
"function":"refresh"
}],
"tagEvents":[],
"attributes":{"nosliwattribute_placeholder":"id:pleaseEmbed"
},
"html":"&lt;div class=&quot;page-content&quot; nosliwid=&quot;2144&quot;&gt;  &lt;div class=&quot;navbar&quot; nosliwid=&quot;2145&quot;&gt;   &lt;div class=&quot;navbar-inner&quot; nosliwid=&quot;2146&quot;&gt;    &lt;div class=&quot;left&quot; nosliwid=&quot;2147&quot;&gt;     &lt;a href=&quot;&quot; class=&quot;link&quot; style=&quot;&quot; nosliwid=&quot;2148&quot;&gt;&lt;span&gt;Back&lt;/span&gt;&lt;/a&gt;    &lt;/div&gt;    &lt;div class=&quot;title&quot; nosliwid=&quot;2149&quot;&gt;    &lt;span nosliwid=&quot;2150&quot;&gt;&lt;/span&gt;   &lt;/div&gt;    &lt;div class=&quot;right&quot; nosliwid=&quot;2151&quot;&gt;     &lt;a href=&quot;&quot; class=&quot;link&quot; nosliwid=&quot;2152&quot;&gt;&lt;span&gt;Refresh&lt;/span&gt;&lt;/a&gt;    &lt;/div&gt;   &lt;/div&gt;  &lt;/div&gt;  &lt;div id=&quot;pleaseEmbed&quot; nosliwid=&quot;2153&quot;&gt;&lt;/div&gt; &lt;/div&gt;",
"constants":{},
"events":{"transferBack":{"name":"transferBack",
"description":"",
"info":{},
"data":{"element":{}
}
},
"refresh":{"name":"refresh",
"description":"",
"info":{},
"data":{"element":{}
}
}
},
"commands":{},
"services":{},
"serviceProviders":{},
"scriptExpressionsInContent":[{"scriptExpressions":{"0":{"definition":"?(nosliw_ui_info.title)?",
"variableNames":["nosliw_ui_info.title"],
"expressions":{}
}
},
"uiId":"2150",
"scriptFunction":function(scriptExpressionData){
	return scriptExpressionData["0"];
} 
,
"scriptExpressionScriptFunction":{"0":function(expressionsData, constantsData, variablesData){
	return variablesData["nosliw_ui_info.title"];
} 

}
}],
"scriptExpressionInAttributes":[{"scriptExpressions":{"1":{"definition":"?(nosliw_module_application_ui_status.index)?==0?'none':'inline'",
"variableNames":["nosliw_module_application_ui_status.index"],
"expressions":{}
}
},
"uiId":"2148",
"attribute":"style",
"scriptFunction":function(scriptExpressionData){
	return "display:"+scriptExpressionData["1"];
} 
,
"scriptExpressionScriptFunction":{"1":function(expressionsData, constantsData, variablesData){
	return variablesData["nosliw_module_application_ui_status.index"]==0?'none':'inline';
} 

}
}],
"scriptExpressionTagAttributes":[],
"uiTags":{},
"script":{ transferBack : function(info, env){ event.preventDefault(); env.trigueNosliwEvent("module_application_transferBack", info.eventData); }, refresh : function(info, env){ event.preventDefault(); env.trigueNosliwEvent("module_application_refresh", info.eventData); }, }
}, {"loadPattern":"file"
});

