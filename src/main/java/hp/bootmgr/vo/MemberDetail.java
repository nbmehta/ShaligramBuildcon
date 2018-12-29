/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class MemberDetail {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(length=50)
	private String dateOfBirth;
	
	@Column(length=50)
	private String age;
	
	@Column(length=50)
	private String contactNo1;
	
	@Column(length=200)
	private String profession;
	
	@Column(length=50)
	private String PANNumber;
	
	@Column(length=50)
	private String marriageAnniversaryDate;

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getContactNo1() {
		return contactNo1;
	}

	public void setContactNo1(String contactNo1) {
		this.contactNo1 = contactNo1;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getPANNumber() {
		return PANNumber;
	}

	public void setPANNumber(String pANNumber) {
		PANNumber = pANNumber;
	}

	public String getMarriageAnniversaryDate() {
		return marriageAnniversaryDate;
	}

	public void setMarriageAnniversaryDate(String marriageAnniversaryDate) {
		this.marriageAnniversaryDate = marriageAnniversaryDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
