package com.dan.papis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.constant.PapisConfigConstant;
import com.dan.papis.constant.PapisConstant;
import com.dan.papis.entity.EmergencyButton;
import com.dan.papis.entity.PASystem;
import com.dan.papis.repository.EmergencyButtonRepo;
import com.dan.papis.service.DeviceConfigurationService;
import com.dan.papis.utils.PapisUtils;

@Controller
public class EmergencyButtonController {

	@Autowired
	EmergencyButtonRepo emergencyButtonRepo;

	@Autowired
	PapisConfigConstant papisConfigConstant;

	@Autowired
	DeviceConfigurationService deviceConfigurationService;
	
	@GetMapping("/emergencyButton")
	public String emergencyButton(Model model) {
		model.addAttribute("emergencyButtons", getConfiguration());
		EmergencyButton emergencyButton = new EmergencyButton();
		emergencyButton.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(5));
		model.addAttribute("emergencyButton", emergencyButton);
		return "/EmergencyButton";
	}

	@GetMapping("/addEmergencyButton")
	public String addEmergencyButton(Model model) {
		model.addAttribute("emergencyButtons", getConfiguration());
		EmergencyButton emergencyButton = new EmergencyButton();
		emergencyButton.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(5));
		model.addAttribute("emergencyButton", emergencyButton);
		return "/AddEmergencyButton";
	}

	@PostMapping("/addEmergencyButton")
	public String addEmergencyButton(@ModelAttribute EmergencyButton emergencyButton, Model model) {
		emergencyButton.setLastModifiedDate(PapisUtils.getDate());
		emergencyButton.setStatus(PapisConstant.WORKING);
		emergencyButtonRepo.save(emergencyButton);
		this.updateModel(model);
		return "/EmergencyButton";
	}

	@GetMapping("/editEmergencyButton/{id}")
	public String editEmergencyButton(@PathVariable Long id, Model model) {

		Optional<EmergencyButton> emergencyButton = emergencyButtonRepo.findById(id);
		if (emergencyButton.isPresent()) {
			EmergencyButton pa = emergencyButton.get();
			pa.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(5));
			model.addAttribute("emergencyButton", pa);
			
		}
		model.addAttribute("emergencyButtons", getConfiguration());

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
		model.addAttribute("emergencyButtons", getConfiguration());
		EmergencyButton emergencyButton = new EmergencyButton();
		emergencyButton.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(5));
		model.addAttribute("emergencyButton", emergencyButton);
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	private List<EmergencyButton> getConfiguration() {

		List<EmergencyButton> list = emergencyButtonRepo.findAll();
		List<EmergencyButton> objects = new ArrayList<>();
		for (EmergencyButton dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(5));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}

}
