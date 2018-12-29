/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class EmployeeRole implements Serializable {
	
	private static final long serialVersionUID = 4815747702065469860L;
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
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
}
