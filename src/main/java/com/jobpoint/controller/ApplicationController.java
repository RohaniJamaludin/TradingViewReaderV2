package com.jobpoint.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.jobpoint.database.CRUDApplication;
import com.jobpoint.database.CRUDProduct;
import com.jobpoint.database.CreateDatabase;
import com.jobpoint.gui.MainWindow;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.object.Alert;
import com.jobpoint.object.Application;
import com.jobpoint.object.Price;
import com.jobpoint.object.Product;
import com.jobpoint.tools.Element;

public class ApplicationController{
	
	public static ApplicationController APPLICATIONCONTROLLER; 
	public static DriverController DRIVERCONTROLLER;
	private static boolean ISBROWSERACTIVE;
	public static Thread THREAD;
	public static String MAINTAB;
	public static Map<Integer, TradingTableModel> MODELMAP;
	public static Map<Integer,Integer> SKIPTRADEMAP;
	
	public ApplicationController() {
		APPLICATIONCONTROLLER = this;
		MODELMAP = new HashMap<Integer, TradingTableModel>();
		SKIPTRADEMAP = new HashMap<Integer, Integer>();
		initialize();
	}
	
	private void initialize() {
		try {
			CreateDatabase.startDatabase();
			new MainWindow();
			disableAllChart();
			//start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Application getApplication() {
		
		CRUDApplication crudApplication = new CRUDApplication();
		Application application = crudApplication.find();  
		
		if(application.getId() == 0) {
			application.setTvAccount("");
			application.setTvPassword("");
			application.setIsSendEmail(false);
			application.setEmail("");
			int id = crudApplication.insert(application);
			application.setId(id);
		}
		
		return application;
	}
	
	public int saveApplication(Application application) {
		int id;
		
		CRUDApplication crudApplication = new CRUDApplication();
		id = crudApplication.insert(application);
		return id;
	}
	
	public static void updateApplication(Application application) {
		CRUDApplication crudApplication = new CRUDApplication();
		crudApplication.update(application);
	}
	
	public void start(String userName, String password) {
		
		boolean isLogin = false;
		
		try {
			DRIVERCONTROLLER = new DriverController();
			DRIVERCONTROLLER.start();
			
			MAINTAB = DriverController.WEBDRIVER.getWindowHandle();
			Element element = new Element();
				
			if(element.findElementByLocator(By.xpath("//*[@id='signin-form']/div[1]/div[1]/input"))) {
				if(!loginTradingView(userName, password)) {
						isLogin = false;
						//show message login error
					}
				}
				
			isLogin = true;
				
			if(isLogin) {
				ISBROWSERACTIVE = true;
				run();
			}
				
		}catch(Exception e) {
			//show error message
		}
	}
	
	private void run() {
	
		Runnable myRunnable = new Runnable(){

		    public void run(){
		    	AlertController alertController = new AlertController();
		 		ProductController productController = new ProductController();
		 		WebDriver webDriver = DriverController.WEBDRIVER;
		 		
		 		int sleep = 10;
		 		
		 		while(ISBROWSERACTIVE) {
		 			
		 			Calendar cal = Calendar.getInstance();
		 	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 	        
		 	        if(sdf.format(cal.getTime()).equals("06:00")) {
		 	        	webDriver.navigate().refresh();
		 	        	try {
							Thread.sleep(1000 * 60);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		 	        }
		 			
		 			Map<String, List<Alert>> alertMap = alertController.getAlertWindow();
		 			List<Product> productList = productController.getActiveProduct(true);
		 			
		 			TradingController tradingController = new TradingController();
		 			
		 			for(Product product : productList) {
		 				verifyTabOpen(product, webDriver);
		 				boolean isMarketOpen = productController.isMarketOpen(product);
		 				boolean isTradingPause = productController.isTradingPause(product);
		 				if((isMarketOpen && !isTradingPause)) {
		 					
		 				}else {
		 					tradingController.exitTradingMarket(product.getId());

		 					if( SKIPTRADEMAP.get(product.getId()) == 1) {
		 						SKIPTRADEMAP.put(product.getId(), 0);
		 					}
		 				}
		 			}
		 			
		 			for(Map.Entry<String, List<Alert>> entry : alertMap.entrySet()) {
		                String productName = entry.getKey();
		                List<Alert> alertList = entry.getValue();
		 				System.out.println(productName);
		 				Product product = productController.getProduct(productName);
		 				
		 				boolean isMarketOpen = productController.isMarketOpen(product);
		 				boolean isTradingPause = productController.isTradingPause(product);
		 				
		 				System.out.println("Product: " +product.getName());
		 				System.out.println("Chart Status: " +product.getIsChartActive());
		 				if(product.getIsChartActive()) {
		 				
		 					verifyTabOpen(product, webDriver);

		 					if(isMarketOpen && !isTradingPause) {
		 						
		 						Price price = productController.getProductPrice(product.getTab());
		 						if(price.getBuyPrice()!= null && price.getSellPrice()!= null) {
		 							
		 							int listSize = alertList.size();
		 		
		 							for(int s = 0 ; s < listSize ; s++) {
		 								boolean isEnter = getTradeStatus(productName);
		 								if(isEnter) {
		 									String trade = "exit";
		 									identifyAlert(alertList, trade, price, product);
		 								}else {
		 									String trade = "enter";
		 									identifyAlert(alertList, trade, price, product);
		 								}
		 							}
		 						}
		 						else {
		 							System.out.println("Price for product " + product.getName() + " could not be retrieve. Please check the chart");
		 						}
		 					}
		 				}
		 			}
		 			
		 			try {
						Thread.sleep(1000 * sleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		 		}
		     }
		   };
		   
		   if(THREAD == null)
			   THREAD = new Thread(myRunnable);
		   
		   THREAD.start();
	}
	
	private void trade(Alert alert, String price, Product product) {
		ProductController productController = new ProductController();
		TradingController tradingController = new TradingController();
		
		boolean enableTrade = true;
		
		if(productController.isTradingSkip(product) && SKIPTRADEMAP.get(product.getId()) == 0) {
			SKIPTRADEMAP.put(product.getId(), 1);
			enableTrade = false;
		}
			
		if(enableTrade) {
			String trade = tradingController.manageTrading(alert, price, product.getId());
			
			if(trade != "") {
				PTAController ptaController = new PTAController();
				List<String> urlList = ptaController.handleURL(product.getPtaUrl());
				for(String url : urlList) {
					ptaController.send(url,product, price, trade, alert.getAction());
				}
				Application application = ApplicationController.getApplication();
				if(application.getIsSendEmail()) {
					String subject = "Product:" + product.getSymbol() + " Action:" + alert.getAction() +
							" Trade:" + alert.getTrade() + " Price:" + price;
					
					String messageBody = "Product:" + product.getSymbol() + " Action:" + alert.getAction() +
							" Trade:" + alert.getTrade() + " Price:" + price;
					
					EmailController emailController = new EmailController();
					List<String> emailList = emailController.handleEmail(application.getEmail());
					for(String email : emailList) {
						emailController.send(email, subject, messageBody);
					}
				}
			}
		}
	}
	
	private void identifyAlert(List<Alert> alertList, String trade, Price price, Product product) {
		for(int i = 0; i < alertList.size() ; i++) {
			System.out.println(alertList.get(i).getTrade());
				if(alertList.get(i).getTrade().equals(trade)){
					Alert alert = alertList.get(i);
					String priceText = "0";
					if(alert.getAction().equals("sell")) {
						priceText = price.getSellPrice();
					}else if(alert.getAction().equals("buy")) {
						priceText = price.getBuyPrice();
					}
					
					if(priceText != "") {
						trade(alert, priceText, product);
					}
					alertList.remove(alert);
					break;
				}	
			}
	}
	
	private boolean getTradeStatus(String productName) {
		TradingController tradingController = new TradingController();
		boolean isEnter = tradingController.getLastTradingStatus(productName);
		
		return isEnter;
	}
	
	public void stop() {
		if(THREAD != null) {
			THREAD.interrupt();
			THREAD = null;
		}
		
		ISBROWSERACTIVE = false;
		if(((RemoteWebDriver)DriverController.WEBDRIVER).getSessionId() != null) {
				DRIVERCONTROLLER.close();
		}
	}
	
	private boolean loginTradingView(String userName, String password) {
		
		try {
			WebDriver webDriver = DriverController.WEBDRIVER;
			WebElement usernameElement = webDriver.findElement(
	                By.xpath("//*[@id='signin-form']/div[1]/div[1]/input"));	
			usernameElement.sendKeys(userName);
			
			WebElement passwordElement = webDriver.findElement(
	                By.xpath("//*[@id='signin-form']/div[2]/div[1]/div[1]/input"));
			passwordElement.sendKeys(password);
			passwordElement.sendKeys(Keys.RETURN);
			
			return true;
		}catch(Exception e) {
			System.out.println("login error");
		}
		
		return false;
	}
	
	private void disableAllChart() {
		ProductController productController = new ProductController();
		productController.updateProductChart(false);
	}
	
	public static void verifyTabOpen(Product product, WebDriver webDriver) {
			ArrayList<String> tabs = new ArrayList<String> (webDriver.getWindowHandles());	
			boolean isTabOpen = false;
			
			//check if product tab is open
			for(String tab : tabs) {
				if(tab.equals(product.getTab())) {
					isTabOpen = true;
					break;
				}
			}
			
			//if product tab is closed, then open again the tab
			if(!isTabOpen) {
				String script = "window.open('about:blank','_blank');";
				((JavascriptExecutor)webDriver).executeScript(script);
				
				ArrayList<String> newTabs = new ArrayList<String> (webDriver.getWindowHandles());	
				System.out.println(newTabs.size());
				String tab = newTabs.get(newTabs.size()-1);
				webDriver.switchTo().window(tab);
				webDriver.get(product.getChartUrl());
				
				webDriver.switchTo().window(ApplicationController.MAINTAB);
				
				product.setTab(tab);
				
				CRUDProduct crudProduct = new CRUDProduct();
				crudProduct.update(product);
			}
	}
	
}
