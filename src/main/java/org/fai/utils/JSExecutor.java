package org.fai.utils;


import java.util.Arrays;

import org.fai.driver.DriverManager;
import org.fai.reports.ExtentLogger;
import org.fai.reports.FrameworkLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public final class JSExecutor {

	private JSExecutor() {};

	private static JavascriptExecutor js;

	public static void click(WebElement element) {
		FrameworkLogger.logInfo("Start of Javascript Click Method on webElement " +element );
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("arguments[0].click();", element);
		FrameworkLogger.logInfo("End of Javascript Click Method");
	}
	public static String getTextJS(WebElement element) {
		FrameworkLogger.logInfo("Start of Javascript get text Method on webElement " +element );
		js = (JavascriptExecutor) DriverManager.getDriver();
		String text= (String) js.executeScript("return arguments[0].innerText;",element);
		return text;
	}
	public static void scrollPage() throws InterruptedException {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			
	}
	public static String getAttribiteJS(WebElement el, String attribute) {
		js = (JavascriptExecutor) DriverManager.getDriver();
		String attributeValue= (String) js.executeScript("return document.getElementById('el').getAttribute('attribute');");
		return attributeValue;
		
	}
	public static void scrollByPixel(String x, String y) {
		js = (JavascriptExecutor) DriverManager.getDriver();
		js.executeScript("window.scrollBy("+x+","+y+")");
	}
	public static void scrolltoView(By by) throws InterruptedException {
		WebElement element = DriverManager.getDriver().findElement(by);
	((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		ExtentLogger.pass("Scroll to view "+element,true);
		Thread.sleep(500); 
	}
	public static void mouseHover(By by) {
		WebElement element = DriverManager.getDriver().findElement(by);
		 String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor)DriverManager.getDriver()).executeScript(mouseOverScript,element);
	}
	public static void setAttributValue(By by, String text) {
		
		WebElement element = DriverManager.getDriver().findElement(by);
		
		((JavascriptExecutor)DriverManager.getDriver()).executeScript("arguments[0].setAttribute('value','"+ text +"')", element);
		((JavascriptExecutor)DriverManager.getDriver()).executeScript("arguments[0].dispatchEvent(new Event('change'))", element);		
	}
}
