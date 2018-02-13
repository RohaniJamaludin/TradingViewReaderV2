package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jobpoint.object.Trading;
import com.jobpoint.object.TradingDetails;

public class CRUDTrading {
	private Connection connection;
	private Statement statement;
	
	public Trading findById(int id) {
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			Trading trading = new Trading();
			
			String query = "Select * from trading where id = " + id;
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setAction(resultSet.getString("action"));
				trading.setPrice(resultSet.getString("price"));
				trading.setDate(resultSet.getDate("date"));
				trading.setTime(resultSet.getString("time"));
				trading.setIsEnter(resultSet.getBoolean("isEnter"));
				trading.setPairId(resultSet.getInt("pair_id"));
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return trading;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

public Trading findByPairId(int pairId, int productId) {
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from trading where pair_id = " + pairId + " and product_id =" + productId;
			ResultSet resultSet = statement.executeQuery(query);
			
			Trading trading = new Trading();
			
			while(resultSet.next()) {
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setAction(resultSet.getString("action"));
				trading.setPrice(resultSet.getString("price"));
				trading.setDate(resultSet.getDate("date"));
				trading.setTime(resultSet.getString("time"));
				trading.setIsEnter(resultSet.getBoolean("isEnter"));
				trading.setPairId(resultSet.getInt("pair_id"));
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return trading;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
public List<Trading> getListByPairId(int pairId, int productId) {
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from trading where pair_id = " + pairId + " and product_id =" + productId;
			ResultSet resultSet = statement.executeQuery(query);
			
			List<Trading> tradingList = new ArrayList<Trading>();
			
			while(resultSet.next()) {
				Trading trading = new Trading();
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setAction(resultSet.getString("action"));
				trading.setPrice(resultSet.getString("price"));
				trading.setDate(resultSet.getDate("date"));
				trading.setTime(resultSet.getString("time"));
				trading.setIsEnter(resultSet.getBoolean("isEnter"));
				trading.setPairId(resultSet.getInt("pair_id"));
				tradingList.add(trading);
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return tradingList;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<Trading> findByForeignId(String object, int foreignId) {
	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
		
			String query = "Select * from trading where " + object + "_id = " + foreignId;
			ResultSet resultSet = statement.executeQuery(query);
		
			List<Trading> tradingList = new ArrayList<Trading>();
		
			while(resultSet.next()) {
				Trading trading = new Trading();
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setAction(resultSet.getString("action"));
				trading.setPrice(resultSet.getString("price"));
				trading.setDate(resultSet.getDate("date"));
				trading.setTime(resultSet.getString("time"));
				trading.setIsEnter(resultSet.getBoolean("isEnter"));
				trading.setPairId(resultSet.getInt("pair_id"));
			
				tradingList.add(trading);
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
		
			return tradingList;
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	public List<TradingDetails> findDetailsByForeignId(String object, int foreignId) {
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
		
			String query = "Select DISTINCT sell.action as sell_action, sell.price as sell_price, " +
					"   sell.date as sell_date, sell.time as sell_time, sell.isEnter as sell_status, " +
					" 	buy.action as buy_action, buy.price as buy_price, buy.date as buy_date, buy.time as buy_time, " +
					"	buy.isEnter as buy_status, pair.pair_id as pair_id from (Select pair_id from trading where " + object + "_id = " + foreignId + ") as pair " + 
					"	left join (Select * from trading where " + object + "_id = " + foreignId + " and action = 'sell') as sell " + 
					"	on pair.pair_id = sell.pair_id " + 
					"	left join (select * from trading where " + object + "_id = " + foreignId + " and action = 'buy') as buy " + 
					"	on buy.pair_id = pair.pair_id " +
					"	order by pair.pair_id ";
			ResultSet resultSet = statement.executeQuery(query);
			
			List<TradingDetails> tradingDetailsList = new ArrayList<TradingDetails>();
		
			while(resultSet.next()) {
				TradingDetails tradingDetails = new TradingDetails();
				tradingDetails.setPairId(resultSet.getInt("pair_id"));
				tradingDetails.setSellAction(resultSet.getString("sell_action"));
				tradingDetails.setSellPrice(resultSet.getString("sell_price"));
				tradingDetails.setSellDate(resultSet.getDate("sell_date"));
				tradingDetails.setSellTime(resultSet.getString("sell_time"));
				tradingDetails.setSellStatus(resultSet.getBoolean("sell_status"));
				tradingDetails.setBuyAction(resultSet.getString("buy_action"));
				tradingDetails.setBuyPrice(resultSet.getString("buy_price"));
				tradingDetails.setBuyDate(resultSet.getDate("buy_date"));
				tradingDetails.setBuyTime(resultSet.getString("buy_time"));
				tradingDetails.setBuyStatus(resultSet.getBoolean("buy_status"));
			
				tradingDetailsList.add(tradingDetails);
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
		
			return tradingDetailsList;
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return null;
	}


	public List<Trading> findAll() throws SQLException {
		connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		statement = connection.createStatement();
	
		String query = "Select * from trading where 1 = 1";

		ResultSet resultSet;
	
		try {
			resultSet = statement.executeQuery(query);			
			List<Trading> tradingList = new ArrayList<Trading>();
		
			while(resultSet.next()) {
				Trading trading = new Trading();
				trading.setId(resultSet.getInt("id"));
				trading.setProductId(resultSet.getInt("product_id"));
				trading.setAction(resultSet.getString("action"));
				trading.setPrice(resultSet.getString("price"));
				trading.setDate(resultSet.getDate("date"));
				trading.setTime(resultSet.getString("time"));
				trading.setIsEnter(resultSet.getBoolean("isEnter"));
				trading.setPairId(resultSet.getInt("pair_id"));
			
				tradingList.add(trading);
			}
		
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		
			return tradingList;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	public int insert(Trading trading) {
		int id = 0;
	
		String query = "insert into trading (product_id, action, price, date, time, isEnter, pair_id) values " +
						"(?, ?, ?, ?, ?, ?, ?)";

	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);

			PreparedStatement preparedStatement;
		
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, trading.getProductId());
			preparedStatement.setString(2, trading.getAction());
			preparedStatement.setString(3, trading.getPrice());
			preparedStatement.setDate(4, trading.getDate());
			preparedStatement.setString(5, trading.getTime());
			preparedStatement.setBoolean(6, trading.getIsEnter());
			preparedStatement.setInt(7, trading.getPairId());
			
			preparedStatement.executeUpdate();
		
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if(resultSet.next()) {
				id = resultSet.getInt(1);
			}
		
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;	
	}
	
	public void update(Trading trading) {
		String query = "Update trading Set "+ 
				"product_id =  ?, " +
				"action = ?, " +
				"price = ?, " +
				"date = ?," +
				"time = ?, " +
				"isEnter = ?, " +
				"pair_id = ? " +
				"where id = " + trading.getId();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, trading.getProductId());
			preparedStatement.setString(2, trading.getAction());
			preparedStatement.setString(3, trading.getPrice());
			preparedStatement.setDate(4, trading.getDate());
			preparedStatement.setString(5, trading.getTime());
			preparedStatement.setBoolean(6, trading.getIsEnter());
			preparedStatement.setInt(7, trading.getPairId());
			preparedStatement.executeUpdate();

			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
