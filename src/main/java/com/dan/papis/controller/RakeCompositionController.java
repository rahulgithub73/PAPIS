package com.dan.papis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dan.papis.entity.RakeComposition;
import com.dan.papis.entity.TrainMaster;
import com.dan.papis.entity.TrainRakeComposition;
import com.dan.papis.repository.TrainRakeCompositionRepo;
import com.dan.papis.service.DeviceConfigurationService;

@Controller
public class RakeCompositionController {

	@Autowired
	TrainRakeCompositionRepo trainRakeCompositionRepo;

	@Autowired
	DeviceConfigurationService deviceConfigurationService;

	@GetMapping("/RakeComposition")
	public String intensitySetting(Model model) {
		model.addAttribute("trainRakeComposition", this.getTrainRakeComposition(null));

		return "/RakeComposition";
	}

	@PostMapping("/saveRakeComposition")
	public String saveRakeComposition(Model model, TrainRakeComposition trainRakeComposition,
			BindingResult bindingResult) {
		int i = 1;
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMessage", "The submitted data has errors.");
		} else {
			for (RakeComposition cnct : trainRakeComposition.getRakeCompositions()) {
				cnct.setSequenceNumber(i);
				i++;
			}
			TrainRakeComposition trainRakeCompositionCopy = new TrainRakeComposition();
			trainRakeCompositionCopy= trainRakeComposition;
			trainRakeCompositionRepo.save(trainRakeComposition);
			model.addAttribute("trainRakeComposition",trainRakeCompositionCopy );
			model.addAttribute("successMessage", "RakeComposition saved successfully!");
		}

		return "/RakeComposition";
	}

	@PostMapping("/addRakeComposition")
	public String addRakeComposition(Model model, TrainRakeComposition trainRakeComposition) {

		if (trainRakeComposition.getRakeCompositions() != null
				&& trainRakeComposition.getRakeCompositions().size() > 25) {
			model.addAttribute("successMessage", "Rake composition can not add more then 26");
		} else {
			trainRakeComposition.getRakeCompositions().add(new RakeComposition());
		}

		model.addAttribute("trainRakeComposition", trainRakeComposition);

		return "RakeComposition :: RakeCompositions"; // returning the updated section
	}

	@PostMapping("/removeRakeComposition")
	public String removeRakeComposition(Model model, TrainRakeComposition trainRakeComposition,
			@RequestParam("removeDynamicRow") Long rakeIndex) {
		trainRakeComposition.getRakeCompositions().remove(rakeIndex.intValue());
		model.addAttribute("trainRakeComposition", trainRakeComposition);

		return "RakeComposition :: RakeCompositions"; // returning the updated section
	}

	@GetMapping("/fetchRakeComposition/{trainNo}")
	public String geRakeCompositionByTrainNo(Model model, @PathVariable Long trainNo) {

		model.addAttribute("trainRakeComposition", this.getTrainRakeComposition(trainNo));

		return "/RakeComposition"; 
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
		model.addAttribute("trains", deviceConfigurationService.getTrains());

	}

	private TrainRakeComposition getTrainRakeComposition(Long trainNumber) {
		TrainRakeComposition trainRakeComposition = new TrainRakeComposition();
		List<TrainMaster> trains = deviceConfigurationService.getTrains();

		if (trains != null && trains.size() > 0) {
			TrainMaster trainMaster = null;
			if (trainNumber == null) {
				trainMaster = trains.get(0);
			} else {
				trainMaster = deviceConfigurationService.getTrainByNumber(trainNumber);
			}

			Long trainNo = trainMaster.getTrainNumber();
			trainRakeComposition.setTrainNo(trainNo);
			trainRakeComposition.setTrainNumber(trainNumber);
			trainRakeComposition.setTrainNameEnglish(trainMaster.getTrainNameEnglish());
			trainRakeComposition.setDownTrainNo(trainMaster.getTrainReturnNo());
			trainRakeComposition.setDownTrainName(deviceConfigurationService
					.getTrainByReturnNumber(trainMaster.getTrainReturnNo()).getTrainNameEnglish());

			List<TrainRakeComposition> trainRakeCompositions = trainRakeCompositionRepo.findByTrainNo(trainNo);
			if (trainRakeCompositions == null || trainRakeCompositions.isEmpty()) {
				return trainRakeComposition;
			} else {
				trainRakeComposition.setId(trainRakeCompositions.get(0).getId());
				trainRakeComposition.setRakeCompositions(trainRakeCompositions.get(0).getRakeCompositions());
			}
		} else {
			trainRakeComposition = new TrainRakeComposition();
		}

		return trainRakeComposition;

	}

}
