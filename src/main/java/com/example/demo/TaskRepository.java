package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer>{

	List<Task> findByCode(Integer code);
	List<Task> findByGoalCode(Integer goalCode);
	

}
