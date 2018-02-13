package com.jobpoint.object;

import java.sql.Date;

public class Trading {
	
	private int id;
	private int product_id;
	private String price;
	private String action;
	private Date date;
	private String time;
	private boolean isEnter;
	private int pair_id;

	public Trading() {
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getProductId() {
		return product_id;
	}
	
	public void setProductId(int product_id) {
		this.product_id = product_id;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean getIsEnter() {
		return isEnter;
	}
	
	public void setIsEnter(boolean isEnter) {
		this.isEnter = isEnter;
	}
	
	public int getPairId() {
		return pair_id;
	}
	
	public void setPairId(int pair_id) {
		this.pair_id = pair_id;
	}

}
