package com.django.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.django.beans.Student;
import com.django.dao.LevelDAO;
import com.django.dao.StudentDAO;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDAO studentDAO;
	private LevelDAO levelDAO;

    public StudentServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	studentDAO = new StudentDAO();
    	levelDAO = new LevelDAO();
    	super.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		String keyword = request.getParameter("keyword");
		String filter = request.getParameter("filter");
		if (filter == null) {
			filter = "";
		}
		if (keyword == null) {
			keyword = "";
		}
		request.setAttribute("keyword", keyword);
		request.setAttribute("levels", levelDAO.getLevels());
		request.setAttribute("students", studentDAO.getStudents(keyword, filter));
		if(request.getParameter("studentId") != null) {
			Student s = (Student) studentDAO.getStudent(Integer.parseInt(request.getParameter("studentId")));
			request.setAttribute("student", s);
		}
		
		this.getServletContext().getRequestDispatcher("/student.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String matricule = request.getParameter("matricule");
		String names = request.getParameter("names");
		String sexe = request.getParameter("sexe");
		String birthDate = request.getParameter("birth_date");
		String email = request.getParameter("email");
		String institution = request.getParameter("institution");
		String levelId = request.getParameter("levelId");

		String action = request.getParameter("action");
		if(action == null) {
			action = "update";
		}
		Student student = new Student();
		
		if(matricule != null && levelId != null && names != null && email != null && birthDate != null && institution != null && sexe != null) {
			student.setMatricule(matricule);
			student.setNames(names);
			student.setBirthDate(birthDate);
			student.setSexe(sexe);
			student.setEmail(email);
			student.setInstitution(institution);
			student.setLevelId(Integer.parseInt(levelId));
		}
		try {
			if(id != null && !id.isEmpty()) {
				student.setId(Integer.parseInt(id));
				switch(action) {
					case "delete" :
						if(studentDAO.deleteStudent(student)) {
							request.setAttribute("success", "student deleted succesfully!");
						} else {
							request.setAttribute("error", "Cannot delete the student!");
						}
						break;
					case "update":
						if(studentDAO.updateStudent(student)) {
							request.setAttribute("success", "student updated succesfully!");
						} else {
							request.setAttribute("error", "Cannot update the student!");
						}
						break;
					default:
						break;
				}
				
			}else {
				if(studentDAO.addStudent(student)) {
					request.setAttribute("success", "student added succesfully!");
				} else {
					request.setAttribute("error", "Cannot add a student!");
				}
			}
			doGet(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
