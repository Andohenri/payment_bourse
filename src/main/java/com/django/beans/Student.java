package com.django.beans;

public class Student {
	private int id;
	private String matricule;
	private String names;
	private String sexe;
	private String birthDate;
	private String email;
	private String institution;
	private int levelId;
	private int equipementPaid;
	private int remainMonth;
	private String desc;
	
	public Student() {
		super();
	}
	
	public Student(int id, String matricule, String names, String sexe, String birthDate, String email,
			String institution, int levelId, int equipementPaid, int remainMonth) {
		super();
		this.id = id;
		this.matricule = matricule;
		this.names = names;
		this.sexe = sexe;
		this.birthDate = birthDate;
		this.email = email;
		this.institution = institution;
		this.levelId = levelId;
		this.equipementPaid = equipementPaid;
		this.remainMonth = remainMonth;
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

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public int getEquipementPaid() {
		return equipementPaid;
	}

	public void setEquipementPaid(int equipementPaid) {
		this.equipementPaid = equipementPaid;
	}

	public int getRemainMonth() {
		return remainMonth;
	}

	public void setRemainMonth(int remainMonth) {
		this.remainMonth = remainMonth;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
