package com.jobpoint.tools;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.jobpoint.controller.DriverController;

public class Element {
	private WebDriver webDriver = DriverController.WEBDRIVER;
	
	public boolean findElementByLocator(By locator) {
		  final long startTime = System.currentTimeMillis();
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		    .withTimeout(30, TimeUnit.SECONDS)
		    .pollingEvery(5, TimeUnit.SECONDS);

		  boolean found = false;

		  int counter = 0;
		  while ( counter < 10 ) {
		   
			  try {
				  wait.until( ExpectedConditions.presenceOfElementLocated(locator));
				  found = true;
				  break;
			  } catch (NoSuchElementException|ElementNotVisibleException|StaleElementReferenceException|TimeoutException e ) {   
				  found = false;
				  //System.out.println("Element not found");
			  }
			  
			  counter++;
		 }
		  
		 return found;
	}
	
	public WebElement getWebElementByLocator(By locator) {
		WebElement webElement = null;
		
		final long startTime = System.currentTimeMillis();
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		    .withTimeout(30, TimeUnit.SECONDS)
		    .pollingEvery(5, TimeUnit.SECONDS);
		  
		  int counter = 0;
		  while ( counter < 10 )  {
			   
			  try {
				  webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
				  break;
			  } catch (NoSuchElementException|ElementNotVisibleException|StaleElementReferenceException|TimeoutException e ) {   
				  //System.out.println("Element not found");
			  }
			  counter++;
		 }
		
		return webElement;
	}
	
	public List<WebElement> getWebElementListByLocator(By locator) {
		List<WebElement> webElementList = null;
		
		final long startTime = System.currentTimeMillis();
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		    .withTimeout(30, TimeUnit.SECONDS)
		    .pollingEvery(5, TimeUnit.SECONDS);
		  
		  int counter = 0;
		  while ( counter < 10 ) {
			   
			  try {
				  webElementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				  break;
			  } catch (NoSuchElementException|ElementNotVisibleException|StaleElementReferenceException|TimeoutException e ) {   
				  //System.out.println("Element not found");
			  }
			  
			  counter++;
		 }
		
		return webElementList;
	}

}
