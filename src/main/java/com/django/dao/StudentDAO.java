package com.django.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.django.beans.Student;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class StudentDAO {
	public List<Student> getStudents(String keyword, String filter) {
		String SELECT_STUDENT_SQL = "SELECT * FROM student";
		
		if(!keyword.isEmpty()) {
			SELECT_STUDENT_SQL += " WHERE names LIKE ? OR matricule LIKE ? OR institution LIKE ?";
		}
		if(!filter.isEmpty()) {
			switch (filter) {
			case "minor": {
				SELECT_STUDENT_SQL += " WHERE TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) < 18";
				break;
			}
			case "major": {
				SELECT_STUDENT_SQL += " WHERE TIMESTAMPDIFF(YEAR, birth_date, CURDATE()) > 18";
				break;
			}
			default :
				SELECT_STUDENT_SQL += "";
				break;
			}
		}
		
		List<Student> students = new ArrayList<Student>();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_STUDENT_SQL);){
			if(!keyword.isEmpty()) {
				p.setString(1, "%" + keyword + "%");
				p.setString(2, "%" + keyword + "%");
				p.setString(3, "%" + keyword + "%");
			}
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				Student student = new Student();
				student.setId(rs.getInt(1));
				student.setMatricule(rs.getString(2));
				student.setNames(rs.getString(3));
				student.setSexe(rs.getString(4));
				student.setBirthDate(rs.getString(5));
				student.setEmail(rs.getString(6));
				student.setInstitution(rs.getString(7));
				student.setLevelId(rs.getInt(8));
				student.setEquipementPaid(rs.getInt(9));
				student.setRemainMonth(rs.getInt(10));
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	
	public Student getStudent(int studentId) {
		String SELECT_STUDENT_SQL = "SELECT * FROM Student WHERE id = ?";
		Student student = new Student();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_STUDENT_SQL);){
			p.setInt(1, studentId);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				student.setId(rs.getInt(1));
				student.setMatricule(rs.getString(2));
				student.setNames(rs.getString(3));
				student.setSexe(rs.getString(4));
				student.setBirthDate(rs.getString(5));
				student.setEmail(rs.getString(6));
				student.setInstitution(rs.getString(7));
				student.setLevelId(rs.getInt(8));
				student.setEquipementPaid(rs.getInt(9));
				student.setRemainMonth(rs.getInt(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public boolean addStudent(Student student) {
		String INSERT_STUDENT_SQL = "INSERT INTO Student (matricule, names, sexe, birth_date, email, institution, level_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(INSERT_STUDENT_SQL);){
			p.setString(1, student.getMatricule());
			p.setString(2, student.getNames());
			p.setString(3, student.getSexe());
			p.setString(4, student.getBirthDate());
			p.setString(5, student.getEmail());
			p.setString(6, student.getInstitution());
			p.setInt(7, student.getLevelId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateStudent(Student student) {
		String UPDATE_STUDENT_SQL = "UPDATE student SET matricule = ?, names = ?, sexe = ?, birth_date = ?, email = ?, institution = ?, level_id = ? WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(UPDATE_STUDENT_SQL);){
			p.setString(1, student.getMatricule());
			p.setString(2, student.getNames());
			p.setString(3, student.getSexe());
			p.setString(4, student.getBirthDate());
			p.setString(5, student.getEmail());
			p.setString(6, student.getInstitution());
			p.setInt(7, student.getLevelId());
			p.setInt(8, student.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteStudent(Student student) {
		String DELETE_STUDENT_SQL = "DELETE FROM student WHERE id = ?";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(DELETE_STUDENT_SQL);){
			p.setInt(1, student.getId());
			return p.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void sendEmail(List<String> recipients, String subject, String content) throws MessagingException {
        Properties props = new Properties();
        try (InputStream input = StudentDAO.class.getClassLoader().getResourceAsStream("email.properties")) {
            if (input == null) {
                throw new IOException("Unable to find email.properties");
            }
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new MessagingException("Failed to load email properties", ex);
        }
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(props.getProperty("mail.smtp.username"), props.getProperty("mail.smtp.password"));
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("mail.smtp.username")));
        Address[] recipientAddresses = new Address[recipients.size()];
        for (int i = 0; i < recipients.size(); i++) {
			recipientAddresses[i] = new InternetAddress(recipients.get(i));
		}
        message.setRecipients(Message.RecipientType.TO, recipientAddresses);
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
	}

	public List<Student> getLateListStudent(String monthIdx) {
		String SELECT_STUDENT_SQL = "SELECT s.matricule, names, description, institution, sexe, remain_month, email "
				+ "FROM student s JOIN level l ON s.level_id = l.id LEFT JOIN payment p ON s.matricule = p.matricule ";
		if(monthIdx != null && !monthIdx.isEmpty()) {
			SELECT_STUDENT_SQL += " WHERE remain_month > 10 - ? ";
		}
		SELECT_STUDENT_SQL += "GROUP BY names ORDER BY description, names ";
		
		List<Student> students = new ArrayList<Student>();
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_STUDENT_SQL);){
			if(monthIdx != null && !monthIdx.isEmpty()) {
				p.setInt(1, Integer.parseInt(monthIdx));
			};
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				Student student = new Student();
				student.setMatricule(rs.getString("matricule"));
				student.setNames(rs.getString("names"));
				student.setDesc(rs.getString("description"));
				student.setInstitution(rs.getString("institution"));
				student.setSexe(rs.getString("sexe"));
				student.setLevelId(rs.getInt("remain_month"));
				student.setEmail(rs.getString("email"));;
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	public List<Student> getGroupedStudent(String institution) {
		String SELECT_STUDENT_SQL = "SELECT matricule, names, description, institution, sexe FROM student s JOIN level l ON s.level_id = l.id";
		if(institution != null && !institution.isEmpty()) {
			SELECT_STUDENT_SQL += " WHERE institution = ?";
		}
		SELECT_STUDENT_SQL += " ORDER BY description, names";
		
		List<Student> students = new ArrayList<Student>();
		try(Connection c = DatabaseConnection.getConnection();
				PreparedStatement p = c.prepareStatement(SELECT_STUDENT_SQL);){
				if(institution != null && !institution.isEmpty()) {
					p.setString(1, institution);
				};
				ResultSet rs = p.executeQuery();
				while(rs.next()) {
					Student student = new Student();
					student.setMatricule(rs.getString("matricule"));
					student.setNames(rs.getString("names"));
					student.setDesc(rs.getString("description"));
					student.setInstitution(rs.getString("institution"));
					student.setSexe(rs.getString("sexe"));
					students.add(student);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return students;
	}
}
