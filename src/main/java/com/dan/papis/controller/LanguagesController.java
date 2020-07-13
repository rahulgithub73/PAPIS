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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dan.papis.entity.Languages;
import com.dan.papis.entity.TrainMaster;
import com.dan.papis.repository.LanguagesRepo;
import com.dan.papis.service.DeviceConfigurationService;

@Controller
public class LanguagesController {

	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@Autowired
	LanguagesRepo languagesRepo;

	@GetMapping("/languages")
	public String languages(Model model) {
		model.addAttribute("languagess", this.getLanguages());
		Languages lang = new Languages();
		List<TrainMaster> list= deviceConfigurationService.getTrains() ;
		if(list != null && list.size()> 0) {
			lang.setTrainNo(list.get(0).getTrainNumber());
			lang.setTrainNameEnglish(list.get(0).getTrainNameEnglish());
		}
		model.addAttribute("languages", lang);
		return "/Languages";
	}

	@GetMapping("/addLanguages")
	public String addLanguages(Model model) {
		
		model.addAttribute("languages", new Languages());
		
		return "/AddLanguages";
	}

	@PostMapping("/addLanguages")
	public String addLanguages(@ModelAttribute Languages languages, Model model) {
		languagesRepo.save(languages);
		this.updateModel(model);
		return "/Languages";
	}

	@GetMapping("/editLanguages/{id}")
	public String editLanguages(@PathVariable Long id, Model model) {

		Optional<Languages> languages = languagesRepo.findById(id);
		if (languages.isPresent()) {
			Languages lang = languages.get();
			lang.setTrainNameEnglish(
					deviceConfigurationService.getTrainByNumber(lang.getTrainNo()).getTrainNameEnglish());
			model.addAttribute("languages", lang);
		}
		model.addAttribute("languagess", this.getLanguages());

		return "/Languages :: responsiveDeviceModal";
	}

	@GetMapping("/deleteLanguages/{id}")
	public String deleteLanguages(@PathVariable Long id, Model model) {

		Optional<Languages> languages = languagesRepo.findById(id);
		if (languages.isPresent()) {
			languagesRepo.delete(languages.get());
		}

		this.updateModel(model);
		return "/Languages";
	}

	private void updateModel(Model model) {
		model.addAttribute("languagess", this.getLanguages());
		model.addAttribute("languages", new Languages());
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("trains", deviceConfigurationService.getTrains());

	}

	@RequestMapping(value = "/getTrain/{trainNo}", method = RequestMethod.GET)
	public @ResponseBody String getTrain(@PathVariable Long trainNo) {
		return deviceConfigurationService.getTrainByNumber(trainNo).getTrainNameEnglish();

	}

	private List<Languages> getLanguages() {
		List<Languages> list = languagesRepo.findAll();
		if (list.isEmpty()) {
			return list;
		}
		List<Languages> listNew = new ArrayList<>();

		for (Languages languages : list) {
			languages.setTrainNameEnglish(
					deviceConfigurationService.getTrainByNumber(languages.getTrainNo()).getTrainNameEnglish());
			listNew.add(languages);
		}
		return listNew;
	}

}
