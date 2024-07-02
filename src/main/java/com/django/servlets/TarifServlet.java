package com.django.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.django.beans.Tarif;
import com.django.dao.LevelDAO;
import com.django.dao.TarifDAO;

public class TarifServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TarifDAO tarifDAO;
	private LevelDAO levelDAO; 

    public TarifServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	tarifDAO = new TarifDAO();
    	levelDAO = new LevelDAO();
    	super.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("tarifs", tarifDAO.getTarifs());
		request.setAttribute("levels", levelDAO.getLevels());
		if(request.getParameter("tarifId") != null) {
			request.setAttribute("tarif", tarifDAO.getTarif(Integer.parseInt(request.getParameter("tarifId"))));
		}
		this.getServletContext().getRequestDispatcher("/tarif.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String amount = request.getParameter("amount");
		String levelId = request.getParameter("levelId");
		String disponibiltyDate = request.getParameter("dispo_date");
		String action = request.getParameter("action");
		if(action == null) {
			action = "update";
		}
		Tarif tarif = new Tarif();
		
		if(amount != null && levelId != null && disponibiltyDate != null) {
			tarif.setAmount(Integer.parseInt(amount));
			tarif.setLevelId(Integer.parseInt(levelId));
			tarif.setDisponibilityDate(disponibiltyDate);
		}
		
		try {
			if(id != null && !id.isEmpty()) {
				tarif.setId(Integer.parseInt(id));
				switch(action) {
					case "delete" :
						if(tarifDAO.deleteTarif(tarif)) {
							request.setAttribute("success", "Tarif deleted succesfully!");
						} else {
							request.setAttribute("error", "Cannot delete the tarif!");
						}
						break;
					case "update":
						if(tarifDAO.updateTarif(tarif)) {
							request.setAttribute("success", "Tarif updated succesfully!");
						} else {
							request.setAttribute("error", "Cannot update the tarif!");
						}
					default:
						break;
				}
				
			}else {
				if(tarifDAO.addTarif(tarif)) {
					request.setAttribute("success", "Tarif added succesfully!");
				} else {
					request.setAttribute("error", "Cannot add a tarif!");
				}
			}
			doGet(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
