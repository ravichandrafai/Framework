package org.fai.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fai.constants.FrameworkConstants;
import org.fai.reports.FrameworkLogger;
import org.testng.annotations.DataProvider;

/**
 * Holds the data provider for all the test cases in the framework.
 * 
 * 
 */
public final class DataProviderUtil {
	
	private static List<Map<String, String>> listOfAllTests = new ArrayList<>();
	
	/**
	 * Private constructor to avoid external instantiation
	 */
	private DataProviderUtil() {

	}
	
	/**
	 * Acts as a data provider for all the test cases.
	 * Parallel=true indicates that each of the iteration will be ran in parallel.
	 * 
	 * 
	 * @param m {@link java.lang.reflect.Method} holds the information about the testcases at runtime
	 * @return Object[] containing the List. Each index of the list contains HashMap and each of them
	 * contains the test data needed to run the iterations.
	 *  
	 * 
	 * @see org.ravi.tests;
	 * @see org.fai.listeners.AnnotationTransformer
	 */
	
	
	@DataProvider(name="data",parallel=true)
	public static Object[] getTestData(Method m) throws Exception {
		
		FrameworkLogger.logTrace("inside getTestData method of DataProviderUtil");
		String testName = m.getName();
		if(listOfAllTests.isEmpty()) {
		listOfAllTests= ExcelUtils.getTestDetails(FrameworkConstants.getTestDataSheetName());
		}
		
		List<Map<String, String>> listOfExecutableTests= new ArrayList<>();
		
		for(int i=0; i<listOfAllTests.size();i++) {

			if(listOfAllTests.get(i).get("testname").equalsIgnoreCase(testName) && listOfAllTests.get(i).get("execute").equalsIgnoreCase("yes")) {

				listOfExecutableTests.add(listOfAllTests.get(i));
			}

		}
		FrameworkLogger.logTrace("end of getTestData method of DataProviderUtil");
		return listOfExecutableTests.toArray();
	}


}
