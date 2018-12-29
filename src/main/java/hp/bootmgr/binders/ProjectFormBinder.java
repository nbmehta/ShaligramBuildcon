/**
 * Copyright 2015 - Harsh Panchal <panchal.harsh18@gmail.com>
 */
package hp.bootmgr.binders;

import java.util.ArrayList;
import java.util.List;

import hp.bootmgr.vo.Employee;
import hp.bootmgr.vo.Project;
import hp.bootmgr.vo.PropertyType;

public class ProjectFormBinder {
	private Project project;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<PropertyType> propTypes = new ArrayList<PropertyType>();
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public List<PropertyType> getPropTypes() {
		return propTypes;
	}
	public void setPropTypes(List<PropertyType> propTypes) {
		this.propTypes = propTypes;
	}
}
