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
import com.dan.papis.entity.PASystem;
import com.dan.papis.repository.PASystemRepo;
import com.dan.papis.service.DeviceConfigurationService;
import com.dan.papis.utils.PapisUtils;

@Controller
public class PASystemController {

	@Autowired
	PASystemRepo paSystemRepo;

	@Autowired
	PapisConfigConstant papisConfigConstant;

	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@GetMapping("/paSystem")
	public String paSystem(Model model) {
		model.addAttribute("paSystems", getConfiguration());
		PASystem paSystem = new PASystem();
		paSystem.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(4));
		model.addAttribute("paSystem",paSystem);
		return "/PASystem";
	}

	@GetMapping("/addPASystem")
	public String addPASystem(Model model) {
		PASystem paSystem = new PASystem();
		paSystem.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(4));
		model.addAttribute("paSystem",paSystem);
		return "/AddPASystem";
	}

	@PostMapping("/addPASystem")
	public String addPASystem(@ModelAttribute PASystem paSystem, Model model) {
		paSystem.setLastModifiedDate(PapisUtils.getDate());
		paSystem.setStatus(PapisConstant.WORKING);
		paSystem.setDeviceTypeId(4);
		paSystemRepo.save(paSystem);
		this.updateModel(model);
		return "/PASystem";
	}

	@GetMapping("/editPASystem/{id}")
	public String editPASystem(@PathVariable Long id, Model model) {

		Optional<PASystem> paSystem = paSystemRepo.findById(id);
		if (paSystem.isPresent()) {
			PASystem pa = paSystem.get();
			pa.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(4));
			model.addAttribute("paSystem", pa);
		}
		model.addAttribute("paSystems", getConfiguration());

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
		model.addAttribute("paSystems", getConfiguration());
		PASystem paSystem = new PASystem();
		paSystem.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(4));
		model.addAttribute("paSystem", paSystem);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	private List<PASystem> getConfiguration() {

		List<PASystem> list = paSystemRepo.findAll();
		List<PASystem> objects = new ArrayList<>();
		for (PASystem dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(4));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}

}
