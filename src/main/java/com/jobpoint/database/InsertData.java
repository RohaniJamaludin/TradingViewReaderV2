package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InsertData {
	public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_URL = "jdbc:derby:tradingdb;create=true";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		//Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(JDBC_URL);
		connection.createStatement().execute("insert into trading (product_id, action, price, date, time, isenter, pair_id) values "
				+ "(1, 'sell' , '2.00', '01-01-2017', '00:00', 'true', 1), "
				+ "(1, 'buy' , '1.00', '01-01-2017', '00:02', 'false', 1),"
				+ "(1, 'buy' , '1.00', '01-01-2017', '00:02', 'true', 2), "
				+ "(1, 'sell' , '3.00', '01-01-2017', '00:07', 'false', 2),"
				+ "(1, 'sell' , '3.00', '01-01-2017', '00:07', 'true', 3)"
				+ " ");
		
		System.out.println("records successfully inserted ....");
	}

}
