package org.fai.listeners;

import org.fai.enums.PropertyEnums;
import org.fai.reports.FrameworkLogger;
import org.fai.utils.ReadProperties;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedCases implements IRetryAnalyzer {

	private int count=0;
	private int maxRetries =1;

	@Override
	public boolean retry(ITestResult result) {
		boolean value = false;
		FrameworkLogger.logTrace("inside retry method of RetryFailedCases");

			if(ReadProperties.get(PropertyEnums.RETRYFAILEDCASES).equalsIgnoreCase("yes")) {
				value = count<maxRetries;
				count++;
			}
			FrameworkLogger.logTrace("end of retry method of RetryFailedCases");
		return value;
	
	}
}