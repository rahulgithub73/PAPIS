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

import com.dan.papis.constant.PapisConfigConstant;
import com.dan.papis.constant.PapisConstant;
import com.dan.papis.entity.IdNameVO;
import com.dan.papis.entity.LEDBoard;
import com.dan.papis.entity.PeriphralDevices;
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
		LEDBoard lEDBoard = new LEDBoard();
		List<LEDBoard> list = lEDBoardRepo.findAll();
		if(!list.isEmpty()) {
			model.addAttribute("lists", getConfiguration(list.get(0).getDeviceId()));
			lEDBoard.setDeviceId(list.get(0).getDeviceId());
		}else {
			model.addAttribute("lists", list);
		
		}
		lEDBoard.setPeriDeviceType("1");
		model.addAttribute("lEDBoard", lEDBoard);
		return "/LEDBoard";
	}

	@GetMapping("/addLEDBoard/{deviceId}")
	public String addLEDBoard(Model model, @PathVariable String deviceId) {
		LEDBoard lEDBoard = new LEDBoard();
		lEDBoard.setDeviceId(deviceId);
		lEDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(1));
		model.addAttribute("lEDBoard", lEDBoard);
		model.addAttribute("lists", getConfiguration(deviceId));
		return "/LEDBoard";
	}

	@PostMapping("/addLEDBoard")
	public String addLEDBoard(@ModelAttribute LEDBoard lEDBoard, Model model) {
		lEDBoard.setLastModifiedDate(PapisUtils.getDate());
		lEDBoard.setStatus(PapisConstant.WORKING);
		lEDBoard.setDeviceTypeId(Integer.valueOf(lEDBoard.getPeriDeviceType()));
		lEDBoardRepo.save(lEDBoard);
		this.updateModel(model,lEDBoard.getDeviceId(),lEDBoard.getDeviceTypeId());
		return "/LEDBoard";
	}

	@GetMapping("/editLEDBoard/{id}")
	public String editLEDBoard(@PathVariable Long id, Model model) {

		Optional<LEDBoard> lEDBoard = lEDBoardRepo.findById(id);
		if (lEDBoard.isPresent()) {
			LEDBoard pa = lEDBoard.get();
			pa.setPeriDeviceType(String.valueOf(pa.getDeviceTypeId()));
			model.addAttribute("lEDBoard", pa);
			model.addAttribute("lists", getConfiguration(pa.getDeviceId()));
		}
		

		return "/LEDBoard";
	}

	@GetMapping("/deleteLEDBoard/{id}")
	public String deleteLEDBoard(@PathVariable Long id, Model model) {

		Optional<LEDBoard> lEDBoard = lEDBoardRepo.findById(id);
		if (lEDBoard.isPresent()) {
			lEDBoardRepo.delete(lEDBoard.get());
			this.updateModel(model,lEDBoard.get().getDeviceId(),lEDBoard.get().getDeviceTypeId());
		}

		
		return "/LEDBoard";
	}

	private void updateModel(Model model,String deviceId,Integer deviceTypeId) {
		model.addAttribute("lists", getConfiguration(deviceId));
		LEDBoard lEDBoard = new LEDBoard();
		lEDBoard.setDeviceId(deviceId);
		lEDBoard.setPeriDeviceType(String.valueOf(deviceTypeId));
		lEDBoard.setPeriDeviceType(deviceConfigurationService.getDeviceType().get(1));
		model.addAttribute("lEDBoard", lEDBoard);
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}

	
	private List<PeriphralDevices>  getConfiguration(String deviceId) {
        return deviceConfigurationService.getAllPeriphralDevice(deviceId);
     }
	
	@RequestMapping(value = "/ledLists/{deviceTypeId}", method = RequestMethod.GET)
	public @ResponseBody
	List<IdNameVO> findAllList(@PathVariable Integer deviceTypeId) {
		
		List<LEDBoard> list = lEDBoardRepo.findByDeviceTypeId(deviceTypeId);
		List<IdNameVO> lists = new ArrayList<>();
		for(LEDBoard ob : list) {
			IdNameVO idNameVO= new IdNameVO();
			idNameVO.setId(ob.getBoardHardwareId());
			idNameVO.setName(ob.getName());
			lists.add(idNameVO);
		}
		return lists;
	}

	private List<LEDBoard> getConfiguration1() {

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
