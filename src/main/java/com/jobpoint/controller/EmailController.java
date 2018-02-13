package com.jobpoint.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailController {
	
	private static final String COMMA_DELIMITER = ",";

	public static void main(String[] args) {
		EmailController emailController = new EmailController();
		emailController.send("rohanijamaludin@gmail.com", "Testing" , "Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
	}

	public void send(String email, String subject, String messageBody) {
		final String username = "jp.tradingview@gmail.com";
		final String password = "jp_tv@8872";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("jp.tradingview@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(messageBody);

			Transport.send(message);

			System.out.println("Email send!!");

		} catch (MessagingException e) {
			System.out.println(e);
			//throw new RuntimeException(e);
		}
	}
	
	public List<String> handleEmail(String emailText){
		List<String> emailList = new ArrayList<String>();
		
		if(emailText != null && !emailText.equals("")) {
			emailText = emailText.replaceAll("\\s", "");
			String[] urlParams = emailText.split(COMMA_DELIMITER);
			if(urlParams.length > 0) {
				for(String uParam : urlParams) {
					if(!uParam.equals("")) {
						emailList.add(uParam);
					}
				}
			}
		}
		
		return emailList;
	}
}
