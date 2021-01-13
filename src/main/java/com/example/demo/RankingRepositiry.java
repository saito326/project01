package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepositiry extends JpaRepository<Ranking,Integer>{

	Ranking findByAcountCode(Integer acountCode);

	List<Ranking> findByOrderByPointDesc();

}
