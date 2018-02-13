package com.jobpoint.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jobpoint.database.CRUDTrading;
import com.jobpoint.gui.TradingTableModel;
import com.jobpoint.gui.TradingView;
import com.jobpoint.object.Alert;
import com.jobpoint.object.Application;
import com.jobpoint.object.Price;
import com.jobpoint.object.Product;
import com.jobpoint.object.State;
import com.jobpoint.object.Trading;
import com.jobpoint.object.TradingDetails;

public class TradingController {
	public List<Trading> getAllTrading(){
		CRUDTrading crudTrading = new CRUDTrading();
		List<Trading> tradingList = new ArrayList<Trading>();
		try {
			tradingList = crudTrading.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return tradingList; 
	}
	
	public List<Trading> getAllTradingByForeignId(String object, int foreignId){
		CRUDTrading crudTrading = new CRUDTrading();
		List<Trading> tradingList = new ArrayList<Trading>();
		tradingList = crudTrading.findByForeignId(object, foreignId);
		return tradingList; 
	}
	
	
	public List<TradingDetails> getAllTradingDetailsByForeignId(String object, int foreignId){
		CRUDTrading crudTrading = new CRUDTrading();
		List<TradingDetails> tradingDetailsList = new ArrayList<TradingDetails>();
		tradingDetailsList = crudTrading.findDetailsByForeignId(object, foreignId);
		return tradingDetailsList; 
	}
	
	public Trading getTrading(int id) {
		Trading trading = new Trading();
		
		CRUDTrading crudTrading = new CRUDTrading();
		trading = crudTrading.findById(id);
		
		return trading;
	}
	
	public boolean getLastTradingStatus(String productName) {
		ProductController productController = new ProductController();
		Product product = productController.getProduct(productName);
		StateController stateController = new StateController();
		State state = stateController.getStateByForeignId(product.getId());
		TradingController tradingController = new TradingController();
		Trading trading = tradingController.getTrading(state.getTradingId());
		
		return trading.getIsEnter();
	}
	
	public Trading getLastTradingAction(int productId) {
		StateController stateController = new StateController();
		State state = stateController.getStateByForeignId(productId);
		TradingController tradingController = new TradingController();
		Trading trading = tradingController.getTrading(state.getTradingId());
		
		return trading;
	}
	
	public List<Trading> getTradingByPairId(int pairId, int productId) {
		List<Trading> tradingList = new ArrayList<Trading>();
		
		CRUDTrading crudTrading = new CRUDTrading();
		tradingList = crudTrading.getListByPairId(pairId, productId);
		
		return tradingList;
	}
	
	public String manageTrading(Alert alert, String price, int productId) {
		boolean startTrade = false;
		LocalDateTime now = LocalDateTime.now();
		
		java.util.Date date = new java.util.Date();
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		
		//get state of product
		StateController stateController = new StateController();
		State state = stateController.getStateByForeignId(productId);
		
		int pairId = 0;
		
		//get latest trading
		Trading trading = getTrading(state.getTradingId());
		
		Trading newTrading = new Trading();
		newTrading.setProductId(productId);
		newTrading.setAction(alert.getAction());
		newTrading.setPrice(price);
		newTrading.setDate(new Date(date.getTime()));
		newTrading.setTime(timeFormat.format(now));

		
		if(trading.getIsEnter()) {
			if(trading.getAction().equals("buy") && alert.getAction().equals("sell") && alert.getTrade().equals("exit")) {
				//exit trade
				pairId = trading.getPairId();
				newTrading.setPairId(pairId);
				newTrading.setIsEnter(false);
				startTrade = true;
			}
			else if(trading.getAction().equals("sell") && alert.getAction().equals("buy") && alert.getTrade().equals("exit")) {
				//exit trade
				pairId = trading.getPairId();
				newTrading.setPairId(pairId);
				newTrading.setIsEnter(false);
				startTrade = true;
			}
		}else {
			if(alert.getTrade().equals("enter")) {
				//new alert will enter the trade
				pairId = trading.getPairId() + 1;
				newTrading.setPairId(pairId);
				newTrading.setIsEnter(true);
				startTrade = true;
			}
		}
		
		if(startTrade) {
			if(saveTrading(newTrading, trading, state)) {
				if(newTrading.getIsEnter())
					return "enter";
				else 
					return "exit";
			}
		}
		
		return "";
		
	}
	
	public void exitTradingMarket(int productId) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		
		java.util.Date date = new java.util.Date();
		
		StateController stateController = new StateController();
		State state = stateController.getStateByForeignId(productId);
		
		Trading trading = getTrading(state.getTradingId());
		
		if(trading.getIsEnter()) {
			Trading newTrading = new Trading();
			
			int pairId = trading.getPairId();
			ProductController productController = new ProductController();
			Product product = productController.getProduct(productId);
			Price price = productController.getProductPrice(product.getTab());
			String action = "";
			String priceText = "0";
			
			if(price.getBuyPrice()!= null && price.getSellPrice()!= null) {
				if(trading.getAction().equals("buy")) {
					newTrading.setAction("sell");
					action = "sell";
					newTrading.setPrice(price.getSellPrice());
					priceText = price.getSellPrice();
				}
				else {
					newTrading.setAction("buy");
					action = "buy";
					newTrading.setPrice(price.getBuyPrice());
					priceText = price.getBuyPrice();
				}
				
				newTrading.setProductId(productId);
				newTrading.setDate(new Date(date.getTime()));
				newTrading.setTime(timeFormat.format(now));
				newTrading.setIsEnter(false);
				newTrading.setPairId(pairId);
				
				if(priceText != "") {
					saveTrading(newTrading, trading, state);
					
					PTAController ptaController = new PTAController();
					List<String> urlList = ptaController.handleURL(product.getPtaUrl());
					for(String url : urlList) {
						ptaController.send(url,product, priceText, "exit", action);
					}
					Application application = ApplicationController.getApplication();
					if(application.getIsSendEmail()) {
						String subject = "Product:" + product.getSymbol() + " Action:" + action +
									" Trade:exit Price:" + priceText;
							
						String messageBody = "Product:" + product.getSymbol() + " Action:" + action +
									" Trade:exit Price:" + priceText;
							
						EmailController emailController = new EmailController();
						List<String> emailList = emailController.handleEmail(application.getEmail());
						for(String email : emailList) {
							emailController.send(email, subject, messageBody);
						}
					}
				}
				
			}else {
				System.out.println("Price for product " + product.getName() + "could not be retrieved. Please check the chart");
			}
		}
	}
	
