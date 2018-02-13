package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UpdateData {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection(JDBC_URL);
/*		connection.createStatement().execute("Update trading Set  "
				+ "exit_price =  '1280.0',"  
				+ "exit_date = '02/11/2017',"  
				+ "exit_time = '09:30' ," 
				+ "profit = '0' "
				+ "where id = 2802 ");*/
	/*			connection.createStatement().execute("Update trading Set  "
		+ "exit_price =  'n/a',"  
		+ "exit_date = 'n/a',"  
		+ "exit_time = 'n/a' ," 
		+ "profit = 'n/a' "
		+ "where id = 3003 ");*/
/*		connection.createStatement().execute("Update state Set " + 
				"trading_id = 0," + 
				"isEnter = false," + 
				"type = '' ," + 
				"enter_price = '' " + 
				"where id = 1201 ");*/
/*		connection.createStatement().execute("Update state Set " + 
				"trading_id = 3003," + 
				"isEnter = true," + 
				"type = 'buy' ," + 
				"enter_price = '1278.1' " + 
				"where id = 801 ");*/
/*		connection.createStatement().execute("Update trading Set " +
				 "first_bar_enter = '-2.9332',"  +
				 "second_bar_enter = '-4.4282',"  +
				 "third_bar_enter = '-5.7725' "  +
				 "first_bar_exit = 'n/a',"  +
				 "second_bar_exit = 'n/a',"  +
				 "third_bar_exit = 'n/a' "  +
				"where id = 3003 ");*/
	/*			connection.createStatement().execute("Update state Set " + 
		"isEnter = true," + 
		"type = 'sell' ," + 
		"enter_price = '61.78' " + 
		"where id = 103 ");*/
/*		connection.createStatement().execute("Update trading Set " + 
		"product_id = 103 " + 
		"where id = 301 ");*/
				connection.createStatement().execute("Update state Set " + 
						"trading_id = 115 where product_id = 1 ");
		
		if(connection != null) connection.close();
		
		System.out.println("records successfully updated ....");
	}

}
