/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table
public class ParkingBookingDetail {
	@Id @GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="projectID")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="extraParkingID")
	private ProjectExtraParking extraParking;
	
	private Date bookingDate;
	
	@ManyToOne
	@JoinColumn(name="memberID")
	private MemberDetail memberDetail;
	
	@ManyToOne
	@JoinColumn(name="employeeID")
	private Employee bookedByEmployee;
	
	private String note;
	
	@Type(type="yes_no")
	private boolean resale = false;
	
	@Type(type="yes_no")
	private boolean availableForResale = false;

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

	public ProjectExtraParking getExtraParking() {
		return extraParking;
	}

	public void setExtraParking(ProjectExtraParking extraParking) {
		this.extraParking = extraParking;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public MemberDetail getMemberDetail() {
		return memberDetail;
	}

	public void setMemberDetail(MemberDetail memberDetail) {
		this.memberDetail = memberDetail;
	}

	public Employee getBookedByEmployee() {
		return bookedByEmployee;
	}

	public void setBookedByEmployee(Employee bookedByEmployee) {
		this.bookedByEmployee = bookedByEmployee;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isResale() {
		return resale;
	}

	public void setResale(boolean resale) {
		this.resale = resale;
	}

	public boolean isAvailableForResale() {
		return availableForResale;
	}

	public void setAvailableForResale(boolean availableForResale) {
		this.availableForResale = availableForResale;
	}
	
}
