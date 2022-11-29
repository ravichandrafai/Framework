package org.fai.reports;

import org.fai.enums.PropertyEnums;
import org.fai.utils.ReadProperties;
import org.fai.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;

public final class ExtentLogger {

	public static void pass(String logMessage) {
		FrameworkLogger.logTrace("inside pass method of ExtentLogger");
		ReportManager.getExtentTest().pass(logMessage);	
	}

	public static void fail(String logMessage) {
		FrameworkLogger.logTrace("inside fail method of ExtentLogger");
		ReportManager.getExtentTest().fail(logMessage);
	}

	public static void skip(String logMessage) {
		FrameworkLogger.logTrace("inside skip method of ExtentLogger");
		ReportManager.getExtentTest().skip(logMessage);
	}
	
	public static void pass(String logMessage, boolean isScreenShotReruired)  {
		if(ReadProperties.get(PropertyEnums.PASSEDCASESSCREENSHOT).equals("yes")&& isScreenShotReruired) {
			ReportManager.getExtentTest().pass(logMessage, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
		}else {
			pass(logMessage);
		}
			
	}
	
	public static void fail(String logMessage,boolean isScreenShotReruired)  {
		if(ReadProperties.get(PropertyEnums.FAILEDCASESSCREENSHOT).equals("yes")&& isScreenShotReruired) {
			ReportManager.getExtentTest().fail(logMessage, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
		}else {
			fail(logMessage);
		}
	}
	
	public static void skip(WebDriver driver,String logMessage,boolean isScreenShotReruired) {
		if(ReadProperties.get(PropertyEnums.SKIPPEDCASESSCREENSHOT).equals("yes")&& isScreenShotReruired) {
			ReportManager.getExtentTest().skip(logMessage, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
		}else {
			skip(logMessage);
		}
	}
	
	
}
