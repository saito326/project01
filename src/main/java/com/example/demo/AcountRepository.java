package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AcountRepository extends JpaRepository<Acount, Integer> {

	Acount findByName(String name);

	Acount findByNameAndPassword(String name, String password);

}
