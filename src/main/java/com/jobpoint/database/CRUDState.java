package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jobpoint.object.State;

public class CRUDState {
	
	private Connection connection;
	private Statement statement;
	
	public State findByForeignId(int productId) {
		
		State state = new State();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from state where product_id = " + productId;
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				state.setId(resultSet.getInt("id"));
				state.setProductId(resultSet.getInt("product_id"));
				state.setTradingId(resultSet.getInt("trading_id"));
			}

			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return state;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public int insert(State state) {
		int id = 0;
	
		String query = "insert into state (product_id, trading_id) values " +
						"(?, ?)";

	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);

			PreparedStatement preparedStatement;
		
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, state.getProductId());
			preparedStatement.setInt(2, state.getTradingId());

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
	
	public void update( int id, State state) {
		String query = "Update state Set trading_id =  ? " +
				"where id = " + id;
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, state.getTradingId());
			preparedStatement.executeUpdate();

			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete(String object, int foreign_id) {
		String query = "Delete from state where " + object + "_id = " + foreign_id;
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			statement.execute(query);
			if(statement != null) statement.close();
			if(connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
