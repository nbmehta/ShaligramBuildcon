package hp.bootmgr.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class PaymentPlan {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private int completedPercentage;
	
	@ManyToOne
	@JoinColumn(name="projectID")
	@JsonIgnore
	private Project project;
	
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
	public int getCompletedPercentage() {
		return completedPercentage;
	}
	public void setCompletedPercentage(int completedPercentage) {
		this.completedPercentage = completedPercentage;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
}
