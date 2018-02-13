package com.jobpoint.controller;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jobpoint.object.Product;


public class PTAController {
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	private static final String COMMA_DELIMITER = ",";
	
	public void send(String urlText, Product product, String price, String trade, String action ) {
		try {
			int order;
			int type;
			
			if(product.getOrderType().equals("Limit")) {
				order = 0;
			}else if(product.getOrderType().equals("Market")){
				order = 2;
			}else if(product.getOrderType().equals("Limit - Market")){
				if(trade.equals("enter")) {
					order = 0;
				}else {
					order = 2;
				}
			}else if(product.getOrderType().equals("Market - Limit")){
				if(trade.equals("enter")) {
					order = 2;
				}else {
					order = 0;
				}
			}else {
				order = 2;
			}
			
			switch(action) {
			case "buy" : type = 0;
			break;
			case "sell" : type = 1;
			break;
			default : type = 0;
			}
			
			String url = urlText + product.getSymbol() +"?price="+price+"&lot_size="+product.getLot()+"&action=0&action_type="+order+"&type="+type;

			URL obj;
			
			obj = new URL(url);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public List<String> handleURL(String urlText) {
		List<String> urlList = new ArrayList<String>();
		if(urlText != null && !urlText.equals("")) {
			urlText = urlText.replaceAll("\\s", "");
			String[] urlParams = urlText.split(COMMA_DELIMITER);
			if(urlParams.length > 0) {
				for(String uParam : urlParams) {
					if(!uParam.equals("")) {
						urlList.add(uParam);
					}
				}
			}
		}
		return urlList;
	}
	
	public static void main( String[] args )
    {
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PTAController ptaController = new PTAController();
					/*List<String> urlList = ptaController.handleURL("http://127.0.0.1:8872/,");
					for(String url : urlList) {
						System.out.println(url);
					}*/
					
					ProductController productController = new ProductController();
					Product product = productController.getProduct(1);
					ptaController.send("http://127.0.0.1:8872/", product, "34562", "exit", "buy");
					
					EmailController emailController = new EmailController();
					emailController.send("rohanijamaludin@gmail.com", "Testing with pta", "Hi, this is testing message");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

}
