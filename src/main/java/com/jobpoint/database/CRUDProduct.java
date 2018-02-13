package com.jobpoint.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jobpoint.object.Product;



public class CRUDProduct {
	private Connection connection;
	private Statement statement;
	
	public Product findById(int id) {
		
		Product product = new Product();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			
			
			String query = "Select * from product where id = " + id;
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setSymbol(resultSet.getString("symbol"));
				product.setSymbolDescription(resultSet.getString("symbol_description"));
				product.setChartUrl(resultSet.getString("chart_url"));
				product.setIsMarketOpen(resultSet.getBoolean("isMarketOpen"));
				product.setIsMonday(resultSet.getBoolean("isMonday"));
				product.setIsTuesday(resultSet.getBoolean("isTuesday"));
				product.setIsWednesday(resultSet.getBoolean("isWednesday"));
				product.setIsThursday(resultSet.getBoolean("isThursday"));
				product.setIsFriday(resultSet.getBoolean("isFriday"));
				product.setIsSaturday(resultSet.getBoolean("isSaturday"));
				product.setIsSunday(resultSet.getBoolean("isSunday"));
				product.setIsSkip(resultSet.getBoolean("isSkip"));
				product.setSkipTimeSpan(resultSet.getInt("skip_time_span"));
				product.setOpenTime(resultSet.getString("open_time"));
				product.setCloseTime(resultSet.getString("close_time"));
				product.setIsTradingPause(resultSet.getBoolean("isTradingPause"));
				product.setPauseDay(resultSet.getString("pause_day"));
				product.setPauseTimeFrom(resultSet.getString("pause_time_from"));
				product.setPauseTimeTo(resultSet.getString("pause_time_to"));
				product.setPtaUrl(resultSet.getString("pta_url"));
				product.setOrderType(resultSet.getString("order_type"));
				product.setLot(resultSet.getInt("lot"));
				product.setIsChartActive(resultSet.getBoolean("isChartActive"));
				product.setTab(resultSet.getString("tab"));
			}

			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return product;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<Product> findAll() throws SQLException {
		connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		statement = connection.createStatement();
		
		String query = "Select * from product where 1 = 1";

		ResultSet resultSet;
		
		try {
			resultSet = statement.executeQuery(query);			
			List<Product> productList = new ArrayList<Product>();
			
			while(resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setSymbol(resultSet.getString("symbol"));
				product.setSymbolDescription(resultSet.getString("symbol_description"));
				product.setChartUrl(resultSet.getString("chart_url"));
				product.setIsMarketOpen(resultSet.getBoolean("isMarketOpen"));
				product.setIsMonday(resultSet.getBoolean("isMonday"));
				product.setIsTuesday(resultSet.getBoolean("isTuesday"));
				product.setIsWednesday(resultSet.getBoolean("isWednesday"));
				product.setIsThursday(resultSet.getBoolean("isThursday"));
				product.setIsFriday(resultSet.getBoolean("isFriday"));
				product.setIsSaturday(resultSet.getBoolean("isSaturday"));
				product.setIsSunday(resultSet.getBoolean("isSunday"));
				product.setIsSkip(resultSet.getBoolean("isSkip"));
				product.setSkipTimeSpan(resultSet.getInt("skip_time_span"));
				product.setOpenTime(resultSet.getString("open_time"));
				product.setCloseTime(resultSet.getString("close_time"));
				product.setIsTradingPause(resultSet.getBoolean("isTradingPause"));
				product.setPauseDay(resultSet.getString("pause_day"));
				product.setPauseTimeFrom(resultSet.getString("pause_time_from"));
				product.setPauseTimeTo(resultSet.getString("pause_time_to"));
				product.setPtaUrl(resultSet.getString("pta_url"));
				product.setOrderType(resultSet.getString("order_type"));
				product.setLot(resultSet.getInt("lot"));
				product.setIsChartActive(resultSet.getBoolean("isChartActive"));
				product.setTab(resultSet.getString("tab"));
				
				productList.add(product);
			}
			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return productList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public List<Product> findByChartStatus(boolean status) throws SQLException {
		connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
		statement = connection.createStatement();
		
		String query = "Select * from product where isChartActive = " + true;

		ResultSet resultSet;
		
		try {
			resultSet = statement.executeQuery(query);			
			List<Product> productList = new ArrayList<Product>();
			
			while(resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setSymbol(resultSet.getString("symbol"));
				product.setSymbolDescription(resultSet.getString("symbol_description"));
				product.setChartUrl(resultSet.getString("chart_url"));
				product.setIsMarketOpen(resultSet.getBoolean("isMarketOpen"));
				product.setIsMonday(resultSet.getBoolean("isMonday"));
				product.setIsTuesday(resultSet.getBoolean("isTuesday"));
				product.setIsWednesday(resultSet.getBoolean("isWednesday"));
				product.setIsThursday(resultSet.getBoolean("isThursday"));
				product.setIsFriday(resultSet.getBoolean("isFriday"));
				product.setIsSaturday(resultSet.getBoolean("isSaturday"));
				product.setIsSunday(resultSet.getBoolean("isSunday"));
				product.setIsSkip(resultSet.getBoolean("isSkip"));
				product.setSkipTimeSpan(resultSet.getInt("skip_time_span"));
				product.setOpenTime(resultSet.getString("open_time"));
				product.setCloseTime(resultSet.getString("close_time"));
				product.setIsTradingPause(resultSet.getBoolean("isTradingPause"));
				product.setPauseDay(resultSet.getString("pause_day"));
				product.setPauseTimeFrom(resultSet.getString("pause_time_from"));
				product.setPauseTimeTo(resultSet.getString("pause_time_to"));
				product.setPtaUrl(resultSet.getString("pta_url"));
				product.setOrderType(resultSet.getString("order_type"));
				product.setLot(resultSet.getInt("lot"));
				product.setIsChartActive(resultSet.getBoolean("isChartActive"));
				product.setTab(resultSet.getString("tab"));
				
				productList.add(product);
			}
			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return productList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public Product findByName(String name) {
		
		try {
			
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from product where name ='" + name + "'";

			ResultSet resultSet;
			resultSet = statement.executeQuery(query);			
			Product product = new Product();
			
			while(resultSet.next()) {
				product.setId(resultSet.getInt("id"));
				product.setName(resultSet.getString("name"));
				product.setSymbol(resultSet.getString("symbol"));
				product.setSymbolDescription(resultSet.getString("symbol_description"));
				product.setChartUrl(resultSet.getString("chart_url"));
				product.setIsMarketOpen(resultSet.getBoolean("isMarketOpen"));
				product.setIsMonday(resultSet.getBoolean("isMonday"));
				product.setIsTuesday(resultSet.getBoolean("isTuesday"));
				product.setIsWednesday(resultSet.getBoolean("isWednesday"));
				product.setIsThursday(resultSet.getBoolean("isThursday"));
				product.setIsFriday(resultSet.getBoolean("isFriday"));
				product.setIsSaturday(resultSet.getBoolean("isSaturday"));
				product.setIsSunday(resultSet.getBoolean("isSunday"));
				product.setIsSkip(resultSet.getBoolean("isSkip"));
				product.setSkipTimeSpan(resultSet.getInt("skip_time_span"));
				product.setOpenTime(resultSet.getString("open_time"));
				product.setCloseTime(resultSet.getString("close_time"));
				product.setIsTradingPause(resultSet.getBoolean("isTradingPause"));
				product.setPauseDay(resultSet.getString("pause_day"));
				product.setPauseTimeFrom(resultSet.getString("pause_time_from"));
				product.setPauseTimeTo(resultSet.getString("pause_time_to"));
				product.setPtaUrl(resultSet.getString("pta_url"));
				product.setOrderType(resultSet.getString("order_type"));
				product.setLot(resultSet.getInt("lot"));
				product.setIsChartActive(resultSet.getBoolean("isChartActive"));
				product.setTab(resultSet.getString("tab"));
			}
			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return product;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public int count(int id, String action, Date dateFrom, Date dateTo) {
		int c = 0;
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			
			String query = "Select count(*) from trading where product_id =" + id + " and action ='" + action + "'  AND date >= '" + dateFrom + "' AND date <= '" + dateTo + "'";

			ResultSet resultSet;
			resultSet = statement.executeQuery(query);			
			
			while(resultSet.next()) {
				c = resultSet.getInt(1);
			}
			
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;

	}
	
	public int insert(Product product) {
		int id = 0;
		
		String query = "insert into product (name, symbol, symbol_description, chart_url, pta_url, order_type, lot,  isMarketOpen, " +
						"isMonday, isTuesday, isWednesday, isThursday, isFriday, isSaturday, isSunday, isSkip, skip_time_span, open_time, close_time, " +
						"isTradingPause, pause_day, pause_time_from, pause_time_to, isChartActive, tab) values " + 
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			
			//statement = connection.createStatement(); 
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getSymbol());
			preparedStatement.setString(3, product.getSymbolDescription());
			preparedStatement.setString(4, product.getChartUrl());
			preparedStatement.setString(5, product.getPtaUrl());
			preparedStatement.setString(6, product.getOrderType());
			preparedStatement.setInt(7, product.getLot());
			preparedStatement.setBoolean(8, product.getIsMarketOpen());
			preparedStatement.setBoolean(9, product.getIsMonday());
			preparedStatement.setBoolean(10, product.getIsTuesday());
			preparedStatement.setBoolean(11, product.getIsWednesday());
			preparedStatement.setBoolean(12, product.getIsThursday());
			preparedStatement.setBoolean(13, product.getIsFriday());
			preparedStatement.setBoolean(14, product.getIsSaturday());
			preparedStatement.setBoolean(15, product.getIsSunday());
			preparedStatement.setBoolean(16, product.getIsSkip());
			preparedStatement.setInt(17, product.getSkipTimeSpan());
			preparedStatement.setString(18, product.getOpenTime());
			preparedStatement.setString(19, product.getCloseTime());
			preparedStatement.setBoolean(20, product.getIsTradingPause());
			preparedStatement.setString(21, product.getPauseDay());
			preparedStatement.setString(22, product.getPauseTimeFrom());
			preparedStatement.setString(23, product.getPauseTimeTo());
			preparedStatement.setBoolean(24, product.getIsChartActive());
			preparedStatement.setString(25, product.getTab());
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if(resultSet.next()) {
				id = resultSet.getInt(1);
			}
			
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;	
	}
	
	public void update(Product product) {
		
		String query = "Update product Set name =  ?, " +
				"symbol = ?, " +
				"symbol_description = ?," +
				"chart_url = ?, " +
				"pta_url = ?, " +
				"order_type = ?, " +
				"lot = ?, " +
				"isMarketOpen = ?, " +
				"isMonday = ?, " +
				"isTuesday = ?, " +
				"isWednesday = ?, " +
				"isThursday = ?, " +
				"isFriday = ?, " +
				"isSaturday = ?, " +
				"isSunday = ?, " +
				"isSkip = ?, " +
				"skip_time_span = ?, " +
				"open_time = ?, " +
				"close_time = ?, " +
				"isTradingPause = ?, "+
				"pause_day = ?, " +
				"pause_time_from = ?, " +
				"pause_time_to = ?, " +
				"isChartActive = ?, " +
				"tab = ? " +
				"where id = " + product.getId();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getSymbol());
			preparedStatement.setString(3, product.getSymbolDescription());
			preparedStatement.setString(4, product.getChartUrl());
			preparedStatement.setString(5, product.getPtaUrl());
			preparedStatement.setString(6, product.getOrderType());
			preparedStatement.setInt(7, product.getLot());
			preparedStatement.setBoolean(8, product.getIsMarketOpen());
			preparedStatement.setBoolean(9, product.getIsMonday());
			preparedStatement.setBoolean(10, product.getIsTuesday());
			preparedStatement.setBoolean(11, product.getIsWednesday());
			preparedStatement.setBoolean(12, product.getIsThursday());
			preparedStatement.setBoolean(13, product.getIsFriday());
			preparedStatement.setBoolean(14, product.getIsSaturday());
			preparedStatement.setBoolean(15, product.getIsSunday());
			preparedStatement.setBoolean(16, product.getIsSkip());
			preparedStatement.setInt(17, product.getSkipTimeSpan());
			preparedStatement.setString(18, product.getOpenTime());
			preparedStatement.setString(19, product.getCloseTime());
			preparedStatement.setBoolean(20, product.getIsTradingPause());
			preparedStatement.setString(21, product.getPauseDay());
			preparedStatement.setString(22, product.getPauseTimeFrom());
			preparedStatement.setString(23, product.getPauseTimeTo());
			preparedStatement.setBoolean(24, product.getIsChartActive());
			preparedStatement.setString(25, product.getTab());
			preparedStatement.executeUpdate();
			
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateChartStatus(boolean status) {
		String query = "Update product Set " +
				"isChartActive = ? ";
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			preparedStatement.executeUpdate();
			
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean delete(int id) {
		String query = "Delete from product where id = " + id;
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			statement.execute(query);
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
