package com.django.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.django.beans.Payment;
import com.django.dao.PaymentDAO;
import com.django.dao.TarifDAO;

public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TarifDAO tarifDAO;
	private PaymentDAO paymentDAO;

    public PaymentServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	tarifDAO = new TarifDAO();
    	paymentDAO = new PaymentDAO();
    	super.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tarifs", tarifDAO.getTarifs());
		request.setAttribute("payments", paymentDAO.getPayments());
		
		this.getServletContext().getRequestDispatcher("/payment.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String matricule = request.getParameter("matricule");
		String nbrMonth = request.getParameter("nbr_month");

		String action = request.getParameter("action");
		if(action == null) {
			action = "update";
		}
		Payment payment = new Payment();
		
		if(matricule != null && nbrMonth != null) {
			payment.setMatricule(matricule);
			payment.setNbrMonth(Integer.parseInt(nbrMonth));
		}
		try {
			if(id != null && !id.isEmpty()) {
				payment.setId(Integer.parseInt(id));
				switch(action) {
					case "delete" :
						if(paymentDAO.deletePayment(payment)) {
							request.setAttribute("success", "payment deleted succesfully!");
						} else {
							request.setAttribute("error", "Cannot delete the payment!");
						}
						break;
					case "update":
						if(paymentDAO.updatePayment(payment)) {
							request.setAttribute("success", "payment updated succesfully!");
						} else {
							request.setAttribute("error", "Cannot update the payment!");
						}
						break;
					default:
						break;
				}
				
			}else {
				if(paymentDAO.addPayment(payment)) {
					request.setAttribute("success", "payment added succesfully!");
				} else {
					request.setAttribute("error", "Cannot add a payment!");
				}
			}
			doGet(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}
}
