package com.jobpoint.object;

public class Application {
	
	private int id;
	private String tvAccount;
	private String tvPassword;
	private boolean isSendEmail;
	private String email;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTvAccount() {
		return tvAccount;
	}
	
	public void setTvAccount(String tvAccount) {
		this.tvAccount = tvAccount;
	}
	
	public String getTvPassword() {
		return tvPassword;
	}
	
	public void setTvPassword(String tvPassword) {
		this.tvPassword = tvPassword;
	}
	
	public boolean getIsSendEmail() {
		return isSendEmail;
	}
	
	public void setIsSendEmail(boolean isSendEmail) {
		this.isSendEmail = isSendEmail;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	

}
