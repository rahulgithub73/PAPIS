package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.Languages;
import com.dan.papis.repository.LanguagesRepo;
import com.dan.papis.service.DeviceConfigurationService;

@Controller
public class LanguagesController {

	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@Autowired
	LanguagesRepo languagesRepo;

	@GetMapping("/languages")
	public String languages(Model model) {
		model.addAttribute("languagess", languagesRepo.findAll());
		model.addAttribute("languages", new Languages());
		return "/Languages";
	}

	@GetMapping("/addLanguages")
	public String addLanguages(Model model) {
		model.addAttribute("languages", new Languages());
		return "/AddLanguages";
	}

	@PostMapping("/addLanguages")
	public String addLanguages(@ModelAttribute Languages languages, Model model) {
		languagesRepo.save(languages);
		this.updateModel(model);
		return "/Languages";
	}

	@GetMapping("/editLanguages/{id}")
	public String editLanguages(@PathVariable Long id, Model model) {

		Optional<Languages> languages = languagesRepo.findById(id);
		if (languages.isPresent()) {
			model.addAttribute("languages", languages.get());
		}
		model.addAttribute("languagess", languagesRepo.findAll());

		return "/Languages";
	}

	@GetMapping("/deleteLanguages/{id}")
	public String deleteLanguages(@PathVariable Long id, Model model) {

		Optional<Languages> languages = languagesRepo.findById(id);
		if (languages.isPresent()) {
			languagesRepo.delete(languages.get());
		}

		this.updateModel(model);
		return "/Languages";
	}

	private void updateModel(Model model) {
		model.addAttribute("languagess", languagesRepo.findAll());
		model.addAttribute("languages", new Languages());
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("trains", deviceConfigurationService.getTrains());

	}

}
