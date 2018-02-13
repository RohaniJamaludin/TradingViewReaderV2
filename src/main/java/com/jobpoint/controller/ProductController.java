package com.jobpoint.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.jobpoint.database.CRUDProduct;
import com.jobpoint.gui.MainWindow;
import com.jobpoint.gui.ProductView;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.object.Price;
import com.jobpoint.object.Product;
import com.jobpoint.object.TradingDetails;
import com.jobpoint.tools.Element;
import com.jobpoint.tools.Time;

public class ProductController {
	
	public List<Product> getAllProduct(){
		CRUDProduct crudProduct = new CRUDProduct();
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = crudProduct.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return productList; 
	}
	
	public List<Product> getActiveProduct(boolean status){
		CRUDProduct crudProduct = new CRUDProduct();
		List<Product> productList = new ArrayList<Product>();
		try {
			productList = crudProduct.findByChartStatus(status);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return productList; 
	}

	public Product getProduct(int id) {
		Product product;
		CRUDProduct crudProduct = new CRUDProduct();
		product = crudProduct.findById(id);
		
		return product;
	}
	
	public Product getProduct(String name) {
		Product product;
		CRUDProduct crudProduct = new CRUDProduct();
		product = crudProduct.findByName(name);
		
		return product;
	}
	
	public int getActionCount(int id, String action, Date dateFrom, Date dateTo) {
		
		CRUDProduct crudProduct = new CRUDProduct();
		
		return crudProduct.count(id, action, dateFrom, dateTo);
		
	}
	
	public void newProduct() {
		new ProductView(true, null,null,0);
	}
	
	public void editProduct(int id, int rowIndex) {
		
		Product product = new Product();
		List<TradingDetails> tradingDetailsList = new ArrayList<TradingDetails>();
		
		
		CRUDProduct crudProduct = new CRUDProduct();
		product = crudProduct.findById(id);
		
		TradingController tradingController = new TradingController();
		tradingDetailsList = tradingController.getAllTradingDetailsByForeignId("product", id);
		new ProductView(false, product, tradingDetailsList, rowIndex);
		
	}
	
	public int saveProduct(Product product) {
		
		CRUDProduct crudProduct = new CRUDProduct();
		product.setTab("");
		int id = crudProduct.insert(product);
		product.setId(id);
		
		StateController stateController = new StateController();
		stateController.saveState(id);
		
		MainWindow.model.addProduct(product);
		
		return id;
	}
	
	public void updateProduct(Product product, int rowIndex) {
		CRUDProduct crudProduct = new CRUDProduct();
		crudProduct.update(product);
		MainWindow.model.updateProduct(rowIndex, product);
	}
	
	public void updateProductChart(boolean status) {
		CRUDProduct crudProduct = new CRUDProduct();
		crudProduct.updateChartStatus(status);
	}
	
	public boolean runProduct(Product product, TradingTableModel model) {
		WebDriver webDriver = DriverController.WEBDRIVER;
		try{
			
			
			String script = "window.open('about:blank','_blank');";
			((JavascriptExecutor)webDriver).executeScript(script);
			
			ArrayList<String> tabs = new ArrayList<String> (webDriver.getWindowHandles());	
			
			System.out.println(tabs.size());
			
			int tabId = tabs.size()-1;
			webDriver.switchTo().window(tabs.get(tabId));
			webDriver.get(product.getChartUrl());
			
			webDriver.switchTo().window(ApplicationController.MAINTAB);
			
			product.setIsChartActive(true);
			product.setTab(tabs.get(tabId));

			CRUDProduct crudProduct = new CRUDProduct();
			crudProduct.update(product);
			
			return true;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "Trading not started");
			return false;
		}
	}
	
	public void stopProduct(int id) {
		
		CRUDProduct crudProduct = new CRUDProduct();
		Product product = crudProduct.findById(id);
		
		WebDriver webDriver = DriverController.WEBDRIVER;
		
		ArrayList<String> tabs = new ArrayList<String> (webDriver.getWindowHandles());	
		boolean isTabOpen = false;
		
		//check if product tab is open
		for(String tab : tabs) {
			if(tab.equals(product.getTab())) {
				isTabOpen = true;
				break;
			}
		}
		
		if(isTabOpen) {
			webDriver.switchTo().window(product.getTab());
			
			String script = "window.open(window.location, '_self').close()";
			((JavascriptExecutor)webDriver).executeScript(script);
		}
		
		webDriver.switchTo().window(ApplicationController.MAINTAB);
		
		product.setIsChartActive(false);
		product.setTab("");
		crudProduct.update(product);
		
	}
	
	public boolean deleteProduct(int id, int rowIndex) {
		CRUDProduct crudProduct = new CRUDProduct();
		StateController stateController = new StateController(); 
		
		int response = JOptionPane.showConfirmDialog(null, 
	            "Do you want to delete selected row?", 
	            "Confirm", JOptionPane.YES_NO_OPTION, //
	            JOptionPane.QUESTION_MESSAGE);
	    if (response == JOptionPane.YES_OPTION) {
	    	if(crudProduct.delete(id)) {
	    		stateController.deleteState(id);
				
				MainWindow.model.removeProduct(rowIndex);
				return true;
			}
	    }  
	    
	    return false;
	}
	
