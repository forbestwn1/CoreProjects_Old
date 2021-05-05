package com.nosliw.common.path;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPNamingConversionUtility;

public class HAPComplexPath {

	private String m_fullName;

	private HAPPath m_path;
	
	private String m_rootName;
	
	public HAPComplexPath(String rootName, HAPPath path){
		this.m_rootName = rootName;
		this.m_path = path;
		this.m_fullName = HAPNamingConversionUtility.cascadePath(this.m_rootName, this.m_path.getPath());
	}
	
	public HAPComplexPath(String rootName, String path){
		this(rootName, new HAPPath(path));
	}
	
	public HAPComplexPath(String fullName){
		if(HAPBasicUtility.isStringNotEmpty(fullName)){
			this.m_fullName = fullName;
			
			int index = this.m_fullName.indexOf(HAPConstantShared.SEPERATOR_PATH);
			if(index==-1){
				//name only
				this.m_rootName = this.m_fullName;
			}
			else{
				this.m_rootName = this.m_fullName.substring(0, index);
				this.m_path = new HAPPath(this.m_fullName.substring(index+1));
			}
		}
	}
	
	public String getRootName(){	return this.m_rootName; 	}
	
	public HAPPath getPath() {   return this.m_path;    }
	
	public String getPathStr(){
		if(this.m_path==null)   return null;
		return this.m_path.getPath();
	}

	public String[] getPathSegs(){
		if(this.m_path==null)  return new String[0];
		return this.m_path.getPathSegments();
	}
	
	public String getFullName(){
		return this.m_fullName;
	}

	public HAPComplexPath appendSegment(String segment) {
		return new HAPComplexPath(this.m_rootName, this.m_path.appendSegment(segment));
	}
	
	public HAPComplexPath updateRootName(String name) {   return new HAPComplexPath(name, this.m_path);   }
	
	public HAPComplexPath cloneComplexPath() {
		return new HAPComplexPath(this.m_fullName);
	}
}
