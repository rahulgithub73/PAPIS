package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.PASystem;
import com.dan.papis.repository.PASystemRepo;

@Controller
public class PASystemController {

	@Autowired
	PASystemRepo paSystemRepo;

	@GetMapping("/paSystem")
	public String paSystem(Model model) {
		model.addAttribute("paSystems", paSystemRepo.findAll());
		model.addAttribute("paSystem", new PASystem());
		return "/PASystem";
	}

	@GetMapping("/addPASystem")
	public String addPASystem(Model model) {
		model.addAttribute("paSystem", new PASystem());
		return "/AddPASystem";
	}

	@PostMapping("/addPASystem")
	public String addPASystem(@ModelAttribute PASystem paSystem, Model model) {
		paSystemRepo.save(paSystem);
		this.updateModel(model);
		return "/PASystem";
	}

	@GetMapping("/editPASystem/{id}")
	public String editPASystem(@PathVariable Long id, Model model) {

		Optional<PASystem> paSystem = paSystemRepo.findById(id);
		if (paSystem.isPresent()) {
			model.addAttribute("paSystem", paSystem.get());
		}
		model.addAttribute("paSystems", paSystemRepo.findAll());

		return "/PASystem";
	}

	@GetMapping("/deletePASystem/{id}")
	public String deletePASystem(@PathVariable Long id, Model model) {

		Optional<PASystem> paSystem = paSystemRepo.findById(id);
		if (paSystem.isPresent()) {
			paSystemRepo.delete(paSystem.get());
		}

		this.updateModel(model);
		return "/PASystem";
	}

	private void updateModel(Model model) {
		model.addAttribute("paSystems", paSystemRepo.findAll());
		model.addAttribute("paSystem", new PASystem());
	}
}