	public Price getProductPrice(String tab) {
		Element element = new Element();
		
		WebDriver webDriver = DriverController.WEBDRIVER;
		webDriver.switchTo().window(tab);
		
		Price price = new Price();
		
		List<WebElement> sellPriceElement = element.getWebElementListByLocator(By.cssSelector("body > div.tv-floating-toolbar.tv-trading-toolbar.ui-draggable > "
				+ "div > div.tv-floating-toolbar__content.js-content > div:nth-child(3) > div > div.tv-trading-toolbar__value.js-value"));
		
		if(sellPriceElement != null && sellPriceElement.size() == 1) {
			price.setSellPrice(sellPriceElement.get(0).getText());
		}
		
		List<WebElement> buyPriceElement = element.getWebElementListByLocator(By.cssSelector("body > div.tv-floating-toolbar.tv-trading-toolbar.ui-draggable > "
				+ "div > div.tv-floating-toolbar__content.js-content > div:nth-child(1) > div > div.tv-trading-toolbar__value.js-value"));

		
		if(buyPriceElement != null && buyPriceElement.size() == 1) {
			price.setBuyPrice(buyPriceElement.get(0).getText());
		}
		
		webDriver.switchTo().window(ApplicationController.MAINTAB);
		
		return price;
	}
	
	public boolean isMarketOpen(Product product) {
		boolean flag = true;
		
		if(product.getIsMarketOpen()) {
		
			//calculateTimeDifference
			
			DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
			String today = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			
			long hoursOfOperation = Time.getTimeDifference("HH:mm", product.getOpenTime(), product.getCloseTime()); //in minutes
			
			String currentTime = new SimpleDateFormat("HH:mm").format(new java.util.Date());
			long elapseTime = Time.getTimeDifference("HH:mm", product.getOpenTime(), currentTime);
			long passMidNightTime = Time.getTimeDifference("HH:mm", currentTime, "00:00");
			long closeAfterMidNightTime =  Time.getTimeDifference("HH:mm", product.getCloseTime() , "00:00");
			boolean isDifferentDay = Time.isDifferentDay("HH:mm", product.getOpenTime(), product.getCloseTime());
			if(isDifferentDay) {
				if(passMidNightTime>closeAfterMidNightTime) {
					today = dayOfWeek.minus(1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);	
				}
			}
			
			
			if(product.getIsMonday()) {
				if(today.equals("Monday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Monday")){
					flag = false;
				}
			}
			
			
			if(product.getIsTuesday()) {
				if(today.equals("Tuesday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Tuesday")){
					flag = false;
				}
			}
			
			
			if(product.getIsWednesday()) {
				if(today.equals("Wednesday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Wednesday")){
					flag = false;
				}
			}
			
			if(product.getIsThursday()) {
				if(today.equals("Thursday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Thursday")){
					flag = false;
				}
			}
			
			
			if(product.getIsFriday()) {
				if(today.equals("Friday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Friday")){
					flag = false;
				}
			}
			
			
			if(product.getIsSaturday()) {
				if(today.equals("Saturday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Saturday")){
					flag = false;
				}
			}
			
			
			if(product.getIsSunday()) {
				if(today.equals("Sunday")){
					if(elapseTime<hoursOfOperation) {
						flag = true;
					}else {
						flag = false;
					}
				}
			}else {
				if(today.equals("Sunday")){
					flag = false;
				}
			}
			
		}
		
		return flag;
	}
	
	public boolean isTradingPause(Product product) {
		boolean flag = false;
		if(product.getIsTradingPause()) {
			DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
			String today = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
			
			long hoursOfOperation = Time.getTimeDifference("HH:mm", product.getPauseTimeFrom(), product.getPauseTimeTo()); //in minutes
			
			String currentTime = new SimpleDateFormat("HH:mm").format(new java.util.Date());
			long elapseTime = Time.getTimeDifference("HH:mm", product.getPauseTimeFrom(), currentTime);
			long passMidNightTime = Time.getTimeDifference("HH:mm", currentTime, "00:00");
			long closeAfterMidNightTime =  Time.getTimeDifference("HH:mm", product.getPauseTimeTo() , "00:00");
			boolean isDifferentDay = Time.isDifferentDay("HH:mm", product.getPauseTimeFrom(), product.getPauseTimeTo());
			if(isDifferentDay) {
				if(passMidNightTime>closeAfterMidNightTime) {
					today = dayOfWeek.minus(1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);	
				}
			}
			
			if(today.equals(product.getPauseDay())) {
				if(elapseTime<hoursOfOperation) {
					flag = true;
				}else {
					flag = false;
				}
				
			}
			return flag;
		}
		return flag;
	}
	
	public boolean isTradingSkip(Product product) {
		boolean flag = false;
		if(isMarketOpen(product)) {
			if(product.getIsSkip()) {
				
				int skipTimeSpan = product.getSkipTimeSpan();
				String startSkipTime = product.getOpenTime();
				
				String currentTime = new SimpleDateFormat("HH:mm").format(new java.util.Date());
				long elapseTime = Time.getTimeDifference("HH:mm", startSkipTime, currentTime);
			
				if(elapseTime<skipTimeSpan) {
					flag = true;
				}else {
					flag = false;
				}
			}
		
		}
		return flag;
	}
	
	public void reloadProduct(Date dateFrom, Date dateTo) {
		
		List<Product> summaryList = getAllProduct();
		MainWindow.model.reloadProduct(summaryList,dateFrom, dateTo);
		
		
	}
}
