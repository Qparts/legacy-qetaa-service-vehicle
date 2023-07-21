package qetaa.service.vehicles.model.contract;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="veh_make")
public class MakeContract implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="name_ar")
	private String nameAr;
	@Column(name="status")
	@JsonIgnore
	private char status;
	@Transient
	private List<ModelContract> models;
	
	
	public List<ModelContract> getModels() {
		return models;
	}
	public void setModels(List<ModelContract> models) {
		this.models = models;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getNameAr() {
		return nameAr;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}
	
	public char getStatus() {
		return status;
	}
	
	public void setStatus(char status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MakeContract other = (MakeContract) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}