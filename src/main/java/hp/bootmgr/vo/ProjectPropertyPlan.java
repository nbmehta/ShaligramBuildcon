package hp.bootmgr.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class ProjectPropertyPlan {
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "blockId")
	@JsonIgnore
	private ProjectPropertyBlock block;
	
	private int floorNumber;
	
	@Column(length=50)
	private String planName;
	
	@Column(length=255)
	private String planFilePath;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="propertyTypeId")
	private PropertyType propertyType;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="projectId")
	private Project project;

	private String mimeType;

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanFilePath() {
		return planFilePath;
	}
	public void setPlanFilePath(String planFilePath) {
		this.planFilePath = planFilePath;
	}
	public ProjectPropertyBlock getBlock() {
		return block;
	}
	public void setBlock(ProjectPropertyBlock block) {
		this.block = block;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "ProjectPropertyPlan [id=" + id + ", block=" + block
				+ ", floorNumber=" + floorNumber + ", planName=" + planName
				+ ", planFilePath=" + planFilePath + ", propertyType="
				+ propertyType + ", project=" + project + "]";
	}
}
