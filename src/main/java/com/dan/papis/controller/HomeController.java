package com.dan.papis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dan.papis.entity.Login;

@Controller
public class HomeController {

	@GetMapping("/")
	public String greeting(Model model) {
		model.addAttribute("login", new Login());
		model.addAttribute("loginError", "");
		return "/login";
	}

}
