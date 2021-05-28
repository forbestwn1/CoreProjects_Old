package com.nosliw.data.core.valuestructure;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

//flat context represent result of flat a context group to context
//one root in context group named Abc will generate two element in context: local Abc and global Abc___categary
//A has local relative parent of A__categary
//Also, we can find global name by local name
//flat context is back compatible with context
@HAPEntityWithAttribute
public class HAPExecutableStructure extends HAPSerializableImp //implements HAPValueStructure
{
/*
	@HAPAttribute
	public static final String CONTEXT = "context";

	@HAPAttribute
	public static String NAMESBYVARID = "namesByVarId";

	@HAPAttribute
	public static String VARIDBYNAME = "varIdByName";
	
	private Map<String, HAPRoot> m_rootById;
	
	public HAPExecutableStructure() {
		this.m_rootById = new LinkedHashMap<String, HAPRoot>();
	}

	@Override
	public HAPRoot getRoot(String localId) {  return this.m_rootById.get(localId); }

	@Override
	public List<HAPRoot> resolveRoot(HAPReferenceRoot rootReference, boolean createIfNotExist) {
		List<HAPRoot> out = new ArrayList<HAPRoot>(); 
		HAPReferenceRootInExecutable exeRootReference = (HAPReferenceRootInExecutable)rootReference;
		String id = exeRootReference.getId();
		String name = exeRootReference.getName();
		if(HAPBasicUtility.isStringNotEmpty(id)) {
			HAPRoot root = this.getRoot(id);
			if(root!=null)   out.add(root);
		}
		else {
			HAPRoot root = this.getRootByName(name);
			if(root!=null)  out.add(root);
		}
		return out;
	}

	public void addRoot(HAPRoot root, Set<String> aliases) {
		String id = root.getLocalId();
		if(this.m_rootById.get(id)!=null)  throw new RuntimeException();  //no overlapping allowed
		this.m_rootById.put(id, root);
		Set<String> allNames = new HashSet<String>();
		if(aliases!=null)   allNames.addAll(aliases);
		if(HAPBasicUtility.isStringNotEmpty(root.getName()))  allNames.add(root.getName());
		this.m_namesByVarId.put(id, allNames);
		for(String name : allNames) {
			this.m_idByName.put(name, id);
		}
	}

	public void addRoot(HAPRoot root) {	this.addRoot(root, null);	}
	
	public HAPRoot getRootByName(String name) {
		String id = this.m_idByName.get(name);
		if(id==null)  return null;
		else return this.m_rootById.get(id);
	}
	
	@Override
	public List<HAPRoot> getAllRoots(){	return new ArrayList<HAPRoot>(this.m_rootById.values());	}
	
	public Set<String> getAllNames(){
		Set<String> out = new HashSet<String>();
		out.addAll(this.m_idByName.keySet());
		return out;
	}
	
	@Override
	public void updateRootName(HAPUpdateName nameUpdate) {
		this.m_context.updateRootName(nameUpdate);
		
		{
			Map<String, String> updated = new LinkedHashMap<String, String>();
			for(String name : this.m_idByName.keySet()) {
				updated.put(updateAliasRootName(name, nameUpdate), nameUpdate.getUpdatedName(this.m_idByName.get(name)));
			}
			this.m_idByName = updated;
		}
		
		{
			Map<String, Set<String>> updated = new LinkedHashMap<String, Set<String>>();
			for(String varId : this.m_namesByVarId.keySet()) {
				Set<String> oldNames = this.m_namesByVarId.get(varId);
				Set<String> updatedNames = new HashSet<String>();
				for(String oldName : oldNames) {
					updatedNames.add(nameUpdate.getUpdatedName(oldName));
				}
				updated.put(varId, updatedNames);
			}
			this.m_namesByVarId = updated;
		}
	}
	
	private String updateAliasRootName(String aliasRootName, HAPUpdateName nameUpdate) {
		HAPComplexPath path = new HAPComplexPath(aliasRootName);
		HAPComplexPath updatedPath = new HAPComplexPath(nameUpdate.getUpdatedName(path.getRootName()), path.getPathStr());
		return updatedPath.getFullName();
	}

	public HAPExecutableStructure getVariableContext() {   
		HAPExecutableStructure out = new HAPExecutableStructure();
		HAPValueStructureDefinitionFlat varContext = this.m_context.getVariableContext();
		for(String rootName : varContext.getRootNames()) {
			out.addRoot(varContext.getRoot(rootName), rootName, this.m_namesByVarId.get(rootName));
		}
		return out;
	}

	public Map<String, Object> getConstantValue(){
		Map<String, Object> out = new LinkedHashMap<String, Object>();
		Map<String, Object> constantsById = HAPUtilityContext.discoverConstantValue(this.m_context);
		for(String id : constantsById.keySet()) {
			Object constantValue = constantsById.get(id);
			for(String alias : this.m_idByName.keySet()) {
				if(id.equals(this.m_idByName.get(alias))) {
					out.put(alias, constantValue);
				}
			}
		}
		return out;
	}
	
	public Set<String> getAliasById(String id){
		Set<String> out = new HashSet<String>();
		for(String alias : this.m_idByName.keySet()) {
			if(id.equals(this.m_idByName.get(alias))) {
				out.add(alias);
			}
		}
		return out;
	}
	
	public HAPValueStructureDefinitionFlat getContext() {  return this.m_context;  }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONTEXT, this.m_context.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(VARIDBYNAME, HAPSerializeManager.getInstance().toStringValue(this.m_idByName, HAPSerializationFormat.JSON));

		Map<String, String> map = new LinkedHashMap<String, String>();
		for(String varId : this.m_namesByVarId.keySet()) {
			map.put(varId, HAPJsonUtility.buildJson(this.m_namesByVarId.get(varId), HAPSerializationFormat.JSON));
		}
		jsonMap.put(NAMESBYVARID, HAPJsonUtility.buildMapJson(map));
	}

	@Override
	public HAPExecutableStructure cloneStructure() {
		HAPExecutableStructure out = new HAPExecutableStructure();
		out.m_idByName.putAll(this.m_idByName);
		for(String varId : this.m_namesByVarId.keySet()) {
			out.m_namesByVarId.put(varId, new HashSet<String>(this.m_namesByVarId.get(varId)));
		}
		for(String varId : this.m_rootById.keySet()) {
			out.m_rootById.put(varId, this.m_rootById.get(varId).cloneRoot());
		}
		return out;
	}
*/
}