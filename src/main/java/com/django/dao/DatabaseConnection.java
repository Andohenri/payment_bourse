package com.django.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/jakartaee";
	private static final String USER = "root";
	private static final String PASSOWRD = "";
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSOWRD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
