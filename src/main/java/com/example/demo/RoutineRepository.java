package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine,Integer>{

	List<Routine> findByGoalCode(Integer goalCode);

	

}
