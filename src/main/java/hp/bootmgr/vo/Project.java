/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Project implements Serializable {
	
	private static final long serialVersionUID = -6299164271907301000L;
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	@Column(length=20)
	private String code;
	
	@ManyToOne
	@JoinColumn(name="projectTypeId")
	private ProjectType projectType;
	
	@Column
	@Type(type="date")
	private Date startDate;
	
	@Column
	@Type(type="date")
	private Date endDate;
	
	private String address1;
	private String address2;
	
	@Column(length=150)
	private String city;
	
	@ManyToOne
	@JoinColumn(name="stateID")
	private State state;
	
	private String description;
	private String planFilePath;
	
	@ManyToOne
	@JoinColumn(name="projectStatusId")
	private ProjectStatus projectStatus;
	
	@Type(type="yes_no")
	private boolean active;
	
	@OneToMany(mappedBy="project")
	@OrderColumn(name="blockidx")
	@Cascade(CascadeType.SAVE_UPDATE)
	@JsonIgnore
	private List<ProjectPropertyBlock> blocks = new ArrayList<ProjectPropertyBlock>();
		
	@ManyToMany
	@JoinTable(name="projectContactPerson", 
			joinColumns={@JoinColumn(name="projectId")}, 
			inverseJoinColumns={@JoinColumn(name="employeeId")})
	@JsonIgnore
	private List<Employee> contactPerson = new ArrayList<Employee>();
	
	@ManyToMany
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(name="projectPropertyType", 
			joinColumns={@JoinColumn(name="projectId")}, 
			inverseJoinColumns={@JoinColumn(name="propertyTypeId")})
	@JsonIgnore
	private List<PropertyType> propertyTypes = new ArrayList<PropertyType>();
	
	@OneToMany(mappedBy="project", cascade=javax.persistence.CascadeType.ALL, orphanRemoval=true)
	@OrderColumn(name="idx")
	private List<PaymentPlan> paymentPlan = new ArrayList<>();

	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "project_id")
	private Set<ProjectFile> projectFiles = new HashSet<>();


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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlanFilePath() {
		return planFilePath;
	}

	public void setPlanFilePath(String planFilePath) {
		this.planFilePath = planFilePath;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Employee> getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(List<Employee> contactPerson) {
		this.contactPerson = contactPerson;
	}

	public List<ProjectPropertyBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<ProjectPropertyBlock> blocks) {
		this.blocks = blocks;
	}

	public List<PropertyType> getPropertyTypes() {
		return propertyTypes;
	}

	public void setPropertyTypes(List<PropertyType> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}
	
	public List<PaymentPlan> getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(List<PaymentPlan> paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public Set<ProjectFile> getProjectFiles() {
		return projectFiles;
	}

	public void setProjectFiles(Set<ProjectFile> projectFiles) {
		this.projectFiles = projectFiles;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			if(obj instanceof Integer)
				return (Integer) obj == id;
			else if(obj instanceof Project)
				return ((Project) obj).getId() == id;
		}
		return super.equals(obj);
	}
}
