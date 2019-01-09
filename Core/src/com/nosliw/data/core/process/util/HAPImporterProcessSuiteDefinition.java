package com.nosliw.data.core.process.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.data.core.process.HAPDefinitionProcessSuite;
import com.nosliw.data.core.process.plugin.HAPManagerActivityPlugin;

public class HAPImporterProcessSuiteDefinition {

	static public List<HAPDefinitionProcessSuite> readProcessDefinitionSuiteFromFolder(String folder, HAPManagerActivityPlugin activityPluginMan){
		List<HAPDefinitionProcessSuite> out = new ArrayList<HAPDefinitionProcessSuite>();
		Set<File> files = HAPFileUtility.getAllFiles(folder);
		for(File file : files){
			if(file.getName().endsWith(".process")){
				try {
					InputStream inputStream = new FileInputStream(file);
					HAPDefinitionProcessSuite taskDefinitionSuite = readProcessSuiteDefinitionFromFile(inputStream, activityPluginMan);
			         out.add(taskDefinitionSuite);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return out;
	}

	static public List<HAPDefinitionProcessSuite> readProcessSuiteFromClassFolder(Class cs, HAPManagerActivityPlugin activityPluginMan){
		final List<HAPDefinitionProcessSuite> out = new ArrayList<HAPDefinitionProcessSuite>();
		try{
			URI uri = cs.getResource("").toURI();
		    try (FileSystem fileSystem = (uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap()) : null)) {
		    	Path path = Paths.get(uri);
				Files.walkFileTree(path, new HashSet(), 1, new SimpleFileVisitor<Path>() { 
		            @Override
		            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		            	if(file.getFileName().toString().endsWith(".process")){
		            		HAPDefinitionProcessSuite taskDefinitionSuite = readProcessSuiteDefinitionFromFile(Files.newInputStream(file), activityPluginMan); 
					         out.add(taskDefinitionSuite);
		            	}
		                return FileVisitResult.CONTINUE;
		            }
		        });
		    }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return out;
	}

	public static HAPDefinitionProcessSuite readProcessSuiteDefinitionFromFile(InputStream inputStream, HAPManagerActivityPlugin activityPluginMan){
		HAPDefinitionProcessSuite suite = null;
		try{
			String content = HAPFileUtility.readFile(inputStream);
			JSONObject contentJson = HAPJsonUtility.newJsonObject(content);
			suite = HAPParserProcessDefinition.parsePocessSuite(contentJson, activityPluginMan);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return suite;
	}
}