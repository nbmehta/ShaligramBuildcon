/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class BookingDetail {
	@Id @GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name="projectID")
	@JsonIgnore
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="propertyDetailID")
	private PropertyDetail propertyDetail;
	
	private Date bookingDate = new Date();
	
	@ManyToOne
	@JoinColumn(name="memberId")
	@JsonIgnore
	private Employee memberDetail;
	
	@ManyToOne
	@JoinColumn(name="bookedBy")
	@JsonIgnore
	private Employee bookedByEmployee;
	
	private String note;
	
	@Type(type="yes_no")
	private boolean resale = false;
	
	@Type(type="yes_no")
	private boolean availableForResale;
	
	@ElementCollection
	@CollectionTable(joinColumns=@JoinColumn(name="detailID"), name="payment_data")
	@OrderColumn(name="idx")
	private List<Boolean> paymentData = new ArrayList<Boolean>();

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

	public PropertyDetail getPropertyDetail() {
		return propertyDetail;
	}

	public void setPropertyDetail(PropertyDetail propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Employee getMemberDetail() {
		return memberDetail;
	}

	public void setMemberDetail(Employee memberDetail) {
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

	public List<Boolean> getPaymentData() {
		return paymentData;
	}

	public void setPaymentData(List<Boolean> paymentData) {
		this.paymentData = paymentData;
	}

	@Override
	public String toString() {
		return "BookingDetail{" +
				"id=" + id +
				", project=" + project +
				", propertyDetail=" + propertyDetail +
				", bookingDate=" + bookingDate +
				", memberDetail=" + memberDetail +
				", bookedByEmployee=" + bookedByEmployee +
				", note='" + note + '\'' +
				", resale=" + resale +
				", availableForResale=" + availableForResale +
				", paymentData=" + paymentData +
				'}';
	}
}
