package org.fai.driver;

import java.util.Objects;

import org.fai.constants.FrameworkConstants;
import org.fai.reports.FrameworkLogger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 
 * Driver class is responsible for invoking and closing the browsers.
 * 
 * <p>
 * It is also responsible for 
 * setting the driver variable to DriverManager which handles the thread safety for the 
 * webdriver instance.
 * 
 * 
 * 
 * 
 */

public final class Driver {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private Driver() {

	}
	/**
	 * Gets the browser value and initialise the browser based on that
	 * 
	 *
	 * @param browser Value will be passed from {@link org.fai.tests.BaseTest}. Values can be chrome and firefox
	 * 
	 */
	public static WebDriver initDriver(String browser,String mode) {
		FrameworkLogger.logTrace("Inside initDriver method");
		if(Objects.isNull(DriverManager.getDriver())) {
			FrameworkLogger.logInfo("Driver instance is null hence inside init driver");
			if(browser.equalsIgnoreCase("chrome")) {
				FrameworkLogger.logTrace("Inside if browser is chrome method");
				System.setProperty("webdriver.chrome.driver",FrameworkConstants.getChromrDriverPath());
				ChromeOptions option=new ChromeOptions();
				
				if(mode.equals("inconginto")) {
					option.addArguments("--incognito");
				}
				option.setPageLoadStrategy(PageLoadStrategy.NONE);
				DriverManager.setDriver(new ChromeDriver(option));
				DriverManager.getDriver().manage().window().maximize();
				FrameworkLogger.logTrace("End of if browser is chrome");
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				FrameworkLogger.logTrace("Inside if browser is firefox method");
				System.setProperty("webdriver.gecko.driver",FrameworkConstants.getFirefoxDriver());
				DriverManager.setDriver(new FirefoxDriver());
				DriverManager.getDriver().manage().window().maximize();
				FrameworkLogger.logTrace("End of if browser is Firefox");
			}
			//DriverManager.getDriver().get(ReadProperties.get(PropertyEnums.URL));
			FrameworkLogger.logTrace("end of initDriver method");
			
		}
		return DriverManager.getDriver();
		
	}
	
	/**
	 * Terminates the browser instance.
	 * Sets the threadlocal to default value, i.e null.
	 *
	 */

	public static void quitDriver() {
		if(Objects.nonNull(DriverManager.getDriver())){
			FrameworkLogger.logTrace("inside quitDriver method");
			DriverManager.getDriver().quit();
			DriverManager.unload();
			FrameworkLogger.logTrace("end of quitDriver method");
		}


	}
}
