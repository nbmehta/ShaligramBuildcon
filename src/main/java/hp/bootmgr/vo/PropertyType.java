/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class PropertyType {
	@Id
	@GeneratedValue
	private int propertyTypeID;
	private String name;
	
	@ManyToMany(mappedBy="propertyTypes")
	@JsonIgnore
	private List<Project> projects = new ArrayList<Project>();
	
	public int getPropertyTypeID() {
		return propertyTypeID;
	}
	public void setPropertyTypeID(int propertyTypeID) {
		this.propertyTypeID = propertyTypeID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	@Override
	public String toString() {
		return "PropertyType [propertyTypeID=" + propertyTypeID + ", name=" + name + ", projects=" + projects.size() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PropertyType)
			return ((PropertyType) obj).getPropertyTypeID() == this.propertyTypeID;
		else return super.equals(obj);
	}
}
