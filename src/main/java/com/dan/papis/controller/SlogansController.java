package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.Slogans;
import com.dan.papis.repository.SlogansRepo;
import com.dan.papis.utils.PapisUtils;

@Controller
public class SlogansController {
	
	@Autowired
	SlogansRepo slogansRepo;

	@GetMapping("/slogans")
	public String slogans(Model model) {
		model.addAttribute("sloganss", slogansRepo.findAll());
		model.addAttribute("slogans", new Slogans());
		return "/Slogans";
	}

	@GetMapping("/addSlogans")
	public String addSlogans(Model model) {
		model.addAttribute("slogans", new Slogans());
		return "/AddSlogans";
	}

	@PostMapping("/addSlogans")
	public String addSlogans(@ModelAttribute Slogans slogans, Model model) {
		slogans.setLastModifiedDate(PapisUtils.getDate());
		slogansRepo.save(slogans);
		this.updateModel(model);
		return "/Slogans";
	}

	@GetMapping("/editSlogans/{id}")
	public String editSlogans(@PathVariable Long id, Model model) {

		Optional<Slogans> slogans = slogansRepo.findById(id);
		if (slogans.isPresent()) {
			model.addAttribute("slogans", slogans.get());
		}
		model.addAttribute("sloganss", slogansRepo.findAll());

		return "/Slogans :: responsiveDeviceModal";
	}

	@GetMapping("/deleteSlogans/{id}")
	public String deleteSlogans(@PathVariable Long id, Model model) {

		Optional<Slogans> slogans = slogansRepo.findById(id);
		if (slogans.isPresent()) {
			slogansRepo.delete(slogans.get());
		}

		this.updateModel(model);
		return "/Slogans";
	}

	private void updateModel(Model model) {
		model.addAttribute("sloganss", slogansRepo.findAll());
		model.addAttribute("slogans", new Slogans());
	}

}
