package com.nosliw.data.core.codetable;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.system.HAPSystemFolderUtility;

public class HAPManagerCodeTable {

	public HAPCodeTable getCodeTable(String codeId){
		//read content
		String file = HAPSystemFolderUtility.getStoryFolder()+codeId+".res";
		//parse content
		return parseCodeTable(new JSONObject(HAPFileUtility.readFile(file)));
	}
	
	private HAPCodeTable parseCodeTable(JSONObject codeTableJson){
		HAPCodeTable out = new HAPCodeTable();
		out.buildObject(codeTableJson, HAPSerializationFormat.JSON);
		return out;
	}
	
}
