package org.fai.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.fai.constants.FrameworkConstants;
import org.fai.enums.PropertyEnums;
import org.fai.exceptions.PropertyFileUsageException;
import org.fai.reports.FrameworkLogger;


/**
 * Read the property file and store it in a HashMap for faster processing.
 * 
 * 
 * 
 */
public class ReadProperties {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private ReadProperties() {

	} 

	private static Properties prop = new Properties();
	private static Map<String , String> CONFIGMAP = new HashMap<>();
	static {
		FrameworkLogger.logTrace("inside static block of ReadProperties");
		try(FileInputStream fis = new FileInputStream(FrameworkConstants.getConfigFilePath())) {
			
			prop.load(fis);

			
			for(Map.Entry<Object,Object> entry: prop.entrySet()) {

				CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()).trim());

			}

		}
		catch(IOException ie) {
			ie.printStackTrace();
			System.exit(0);
		}
		
	}

	
	/**
	 * Receives the {@link org.ravi.enums.ConfigProperties},converts to lowercase , return the corresponding value
	 * for the key from the HashMap
	 * 
	 * @param key To be fetched from property file
	 * @return corresponding value for the requested key if found else {@link PropertyFileUsageException}
	 */
	public static String get(PropertyEnums key)  {
		FrameworkLogger.logTrace("inside get of ReadProperties");
		if(Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.name().toLowerCase()))) {
			throw new PropertyFileUsageException("Property with the name "+key+" is not found. Please check config.prperties" );
		}
		return CONFIGMAP.get(key.name().toLowerCase());
	}
}
