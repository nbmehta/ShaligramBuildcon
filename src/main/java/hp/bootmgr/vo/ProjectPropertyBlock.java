package hp.bootmgr.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class ProjectPropertyBlock implements Serializable {
	
	private static final long serialVersionUID = -5595668862170844379L;

	@Id
	@GeneratedValue
	private int blockId;
	
	@ManyToOne
	@JoinColumn(name = "projectId")
	@JsonIgnore
	private Project project;
	
	@Column(length=5)
	private String block;
	private int noOfFloor;
	
	@OneToMany(mappedBy="block", cascade=CascadeType.ALL, orphanRemoval=true)
	@OrderColumn(name = "idx")
	private List<BlockProgress> progress = new ArrayList<BlockProgress>();
	
	public int getBlockId() {
		return blockId;
	}
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public int getNoOfFloor() {
		return noOfFloor;
	}
	public void setNoOfFloor(int noOfFloor) {
		this.noOfFloor = noOfFloor;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<BlockProgress> getProgress() {
		return progress;
	}
	public void setProgress(List<BlockProgress> progress) {
		this.progress = progress;
	}
}
