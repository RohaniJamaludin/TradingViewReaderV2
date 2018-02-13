package com.jobpoint.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverController {
	public static WebDriver WEBDRIVER;
	
	public void start() {
		System.setProperty("webdriver.chrome.driver", "\\src\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=" + System.getProperty("user.home") + "/AppData/Local/Google/Chrome/Trading View/Alert/Default");
		options.addArguments("--start-maximized");
	        
		WEBDRIVER = new ChromeDriver(options);
		WEBDRIVER.manage().window().maximize();
		 
		WEBDRIVER.get("https://www.tradingview.com/#signin");
	}
	
	public void close() {
		try {
			WEBDRIVER.close();
			WEBDRIVER.quit();
		}catch(Exception e){
			System.out.println(e);
			Thread.interrupted();
		}
			
	}

}
