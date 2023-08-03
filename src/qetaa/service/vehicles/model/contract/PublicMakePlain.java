package qetaa.service.vehicles.model.contract;

import qetaa.service.vehicles.model.Make;

public class PublicMakePlain {

	private int id;
	private String name;
	private String nameAr;
	
	
	public PublicMakePlain() {
		
	}
	
	public PublicMakePlain(Make make) {
		this.id = make.getId();
		this.name = make.getName();
		this.nameAr = make.getNameAr();
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
