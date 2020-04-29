package com.nosliw.data.core.system;

import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPSystemFolderUtility {

	public static String getApplicationLibFolder(){ return HAPSystemUtility.getApplicationResourceLibFolder(); }
	public static String getTagDefinitionFolder(){  return getApplicationLibFolder() + "uitag/";   }
	public static String getActivityPluginFolder(){  return getApplicationLibFolder() + "activity/";   }
	public static String getUIModuleDecorationFolder(){  return getApplicationLibFolder() + "uimoduledecoration/";  }
	public static String getUIAppConfigureFolder(){  return getApplicationLibFolder() + "uiappconfigure/";  }

	public static String getJSLibraryFolder() {   return HAPSystemFolderUtility.getJSFolder() + "libresources/";   }
	public static String getApplicationDataFolder(){  return HAPSystemUtility.getApplicationResourceDataFolder();  }
	public static String getProcessFolder(){  return getApplicationDataFolder() + "process/";   }

	public static String getJSFolder(){  return HAPSystemUtility.getJSFolder();  }
	public static String getNosliwJSFolder(String lib){  return getJSFolder()+"libresources/nosliw/"+lib+"/";  }
	public static String getUIPageFolder(){  return getApplicationDataFolder() + "page/";  }
	public static String getUIModuleFolder(){  return getApplicationDataFolder() + "uimodule/";  }
	public static String getMiniAppFolder(){  return getApplicationDataFolder() + "miniapp/";  }
	public static String getCronJobFolder(){  return getApplicationDataFolder() + "cronjob/";  }
	public static String getTemplateFolder(){  return getApplicationDataFolder() + "template/";  }
	public static String getExpressionFolder(){  return getApplicationDataFolder() + "expression/";  }
	public static String getScriptGroupFolder(){  return getApplicationDataFolder() + "script/";  }

	public static String getResourceTempFileFolder(){  return HAPSystemUtility.getJSTempFolder() + "resources/";  }
	public static String getJSLibrayrTempFolder() {    return  HAPSystemUtility.getJSTempFolder() + "libs/";  }
	
	public static String getTempFolder(){		return HAPSystemUtility.getTempFolder();	}

	public static String getScriptExportFolder(){  return HAPSystemFolderUtility.getTempFolder()+"scriptexport/scripts/";  }
	public static String getCurrentScriptExportFolder(){  return HAPSystemFolderUtility.getScriptExportFolder() + HAPRuntimeEnvironment.id + "/";  }
	public static String getTaskLogFolder(){  return HAPSystemFolderUtility.getTempFolder()+"tasklog/";  }
	public static String getCronJobInstanceFolder(){  return HAPSystemFolderUtility.getTempFolder()+"cronjob/";  }
	public static String getTemplateExportFolder(){  return HAPSystemFolderUtility.getTempFolder()+"templateexport/";  }
	public static String getCurrentTemplateExportFolder(){  return HAPSystemFolderUtility.getTemplateExportFolder() + HAPRuntimeEnvironment.id + "/";  }
	
}
