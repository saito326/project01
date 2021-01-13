package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "routine")
public class Routine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code")
	private Integer code;

	@Column(name = "goal_code")
	private Integer goalCode;

	@Column(name = "routine")
	private String routine;

	public Routine(String task, Integer goalCode) {
		super();
		this.goalCode=goalCode;
		this.routine=task;
	}
	
	public Routine() {
		
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getGoalCode() {
		return goalCode;
	}

	public void setGoalCode(Integer goalCode) {
		this.goalCode = goalCode;
	}

	public String getRoutine() {
		return routine;
	}

	public void setRoutine(String routine) {
		this.routine = routine;
	}
}
	