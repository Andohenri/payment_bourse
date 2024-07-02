package com.django.servlets;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.django.beans.Student;
import com.django.dao.StudentDAO;

public class LateList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDAO studentDAO;

    public LateList() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	studentDAO = new StudentDAO();
    	super.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> months = new ArrayList<String>();
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
		months.add("Septembre");		
		months.add("Octobre");
		String monthIdx = request.getParameter("month");
		String send = request.getParameter("send");
		List<Student> students = studentDAO.getLateListStudent(monthIdx);
		Map<String, List<Student>> studentByLevel = new HashMap<>();
		
		if(send != null && !send.isEmpty()) {
			List<String> rec = new ArrayList<String>();
			
			for (Student student : students) {
				rec.add(student.getEmail());
			}
			try {
				StudentDAO.sendEmail(rec, "Rappel pour les retardataires", "Vous pouvez prendre votre bourse de retardataires d'ici après 3 semaines");
				request.setAttribute("success", "Les emails sont bien envoyés aux retardataires");
			} catch (MessagingException e) {
				e.printStackTrace();
				request.setAttribute("error", "Erreur lors du renvoie des emails, vérifier votre connexion");
			}
		}
		
		for (Student student : students) {
			if(!studentByLevel.containsKey(student.getDesc())) {
				studentByLevel.put(student.getDesc(), new ArrayList<Student>());
			}
			studentByLevel.get(student.getDesc()).add(student);
		}
		request.setAttribute("students", students);
		request.setAttribute("months", months);
		request.setAttribute("lateStudentByLevel", studentByLevel);
		this.getServletContext().getRequestDispatcher("/student-late-list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
