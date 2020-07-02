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
import com.dan.papis.entity.PeriphralDevices;
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
		PASystem paSystem = new PASystem();
		List<PASystem> list = paSystemRepo.findAll();
		if(!list.isEmpty()) {
			model.addAttribute("lists", getConfiguration(list.get(0).getDeviceId()));
			paSystem.setDeviceId(list.get(0).getDeviceId());
		}else {
			model.addAttribute("lists", list);
			
		}
		paSystem.setPeriDeviceType("4");
		model.addAttribute("paSystem",paSystem);
		return "/PASystem";
	}

	@GetMapping("/addPASystem/{deviceId}")
	public String addPASystem(Model model,@PathVariable String deviceId) {
		model.addAttribute("lists", getConfiguration(deviceId));
		PASystem paSystem = new PASystem();
		paSystem.setDeviceId(deviceId);
		paSystem.setPeriDeviceType("4");
		model.addAttribute("paSystem",paSystem);
		return "/AddPASystem";
	}

	@PostMapping("/addPASystem")
	public String addPASystem(@ModelAttribute PASystem paSystem, Model model) {
		paSystem.setLastModifiedDate(PapisUtils.getDate());
		paSystem.setStatus(PapisConstant.WORKING);
		paSystem.setDeviceTypeId(4);
		paSystemRepo.save(paSystem);
		this.updateModel(model,paSystem.getDeviceId());
		return "/PASystem";
	}

	@GetMapping("/editPASystem/{id}")
	public String editPASystem(@PathVariable Long id, Model model) {

		Optional<PASystem> paSystem = paSystemRepo.findById(id);
		if (paSystem.isPresent()) {
			PASystem pa = paSystem.get();
			pa.setPeriDeviceType("4");
			model.addAttribute("paSystem", pa);
			model.addAttribute("lists", getConfiguration(pa.getDeviceId()));
		}
		

		return "/PASystem";
	}

	@GetMapping("/deletePASystem/{id}")
	public String deletePASystem(@PathVariable Long id, Model model) {

		Optional<PASystem> paSystem = paSystemRepo.findById(id);
		if (paSystem.isPresent()) {
			paSystemRepo.delete(paSystem.get());
			this.updateModel(model,paSystem.get().getDeviceId());
		}

		
		return "/PASystem";
	}

	private void updateModel(Model model,String deviceId) {
		model.addAttribute("lists", getConfiguration(deviceId));
		PASystem paSystem = new PASystem();
		paSystem.setPeriDeviceType("4");
		paSystem.setDeviceId(deviceId);
		model.addAttribute("paSystem", paSystem);
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	private List<PeriphralDevices>  getConfiguration(String deviceId) {
        return deviceConfigurationService.getAllPeriphralDevice(deviceId);
     }
	private List<PASystem> getConfiguration1() {

		List<PASystem> list = paSystemRepo.findAll();
		List<PASystem> objects = new ArrayList<>();
		for (PASystem dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName("4");
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}

}
