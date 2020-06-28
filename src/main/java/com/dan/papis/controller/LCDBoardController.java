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
import com.dan.papis.repository.LCDBoardRepo;
import com.dan.papis.service.DeviceConfigurationService;
import com.dan.papis.utils.PapisUtils;

@Controller
public class LCDBoardController {
	
	@Autowired
	LCDBoardRepo lCDBoardRepo;
	
	@Autowired
	PapisConfigConstant papisConfigConstant;

	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@GetMapping("/lCDBoard")
	public String lCDBoard(Model model) {
		model.addAttribute("lCDBoards", getConfiguration());
		LCDBoard lCDBoard = new LCDBoard();
		lCDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(3));
		model.addAttribute("lCDBoard", lCDBoard);
		return "/LCDBoard";
	}

	@GetMapping("/addLCDBoard")
	public String addLCDBoard(Model model) {
		LCDBoard lCDBoard = new LCDBoard();
		lCDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(3));
		model.addAttribute("lCDBoard", lCDBoard);
		return "/AddLCDBoard";
	}

	@PostMapping("/addLCDBoard")
	public String addLCDBoard(@ModelAttribute LCDBoard lCDBoard, Model model) {
		lCDBoard.setLastModifiedDate(PapisUtils.getDate());
		lCDBoard.setStatus(PapisConstant.WORKING);
		lCDBoard.setDeviceTypeId(1);
		lCDBoardRepo.save(lCDBoard);
		this.updateModel(model);
		return "/LCDBoard";
	}

	@GetMapping("/editLCDBoard/{id}")
	public String editLCDBoard(@PathVariable Long id, Model model) {

		Optional<LCDBoard> lCDBoard = lCDBoardRepo.findById(id);
		if (lCDBoard.isPresent()) {
			LCDBoard pa = lCDBoard.get();
			pa.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(3));
			model.addAttribute("lCDBoard", pa);
		}
		model.addAttribute("lCDBoards", getConfiguration());

		return "/LCDBoard";
	}

	@GetMapping("/deleteLCDBoard/{id}")
	public String deleteLCDBoard(@PathVariable Long id, Model model) {

		Optional<LCDBoard> lCDBoard = lCDBoardRepo.findById(id);
		if (lCDBoard.isPresent()) {
			lCDBoardRepo.delete(lCDBoard.get());
		}

		this.updateModel(model);
		return "/LCDBoard";
	}

	private void updateModel(Model model) {
		model.addAttribute("lCDBoards", getConfiguration());
		LCDBoard lCDBoard = new LCDBoard();
		lCDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(3));
		model.addAttribute("lCDBoard", lCDBoard);
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	private List<LCDBoard> getConfiguration() {

		List<LCDBoard> list = lCDBoardRepo.findAll();
		List<LCDBoard> objects = new ArrayList<>();
		for (LCDBoard dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName(deviceConfigurationService.getDeviceType().get(3));
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}
}
