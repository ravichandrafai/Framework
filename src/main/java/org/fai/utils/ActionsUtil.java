package org.fai.utils;

import org.fai.driver.DriverManager;
import org.fai.reports.ExtentLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class ActionsUtil {

	
	private static Actions action;
	
	public static void doubleClick(By by) {
		
		WebElement element = DriverManager.getDriver().findElement(by);
		action= new Actions(DriverManager.getDriver());
		action.doubleClick(element).perform();;
	}
	public static void dragAndDrop(By sourceBy, By destinationBy) {
			action= new Actions(DriverManager.getDriver());
		//WebElement on which drag and drop operation needs to be performed
			
		WebElement fromElement = DriverManager.getDriver().findElement(sourceBy);

		//WebElement to which the above object is dropped
		WebElement toElement = DriverManager.getDriver().findElement(destinationBy);

		//Creating object of Actions class to build composite actions
		Actions builder = new Actions(DriverManager.getDriver());

		//Building a drag and drop action
		Action dragAndDrop = builder.clickAndHold(fromElement)
		.moveToElement(toElement)
		.release(toElement)
		.build();

		//Performing the drag and drop action
		dragAndDrop.perform();
		
	}
	public static void moveToElement(By by) {
		WebElement element = DriverManager.getDriver().findElement(by);
		Actions actions = new Actions(DriverManager.getDriver());
		actions.moveToElement(element);
		actions.perform();
		ExtentLogger.pass("Moved to element "+by,true);
	}
	public static void mouseHover(By by) {
		WebElement element = DriverManager.getDriver().findElement(by);
		action.moveToElement(element).build().perform();
	}
	public static void scroll() {
		Actions actions = new Actions(DriverManager.getDriver());
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
		actions.keyUp(Keys.CONTROL);
		actions.keyUp(Keys.END);
		ExtentLogger.pass("Scroll",true);
	}
	public static void clickEnter() {
		Actions actions = new Actions(DriverManager.getDriver());
		actions.sendKeys(Keys.ENTER).perform();
		
	}
	public static void moveToEleAndClick(By by) {
		WebElement elt= DriverManager.getDriver().findElement(by);
		Actions action = new Actions(DriverManager.getDriver());
		action.moveToElement(elt).click().build().perform();
		ExtentLogger.pass("Click Element "+by,true);
		
	}
	public static void scrollUP(By by) {
		
		WebElement elt= DriverManager.getDriver().findElement(by);
		int	deltaY= elt.getRect().y;
		new Actions(DriverManager.getDriver())
        .scrollByAmount(0, -deltaY)
        .perform();
		
	}
	public static void scrollUP(WebElement element) {
		
		int	deltaY= element.getRect().y;
		new Actions(DriverManager.getDriver())
        .scrollByAmount(0, -deltaY)
        .perform();
		
	}
	public static void scrollDown(By by) {
		
		WebElement elt= DriverManager.getDriver().findElement(by);
		int	deltaY= elt.getRect().y;
		new Actions(DriverManager.getDriver())
        .scrollByAmount(0, deltaY)
        .perform();
		
	}
	public static void sendText(By by,String text) {
		WebElement element = DriverManager.getDriver().findElement(by);
		Actions actions = new Actions(DriverManager.getDriver());
		actions.moveToElement(element).sendKeys(text).perform();
		ExtentLogger.pass("Entered text "+text,true);
	}
}
