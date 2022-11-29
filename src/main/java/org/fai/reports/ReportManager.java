package org.fai.reports;

import com.aventstack.extentreports.ExtentTest;

public class ReportManager {

		private ReportManager() {
			
		}
		private static ThreadLocal<ExtentTest> et = new ThreadLocal<>();

		public static synchronized ExtentTest getExtentTest() {
			FrameworkLogger.logTrace("inside getExtentTest method of ReportManager");
			ExtentTest extentTest = et.get();
			
			return extentTest;
		}

		public static synchronized void setExtentTest(ExtentTest reportRef) {
			FrameworkLogger.logTrace("inside setExtentTest method of ReportManager");
			et.set(reportRef);
		}

		public static void unload() {
			FrameworkLogger.logTrace("inside unload method of ReportManager");
			et.remove();
		}
}
