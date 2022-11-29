package org.fai.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fai.constants.FrameworkConstants;
import org.fai.reports.FrameworkLogger;
import org.fai.utils.ExcelUtils;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

public class MethodInterceptor implements IMethodInterceptor{

	@Override
	public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {

		FrameworkLogger.logInfo("inside intercept method of MethodInterceptor");


		List<Map<String, String>> listAllTests;
		List<IMethodInstance> listRunTests = new ArrayList<>();
		try {

			listAllTests= ExcelUtils.getTestDetails(FrameworkConstants.getRunmanagerSheetName());
			for(int i=0;i<listAllTests.size();i++) {
				
				String excelTestcase = listAllTests.get(i).get("testname");
				String execute = listAllTests.get(i).get("execute");
				for(int j=0;j<methods.size();j++) {
					String strtestNGMethod = methods.get(j).getMethod().getMethodName();
					if(excelTestcase.equalsIgnoreCase(strtestNGMethod)&& execute.equalsIgnoreCase("yes")) {
						listRunTests.add(methods.get(j));
						
						break;
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		FrameworkLogger.logTrace("end of intercept method of MethodInterceptor");
		return listRunTests;

	}

}
