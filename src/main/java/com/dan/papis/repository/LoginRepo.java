package com.dan.papis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dan.papis.entity.Login;

public interface LoginRepo extends JpaRepository<Login, Long> {

	Login findByUserNameAndPassword(String userName, String password);
	
	Login findByUserName(String userName);

}