	public void editTrading(int pairId, Product product, int indexRow) {
		List<Trading> tradingList = getTradingByPairId(pairId, product.getId());
		if(tradingList.size()> 0) {
			new TradingView(tradingList, product, indexRow);
		}
			
	}
	
	public boolean saveTrading(Trading trading, Trading lastTrading, State state) {
		
		CRUDTrading crudTrading = new CRUDTrading();

		int id = crudTrading.insert(trading);
		
		if(id>0) {
			state.setTradingId(id);
			
			StateController stateController = new StateController();
			stateController.updateState(state);
			
			TradingTableModel model;
			
			model = ApplicationController.MODELMAP.get(trading.getProductId());
			
			TradingDetails tradingDetails = new TradingDetails();
			
			if(trading.getAction().equals("sell")){
				tradingDetails.setSellAction("sell");
				tradingDetails.setSellPrice(trading.getPrice());
				tradingDetails.setSellDate(trading.getDate());
				tradingDetails.setSellTime(trading.getTime());
				tradingDetails.setPairId(trading.getPairId());
			}else {
				tradingDetails.setBuyAction("buy");
				tradingDetails.setBuyPrice(trading.getPrice());
				tradingDetails.setBuyDate(trading.getDate());
				tradingDetails.setBuyTime(trading.getTime());
				tradingDetails.setPairId(trading.getPairId());
			}
			
			if(trading.getIsEnter()){
				if(trading.getAction().equals("sell")) {
					tradingDetails.setSellStatus(true);
				}else {
					tradingDetails.setBuyStatus(true);
				}
				model.addTrading(tradingDetails);
			}else {
				if(trading.getAction().equals("sell")) {
					tradingDetails.setBuyAction("buy");
					tradingDetails.setBuyPrice(lastTrading.getPrice());
					tradingDetails.setBuyDate(lastTrading.getDate());
					tradingDetails.setBuyTime(lastTrading.getTime());
					tradingDetails.setBuyStatus(true);
					tradingDetails.setSellStatus(false);
					tradingDetails.setPairId(lastTrading.getPairId());
				}else {
					tradingDetails.setSellAction("sell");
					tradingDetails.setSellPrice(lastTrading.getPrice());
					tradingDetails.setSellDate(lastTrading.getDate());
					tradingDetails.setSellTime(lastTrading.getTime());
					tradingDetails.setSellStatus(true);
					tradingDetails.setBuyStatus(false);
					tradingDetails.setPairId(lastTrading.getPairId());
				}
				int rowIndex = model.getRowCount() - 1;
				model.updateTrading(rowIndex, tradingDetails);
			}
			
			return true;
		}
		
		return false;
		
	}
	
	public void updateTrading(Trading trading, TradingDetails tradingDetails, int indexRow) {
		CRUDTrading crudTrading = new CRUDTrading();
		
		if(trading.getId() > 0) {
			crudTrading.update(trading);
		}else {
			Trading enterTrading = crudTrading.findByPairId(tradingDetails.getPairId(), trading.getProductId());
			int id = crudTrading.insert(trading);
			StateController stateController = new StateController();
			State state = stateController.getStateByForeignId(trading.getProductId());
			if(state.getTradingId() == enterTrading.getId()) {
				state.setTradingId(id);
				stateController.updateState(state);
			}
		}
		
		TradingTableModel model;
		model = ApplicationController.MODELMAP.get(trading.getProductId());
		model.updateTrading(indexRow, tradingDetails);
	}

}
