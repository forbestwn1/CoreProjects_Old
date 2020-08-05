package com.nosliw.uiresource.page.tag;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.system.HAPSystemFolderUtility;

public class HAPUITagManager {

	private HAPDataTypeHelper m_dataTypeHelper;
	
	public HAPUITagManager(HAPDataTypeHelper dataTypeHelper) {
		this.m_dataTypeHelper = dataTypeHelper;
	}
	
	public HAPUITagQueryResult getDefaultUITag(HAPUITageQuery query) {
		HAPUITagQueryResult result = null;
		HAPUITagQueryResultSet resultSet = this.queryUITag(query);
		List<HAPUITagQueryResult> items = resultSet.getItems();
		if(items!=null && items.size()>=1) {
			result = items.get(0);
		}
		return result;
	}
	
	public HAPUITagQueryResultSet queryUITag(HAPUITageQuery query) {
		HAPUITagQueryResultSet out = new HAPUITagQueryResultSet();
		Set<HAPDataTypeId> dataTypeIds = query.getDataTypeCriterai().getValidDataTypeId(m_dataTypeHelper);
		HAPDataTypeId dataTypeId = dataTypeIds.iterator().next();
		if(dataTypeId.getName().equals("test.string")) {
			out.addItem(new HAPUITagQueryResult("textinput"));
		}
		return out;
	}
	
	public HAPUITagDefinition getUITagDefinition(HAPUITagId id){
		String fileName = HAPSystemFolderUtility.getTagDefinitionFolder() + id.getId() + ".js";
		File file = new File(fileName);
		
		HAPUITagDefinition out = HAPUITagDefinitionParser.parseFromFile(file);
		out.setSourceFile(file);
		
		return out;
	}
	
}
