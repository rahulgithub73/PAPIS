package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.IntensitySetting;
import com.dan.papis.repository.IntensitySettingRepo;

@Controller
public class IntensitySettingController {

	@Autowired
	IntensitySettingRepo intensitySettingRepo;

	@GetMapping("/intensitySetting")
	public String intensitySetting(Model model) {
		model.addAttribute("intensitySettings", intensitySettingRepo.findAll());
		model.addAttribute("intensitySetting", new IntensitySetting());
		return "/IntensitySetting";
	}

	@GetMapping("/addIntensitySetting")
	public String addIntensitySetting(Model model) {
		model.addAttribute("intensitySetting", new IntensitySetting());
		return "/AddIntensitySetting";
	}

	@PostMapping("/addIntensitySetting")
	public String addIntensitySetting(@ModelAttribute IntensitySetting intensitySetting, Model model) {
		intensitySettingRepo.save(intensitySetting);
		this.updateModel(model);
		return "/IntensitySetting";
	}

	@GetMapping("/editIntensitySetting/{id}")
	public String editIntensitySetting(@PathVariable Long id, Model model) {

		Optional<IntensitySetting> intensitySetting = intensitySettingRepo.findById(id);
		if (intensitySetting.isPresent()) {
			model.addAttribute("intensitySetting", intensitySetting.get());
		}
		model.addAttribute("intensitySettings", intensitySettingRepo.findAll());

		return "/IntensitySetting :: responsiveDeviceModal";
	}

	@GetMapping("/deleteIntensitySetting/{id}")
	public String deleteIntensitySetting(@PathVariable Long id, Model model) {

		Optional<IntensitySetting> intensitySetting = intensitySettingRepo.findById(id);
		if (intensitySetting.isPresent()) {
			intensitySettingRepo.delete(intensitySetting.get());
		}

		this.updateModel(model);
		return "/IntensitySetting";
	}

	private void updateModel(Model model) {
		model.addAttribute("intensitySettings", intensitySettingRepo.findAll());
		model.addAttribute("intensitySetting", new IntensitySetting());
	}

}
