package com.django.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.django.beans.Level;

public class LevelDAO {
	public boolean addLevel(Level level) {
		String INSERT_LEVEL_SQL = "INSERT INTO level (description) VALUES (?)";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(INSERT_LEVEL_SQL);){
			p.setString(1, level.getDescription());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Level getLevel(int levelId) {
		String SELECT_LEVEL_SQL = "SELECT * FROM level WHERE id = ?";
		Level level = new Level();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_LEVEL_SQL);){
			p.setInt(1, levelId);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				level.setId(rs.getInt(1));
				level.setDescription(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return level;
	}
	
	public List<Level> getLevels() {
		String SELECT_LEVEL_SQL = "SELECT * FROM level";
		List<Level> levels = new ArrayList<Level>();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_LEVEL_SQL);
			ResultSet rs = p.executeQuery();){
			while(rs.next()) {
				Level level = new Level();
				level.setId(rs.getInt(1));
				level.setDescription(rs.getString(2));
				levels.add(level);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return levels;
	}
	
	public boolean updateLevel(Level level) {
		String UPDATE_Level_SQL = "UPDATE level SET description = ? WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(UPDATE_Level_SQL);){
			p.setString(1, level.getDescription());
			p.setInt(2, level.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteLevel(Level level) {
		String DELETE_Level_SQL = "DELETE FROM level WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(DELETE_Level_SQL);){
			p.setInt(1, level.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
