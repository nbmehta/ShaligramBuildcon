/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Inquiry {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="visitedProjectID")
	@JsonIgnore
	private Project visitedSite;

	@Column(length = 200)
	private String visitorName;

	@Type(type="date")
	private Date visitDate;
	private int intimeHour;
	private int intimeMinute;
	private int outtimeHour;
	private int outtimeMinute;
	
	@Column(length = 255)
	private String areaOrCity;
	
	@Column(length = 30)
	private String contactNumber;
	
	@Column(length = 200)
	private String email;
	
	@Column(length = 200)
	private String referenceBy;
	
	@ManyToOne
	@JoinColumn(name="contactPersonID")
	@JsonIgnore
	private Employee attendee;
	
	private int inquiryType;
	private int noOfVisit;
	
	@Column(length = 500)
	private String remark;
	
	@ManyToOne
	@JoinColumn(name="followUpBy")
	@JsonIgnore
	private Employee followUpBy;
	private Date folowUpDate;
	
	@OneToMany(mappedBy="inquiry", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ProjectInquiry> projectInquiries;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public int getIntimeHour() {
		return intimeHour;
	}

	public void setIntimeHour(int intimeHour) {
		this.intimeHour = intimeHour;
	}

	public int getIntimeMinute() {
		return intimeMinute;
	}

	public void setIntimeMinute(int intimeMinute) {
		this.intimeMinute = intimeMinute;
	}

	public int getOuttimeHour() {
		return outtimeHour;
	}

	public void setOuttimeHour(int outtimeHour) {
		this.outtimeHour = outtimeHour;
	}

	public int getOuttimeMinute() {
		return outtimeMinute;
	}

	public void setOuttimeMinute(int outtimeMinute) {
		this.outtimeMinute = outtimeMinute;
	}

	public String getAreaOrCity() {
		return areaOrCity;
	}

	public void setAreaOrCity(String areaOrCity) {
		this.areaOrCity = areaOrCity;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReferenceBy() {
		return referenceBy;
	}

	public void setReferenceBy(String referenceBy) {
		this.referenceBy = referenceBy;
	}

	public int getInquiryType() {
		return inquiryType;
	}

	public void setInquiryType(int inquiryType) {
		this.inquiryType = inquiryType;
	}

	public int getNoOfVisit() {
		return noOfVisit;
	}

	public void setNoOfVisit(int noOfVisit) {
		this.noOfVisit = noOfVisit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getFolowUpDate() {
		return folowUpDate;
	}

	public void setFolowUpDate(Date folowUpDate) {
		this.folowUpDate = folowUpDate;
	}

	public Employee getAttendee() {
		return attendee;
	}

	public void setAttendee(Employee attendee) {
		this.attendee = attendee;
	}

	public Employee getFollowUpBy() {
		return followUpBy;
	}

	public void setFollowUpBy(Employee followUpBy) {
		this.followUpBy = followUpBy;
	}

	public Project getVisitedSite() {
		return visitedSite;
	}

	public void setVisitedSite(Project visitedSite) {
		this.visitedSite = visitedSite;
	}

	public List<ProjectInquiry> getProjectInquiries() {
		return projectInquiries;
	}

	public void setProjectInquiries(List<ProjectInquiry> projectInquiries) {
		this.projectInquiries = projectInquiries;
	}

}
