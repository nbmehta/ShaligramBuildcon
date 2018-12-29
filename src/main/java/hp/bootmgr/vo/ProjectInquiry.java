/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="projectinquiry")
public class ProjectInquiry {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="inquiryID")
	private Inquiry inquiry;
	
	@ManyToOne
	@JoinColumn(name = "projectID")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "propertyType")
	private PropertyType propertyType;
	
	@Type(type="yes_no")
	private boolean interested;
	
	@Type(type="yes_no")
	private boolean showSampleHouse;
	
	public ProjectInquiry() {}
	
	public ProjectInquiry(Inquiry parentInq, Project project, boolean interested, boolean shownSampleHouse) {
		this.inquiry = parentInq;
		this.project = project;
		this.interested = interested;
		this.showSampleHouse = shownSampleHouse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isInterested() {
		return interested;
	}

	public void setInterested(boolean interested) {
		this.interested = interested;
	}

	public boolean isShowSampleHouse() {
		return showSampleHouse;
	}

	public void setShowSampleHouse(boolean showSampleHouse) {
		this.showSampleHouse = showSampleHouse;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Inquiry getInquiry() {
		return inquiry;
	}

	public void setInquiry(Inquiry inquiry) {
		this.inquiry = inquiry;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
}
