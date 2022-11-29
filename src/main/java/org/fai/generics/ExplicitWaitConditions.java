package org.fai.generics;

import java.time.Duration;

import org.fai.constants.FrameworkConstants;
import org.fai.driver.DriverManager;
import org.fai.enums.ExplicitWaitExpextecConditions;
import org.fai.reports.FrameworkLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class ExplicitWaitConditions {
	 
		private ExplicitWaitConditions() {}
	
		public static WebElement performExplicitWait(By by, ExplicitWaitExpextecConditions exConditions) {
			FrameworkLogger.logTrace("performExplicitWait method");
			WebElement element=null;
			
			if(exConditions == ExplicitWaitExpextecConditions.VISIBILE)		{
				FrameworkLogger.logTrace("inside if expected condition is VISIBILE method");
				element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.visibilityOfElementLocated(by));	
				
			}else if(exConditions == ExplicitWaitExpextecConditions.CLICKABLE) {
				FrameworkLogger.logTrace("inside if expected condition is CLICKABLE method");
				element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.elementToBeClickable(by));	
				
			}else if (exConditions == ExplicitWaitExpextecConditions.PRESENSCE) {
				FrameworkLogger.logTrace("inside if expected condition is PRESENSCE method");
				element = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.presenceOfElementLocated(by));	
			}else if (exConditions == ExplicitWaitExpextecConditions.NONE) {
				FrameworkLogger.logTrace("inside if expected condition is NONE method");
				element = DriverManager.getDriver().findElement(by);
			}
			return element;
		}
		public static boolean invisbileOfElement(By by, ExplicitWaitExpextecConditions exConditions) {
			boolean result = false;
			FrameworkLogger.logTrace("inside if expected condition is Invisiblity method");
			result = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.invisibilityOfElementLocated(by));	
		
			return result;
		}
		public static void waitWithWebElemt(WebElement el) {
			new WebDriverWait(DriverManager.getDriver(),Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.visibilityOf(el));
			
		}
		public static void waitforInvisiblity(By by) {
			new WebDriverWait(DriverManager.getDriver(),Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.invisibilityOfElementLocated(by));
		}
		public static void waitFortextToPresent(By by,String text) {
			new WebDriverWait(DriverManager.getDriver(),Duration.ofSeconds(FrameworkConstants.getExplicitWaitTime())).until(ExpectedConditions.textToBe(by, text));
		}

}
