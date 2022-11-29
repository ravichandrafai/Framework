package org.fai.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.fai.reports.FrameworkLogger;
import org.fai.utils.DataProviderUtil;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
/**
 * Implements {@link org.testng.IAnnotationTransformer} to leverage certain functionality like updating the annotations of test
 * methods at runtime.
 * <pre>Please make sure to add the listener details in the testng.xml file</pre>
 * 
 * 	
 */
public class AnnotationTransformer implements IAnnotationTransformer{
	/**
	 * Helps in setting the dataprovider, dataprovider class and retry analyser annotation to all the test methods
	 * at run time. 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotations, Class testClass,Constructor testConstructor, Method testMethod) {
		
		FrameworkLogger.logTrace("inside transform method of AnnotationTransformer");
		annotations.setDataProvider("data");
		annotations.setDataProviderClass(DataProviderUtil.class);
		annotations.setRetryAnalyzer(RetryFailedCases.class);
		FrameworkLogger.logTrace("endof transform method of AnnotationTransformer");
		
		
	}
}
