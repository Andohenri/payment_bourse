package com.django.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.django.dao.DatabaseConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PrintServlet extends HttpServlet {
	static final long serialVersionUID = 1L;

    public PrintServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("EEEE d MMMM yyyy", Locale.FRENCH);
		StringBuilder htmlContent = new StringBuilder();
		String matricule = null;
		String names = null;
		String date_birth = null;
		String sexe = null;
		String institution = null;
		String level = null;
		String date = df.format(d);
		int nbr_month = 0;
		int remain = 0;
		int withEqui = 0;
		int total_amount = 0;
		int amount = 0;
		
		List<String> months = new ArrayList<String>();
		months.add("Septembre");		
		months.add("Octobre");
		months.add("Novembre");		
		months.add("Décembre");	
		months.add("Janvier");		
		months.add("Février");		
		months.add("Mars");		
		months.add("Avril");		
		months.add("Mai");
		months.add("Juin");	
		months.add("Juillet");		
		months.add("Août");	
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"generated.pdf\"");
		
		String SELECT_SQL = "select s.matricule, s.names, s.birth_date, s.sexe, s.institution, t.amount, p.total_amount, l.description, p.nbr_month, p.rem_month, p.with_equipement from payment p "
				+ "join student s on s.matricule = p.matricule "
				+ "join tarif t on t.id = p.tarif_id "
				+ "join level l on l.id = t.level_id where p.id = ?;";
		try(Connection c = DatabaseConnection.getConnection();
			PreparedStatement p = c.prepareStatement(SELECT_SQL);){
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				matricule = rs.getString("matricule");
				names = rs.getString("names");
				sexe = rs.getString("sexe");
				nbr_month = rs.getInt("nbr_month");
				remain = rs.getInt("rem_month");
				withEqui = rs.getInt("with_equipement");
				LocalDate l = LocalDate.parse(rs.getString("birth_date"));
				DateTimeFormatter f = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH); 
				date_birth = l.format(f);
				amount = rs.getInt("amount");
				total_amount = rs.getInt("total_amount");
				level = rs.getString("description");
				institution = rs.getString("institution");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
		
		htmlContent.append("<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<style>"
				+ "body {font-family: 'Arial', sans-serif; display: flex; justify-content: center;align-items: center; height: 100vh;margin: 0;} "
				+ ".invoice-box {background-color: white;padding: 20px;border-radius: 10px;box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);max-width: 600px; width: 100%;} "
				+ "h1 {text-align: center;color: #333;} "
				+ "p {margin: 0;padding: 5px 0;color: #555;} "
				+ "table { width: 100%;border-collapse: collapse; margin-top: 20px;} "
				+ "table, th, td {border: 1px solid #ddd;} "
				+ "th, td {padding: 10px;text-align: left;} "
				+ "thead {background-color: #f4f4f4;} "
				+ ".total {font-weight: bold;background-color: #f9f9f9;} "
				+ ".paid {font-weight: bold;background-color: #e0ffe0;} "
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div class=\"invoice-box\">"
				+ "<h1>Facture</h1>"
				+ "<p>" + date +"</p>"
				+ "<p>Matricule : " + matricule + "</p>"
				+ "<p>Nom : "+ names +"</p>"
				+ "<p>Date de naissance : "+date_birth+"</p>"
				+ "<p>Sexe : "+sexe+"</p>"
				+ "<p>Institution : "+institution+"</p>"
				+ "<p>Niveau : "+ level +"</p>"
				+ "<table>"
				+ "<thead>"
				+ "<tr> "
				+ "<th>Mois</th> "
				+ "<th>Montant (Ariary)</th> "
				+ "</tr> "
				+ "</thead> "
				+ "<tbody> ");
		
		if(withEqui == 1) {			
			htmlContent.append("<tr> "
					+ "<td>Equipement</td> "
					+ "<td>66.000</td> "
					+ "</tr> ");
		}
		
		for (int i = nbr_month; i > 0 ; i--) {
			htmlContent.append("<tr> "
					+ "<td>"+ months.get(months.size() - remain - i) +"</td> "
					+ "<td>"+ amount +"</td> "
					+ "</tr> ");
		}

		htmlContent.append("<tr class=\"total\"> "
				+ "<td>Total</td> "
				+ "<td>"+total_amount+"</td> "
				+ "</tr> "
				+ "<tr class=\"paid\"> "
				+ "<td>Total Payé</td> "
				+ "<td>"+total_amount+" Ariary</td> "
				+ "</tr> "
				+ "</tbody> "
				+ "</table> "
				+ "</div> "
				+ "</body> "
				+ "</html> ");
	
		try(OutputStream out = response.getOutputStream()){
			Document doc = new Document();
			PdfWriter wr = PdfWriter.getInstance(doc, out);
			doc.open();
			XMLWorkerHelper.getInstance().parseXHtml(wr, doc, new ByteArrayInputStream(htmlContent.toString().getBytes(StandardCharsets.UTF_8)));
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
