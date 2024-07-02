package com.django.beans;

public class Payment {
	private int id;
	private String matricule;
	private int tarifId;
	private int nbrMonth;
	private int remMonth;
	private int withEquipement;
	private String paymentDate;
	private int totalAmount;
	
	public Payment() {
		super();
	}

	public Payment(int id, String matricule, int tarifId, int nbrMonth, int remMonth, int withEquipement,
			String paymentDate, int totalAmount) {
		super();
		this.id = id;
		this.matricule = matricule;
		this.tarifId = tarifId;
		this.nbrMonth = nbrMonth;
		this.remMonth = remMonth;
		this.withEquipement = withEquipement;
		this.paymentDate = paymentDate;
		this.totalAmount = totalAmount;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public int getTarifId() {
		return tarifId;
	}

	public void setTarifId(int tarifId) {
		this.tarifId = tarifId;
	}

	public int getNbrMonth() {
		return nbrMonth;
	}

	public void setNbrMonth(int nbrMonth) {
		this.nbrMonth = nbrMonth;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getRemMonth() {
		return remMonth;
	}

	public void setRemMonth(int remMonth) {
		this.remMonth = remMonth;
	}

	public int getWithEquipement() {
		return withEquipement;
	}

	public void setWithEquipement(int withEquipement) {
		this.withEquipement = withEquipement;
	}
}
