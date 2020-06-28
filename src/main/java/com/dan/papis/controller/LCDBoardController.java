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
import com.dan.papis.repository.LCDBoardRepo;

@Controller
public class LCDBoardController {
	
	@Autowired
	LCDBoardRepo lCDBoardRepo;

	@GetMapping("/lCDBoard")
	public String lCDBoard(Model model) {
		model.addAttribute("lCDBoards", lCDBoardRepo.findAll());
		model.addAttribute("lCDBoard", new LCDBoard());
		return "/LCDBoard";
	}

	@GetMapping("/addLCDBoard")
	public String addLCDBoard(Model model) {
		model.addAttribute("lCDBoard", new LCDBoard());
		return "/AddLCDBoard";
	}

	@PostMapping("/addLCDBoard")
	public String addLCDBoard(@ModelAttribute LCDBoard lCDBoard, Model model) {
		lCDBoardRepo.save(lCDBoard);
		this.updateModel(model);
		return "/LCDBoard";
	}

	@GetMapping("/editLCDBoard/{id}")
	public String editLCDBoard(@PathVariable Long id, Model model) {

		Optional<LCDBoard> lCDBoard = lCDBoardRepo.findById(id);
		if (lCDBoard.isPresent()) {
			model.addAttribute("lCDBoard", lCDBoard.get());
		}
		model.addAttribute("lCDBoards", lCDBoardRepo.findAll());

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
		model.addAttribute("lCDBoards", lCDBoardRepo.findAll());
		model.addAttribute("lCDBoard", new LCDBoard());
	}
}
