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
public class State implements Serializable {
	
	private static final long serialVersionUID = 5729849447322076425L;
	
	@Id
	@GeneratedValue
	private int stateID;
	private String stateName;
	public int getStateID() {
		return stateID;
	}
	public void setStateID(int stateID) {
		this.stateID = stateID;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj != null) {
			if(obj instanceof Integer)
				return (Integer) obj == stateID;
			else if(obj instanceof ProjectType)
				return ((State) obj).getStateID() == stateID;
		}
		return super.equals(obj);
	}
}
