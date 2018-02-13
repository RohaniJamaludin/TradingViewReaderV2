package com.jobpoint.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.jobpoint.object.Alert;
import com.jobpoint.tools.Element;

public class AlertController {
	
	private static final String PRODUCT = "product";
	private static final String ACTION = "action";
	private static final String TRADE = "trade";
	
	private static final int PARAM_NAME_IDX = 0;
	private static final int PARAM_VALUE_IDX = 1;
	
	private static final String AND_DELIMITER = "&";
	private static final String EQUAL_DELIMITER = "=";
	

	public Map<String,List<Alert>> getAlertWindow() {
		
		Map<String, List<Alert>> mapAlert = new HashMap<String,List<Alert>>();
		try {
			Element element = new Element();
			
			if(element.findElementByLocator(By.cssSelector(".tv-alert-single-notification-dialog__message"))) {
				mapAlert = retrieveSingleAlert();
				System.out.println("alert single");
				closeAlert("single");
			}
			
			if(element.findElementByLocator(By.cssSelector(".tv-alerts-multiple-notifications-dialog__table"))) {
				mapAlert = retrieveMultipleAlert();
				System.out.println("alert multiple");
				closeAlert("multiple");
			}
	
			//Test data
			/*List<Alert> alertListHSI = new ArrayList<Alert>();
			Alert alertHSI = new Alert();
			alertHSI.setAction("buy");
			alertHSI.setTrade("enter");
			alertHSI.setProduct("HSIG2018");
			alertListHSI.add(alertHSI);
			*/
		/*	Alert alertHSI2 = new Alert();
			alertHSI2.setAction("sell");
			alertHSI2.setTrade("enter");
			alertHSI2.setProduct("GCG2018");
			alertListHSI.add(alertHSI2);*/
			
			//mapAlert.put(alertHSI.getProduct(), alertListHSI);
			
			
		/*	Alert alertGold = new Alert();
			alertGold.setAction("sell");
			alertGold.setArrow("green");
			alertGold.setProduct("GCH2018");
			alertList.add(alertGold);
			
			Thread.sleep(1000 * 60);*/
			
/*			List<Alert> alertList = new ArrayList<Alert>();
			Alert alertCopper = new Alert();
			alertCopper.setAction("sell");
			alertCopper.setTrade("exit");
			alertCopper.setProduct("HGH2018");
			alertList.add(alertCopper);
			
			mapAlert.put("HGH2018", alertList);
			*/
/*			Alert alertCopper2 = new Alert();
			alertCopper2.setAction("buy");
			alertCopper2.setTrade("exit");
			alertCopper2.setProduct("HGH2018");
			
			alertList.add(alertCopper2);
			*/
/*			
			Alert alertCopper1 = new Alert();
			alertCopper1.setAction("sell");
			alertCopper1.setArrow("red");
			alertCopper1.setProduct("HGH2018");
			alertList.add(alertCopper1);*/
			
			
			
		}catch(Exception e){
			
		}
		return mapAlert;
	}
	
	private Map<String, List<Alert>> retrieveSingleAlert() {
		WebElement alertElement;
		Element element = new Element();
		alertElement = element.getWebElementByLocator(By.cssSelector(".tv-alert-single-notification-dialog__message"));
		Alert alert = handleAlertData(alertElement.getText());
		Map<String, List<Alert>> mapAlert = new HashMap<String,List<Alert>>();
		List<Alert> alertList = new ArrayList<Alert>();
		alertList.add(alert);
		mapAlert.put(alert.getProduct(), alertList);
		return mapAlert;
	}
	
	private Map<String, List<Alert>> retrieveMultipleAlert() {
		List<WebElement> alertElementList;
		//List<Alert> alertList = new ArrayList<Alert>();
		
		Element element = new Element();
		alertElementList = element.getWebElementListByLocator(
				By.cssSelector(".tv-alerts-multiple-notifications-dialog__table-cell-wrap.apply-overflow-tooltip"
						+ ".apply-overflow-tooltip--allow-text"));
		
		System.out.println(alertElementList.size());
		
		Map<String, List<Alert>> mapAlert = new HashMap<String,List<Alert>>();
		for(WebElement alertElement : alertElementList) {
			System.out.println(alertElement.getText());
			Alert alert = handleAlertData(alertElement.getText());
			if(mapAlert.get(alert.getProduct()) != null){
				List<Alert> alertList = mapAlert.get(alert.getProduct());
				alertList.add(alert);
			}else {
				List<Alert> alertList = new ArrayList<Alert>();
				alertList.add(alert);
				mapAlert.put(alert.getProduct(), alertList);
			}
		}
		return mapAlert;
}
	
	private Alert handleAlertData(String alertElement) {
		String product = "";
		String action = "";
		String trade = "";
		Alert alert = new Alert();
		if(alertElement != null && !alertElement.equals("")) {
			String alertNotification = alertElement;
			String[] alertParams = alertNotification.split(AND_DELIMITER);
			if(alertParams.length > 0) {
				for(String aParam : alertParams) {
					String[] param = aParam.split(EQUAL_DELIMITER);
					if(param.length > 0) {
						for(int i = 0; i < param.length; i++) {
							if(PRODUCT.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
								product = param[PARAM_VALUE_IDX];
							}
							if(ACTION.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
								action  = param[PARAM_VALUE_IDX];
							}
							if(TRADE.equalsIgnoreCase(param[PARAM_NAME_IDX])) {
								trade = param[PARAM_VALUE_IDX];
							}
						}
					}
					
				}
			}
			
			alert.setProduct(product);
			alert.setAction(action);
			alert.setTrade(trade);
		}
		
		return alert;
	}
	
	private void closeAlert(String alertType) {
		Element element = new Element();
		
		if(alertType.equals("multiple")) {
			if(element.findElementByLocator(By.cssSelector(".tv-dialog__close.js-dialog__close"))) {
				WebElement buttonX = element.getWebElementByLocator(By.cssSelector(".tv-dialog__close.js-dialog__close"));
				System.out.println("Alert multiple close");
				buttonX.click();
			}
		}
		
		if(alertType.equals("single")){
			if(element.findElementByLocator(By.cssSelector(".js-dialog__action-click.tv-alert-notification-dialog__button--ok"))) {
				WebElement buttonOk = element.getWebElementByLocator(By.cssSelector(".js-dialog__action-click.tv-alert-notification-dialog__button--ok"));
				buttonOk.click();
				System.out.println("Alert single close");
			}
		}
		
	}

}
