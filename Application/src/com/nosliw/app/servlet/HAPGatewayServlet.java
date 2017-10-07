package com.nosliw.app.servlet;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.data.core.runtime.js.HAPGatewayOutput;
import com.nosliw.data.core.runtime.js.HAPJSScriptInfo;

@HAPEntityWithAttribute
public class HAPGatewayServlet extends HAPServiceServlet{

	private static final long serialVersionUID = 3449216679929442927L;

	@Override
	protected HAPServiceData processServiceRequest(String gatewayCommand, JSONObject parms) {
		HAPServiceData out = null;

		String[] segs = HAPNamingConversionUtility.parseLevel1(gatewayCommand);
		String gatewayId = segs[0];
		String command = segs[1];
		
		out = this.getRuntimeEnvironment().getGatewayManager().executeGateway(gatewayId, command, parms);

		HAPGatewayOutput output = (HAPGatewayOutput)out.getData();
		for(HAPJSScriptInfo scriptInfo : output.getScripts()){
			if(scriptInfo.isFile()==null){
				String escaptedScript = StringEscapeUtils.escapeJavaScript(scriptInfo.getScript());
				scriptInfo.setScript(escaptedScript);
			}
		}
		
		return out;
	}

}
