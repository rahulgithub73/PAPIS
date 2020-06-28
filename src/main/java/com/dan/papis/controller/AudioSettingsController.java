package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.AudioSettings;
import com.dan.papis.entity.DeviceConfiguration;
import com.dan.papis.repository.AudioSettingsRepo;

@Controller
public class AudioSettingsController {

	@Autowired
	AudioSettingsRepo audioSettingsRepo;

	@GetMapping("/audioSetting")
	public String audioSetting(Model model) {
		model.addAttribute("audioSettings", audioSettingsRepo.findAll());
		model.addAttribute("audioSetting", new DeviceConfiguration());
		return "/AudioSettings";
	}

	@GetMapping("/addAudioSettings")
	public String addAudioSettings(Model model) {
		model.addAttribute("audioSetting", new AudioSettings());
		return "/AddAudioSettings";
	}

	@PostMapping("/addAudioSettings")
	public String addAudioSettings(@ModelAttribute AudioSettings audioSetting, Model model) {
		audioSettingsRepo.save(audioSetting);
		this.updateModel(model);
		return "/AudioSettings";
	}

	@GetMapping("/editAudioSettings/{id}")
	public String editAudioSettings(@PathVariable Long id, Model model) {

		Optional<AudioSettings> audioSetting = audioSettingsRepo.findById(id);
		if (audioSetting.isPresent()) {
			model.addAttribute("audioSetting", audioSetting.get());
		}
		model.addAttribute("audioSettings", audioSettingsRepo.findAll());

		return "/AudioSettings";
	}

	@GetMapping("/deleteAudioSettings/{id}")
	public String deleteAudioSettings(@PathVariable Long id, Model model) {

		Optional<AudioSettings> audioSetting = audioSettingsRepo.findById(id);
		if (audioSetting.isPresent()) {
			audioSettingsRepo.delete(audioSetting.get());
		}

		this.updateModel(model);
		return "/AudioSettings";
	}

	private void updateModel(Model model) {
		model.addAttribute("audioSettings", audioSettingsRepo.findAll());
		model.addAttribute("audioSetting", new AudioSettings());
	}

}
