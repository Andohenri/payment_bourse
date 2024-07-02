package com.django.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.django.beans.Tarif;

public class TarifDAO {
	public boolean addTarif(Tarif tarif) {
		String INSERT_TARIF_SQL = "INSERT INTO tarif (level_id, amount, disponibility_date) VALUES (?, ?, ?)";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(INSERT_TARIF_SQL);){
			p.setInt(1, tarif.getLevelId());
			p.setInt(2, tarif.getAmount());
			p.setString(3, tarif.getDisponibilityDate());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public List<Tarif> getTarifs() {
		String SELECT_TARIF_SQL = "SELECT * FROM tarif";
		List<Tarif> tarifs = new ArrayList<Tarif>();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_TARIF_SQL);
			ResultSet rs = p.executeQuery();){
			while(rs.next()) {
				Tarif tarif = new Tarif();
				tarif.setId(rs.getInt(1));
				tarif.setLevelId(rs.getInt(2));
				tarif.setAmount(rs.getInt(3));
				tarif.setDisponibilityDate(rs.getString(4));
				tarifs.add(tarif);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tarifs;
	}
	public Tarif getTarif(int tarifId) {
		String SELECT_TARIF_SQL = "SELECT * FROM tarif WHERE id = ?";
		Tarif tarif = new Tarif();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_TARIF_SQL);){
			p.setInt(1, tarifId);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				tarif.setId(rs.getInt(1));
				tarif.setLevelId(rs.getInt(2));
				tarif.setAmount(rs.getInt(3));
				tarif.setDisponibilityDate(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tarif;
	}
	public boolean updateTarif(Tarif tarif) {
		String UPDATE_TARIF_SQL = "UPDATE tarif SET level_id = ? , amount = ?, disponibility_date = ? WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(UPDATE_TARIF_SQL);){
			p.setInt(1, tarif.getLevelId());
			p.setInt(2, tarif.getAmount());
			p.setString(3, tarif.getDisponibilityDate());
			p.setInt(4, tarif.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteTarif(Tarif tarif) {
		String DELETE_TARIF_SQL = "DELETE FROM tarif WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(DELETE_TARIF_SQL);){
			p.setInt(1, tarif.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
