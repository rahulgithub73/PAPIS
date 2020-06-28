package com.dan.papis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SyncController {
	
	@GetMapping("/sync")
	public String backup(Model model) {
		return "/Sync";
	}

}
