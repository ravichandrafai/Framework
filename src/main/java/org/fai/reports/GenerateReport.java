package org.fai.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.fai.constants.FrameworkConstants;
import org.fai.enums.PropertyEnums;
import org.fai.utils.ReadProperties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public final class GenerateReport {

	private static  ExtentReports extReport;


	private GenerateReport() {

	}

	public static void intiReports(){
		FrameworkLogger.logTrace("inside intiReports method of GenerateReport");
		if(Objects.isNull(extReport)) {
			extReport = new ExtentReports();
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(FrameworkConstants.getextentReportFilePath());
			extReport.attachReporter(sparkReporter);
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setReportName(ReadProperties.get(PropertyEnums.EXTENTREPORTTITILE));
			//sparkReporter.config().setDocumentTitle("Automation execution report");
			//sparkReporter.config().setReportName("Extract Automation");
			sparkReporter.config().setReportName(ReadProperties.get(PropertyEnums.EXTENTREPORTNAME));
			FrameworkLogger.logTrace("end of intiReports method of GenerateReport");
		}
		
	}
	public static void flushReports() {
		FrameworkLogger.logTrace("inside flushReports method of GenerateReport");
		if(Objects.nonNull(extReport)) {
			extReport.flush();
		}
			try {
			ReportManager.unload();
			Desktop.getDesktop().browse(new File(FrameworkConstants.getextentReportFilePath()).toURI());
			}catch(IOException ie) {
				ie.printStackTrace();
			}
			FrameworkLogger.logTrace("end of flushReports method of GenerateReport");
	}

	public static void createTest(String testCaseName) {
		FrameworkLogger.logTrace("inside createTest method of GenerateReport");
			ReportManager.setExtentTest(extReport.createTest(testCaseName));

	}
}
