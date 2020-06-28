package com.dan.papis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "login")
public class Login extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "name")
	private String name;

	@Column(name = "role")
	private String role;

	@Column(name = "userName")
	private String userName;

	@Column(name = "password")
	private String password;

	@Transient
	private String confirmPassword;

}
