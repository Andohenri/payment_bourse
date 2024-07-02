package com.django.servlets;

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

public class ListGroupedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDAO studentDAO;

    public ListGroupedServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	studentDAO = new StudentDAO();
    	super.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Student> studentsInst = studentDAO.getStudents("", "");
		List<String> institution = new ArrayList<>();
		List<Student> students = studentDAO.getGroupedStudent(request.getParameter("institution"));
		Map<String, List<Student>> studentByLevel = new HashMap<>();
		
		for (Student student : studentsInst) {
			if(!institution.contains(student.getInstitution())) {
				institution.add(student.getInstitution());
			}
		}
		for (Student student : students) {
			if(!studentByLevel.containsKey(student.getDesc())) {
				studentByLevel.put(student.getDesc(), new ArrayList<Student>());
			}
			studentByLevel.get(student.getDesc()).add(student);
		}
		request.setAttribute("institution", institution);
		request.setAttribute("studentByLevel", studentByLevel);
		this.getServletContext().getRequestDispatcher("/student-list-grouped.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
