package org.fai.driver;

import org.fai.reports.FrameworkLogger;
import org.openqa.selenium.WebDriver;

/**
 * DriverManager class helps to achieve thread safety for the {@link org.openqa.selenium.WebDriver} instance.
 * 
 *   
 */


public final class DriverManager {
	
	/**
	 * Private constructor to avoid external instantiation
	 */
	private DriverManager() {

	}
	
	
	private static ThreadLocal<WebDriver> dr = new ThreadLocal<>();

	
	/**
	 * Returns the thread safe {@link org.openqa.selenium.WebDriver} instance fetched from ThreadLocal variable.
	 * 
	 */
	public static synchronized WebDriver getDriver() {
		FrameworkLogger.logTrace("inside getDriver method");
		WebDriver driver = dr.get();

		return driver;
	}
	/**
	 * Set the WebDriver instance to thread local variable
	 * 
	 *  @param driverref {@link org.openqa.selenium.WebDriver} instance that needs to saved from Thread safety issues.<p>
	 */
	public  static synchronized void setDriver(WebDriver driverRef) {
		FrameworkLogger.logTrace("inside setDriver method");
		dr.set(driverRef);
	}
	/**
	 * Calling remove method on Threadlocal variable ensures to set the default value to Threadlocal variable.
	 * It is much safer than assigning null value to ThreadLocal variable.
	 * 
	 */
	public static void unload() {
		FrameworkLogger.logTrace("inside unload method");
		dr.remove();
	}
}
