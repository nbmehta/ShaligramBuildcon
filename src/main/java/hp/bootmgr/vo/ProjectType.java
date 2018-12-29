/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ProjectType {
	@Id
	@GeneratedValue
	private int projectTypeID;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProjectTypeID() {
		return projectTypeID;
	}
	public void setProjectTypeID(int projectTypeID) {
		this.projectTypeID = projectTypeID;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			if(obj instanceof Integer)
				return (Integer) obj == projectTypeID;
			else if(obj instanceof ProjectType)
				return ((ProjectType) obj).getProjectTypeID() == projectTypeID;
		}
		return super.equals(obj);
	}
}
