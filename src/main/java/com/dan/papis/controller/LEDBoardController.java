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
import com.dan.papis.entity.LCDBoard;
import com.dan.papis.entity.LEDBoard;
import com.dan.papis.entity.PASystem;
import com.dan.papis.repository.LEDBoardRepo;
import com.dan.papis.service.DeviceConfigurationService;
import com.dan.papis.utils.PapisUtils;

@Controller
public class LEDBoardController {
	
	@Autowired
	LEDBoardRepo lEDBoardRepo;

	@Autowired
	PapisConfigConstant papisConfigConstant;

	@Autowired
	DeviceConfigurationService deviceConfigurationService;
	
	@GetMapping("/lEDBoard")
	public String lEDBoard(Model model) {
		model.addAttribute("lEDBoards", getConfiguration());
		LEDBoard lEDBoard = new LEDBoard();
		lEDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(1));
		model.addAttribute("lEDBoard", lEDBoard);
		return "/LEDBoard";
	}

	@GetMapping("/addLEDBoard/{deviceId}")
	public String addLEDBoard(Model model, @PathVariable String deviceId) {
		LEDBoard lEDBoard = new LEDBoard();
		lEDBoard.setDeviceId(deviceId);
		lEDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(1));
		model.addAttribute("lEDBoard", lEDBoard);
		model.addAttribute("lEDBoards", getConfiguration());
		return "/LEDBoard";
	}

	@PostMapping("/addLEDBoard")
	public String addLEDBoard(@ModelAttribute LEDBoard lEDBoard, Model model) {
		lEDBoard.setLastModifiedDate(PapisUtils.getDate());
		lEDBoard.setStatus(PapisConstant.WORKING);
		lEDBoard.setDeviceTypeId(1);
		lEDBoardRepo.save(lEDBoard);
		this.updateModel(model);
		return "/LEDBoard";
	}

	@GetMapping("/editLEDBoard/{id}")
	public String editLEDBoard(@PathVariable Long id, Model model) {

		Optional<LEDBoard> lEDBoard = lEDBoardRepo.findById(id);
		if (lEDBoard.isPresent()) {
			LEDBoard pa = lEDBoard.get();
			pa.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(1));
			model.addAttribute("lEDBoard", pa);
		}
		model.addAttribute("lEDBoards", getConfiguration());

		return "/LEDBoard";
	}

	@GetMapping("/deleteLEDBoard/{id}")
	public String deleteLEDBoard(@PathVariable Long id, Model model) {

		Optional<LEDBoard> lEDBoard = lEDBoardRepo.findById(id);
		if (lEDBoard.isPresent()) {
			lEDBoardRepo.delete(lEDBoard.get());
		}

		this.updateModel(model);
		return "/LEDBoard";
	}

	private void updateModel(Model model) {
		model.addAttribute("lEDBoards", getConfiguration());
		LEDBoard lEDBoard = new LEDBoard();
		lEDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(1));
		model.addAttribute("lEDBoard", lEDBoard);
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	private List<LEDBoard> getConfiguration() {

		List<LEDBoard> list = lEDBoardRepo.findAll();
		List<LEDBoard> objects = new ArrayList<>();
		for (LEDBoard dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(1));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}

}
