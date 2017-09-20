package com.nosliw.uiresource;

/*
 * a block of javascript
 * it may contain declaration, function definition, or any valid javascript 
 */

public class HAPScript {

	//javascript content
	private String m_jsBlock;
	
	public HAPScript(String js){
		this.m_jsBlock = js;
	}
	
	public String getBlock(){
		return this.m_jsBlock;
	}
	
}