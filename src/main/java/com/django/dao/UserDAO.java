package com.django.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.django.beans.User;

public class UserDAO {
	
	public boolean registerUser(User user) {
		String INSERT_USER_SQL = "INSERT INTO user (username, email, password) VALUES (?,?,?)";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(INSERT_USER_SQL);){
			p.setString(1, user.getUsername());
			p.setString(2, user.getEmail());
			p.setString(3, user.getPassword());
			boolean resp = p.executeUpdate() > 0;
			c.close();
			return resp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public User loginUser(String email, String password) {
		String SELECT_USER_SQL = "SELECT * FROM user WHERE email = ? AND password = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_USER_SQL);){
			p.setString(1, email);
			p.setString(2, password);
			ResultSet rs = p.executeQuery();
			if(rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				c.close();
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
