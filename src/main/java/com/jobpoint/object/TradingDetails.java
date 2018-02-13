package com.jobpoint.object;

import java.sql.Date;

public class TradingDetails {
	
	private int pairId;
	private String sellAction;
	private String sellPrice;
	private Date sellDate;
	private String sellTime;
	private boolean sellStatus;
	private String buyAction;
	private String buyPrice;
	private Date buyDate;
	private String buyTime;
	private boolean buyStatus;
	
	public void setPairId(int pairId) {
		this.pairId = pairId;
	}
	
	public int getPairId() {
		return pairId;
	}
	
	public void setSellAction(String sellAction) {
		this.sellAction = sellAction;
	}
	
	public String getSellAction() {
		return sellAction;
	}
	
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public String getSellPrice() {
		return sellPrice;
	}
	
	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}
	
	public Date getSellDate() {
		return sellDate;
	}
	
	public void setSellTime(String sellTime) {
		this.sellTime = sellTime;
	}
	
	public String getSellTime() {
		return sellTime;
	}
	
	public void setSellStatus(boolean sellStatus) {
		this.sellStatus = sellStatus;
	}
	
	public boolean getSellStatus() {
		return sellStatus;
	}
	
	public void setBuyAction(String buyAction) {
		this.buyAction = buyAction;
	}
	
	public String getBuyAction() {
		return buyAction;
	}
	
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public String getBuyPrice() {
		return buyPrice;
	}
	
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	
	public Date getBuyDate() {
		return buyDate;
	}
	
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}
	
	public String getBuyTime() {
		return buyTime;
	}
	
	public void setBuyStatus(boolean buyStatus) {
		this.buyStatus = buyStatus;
	}
	
	public boolean getBuyStatus() {
		return buyStatus;
	}
	
	
	
}
