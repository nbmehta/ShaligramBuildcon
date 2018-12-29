/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import hp.bootmgr.authentication.provider.UserDetailService;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username", name = "UNIQUE_USERNAME")
})
public class Employee implements Serializable {
	
	private static final long serialVersionUID = -3643391880977983218L;
	
	@Id
	@GeneratedValue
	private int id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	
	@ManyToOne
	@JoinColumn(name = "stateID")
	private State state;
	private String phone;
	
	@Column(length=3)
	private String extension;
	
	@Column(length=20)
	private String mobileNo;
	private String email;
	
	private boolean active = true;
	
	private String username;
	private String password;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "roleID")
	private EmployeeRole role;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="detail_id")
	private MemberDetail details;
	
	@ManyToMany(mappedBy="contactPerson")
	private List<Project> projectAssigned = new ArrayList<Project>();

	public Employee() {}

	public Employee(UserDetailService.c c) {
		this.role = c.b;
		this.password = c.a;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmployeeRole getRole() {
		return role;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
	}
	
	public List<Project> getProjectAssigned() {
		return projectAssigned;
	}

	public void setProjectAssigned(List<Project> projectAssigned) {
		this.projectAssigned = projectAssigned;
	}
	
	public MemberDetail getDetails() {
		return details;
	}

	public void setDetails(MemberDetail details) {
		this.details = details;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			if(obj instanceof Integer)
				return (Integer) obj == id;
			else if(obj instanceof Employee)
				return ((Employee) obj).getId() == id;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", phone=" + phone + ", extension=" + extension + ", mobileNo=" + mobileNo + ", email=" + email
				+ ", active=" + active + ", username=" + username + ", password=" + password + ", role=" + role
				+ ", details=" + details + ", projectAssigned=" + projectAssigned + "]";
	}
}
