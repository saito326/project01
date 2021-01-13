package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal,Integer>{

	List<Goal> findByAcountCode(Integer acountCode);

	Goal findByCode(Integer goalCode);

	

}
