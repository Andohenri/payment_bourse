package com.django.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.django.beans.Payment;

public class PaymentDAO {

	public List<Payment> getPayments() {
		String SELECT_PAYMENT_SQL = "SELECT * FROM payment";
		List<Payment> payments = new ArrayList<Payment>();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_PAYMENT_SQL);
			ResultSet rs = p.executeQuery();){
			while(rs.next()) {
				Payment payment = new Payment();
				payment.setId(rs.getInt(1));
				payment.setMatricule(rs.getString(2));
				payment.setTarifId(rs.getInt(3));
				payment.setNbrMonth(rs.getInt(4));
				payment.setRemMonth(rs.getInt(5));
				payment.setWithEquipement(rs.getInt(6));
				payment.setPaymentDate(rs.getString(7));
				payment.setTotalAmount(rs.getInt(8));
				payments.add(payment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}
	
	public Payment getpayment(int paymentId) {
		String SELECT_payment_SQL = "SELECT * FROM payment WHERE id = ?";
		Payment payment = new Payment();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_payment_SQL);){
			p.setInt(1, paymentId);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				payment.setId(rs.getInt(1));
				payment.setMatricule(rs.getString(2));
				payment.setTarifId(rs.getInt(3));
				payment.setNbrMonth(rs.getInt(4));
				payment.setRemMonth(rs.getInt(5));
				payment.setWithEquipement(rs.getInt(6));
				payment.setPaymentDate(rs.getString(7));
				payment.setTotalAmount(rs.getInt(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payment;
	}
	
	public boolean addPayment(Payment payment) throws Exception {
		int remain_month = 0;
		int amount = 0;
		int equipement = 0;
		int tarifId = 0;
		String SELECT_STUDENT = "SELECT t.amount, s.remain_month, s.equipement_paid, t.id FROM student s "
				+ "JOIN level l ON s.level_id = l.id "
				+ "JOIN tarif t on s.level_id = t.level_id "
				+ "WHERE s.matricule = ?";		
		String UPDATE_STUDENT_STATUS_SQL = "UPDATE student SET remain_month = remain_month - ?, equipement_paid = 1 WHERE matricule = ?";
		String INSERT_payment_SQL = "INSERT INTO payment (matricule, tarif_id, nbr_month, payment_date, total_amount, rem_month, with_equipement) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection c = DatabaseConnection.getConnection()){
			c.setAutoCommit(false);
			
			try(PreparedStatement p3 = c.prepareStatement(SELECT_STUDENT)){
					p3.setString(1, payment.getMatricule());
					ResultSet rs = p3.executeQuery();
					while(rs.next()) {
						amount = rs.getInt(1);
						remain_month = rs.getInt(2);
						equipement = rs.getInt(3) == 0 ? 60000 : 0;
						tarifId = rs.getInt(4);
					}
						
				}
			
			if(payment.getNbrMonth() <= remain_month) {
				try(PreparedStatement p1 = c.prepareStatement(UPDATE_STUDENT_STATUS_SQL)){
					p1.setInt(1, payment.getNbrMonth());
					p1.setString(2, payment.getMatricule());
					p1.executeUpdate();
				}
				
				try(PreparedStatement p2 = c.prepareStatement(INSERT_payment_SQL);){
					Date date = new Date();
					SimpleDateFormat f = new SimpleDateFormat("yyy-MM-dd HH:mm");
					
					p2.setString(1, payment.getMatricule());
					p2.setInt(2, tarifId);
					p2.setInt(3, payment.getNbrMonth());
					p2.setString(4, f.format(date));
					p2.setInt(5, payment.getNbrMonth() * amount + equipement);
					p2.setInt(6, remain_month - payment.getNbrMonth());
					p2.setInt(7, equipement != 0 ? 1 : 0);
					p2.executeUpdate();
				}
				c.commit();
				return true;
			}else {
				c.rollback();
				throw new Exception("C'est trop pour un bourse ein! :)");
			}
		} catch (SQLException e) {
			try(Connection c = DatabaseConnection.getConnection()){
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

	public boolean deletePayment(Payment payment) {
		String DELETE_PAYMENT_SQL = "DELETE FROM payment WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(DELETE_PAYMENT_SQL);){
			p.setInt(1, payment.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatePayment(Payment payment) {
		//
		return false;
	}
}
