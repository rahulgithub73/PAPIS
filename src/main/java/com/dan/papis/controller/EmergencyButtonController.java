package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.EmergencyButton;
import com.dan.papis.repository.EmergencyButtonRepo;

@Controller
public class EmergencyButtonController {

	@Autowired
	EmergencyButtonRepo emergencyButtonRepo;

	@GetMapping("/emergencyButton")
	public String emergencyButton(Model model) {
		model.addAttribute("emergencyButtons", emergencyButtonRepo.findAll());
		model.addAttribute("emergencyButton", new EmergencyButton());
		return "/EmergencyButton";
	}

	@GetMapping("/addEmergencyButton")
	public String addEmergencyButton(Model model) {
		model.addAttribute("emergencyButtons", emergencyButtonRepo.findAll());
		model.addAttribute("emergencyButton", new EmergencyButton());
		return "/AddEmergencyButton";
	}

	@PostMapping("/addEmergencyButton")
	public String addEmergencyButton(@ModelAttribute EmergencyButton emergencyButton, Model model) {
		emergencyButtonRepo.save(emergencyButton);
		this.updateModel(model);
		return "/EmergencyButton";
	}

	@GetMapping("/editEmergencyButton/{id}")
	public String editEmergencyButton(@PathVariable Long id, Model model) {

		Optional<EmergencyButton> emergencyButton = emergencyButtonRepo.findById(id);
		if (emergencyButton.isPresent()) {
			model.addAttribute("emergencyButton", emergencyButton.get());
		}
		model.addAttribute("emergencyButtons", emergencyButtonRepo.findAll());

		return "/EmergencyButton";
	}

	@GetMapping("/deleteEmergencyButton/{id}")
	public String deleteEmergencyButton(@PathVariable Long id, Model model) {

		Optional<EmergencyButton> emergencyButton = emergencyButtonRepo.findById(id);
		if (emergencyButton.isPresent()) {
			emergencyButtonRepo.delete(emergencyButton.get());
		}

		this.updateModel(model);
		return "/EmergencyButton";
	}

	private void updateModel(Model model) {
		model.addAttribute("emergencyButtons", emergencyButtonRepo.findAll());
		model.addAttribute("emergencyButton", new EmergencyButton());
	}

}
