package org.fai.constants;

import org.fai.enums.PropertyEnums;
import org.fai.reports.FrameworkLogger;
import org.fai.utils.ReadProperties;

/**
 * Framework Constants holds all the constant values used within the framework. If some value needs to be changed
 * or modified often, then it should be stored in the property files
 * 
 *  
 *
 */

public final class FrameworkConstants {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private FrameworkConstants() {

	}
	
	private static String RESOURCESPATH = System.getProperty("user.dir")+"/src/test/resources";
	private static String CHROMEDRIVERPATH = RESOURCESPATH+"/executables/chromedriver.exe";
	private static String FIREFOXDRIVERPATH = RESOURCESPATH+"/executables/geckodriver.exe";
	private static String CONFIGFILEPATH = RESOURCESPATH+"/config/config.properties";
	private static int EXPLIXITWAITTIME = 45;
	private static String EXTENTREPORTPATH = RESOURCESPATH+"/extent-test-output/";
	private static String EXTENTREPORTFILEPATH ="";
	private static String EXCELPATH = RESOURCESPATH+"/excel/testdata.xlsx";
	private static String RUNMANAGERSHEET = "RUNMANAGER";
	private static String TESTDATASHEET = "DATA";
	private static String TESTRESULTPATH = RESOURCESPATH+"/excel/";
	private static String TESTFILESPATH = System.getProperty("user.dir")+"\\src\\test\\resources\\testFiles\\";
	public static int getExplicitWaitTime() {
		FrameworkLogger.logTrace("Inside getExplicitWaitTime Name");
		return EXPLIXITWAITTIME;
	}

	public static String getConfigFilePath() {
		FrameworkLogger.logTrace("Inside getConfigFilePath Name");
		return CONFIGFILEPATH;
	}

	public static String getChromrDriverPath() {
		FrameworkLogger.logTrace("Inside getChromrDriverPath Name");
		return CHROMEDRIVERPATH;
	}
	
	public static String getFirefoxDriver() {
		FrameworkLogger.logTrace("Inside getFirefoxDriver Name");
		
		return FIREFOXDRIVERPATH;
	}
		
	/**
	 * 
	 *
	 * If Override reports value in the 
	 * property file is no,then the timestamp will be appended
	 */
	private static String createReportPath(){
		FrameworkLogger.logTrace("Inside createReportPath Name");
		if(ReadProperties.get(PropertyEnums.OVERRIDEREPORTS).equalsIgnoreCase("no")) {
			return EXTENTREPORTPATH+"/"+System.currentTimeMillis()+"index.html";
		}
		else {
			return EXTENTREPORTPATH+"/index.html";
		}
		
	}
	
	public static String getextentReportFilePath() {
		FrameworkLogger.logTrace("Inside getextentReportFilePath Name");
		if(EXTENTREPORTFILEPATH.isEmpty()) {
			EXTENTREPORTFILEPATH = createReportPath();
		}
		
			return EXTENTREPORTFILEPATH;
		
		
	}
	/**
	 * Extent Report path where the index.html file will be generated.
	 */
	public static String getExcelFilePath(){
		FrameworkLogger.logTrace("Inside getExcelFilePath Name");
		return EXCELPATH;
	}
	
	public static String getRunmanagerSheetName() {
		FrameworkLogger.logTrace("Inside getRunManagersheet Name");
		return RUNMANAGERSHEET;
	}
	public static String getTestDataSheetName() {
		FrameworkLogger.logTrace("Inside getTestDataSheetName Name");
		return TESTDATASHEET;
	}
	
	public static String getTestResultPath() {
		
		return TESTRESULTPATH;
	}
	public static String getUploadFilePath() {
		
		return TESTFILESPATH;
	}
}
