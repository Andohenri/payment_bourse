package com.django.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.django.beans.User;
import com.django.dao.UserDAO;



public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    public RegisterServlet() {
        super();
    }
    @Override
    public void init() throws ServletException {
    	userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			if(userDAO.registerUser(user)) {
				response.sendRedirect("login");
			} else {
				request.setAttribute("error", "Cannot register a user!");
				doGet(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
