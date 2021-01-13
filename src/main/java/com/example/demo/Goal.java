package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goal")
public class Goal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "code")
	private Integer code;

	@Column(name = "acount_code")
	private Integer acountCode;

	@Column(name = "start")
	private String start;

	@Column(name = "finish")
	private String finish;

	@Column(name = "level")
	private Integer level;

	@Column(name = "goal")
	private String goal;

	public Goal(String goal, String start, String finish, Integer acountCode) {
		super();
		this.goal = goal;
		this.start = start;
		this.finish = finish;
		this.acountCode=acountCode;
		this.level=0;
	}
	
	public Goal() {
	}

	public Goal(Integer code, Integer level, String goal) {
		
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getAcountCode() {
		return acountCode;
	}

	public void setAcountCode(Integer acountCode) {
		this.acountCode = acountCode;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
		
	}
}