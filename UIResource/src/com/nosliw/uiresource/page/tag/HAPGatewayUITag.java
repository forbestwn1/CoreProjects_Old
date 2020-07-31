package com.nosliw.uiresource.page.tag;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.core.criteria.HAPCriteriaUtility;
import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.runtime.js.HAPGatewayImp;

@HAPEntityWithAttribute
public class HAPGatewayUITag extends HAPGatewayImp{

	@HAPAttribute
	final public static String COMMAND_GETDEFAULTTAG = "getDefaultTag";
	@HAPAttribute
	final public static String COMMAND_GETDEFAULTTAG_CRITERIA = "criteria";

	private HAPUITagManager m_uiTagMan;
	
	public HAPGatewayUITag(HAPUITagManager uiTagMan) {
		this.m_uiTagMan = uiTagMan;
	}
	
	@Override
	public HAPServiceData command(String command, JSONObject parms, HAPRuntimeInfo runtimeInfo) {
		HAPServiceData out = null;
		try{
			switch(command){
			case COMMAND_GETDEFAULTTAG:
				HAPUITageQuery query = new HAPUITageQuery(HAPCriteriaUtility.parseCriteria(parms.getString(COMMAND_GETDEFAULTTAG_CRITERIA)));
				HAPUITagQueryResult result = this.m_uiTagMan.getDefaultUITag(query);
				out = this.createSuccessWithObject(result);
				break;
			}
		}
		catch(Exception e){
			out = HAPServiceData.createFailureData(e, "");
			e.printStackTrace();
		}
		return out;
	}

}
