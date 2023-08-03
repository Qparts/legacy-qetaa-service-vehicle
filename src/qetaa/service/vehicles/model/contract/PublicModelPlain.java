package qetaa.service.vehicles.model.contract;

import qetaa.service.vehicles.model.Model;

public class PublicModelPlain {
	private int id;
	private String name;
	private String nameAr;
	
	public PublicModelPlain() {
		
	}
	
	public PublicModelPlain(Model model) {
		this.id = model.getId();
		this.name = model.getName();
		this.nameAr = model.getNameAr();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAr() {
		return nameAr;
	}
	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}
	
	
}
