package qetaa.service.vehicles.model.contract;

import qetaa.service.vehicles.model.ModelYear;

public class PublicModelYearContained {

	private static final long serialVersionUID = 1L;
	private int id;
	private PublicModelPlain model;
	private PublicMakePlain make;
	private int year;
	
	public PublicModelYearContained() {
		
	}
	
	public PublicModelYearContained (ModelYear modelYear) {
		this.id = modelYear.getId();
		this.year = modelYear.getYear();
		this.make = new PublicMakePlain(modelYear.getMake());
		this.model = new PublicModelPlain(modelYear.getModel());
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public PublicModelPlain getModel() {
		return model;
	}
	public void setModel(PublicModelPlain model) {
		this.model = model;
	}

	public PublicMakePlain getMake() {
		return make;
	}
	public void setMake(PublicMakePlain make) {
		this.make = make;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
}
