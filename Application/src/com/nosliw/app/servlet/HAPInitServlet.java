package com.nosliw.app.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.imp.expression.HAPExpressionImporter;
import com.nosliw.data.core.imp.runtime.js.browser.HAPRuntimeEnvironmentImpBrowser;
import com.nosliw.data.expression.test.HAPExpressionTest;
import com.nosliw.uiresource.HAPUIResourceManager;
import com.nosliw.uiresource.resource.HAPResourceManagerUIResource;
import com.nosliw.uiresource.tag.HAPUITagManager;

public class HAPInitServlet  extends HttpServlet{

	private static final long serialVersionUID = -703775909733982650L;

	public void init() throws ServletException
	   {
			//create runtime
			HAPRuntimeEnvironmentImpBrowser runtimeEnvironment = new HAPRuntimeEnvironmentImpBrowser();

			HAPExpressionImporter.importExpressionSuiteFromClassFolder(HAPExpressionTest.class, runtimeEnvironment.getExpressionManager());

			//set runtime object to context
			this.getServletContext().setAttribute("runtime", runtimeEnvironment);
			
			HAPUIResourceManager uiResourceMan = new HAPUIResourceManager(new HAPUITagManager(), runtimeEnvironment.getExpressionManager(), runtimeEnvironment.getResourceManager(), runtimeEnvironment.getRuntime(), runtimeEnvironment.getDataTypeHelper());
			this.getServletContext().setAttribute("uiResourceManager", uiResourceMan);
			
			runtimeEnvironment.getResourceManager().registerResourceManager(HAPConstant.RUNTIME_RESOURCE_TYPE_UIRESOURCE, new HAPResourceManagerUIResource(uiResourceMan));

//			String file = HAPFileUtility.getFileNameOnClassPath(HAPInitServlet.class, "Example1.res");
//			HAPUIDefinitionUnitResource uiResource = uiResourceMan.addUIResourceDefinition(file);

	   }
}
