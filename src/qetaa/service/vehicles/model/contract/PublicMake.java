package qetaa.service.vehicles.model.contract;

import java.io.Serializable;
import java.util.List;

import qetaa.service.vehicles.model.Make;

public class PublicMake implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String nameAr;
	private List<PublicModel> models;
	
	public PublicMake(Make make, List<PublicModel> models) {
		this.id = make.getId();
		this.name = make.getName();
		this.nameAr = make.getNameAr();
		this.models = models;
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
	public List<PublicModel> getModels() {
		return models;
	}
	public void setModels(List<PublicModel> models) {
		this.models = models;
	}
	
	
	
}
