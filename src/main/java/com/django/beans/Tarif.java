package com.django.beans;

public class Tarif {
	private int id;
	private int levelId;
	private int amount;
	private String disponibilityDate;
	
	public Tarif() {
		super();
	}

	public Tarif(int id, int levelId, int amount, String disponibilityDate) {
		super();
		this.id = id;
		this.levelId = levelId;
		this.amount = amount;
		this.disponibilityDate = disponibilityDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDisponibilityDate() {
		return disponibilityDate;
	}

	public void setDisponibilityDate(String disponibilityDate) {
		this.disponibilityDate = disponibilityDate;
	}
	
	
}
