package org.fai.listeners;

import java.util.Arrays;

import org.fai.constants.FrameworkConstants;
import org.fai.reports.ExtentLogger;
import org.fai.reports.FrameworkLogger;
import org.fai.reports.GenerateReport;
import org.fai.utils.ExcelUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
/**
 * Implements {@link org.testng.ITestListener} and {@link org.testng.ISuiteListener} to leverage the abstract methods
 * Mostly used to help in extent report generation
 * 
 * <pre>Please make sure to add the listener details in the testng.xml file</pre>
 * 
 * 
 */
public class ListenerClass implements ITestListener , ISuiteListener{
	
	/**
	 * Initialise the reports with the file name
	 * 
	 */
	public void onStart(ISuite suite) {
			FrameworkLogger.logTrace("inside onStart method of ListenerClass");
			GenerateReport.intiReports();
		
	}
	
	/**
	 * Terminate the reports
	 * @see org.fai.reports.ExtentReport
	 */
	
	@Override
	public void onFinish(ISuite suite) {
			FrameworkLogger.logTrace("inside onFinish method of ListenerClass");
			GenerateReport.flushReports();
	}
	
	
	@Override
	public void onTestStart(ITestResult result) {
		FrameworkLogger.logTrace("inside onTestStart method of ListenerClass");
		GenerateReport.createTest(result.getMethod().getMethodName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		FrameworkLogger.logTrace("inside onTestSuccess method of ListenerClass");
			ExtentLogger.pass(result.getMethod().getMethodName()+ " is passed",true);
			ExcelUtils.updateTestStatus(FrameworkConstants.getTestDataSheetName(), result.getMethod().getMethodName());
		
		
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		FrameworkLogger.logTrace("inside onTestFailure method of ListenerClass");
		ExtentLogger.fail(result.getMethod().getMethodName()+ " is failed",true);
		ExcelUtils.updateTestStatus(FrameworkConstants.getTestDataSheetName(), result.getMethod().getMethodName());
		ExtentLogger.fail(result.getThrowable().toString());
		ExtentLogger.fail(Arrays.toString(result.getThrowable().getStackTrace()));
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		FrameworkLogger.logTrace("inside onTestSkipped method of ListenerClass");
		ExtentLogger.skip(result.getMethod().getMethodName()+ " is skipped");
	}
	
	
	
		
}
