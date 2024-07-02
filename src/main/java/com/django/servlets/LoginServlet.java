package com.django.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.django.beans.User;
import com.django.dao.UserDAO;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public LoginServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User user = userDAO.loginUser(email, password);
			if(user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("/payement_bourse");
			} else {
				request.setAttribute("error", "Email or password incorrect!");
				doGet(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
