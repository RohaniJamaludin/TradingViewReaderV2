package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CreateDatabase {
	
	public static void startDatabase() throws SQLException, ClassNotFoundException {
		
		Class.forName(ConfigDatabase.DRIVER);
		Connection connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		List<String[]> sqlQueryList = new ArrayList<String[]>();
		
		String[] sqlQueryApplication = {"Create table application " +
				"(id int not null  GENERATED ALWAYS AS IDENTITY " + 
				"(START WITH 1, INCREMENT BY 1), " +
				"tv_account varchar(100), " +
				"tv_password varchar(30), " +
				"isSendEmail boolean not null, " +
				"email varchar(300), " +
				"PRIMARY KEY (id))", "application"};
		sqlQueryList.add(sqlQueryApplication);
		
		String[] sqlQueryProduct = {"Create table product " +
				"(id int not null  GENERATED ALWAYS AS IDENTITY " + 
				"(START WITH 1, INCREMENT BY 1), " +
				"name varchar(50) not null, " +
				"symbol varchar(10) not null, " +
				"symbol_description varchar(50) not null, " +
				"chart_url varchar(100) not null, " +
				"pta_url varchar(150), " +
				"order_type varchar(20) not null, " +
				"lot int not null, " +
				"isMarketOpen boolean not null, " +
				"isMonday boolean not null, " +
				"isTuesday boolean not null, " +
				"isWednesday boolean not null, " +
				"isThursday boolean not null, " +
				"isFriday boolean not null, " +
				"isSaturday boolean not null, " +
				"isSunday boolean not null, " +
				"isSkip boolean not null, " +
				"skip_time_span int not null, " +
				"open_time varchar(8) not null, " +
				"close_time varchar(8) not null, " +
				"isTradingPause boolean not null, " +
				"pause_day varchar(10) not null, " +
				"pause_time_from varchar(8) not null, " +
				"pause_time_to varchar(8) not null, " +
				"isChartActive boolean not null, " +
				"tab varchar(100) not null, " +
				"PRIMARY KEY (id))", "product"};
		sqlQueryList.add(sqlQueryProduct);
		
		String[] sqlQueryTrading = {"Create table trading " +
				"(id int not null  GENERATED ALWAYS AS IDENTITY " + 
				"(START WITH 1, INCREMENT BY 1), " +
				"product_id int not null, " +
				"action varchar(5) not null, " +
				"price varchar(20) not null, " +
				"date Date not null Default CURRENT_DATE, " +
				"time varchar(8) not null, " +
				"isEnter boolean not null, " +
				"pair_id int not null, " +
				"PRIMARY KEY (id))", "trading"};
		sqlQueryList.add(sqlQueryTrading);
		
		String[] sqlQueryCreateTableState = {"Create table state " +
				"(id int not null  GENERATED ALWAYS AS IDENTITY " + 
				"(START WITH 1, INCREMENT BY 1), " +
				"product_id int not null, " +
				"trading_id int not null, " +
				"PRIMARY KEY (id))", "state"};
		sqlQueryList.add(sqlQueryCreateTableState);
		
		Iterator<String[]> iterator = sqlQueryList.iterator();
		
		while(iterator.hasNext()) {
			String[] iteratorResult = (String[])iterator.next();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet resultSet = databaseMetaData.getTables(null, "APP", iteratorResult[1].toUpperCase(), null);
			if(resultSet.next()) {
				
			}else {
				connection.createStatement().execute(iteratorResult[0]);
			}
		}
	}
}
