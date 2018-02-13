package com.jobpoint.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jobpoint.object.Application;

public class CRUDApplication {
	private Connection connection;
	private Statement statement;
	
	public Application find() {
		
		Application application = new Application();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			statement = connection.createStatement();
			
			String query = "Select * from application";
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				application.setId(resultSet.getInt("id"));
				application.setTvAccount(resultSet.getString("tv_account"));
				application.setTvPassword(resultSet.getString("tv_password"));
				application.setIsSendEmail(resultSet.getBoolean("isSendEmail"));
				application.setEmail(resultSet.getString("email"));
			}
		
			if(statement != null) statement.close();
			if(connection != null) connection.close();
			
			return application;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int insert(Application application) {
		int id = 0;
		
		String query = "insert into application (tv_account, tv_password, isSendEmail, email) values " + 
						"(?, ?, ?, ?)";
	
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, application.getTvAccount());
			preparedStatement.setString(2, application.getTvPassword());
			preparedStatement.setBoolean(3, application.getIsSendEmail());
			preparedStatement.setString(4, application.getEmail());
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
	
public void update(Application application) {
		
		String query = "Update application Set tv_account =  ?, " +
				"tv_password = ?, " +
				"isSendEmail = ?," +
				"email = ? " +
				"where id = " + application.getId();
		
		try {
			connection = DriverManager.getConnection(ConfigDatabase.JDBC_URL);
			PreparedStatement preparedStatement;
			
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, application.getTvAccount());
			preparedStatement.setString(2, application.getTvPassword());
			preparedStatement.setBoolean(3, application.getIsSendEmail());
			preparedStatement.setString(4, application.getEmail());
			preparedStatement.executeUpdate();
			
			if(preparedStatement != null) preparedStatement.close();
			if(connection != null) connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}



}
