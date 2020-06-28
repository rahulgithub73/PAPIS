package com.dan.papis.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dan.papis.entity.LCDBoard;
import com.dan.papis.entity.LEDBoard;
import com.dan.papis.repository.LEDBoardRepo;
import com.dan.papis.utils.PapisUtils;

@Controller
public class LEDBoardController {
	
	@Autowired
	LEDBoardRepo lEDBoardRepo;

	@GetMapping("/lEDBoard")
	public String lEDBoard(Model model) {
		model.addAttribute("lEDBoards", lEDBoardRepo.findAll());
		model.addAttribute("lEDBoard", new LCDBoard());
		return "/LEDBoard";
	}

	@GetMapping("/addLEDBoard/{deviceId}")
	public String addLEDBoard(Model model, @PathVariable String deviceId) {
		LEDBoard lEDBoard = new LEDBoard();
		lEDBoard.setDeviceId(deviceId);
		model.addAttribute("lEDBoard", lEDBoard);
		model.addAttribute("lEDBoards", lEDBoardRepo.findAll());
		return "/LEDBoard";
	}

	@PostMapping("/addLEDBoard")
	public String addLEDBoard(@ModelAttribute LEDBoard lEDBoard, Model model) {
		lEDBoard.setLastModifiedDate(PapisUtils.getDate());
		lEDBoard.setDeviceTypeName("LED Dislination Board");
		lEDBoardRepo.save(lEDBoard);
		this.updateModel(model);
		return "/LEDBoard";
	}

	@GetMapping("/editLEDBoard/{id}")
	public String editLEDBoard(@PathVariable Long id, Model model) {

		Optional<LEDBoard> lEDBoard = lEDBoardRepo.findById(id);
		if (lEDBoard.isPresent()) {
			model.addAttribute("lEDBoard", lEDBoard.get());
		}
		model.addAttribute("lEDBoards", lEDBoardRepo.findAll());

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
		model.addAttribute("lEDBoards", lEDBoardRepo.findAll());
		model.addAttribute("lEDBoard", new LEDBoard());
	}

}
