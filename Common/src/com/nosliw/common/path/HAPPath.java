package com.nosliw.common.path;

import com.nosliw.common.pattern.HAPNamingConversionUtility;

public class HAPPath {

	private String m_path;
	
	private String[] m_pathSegs = new String[0];

	public HAPPath(String path){
		this.m_path = path;
		this.m_pathSegs = HAPNamingConversionUtility.parsePathSegs(this.m_path);
	}
	
	public String getPath(){
		return this.m_path;
	}

	public String[] getPathSegs(){
		return this.m_pathSegs;
	}
	
	
}
