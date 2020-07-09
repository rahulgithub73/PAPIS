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

import com.dan.papis.entity.DeviceConfiguration;
import com.dan.papis.entity.Slogans;
import com.dan.papis.repository.SlogansRepo;
import com.dan.papis.service.DeviceConfigurationService;
import com.dan.papis.utils.PapisUtils;

@Controller
public class SlogansController {
	
	@Autowired
	SlogansRepo slogansRepo;
	
	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@GetMapping("/slogans")
	public String slogans(Model model) {
		model.addAttribute("sloganss", getAll());
		model.addAttribute("slogans", new Slogans());
		model.addAttribute("deviceId", "");
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
		model.addAttribute("deviceId", slogans.getDeviceId());
		slogansRepo.save(slogans);
		this.updateModel(model);
		return "/Slogans";
	}

	@GetMapping("/editSlogans/{id}")
	public String editSlogans(@PathVariable Long id, Model model) {

		Optional<Slogans> slogans = slogansRepo.findById(id);
		if (slogans.isPresent()) {
			model.addAttribute("slogans", slogans.get());
			model.addAttribute("deviceId", slogans.get().getDeviceId());
		}else {
			model.addAttribute("deviceId", "");
		}
		model.addAttribute("sloganss", getAll());

		return "/Slogans :: responsiveDeviceModal";
	}

	@GetMapping("/deleteSlogans/{id}")
	public String deleteSlogans(@PathVariable Long id, Model model) {

		Optional<Slogans> slogans = slogansRepo.findById(id);
		if (slogans.isPresent()) {
			slogansRepo.delete(slogans.get());
			model.addAttribute("deviceId",slogans.get().getDeviceId());
		}else {
			model.addAttribute("deviceId", "");
		}

		this.updateModel(model);
		return "/Slogans";
	}

	@GetMapping("/findSlogans/{deviceId}")
	public String findIntensitySetting(@PathVariable String deviceId, Model model) {
		List<Slogans> objects = new ArrayList<>();
		List<Slogans>  list = slogansRepo.findByDeviceId(deviceId);
		for (Slogans dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(dc.getDeviceTypeId()));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}
		model.addAttribute("sloganss", objects);
		model.addAttribute("deviceId", deviceId);
		Slogans slogans = new Slogans();
		slogans.setDeviceId(deviceId);
		model.addAttribute("slogans",slogans );
		return "/Slogans";
	}

	private void updateModel(Model model) {
		model.addAttribute("sloganss", getAll());
		model.addAttribute("slogans", new Slogans());
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
	    model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	
	private List<Slogans> getAll() {
		List<Slogans> objects = new ArrayList<>();
		List<DeviceConfiguration> dcList = deviceConfigurationService.getAlldevices();
		List<Slogans> list = null;
		if (dcList != null && dcList.size() > 0) {
			String deviceTypeId = dcList.get(0).getDeviceId();
			list = slogansRepo.findByDeviceId(deviceTypeId);

		} else {
			list = slogansRepo.findAll();
		}

		for (Slogans dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(dc.getDeviceTypeId()));
			}
			if(dc.getMessageTypeId() != null) {
				dc.setMessageType(deviceConfigurationService.getSlogansType().get(dc.getMessageTypeId()));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}


}
