package com.dan.papis.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.Login;
import com.dan.papis.repository.LoginRepo;
import com.dan.papis.utils.PapisUtils;

@Controller
public class LoginController {

	@Autowired
	LoginRepo loginRepo;

	@PostMapping("/user")
	public String login1(@ModelAttribute Login login, Model model) {

		if (login.getUserName() != null) {
			Login loginDb = loginRepo.findByUserName(login.getUserName());
			if (loginDb != null && !loginDb.getUserName().equals(login.getUserName())) {
				model.addAttribute("users", loginRepo.findAll());
				model.addAttribute("userAlreadyExits", "User name already exits!");
				return "/Users";
			}
			login.setLastModifiedDate(loginDb.getLastModifiedDate());
		}


		login.setCreatedDate(PapisUtils.getDate());
		loginRepo.save(login);
		model.addAttribute("users", loginRepo.findAll());
		
		return "/Users";
	}

	@GetMapping("/user")
	public String user(Model model) {
		model.addAttribute("login", new Login());
		model.addAttribute("users", loginRepo.findAll());
		return "/Users";
	}

	@GetMapping("/editUser/{id}")
	public String editUser(@PathVariable Long id, Model model) {

		Optional<Login> users = loginRepo.findById(id);
		if (users.isPresent()) {
			model.addAttribute("login", users.get());
		}
		model.addAttribute("users", loginRepo.findAll());

		return "/Users :: responsiveDeviceModal";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable Long id, Model model) {

		Optional<Login> users = loginRepo.findById(id);
		if (users.isPresent()) {
			loginRepo.delete(users.get());
		}
		model.addAttribute("login", new Login());
		model.addAttribute("users", loginRepo.findAll());

		return "/Users";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Login login, Model model,HttpSession session) {
		model.addAttribute("login", new Login());
		Login loginDb = loginRepo.findByUserNameAndPassword(login.getUserName(), login.getPassword());
		if (loginDb == null) {
			model.addAttribute("loginError", "Invalid user name and password");
			return "/login";
		}
		loginDb.setLastModifiedDate(PapisUtils.getDate());
		loginRepo.save(loginDb);
		session.setAttribute("userName", loginDb.getUserName());
		return "/index";
	}
	
	@GetMapping(value = "/index")
	public String index(Model model) {
		
		return "/index";
	}
	
	@GetMapping(value = "/logout")
	public String logout(Model model,HttpSession session) {
		session.removeAttribute("userName");
		return "redirect:/";
	}

}
