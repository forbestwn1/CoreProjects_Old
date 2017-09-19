package com.nosliw.data.core.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataTypeFamily;
import com.nosliw.data.core.HAPDataTypeHelper;
import com.nosliw.data.core.HAPDataTypeId;
import com.nosliw.data.core.HAPDataTypeOperation;
import com.nosliw.data.core.HAPRelationship;
import com.nosliw.data.core.criteria.HAPCriteriaParser;
import com.nosliw.data.core.criteria.HAPDataTypeCriteria;
import com.nosliw.data.core.criteria.HAPDataTypeCriteriaAny;
import com.nosliw.data.core.criteria.HAPDataTypeCriteriaExpression;
import com.nosliw.data.core.criteria.HAPDataTypeCriteriaId;
import com.nosliw.data.core.criteria.HAPDataTypeCriteriaIds;
import com.nosliw.data.core.criteria.HAPDataTypeSubCriteriaGroup;
import com.nosliw.data.core.expression.HAPExpressionDefinition;
import com.nosliw.data.core.expression.HAPMatcher;
import com.nosliw.data.core.expression.HAPMatchers;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPDataTypeHelperImp implements HAPDataTypeHelper{

	private HAPDataAccessDataType m_dataAccess = null;
	private HAPRuntimeEnvironment m_runtimeEnv;
	
	public HAPDataTypeHelperImp(HAPRuntimeEnvironment runtimeEnv, HAPDataAccessDataType dataAccess){
		this.m_dataAccess = dataAccess;
		this.m_runtimeEnv = runtimeEnv;
	}
	
	@Override
	public HAPDataTypeId getTrunkDataType(HAPDataTypeCriteria criteria) {
		List<HAPDataTypeId> dataTypeIds = new ArrayList<HAPDataTypeId>(criteria.getValidDataTypeId(this));
		HAPDataTypeId firstDataTypeId = dataTypeIds.get(0);
		HAPDataTypeFamily firstDataTypeFamily = this.m_dataAccess.getDataTypeFamily(firstDataTypeId);
		
		List<HAPRelationship> candidates = new ArrayList<HAPRelationship>();
		
		Set<HAPRelationship> fistRelationships = (Set<HAPRelationship>)firstDataTypeFamily.getRelationships();
		for(HAPRelationship firstRelationship : fistRelationships){
			boolean isCandidate = true;
			for(int i=1; i<dataTypeIds.size(); i++){
				HAPDataTypeId otherDataTypeId = dataTypeIds.get(i);
				if(this.convertable(otherDataTypeId, firstRelationship.getTarget())==null){
					isCandidate = false;
					break;
				}
			}
			if(isCandidate)   candidates.add(firstRelationship);
		}
		
		if(candidates.size()==0)  return null;
		else if(candidates.size()==1)  return candidates.get(0).getTarget();
		else{
			HAPDataTypeId out = candidates.get(0).getTarget();
			for(int i=1; i<candidates.size(); i++){
				HAPDataTypeId candidateTarget = candidates.get(i).getTarget();
				if(this.convertable(out, candidateTarget)!=null){
					
				}
				else if(this.convertable(candidateTarget, out)!=null){
					out = candidateTarget;
				}
				else{
					return null;
				}
			}
			return out;
		}
	}

	@Override
	public HAPDataTypeCriteria looseCriteria(HAPDataTypeCriteria criteria) {
		HAPDataTypeCriteria out = null;
		
//		Set<HAPDataTypeId> dataTypeIds = criteria.getValidDataTypeId(this);
//		Set<HAPDataTypeId> normalizedDataTypeIds = this.normalize(dataTypeIds);
//		
//		if(normalizedDataTypeIds.size()==1){
//			//one element, use range
//			out = new HAPDataTypeCriteriaRange(normalizedDataTypeIds.iterator().next(), null);
//		}
//		else{
//			//multiple, use or
//			List<HAPDataTypeCriteria> criterias = new ArrayList<HAPDataTypeCriteria>();
//			for(HAPDataTypeId normalizedDataTypeId : normalizedDataTypeIds){
//				criterias.add(new HAPDataTypeCriteriaRange(normalizedDataTypeId, null));
//			}
//			out = new HAPDataTypeCriteriaOr(criterias);
//		}
		return out;
	}

	@Override
	public Set<HAPDataTypeId> normalize(Set<HAPDataTypeId> dataTypeIds1) {
		List<HAPDataTypeId> dataTypeIds = new ArrayList<HAPDataTypeId>(dataTypeIds1);
		Set<HAPDataTypeId> out = new HashSet<HAPDataTypeId>();
		if(dataTypeIds.size()==0){}
		else if(dataTypeIds.size()==1)  out.add(dataTypeIds.get(0));
		else{
			out.addAll(dataTypeIds1);
			Set<HAPDataTypeId> removes = new HashSet<HAPDataTypeId>();
			for(int i=0; i< dataTypeIds.size()-1; i++){
				for(int j=i+1; j<dataTypeIds.size(); j++){
					if(this.convertable(dataTypeIds.get(i), dataTypeIds.get(j))!=null){
						removes.add(dataTypeIds.get(i));
					}
					else if(this.convertable(dataTypeIds.get(j), dataTypeIds.get(i))!=null){
						removes.add(dataTypeIds.get(j));
					}
				}
			}
			out.removeAll(removes);
		}
		return out;
	}

	@Override
	public Set<HAPDataTypeCriteriaId> normalizeCriteria(Set<HAPDataTypeCriteriaId> dataTypeCriteriaIds1){
		List<HAPDataTypeCriteriaId> dataTypeCriteriaIds = new ArrayList<HAPDataTypeCriteriaId>(dataTypeCriteriaIds1);
		Set<HAPDataTypeCriteriaId> out = new HashSet<HAPDataTypeCriteriaId>();
		if(dataTypeCriteriaIds.size()==0){}
		else if(dataTypeCriteriaIds.size()==1)  out.add(dataTypeCriteriaIds.get(0));
		else{
			out.addAll(dataTypeCriteriaIds1);
			Set<HAPDataTypeCriteriaId> removes = new HashSet<HAPDataTypeCriteriaId>();
			for(int i=0; i< dataTypeCriteriaIds.size()-1; i++){
				for(int j=i+1; j<dataTypeCriteriaIds.size(); j++){
					if(this.convertable(dataTypeCriteriaIds.get(i), dataTypeCriteriaIds.get(j))!=null){
						removes.add(dataTypeCriteriaIds.get(i));
					}
					else if(this.convertable(dataTypeCriteriaIds.get(j), dataTypeCriteriaIds.get(i))!=null){
						removes.add(dataTypeCriteriaIds.get(j));
					}
				}
			}
			out.removeAll(removes);
		}
		return out;
	}

	@Override
	public HAPMatchers buildMatchers(HAPDataTypeCriteria from, HAPDataTypeCriteria to){
		return this.convertable(from, to);
	}

	
	@Override
	public HAPMatchers convertable(HAPDataTypeCriteria sourceCriteria, HAPDataTypeCriteria targetCriteria) {
		if(targetCriteria==null){
			if(sourceCriteria==null)  return new HAPMatchers();
			else return null; 
		}
		else if(targetCriteria==HAPDataTypeCriteriaAny.getCriteria()){
			return new HAPMatchers();
		}
		else{
			HAPMatchers out = new HAPMatchers();
			Set<HAPDataTypeCriteriaId> sourceIdCriteriaSet = sourceCriteria.getValidDataTypeCriteriaId(this);
			Set<HAPDataTypeCriteriaId> targetIdCriteriaSet = this.normalizeCriteria(targetCriteria.getValidDataTypeCriteriaId(this));
			
			for(HAPDataTypeCriteriaId sourceIdCriteria : sourceIdCriteriaSet){
				boolean match = false;
				for(HAPDataTypeCriteriaId targetIdCriteria : targetIdCriteriaSet){
					HAPMatcher matcher = this.convertableIdCriteria(sourceIdCriteria, targetIdCriteria);
					if(matcher!=null){
						match = true;
						out.addMatcher(matcher);
						break;
					}
				}
				if(!match)  return null;
			}
			return out;
		}
	}


	
	@Override
	public HAPDataTypeCriteria and(HAPDataTypeCriteria criteria1, HAPDataTypeCriteria criteria2) {
		if(criteria1==null || criteria2==null){
			return null;
		}
		else if(criteria1.equals(HAPDataTypeCriteriaAny.getCriteria()) && criteria2.equals(HAPDataTypeCriteriaAny.getCriteria())){
			return HAPDataTypeCriteriaAny.getCriteria();
		}
		if(criteria1.equals(HAPDataTypeCriteriaAny.getCriteria())){
			return criteria2;
		}
		else if(criteria2.equals(HAPDataTypeCriteriaAny.getCriteria())){
			return criteria1;
		}
		else{
			Set<HAPDataTypeCriteriaId> dataTypesIdCriteria1 = criteria1.getValidDataTypeCriteriaId(this);
			Set<HAPDataTypeCriteriaId> dataTypesIdCriteria2 = criteria2.getValidDataTypeCriteriaId(this);
			Set<HAPDataTypeCriteriaId> andDataTypeIdCriterias = Sets.intersection(dataTypesIdCriteria1, dataTypesIdCriteria2);
			return this.buildDataTypeCriteria(andDataTypeIdCriterias);
		}
	}

	@Override
	public HAPDataTypeCriteria buildDataTypeCriteria(Set<HAPDataTypeCriteriaId> dataTypeCriterias) {
		HAPDataTypeCriteria out = null;
		if(dataTypeCriterias.size()==1){
			out = dataTypeCriterias.iterator().next();
		}
		else{
			out = new HAPDataTypeCriteriaIds(dataTypeCriterias);
		}
		return out;
	}

	@Override
	public HAPDataTypeCriteria merge(HAPDataTypeCriteria criteria1, HAPDataTypeCriteria criteria2) {
		
		List<HAPDataTypeCriteriaId> criterias1 = new ArrayList(criteria1.getValidDataTypeCriteriaId(this));
		List<HAPDataTypeCriteriaId> leaves1 = this.getLeafCriteriaIds(criterias1);
		
		List<HAPDataTypeCriteriaId> criterias2 = new ArrayList(criteria2.getValidDataTypeCriteriaId(this));
		List<HAPDataTypeCriteriaId> leaves2 = this.getLeafCriteriaIds(criterias2);

		Set<HAPDataTypeCriteriaId> out = new HashSet<HAPDataTypeCriteriaId>();
		for(int i=0; i<leaves1.size(); i++){
			for(int j=0; j<leaves2.size(); j++){
				if(this.convertableIdCriteria(leaves1.get(i), leaves2.get(i))!=null){
					out.add(leaves2.get(i));
					break;
				}
				else if(this.convertableIdCriteria(leaves2.get(i), leaves1.get(i))!=null){
					out.add(leaves1.get(i));
					break;
				}
			}
		}
		if(out.size()==0)   return null;
		else	return new HAPDataTypeCriteriaIds(out);
	}

	
	@Override
	public HAPMatcher convertableIdCriteria(HAPDataTypeCriteriaId sourceIdCriteria, HAPDataTypeCriteriaId targetIdCriteria){
		HAPMatcher out = null;
		HAPRelationship relationship = this.convertable(sourceIdCriteria.getDataTypeId(), targetIdCriteria.getDataTypeId());
		if(relationship!=null){
			out = new HAPMatcher(sourceIdCriteria.getDataTypeId(), relationship);

			HAPDataTypeSubCriteriaGroup sourceSubCriterias = sourceIdCriteria.getSubCriteria();
			HAPDataTypeSubCriteriaGroup targetSubCriterias = targetIdCriteria.getSubCriteria();

			if(targetSubCriterias!=null){
				Set<String> targetSubNames = targetSubCriterias.getSubCriteriaNames();
				for(String targetSubName : targetSubNames){
					HAPMatchers matchers = null;
					HAPDataTypeCriteria targetSubCriteria = targetSubCriterias.getSubCriteria(targetSubName);
					HAPDataTypeCriteria sourceSubCriteria = sourceSubCriterias.getSubCriteria(targetSubName);
					if(sourceSubCriteria==null){
						//no sub criteria by same name in source, fail
						return null;
					}
					else if(targetSubCriteria==HAPDataTypeCriteriaAny.getCriteria()){
						matchers = this.buildMatchers(sourceSubCriteria, targetSubCriteria);
						if(matchers!=null){
							out.addSubMatchers(targetSubName, matchers);
						}
					}
					else if(sourceSubCriteria==HAPDataTypeCriteriaAny.getCriteria()){
						return null;
					}
					else{
						matchers = this.buildMatchers(sourceSubCriteria, targetSubCriteria);
						if(matchers!=null){
							out.addSubMatchers(targetSubName, matchers);
						}
					}
					if(matchers==null)  return null;
				}
			}
		}
		return out;
	}

	
	
	
	private List<HAPDataTypeCriteriaId> getLeafCriteriaIds(List<HAPDataTypeCriteriaId> criteriaIds){
		List<HAPDataTypeCriteriaId> out = new ArrayList(criteriaIds);
		
		int i = 0; 
		while(i<out.size()){
			int j = i+1;
			boolean increasI = true;
			while(j<out.size()){
				if(this.convertableIdCriteria(out.get(j), out.get(i))!=null){
					out.remove(i);
					increasI = false;
					break;
				}
				else if(this.convertableIdCriteria(out.get(i), out.get(j))!=null){
					out.remove(j);
				}
				else{
					j++;
				}
			}
			if(increasI)  i++;
		}
		return out;
	}

	
	@Override
	public Set<HAPRelationship> getRootDataTypeRelationship(HAPDataTypeId dataTypeId){
		List<HAPRelationshipImp> rootRelationships = this.m_dataAccess.getRelationships(dataTypeId, HAPConstant.DATATYPE_RELATIONSHIPTYPE_ROOT);
		return new HashSet<HAPRelationship>(rootRelationships);
	}
	
	@Override
	public HAPDataTypeOperation getOperationInfoByName(HAPDataTypeId dataTypeInfo, String name) {
		return this.m_dataAccess.getDataTypeOperation(dataTypeInfo, name);
	}
	
	@Override
	public Set<HAPDataTypeId> getAllDataTypeInRange(HAPDataTypeId from, HAPDataTypeId to) {
		Set<HAPDataTypeId> out = null;
		Set<HAPDataTypeId> toSet = null;
		Set<HAPDataTypeId> fromSet = null;
		
		if(to!=null){
			toSet = new HashSet<HAPDataTypeId>();
			HAPDataTypePictureImp toPic = this.m_dataAccess.getDataTypePicture(to);
			Set<HAPRelationship> relationships = (Set<HAPRelationship>)toPic.getRelationships();
			for(HAPRelationship relationship : relationships){
				toSet.add(relationship.getTarget());
			}
		}

		if(from!=null){
			fromSet = new HashSet<HAPDataTypeId>();
			HAPDataTypeFamilyImp fromFamily = this.m_dataAccess.getDataTypeFamily(from);
			Set<HAPRelationship> relationships = (Set<HAPRelationship>)fromFamily.getRelationships();
			for(HAPRelationship relationship : relationships){
				fromSet.add(relationship.getSource());
			}
		}
		
		if(to==null)  out = fromSet;
		else if(from==null)  out = toSet;
		else out = Sets.intersection(fromSet, toSet);
		return out;
	}

	@Override
	public HAPRelationship convertable(HAPDataTypeId sourceDataTypeId, HAPDataTypeId targetDataTypeId){
		return this.m_dataAccess.getRelationship(sourceDataTypeId, targetDataTypeId);
	}
	
	@Override
	public Set<HAPDataTypeId> getRootDataTypeId(HAPDataTypeId dataTypeId) {
		Set<HAPRelationship> rootRelationships = this.getRootDataTypeRelationship(dataTypeId);
		Set<HAPDataTypeId> out = new HashSet<HAPDataTypeId>();
		for(HAPRelationship rootRelationship : rootRelationships){
			out.add(rootRelationship.getTarget());
		}
		return out;
	}

	/**
	 * From a set of data type ids, find all the data type ids that :
	 * 	1. they are not convertable to each other
	 *  2. they are able to conver to all the original data types
	 */
	private List<HAPDataTypeId> getLeafDataTypeIds(List<HAPDataTypeId> dataTypeIds){
		List<HAPDataTypeId> out = new ArrayList(dataTypeIds);
		
		int i = 0; 
		while(i<out.size()){
			int j = i+1;
			boolean increasI = true;
			while(j<out.size()){
				if(this.convertable(out.get(j), out.get(i))!=null){
					out.remove(i);
					increasI = false;
					break;
				}
				else if(this.convertable(out.get(i), out.get(j))!=null){
					out.remove(j);
				}
				else{
					j++;
				}
			}
			if(increasI)  i++;
		}
		return out;
	}

	@Override
	public HAPDataTypeCriteriaId getDataTypeIdCriteriaByData(HAPData data) {
		if(data==null){
			int kkkk = 5555;
			kkkk++;
		}
		
		return new HAPDataTypeCriteriaId(data.getDataTypeId(), null);
	}

	@Override
	public void processExpressionCriteria(HAPDataTypeCriteria criteria, Map<String, HAPData> parms) {
		Set<HAPDataTypeCriteriaExpression> expCriterias = new HashSet<HAPDataTypeCriteriaExpression>();
		this.discoverExpressionCriteria(criteria, expCriterias);
		for(HAPDataTypeCriteriaExpression expCriteria : expCriterias){
			String expressionStr = expCriteria.getExpression();
			HAPData expressionResult = (HAPData)this.m_runtimeEnv.getRuntime().executeExpressionSync(expressionStr, parms).getData();
			String criteriaStr = expressionResult.getValue().toString();
			HAPDataTypeCriteria solidCriteria = HAPCriteriaParser.getInstance().parseCriteria(criteriaStr);
			expCriteria.setSolidCriteria(solidCriteria);
		}
	}

	private void discoverExpressionCriteria(HAPDataTypeCriteria criteria, Set<HAPDataTypeCriteriaExpression> expCriterias){
		if(criteria.getType().equals(HAPConstant.DATATYPECRITERIA_TYPE_EXPRESSION)){
			expCriterias.add((HAPDataTypeCriteriaExpression)criteria);
		}
		else{
			List<HAPDataTypeCriteria> children = criteria.getChildren();
			for(HAPDataTypeCriteria child : children){
				this.discoverExpressionCriteria(child, expCriterias);
			}
		}
	}
	
}
