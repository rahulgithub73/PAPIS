package com.dan.papis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.constant.PapisConstant;
import com.dan.papis.entity.DeviceConfiguration;
import com.dan.papis.repository.DeviceConfigurationRepository;
import com.dan.papis.utils.PapisUtils;

@Controller
public class DeviceConfigurationController {

	@Autowired
	DeviceConfigurationRepository deviceConfigurationRepository;

	@GetMapping("/deviceConfiguration")
	public String deviceConfiguration(Model model) {
		model.addAttribute("deviceConfigurations", getDeviceConfiguration());
		model.addAttribute("deviceConfiguration", new DeviceConfiguration());
		return "/DeviceConfiguration";
	}

	@GetMapping("/addDeviceConfiguration")
	public String addDeviceConfiguration(Model model) {
		this.updateModel(model);
		return "/AddPeripheralDevice";
	}

	@PostMapping("/addDeviceConfiguration")
	public String addDeviceConfiguration(@ModelAttribute DeviceConfiguration deviceConfiguration, Model model) {
		deviceConfiguration.setLastModifiedDate(PapisUtils.getDate());
		deviceConfiguration.setStatus(PapisConstant.WORKING);
		deviceConfigurationRepository.save(deviceConfiguration);
		this.updateModel(model);
		return "/DeviceConfiguration";
	}

	@GetMapping("/editDeviceConfiguration/{deviceId}")
	public String editDeviceConfiguration(@PathVariable String deviceId, Model model) {

		Optional<DeviceConfiguration> deviceConfiguration = deviceConfigurationRepository.findById(deviceId);
		if (deviceConfiguration.isPresent()) {
			model.addAttribute("deviceConfiguration", deviceConfiguration.get());
		}
		model.addAttribute("deviceConfigurations", getDeviceConfiguration());

		return "DeviceConfiguration :: responsiveDeviceModal";
	}

	@GetMapping("/deleteDeviceConfiguration/{deviceId}")
	public String deleteDeviceConfiguration(@PathVariable String deviceId, Model model) {

		Optional<DeviceConfiguration> deviceConfiguration = deviceConfigurationRepository.findById(deviceId);
		if (deviceConfiguration.isPresent()) {
			deviceConfigurationRepository.delete(deviceConfiguration.get());
		}

		this.updateModel(model);
		return "/DeviceConfiguration";
	}

	private void updateModel(Model model) {
		model.addAttribute("deviceConfigurations", getDeviceConfiguration());
		model.addAttribute("deviceConfiguration", new DeviceConfiguration());
	}

	public Map<String, String> getZonal() {
		Map<String, String> zonal = new HashMap<>();
		zonal.put("CR", "Central Railway");
		zonal.put("NR", "Northern Railway");
		zonal.put("NER", "North Eastern Railway");
		zonal.put("NFR", "Northeast Frontier Railway");
		zonal.put("ER", "Eastern Railway");
		zonal.put("ESR", "South Eastern Railway");
		zonal.put("SCR", "South Central Railway");
		zonal.put("SR", "Southern Railway");
		zonal.put("WR", "Western Railway");
		zonal.put("SWR", "South Western Railway");
		zonal.put("NWR", "North Western Railway");
		zonal.put("WCR", "West Central Railway");
		zonal.put("NCR", "North Central Railway");
		zonal.put("SECR", "South East Central Railway");
		zonal.put("ECR", "ECoR");
		zonal.put("ECR", "East Central Railway");
		zonal.put("MTP", "Metro Railway");
		zonal.put("SCoR", "South Coast Railway");

		return zonal;
	}

	private List<DeviceConfiguration> getDeviceConfiguration() {

		List<DeviceConfiguration> list = deviceConfigurationRepository.findAll();
		List<DeviceConfiguration> objects = new ArrayList<>();
		for (DeviceConfiguration dc : list) {
			if (!StringUtils.isEmpty(dc.getZonalRailway())) {
				dc.setZonalRailwayDisplay(getZonal().get(dc.getZonalRailway()));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}

}
