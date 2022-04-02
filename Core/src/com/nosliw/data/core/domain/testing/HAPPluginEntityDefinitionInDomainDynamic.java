package com.nosliw.data.core.domain.testing;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.core.domain.HAPDefinitionEntityInDomain;
import com.nosliw.data.core.domain.HAPDomainDefinitionEntity;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.domain.HAPPluginEntityDefinitionInDomainImp;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPPluginEntityDefinitionInDomainDynamic extends HAPPluginEntityDefinitionInDomainImp{

	private boolean m_isComplex;
	
	public HAPPluginEntityDefinitionInDomainDynamic(String entityType, Class<? extends HAPDefinitionEntityInDomain> entityClass, boolean isComplex, HAPRuntimeEnvironment runtimeEnv) {
		super(entityType, entityClass, runtimeEnv);
		this.m_isComplex = isComplex;
	}

	public HAPPluginEntityDefinitionInDomainDynamic(Class<? extends HAPDefinitionEntityInDomain> entityClass, boolean isComplex, HAPRuntimeEnvironment runtimeEnv) {
		super(entityClass, runtimeEnv);
		this.m_isComplex = isComplex;
	}

	@Override
	public boolean isComplexEntity() {	return this.m_isComplex;	}

	@Override
	protected void parseDefinitionContent(HAPIdEntityInDomain entityId, Object obj,
			HAPDomainDefinitionEntity definitionDomain) {
		HAPDefinitionEntityInDomain entity = definitionDomain.getEntityInfo(entityId).getEntity();

		if(obj instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject)obj;
			for(Object key : jsonObj.keySet()) {
				String attrName = (String)key;
				Object entityObj = jsonObj.opt(attrName);
				HAPEntityInfo entityInfo = this.parseEntityInfo(attrName);
				if(entityInfo.isContainer) {
					if(entityInfo.isComplex) {
						parseComplexContainerAttribute(jsonObj, entityId, attrName, entityInfo.entityType, entityInfo.adapterType, entityInfo.containerType, null, definitionDomain);
					}
					else {
						parseSimpleContainerAttribute(jsonObj, entityId, attrName, entityInfo.entityType, entityInfo.adapterType, entityInfo.containerType, definitionDomain);
					}
				}
				else {
					if(entityInfo.isComplex) {
						this.parseComplexEntityAttribute(jsonObj, entityId, attrName, entityInfo.entityType, entityInfo.adapterType, null, definitionDomain);
					}
					else {
						this.parseSimpleEntityAttribute(jsonObj, entityId, attrName, entityInfo.entityType, entityInfo.adapterType, definitionDomain);
					}
				}
			}
		}
		
	}
	
	//name_(none|set|list|container)_entitytype_adapterType
	private HAPEntityInfo parseEntityInfo(String attrName) {
		HAPEntityInfo out = new HAPEntityInfo();

		String str = attrName;
		
		{
			if(HAPBasicUtility.isStringNotEmpty(str)) {
				Pair<String, String> pair = this.parseString(str);
				out.attirbuteName = pair.getLeft();
				str = pair.getRight();
			}
		}

		{
			if(HAPBasicUtility.isStringNotEmpty(str)) {
				Pair<String, String> pair = this.parseString(str);
				String container = pair.getLeft();
				if(container.equals("none"))  out.isContainer = false;
				if(container.equals("container"))  out.isContainer = true;
				else {
					out.isContainer = true;
					out.containerType = container;
				}
				str = pair.getRight();
			}
		}

		{
			if(HAPBasicUtility.isStringNotEmpty(str)) {
				Pair<String, String> pair = this.parseString(str);
				out.entityType = pair.getLeft();
				str = pair.getRight();
			}
		}

		{
			if(HAPBasicUtility.isStringNotEmpty(str)) {
				Pair<String, String> pair = this.parseString(str);
				out.adapterType = pair.getLeft();
				str = pair.getRight();
			}
		}

		out.isComplex = this.getRuntimeEnvironment().getDomainEntityManager().isComplexEntity(out.entityType);
		
		return out;
	}
	
	private Pair<String, String> parseString(String str){
		String seperator = "_";
		int index = str.indexOf(seperator);
		if(index!=-1) {
			return Pair.of(str.substring(0, index), str.substring(index+1));
		}
		else {
			return Pair.of(str, null);
		}
	}
	
	class HAPEntityInfo {
		public String entityType;
		public String attirbuteName;
		public boolean isComplex;
		public boolean isContainer;
		public String containerType;
		public String adapterType;
	}
	
}
