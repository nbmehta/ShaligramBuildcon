/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class PropertyDetail {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="projectID")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="propertyTypeId")
	@JsonIgnore
	private PropertyType propertyType;
	
	private int propertyNumber;
	
	@ManyToOne
	@JoinColumn(name="blockID")
	private ProjectPropertyBlock block;
	
	private int floorNumber;
	
	@Column(precision=6, scale=2)
	private float flatArea;
	
	@Column(precision=6, scale=2)
	private float buildUpArea;
	
	@Column(precision=6, scale=2)
	private float undividedLandArea;
	
	@ManyToOne
	@JoinColumn(name="measureMentUnitID")
	@JsonIgnore
	private MeasurementUnit unit;
	
	private String description;
	
	@OneToOne
	@JsonIgnore
	private ProjectPropertyPlan projectPropertyPlan;
	
	@OneToOne(mappedBy="propertyDetail")
	@JsonIgnore
	private BookingDetail bookingDetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public int getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(int propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public ProjectPropertyBlock getBlock() {
		return block;
	}

	public void setBlock(ProjectPropertyBlock block) {
		this.block = block;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public float getFlatArea() {
		return flatArea;
	}

	public void setFlatArea(float flatArea) {
		this.flatArea = flatArea;
	}

	public float getBuildUpArea() {
		return buildUpArea;
	}

	public void setBuildUpArea(float buildUpArea) {
		this.buildUpArea = buildUpArea;
	}

	public float getUndividedLandArea() {
		return undividedLandArea;
	}

	public void setUndividedLandArea(float undividedLandArea) {
		this.undividedLandArea = undividedLandArea;
	}

	public MeasurementUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasurementUnit unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectPropertyPlan getProjectPropertyPlan() {
		return projectPropertyPlan;
	}

	public void setProjectPropertyPlan(ProjectPropertyPlan projectPropertyPlan) {
		this.projectPropertyPlan = projectPropertyPlan;
	}
	
	public BookingDetail getBookingDetail() {
		return bookingDetail;
	}

	public void setBookingDetail(BookingDetail bookingDetail) {
		this.bookingDetail = bookingDetail;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			if(obj instanceof Integer)
				return (Integer) obj == id;
			else if(obj instanceof PropertyDetail)
				return ((PropertyDetail) obj).getId() == id;
		}
		return super.equals(obj);
	}
}
