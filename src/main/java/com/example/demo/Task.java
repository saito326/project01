package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="code")
	private Integer code;
	
	@Column(name="goal_code")
	private Integer goalCode;
	
	@Column(name="task")
	private String task;
	
	@Column(name="start")
	private String start;
	
	@Column(name="finish")
	private String finish;
	
	@Column(name="level")
	private String level;

	public Task(String task, String start, String finish, Integer goalCode) {
		super();
		this.task=task;
		this.start=start;
		this.finish=finish;
		this.goalCode=goalCode;
		this.level="0%";
	}

	public Task(String task, String start, String finish) {
		this.task=task;
		this.start=start;
		this.finish=finish;
	}

	public Task() {
		
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

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFinish() {
		return finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
}