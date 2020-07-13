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
import com.dan.papis.entity.IntensitySetting;
import com.dan.papis.entity.LEDBoard;
import com.dan.papis.repository.IntensitySettingRepo;
import com.dan.papis.repository.LEDBoardRepo;
import com.dan.papis.service.DeviceConfigurationService;
import com.dan.papis.utils.PapisUtils;

@Controller
public class IntensitySettingController {

	@Autowired
	IntensitySettingRepo intensitySettingRepo;

	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@Autowired
	LEDBoardRepo lEDBoardRepo;

	@GetMapping("/intensitySetting")
	public String intensitySetting(Model model) {
		model.addAttribute("intensitySettings", getAll());
		model.addAttribute("deviceId", "");
		model.addAttribute("intensitySetting", new IntensitySetting());
		return "/IntensitySetting";
	}

	@GetMapping("/addIntensitySetting")
	public String addIntensitySetting(Model model) {
		model.addAttribute("intensitySetting", new IntensitySetting());
		model.addAttribute("deviceId", "");
		return "/AddIntensitySetting";
	}

	@PostMapping("/addIntensitySetting")
	public String addIntensitySetting(@ModelAttribute IntensitySetting intensitySetting, Model model) {
		intensitySettingRepo.save(intensitySetting);
		model.addAttribute("deviceId", intensitySetting.getDeviceId());
		this.updateModel(model);
		return "/IntensitySetting";
	}

	@GetMapping("/editIntensitySetting/{id}")
	public String editIntensitySetting(@PathVariable Long id, Model model) {

		Optional<IntensitySetting> intensitySetting = intensitySettingRepo.findById(id);
		if (intensitySetting.isPresent()) {
			model.addAttribute("intensitySetting", intensitySetting.get());
			model.addAttribute("deviceId", intensitySetting.get().getDeviceId());
		}else {
			model.addAttribute("deviceId", "");
		}
		model.addAttribute("intensitySettings", getAll());

		return "/IntensitySetting :: responsiveDeviceModal";
	}

	@GetMapping("/deleteIntensitySetting/{id}")
	public String deleteIntensitySetting(@PathVariable Long id, Model model) {

		Optional<IntensitySetting> intensitySetting = intensitySettingRepo.findById(id);
		if (intensitySetting.isPresent()) {
			intensitySettingRepo.delete(intensitySetting.get());
			model.addAttribute("deviceId", intensitySetting.get().getDeviceId());
		}else {
			model.addAttribute("deviceId", "");
		}

		this.updateModel(model);
		return "/IntensitySetting";
	}
	
	@GetMapping("/findIntensitySetting/{deviceId}")
	public String findIntensitySetting(@PathVariable Long deviceId, Model model) {
		List<IntensitySetting> objects = new ArrayList<>();
		List<IntensitySetting>  list = intensitySettingRepo.findByDeviceId(deviceId);
		for (IntensitySetting dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(dc.getDeviceTypeId()));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}
		model.addAttribute("intensitySettings", objects);
		model.addAttribute("deviceId", deviceId);
		IntensitySetting intensitySetting = new IntensitySetting();
		intensitySetting.setDeviceId(deviceId);
		model.addAttribute("intensitySetting",intensitySetting );
		return "/IntensitySetting";
	}

	private void updateModel(Model model) {
		model.addAttribute("intensitySettings", getAll());
		model.addAttribute("intensitySetting", new IntensitySetting());

	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
		model.addAttribute("preDevices", this.getBoardHardwareId());
		
	}
	
	
	private List<LEDBoard> getBoardHardwareId() {
		List<LEDBoard> listReturn = new ArrayList();
		List<LEDBoard> list = lEDBoardRepo.findByDeviceTypeId(1);
		if(list != null && list.size()>0) {
			for(LEDBoard ob : list) {
				ob.setBoardHardwareId(this.getBoardHardwareId(ob));
				listReturn.add(ob);
			}
		}
		return listReturn;
		
		
	}
	
	private String getBoardHardwareId(LEDBoard dc) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(dc.getVendorCode());
		sb.append("-");
		sb.append(dc.getYear());
		sb.append("-");
		sb.append(dc.getUniqueSerialNumber());
		return sb.toString();
		
	}
	
	

	private List<IntensitySetting> getAll() {
		List<IntensitySetting> objects = new ArrayList<>();
		List<DeviceConfiguration> dcList = deviceConfigurationService.getAlldevices();
		List<IntensitySetting> list = null;
		if (dcList != null && dcList.size() > 0) {
			Long deviceTypeId = dcList.get(0).getDeviceId();
			list = intensitySettingRepo.findByDeviceId(deviceTypeId);

		} else {
			list = intensitySettingRepo.findAll();
		}

		for (IntensitySetting dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(dc.getDeviceTypeId()));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}

	
}
