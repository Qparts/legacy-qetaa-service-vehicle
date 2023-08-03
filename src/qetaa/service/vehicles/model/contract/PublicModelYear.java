package qetaa.service.vehicles.model.contract;

import java.io.Serializable;

import qetaa.service.vehicles.model.ModelYear;

public class PublicModelYear implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int modelId;
	private int makeId;	
	private int year;
	
	
	public PublicModelYear(ModelYear my) {
		this.id = my.getId();
		this.modelId = my.getModel().getId();
		this.makeId = my.getMake().getId();
		this.year = my.getYear();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getMakeId() {
		return makeId;
	}
	public void setMakeId(int makeId) {
		this.makeId = makeId;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
}
