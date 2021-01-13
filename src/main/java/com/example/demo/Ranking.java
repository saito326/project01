package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="ranking")
public class Ranking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="code")
	private Integer code;
	
	@Column(name="acount_code")
	private Integer acountCode;
	

	@Column(name="name")
	private String name;
	
	@Column(name="point")
	private Integer point;
	
	@Column(name="rank")
	private Integer rank;

	public Ranking(Integer acountCode, String acountName, Integer point) {
	    super();
		this.name = acountName;
		this.point = point;
		this.acountCode=acountCode;
	}
	
	public Ranking(Integer code, Integer acountCode, String name, Integer point) {
		super();
		this.code=code;
		this.acountCode=acountCode;
		this.name=name;
		this.point=point;
		
	}
	public Ranking() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String acountname) {
		name = acountname;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
    

}
