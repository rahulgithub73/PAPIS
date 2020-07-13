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
import com.dan.papis.entity.LCDBoard;
import com.dan.papis.entity.LEDBoard;
import com.dan.papis.entity.PeriphralDevices;
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
		LCDBoard lCDBoard = new LCDBoard();
		List<LCDBoard> list = lCDBoardRepo.findAll();
		if(!list.isEmpty()) {
			model.addAttribute("lists", getConfiguration(list.get(0).getDeviceId()));
			lCDBoard.setDeviceId(list.get(0).getDeviceId());
		}else {
			model.addAttribute("lists", list);
			
		}
		lCDBoard.setPeriDeviceType("3");
		model.addAttribute("lCDBoard", lCDBoard);
		return "/LCDBoard";
	}

	@GetMapping("/addLCDBoard/{deviceId}")
	public String addLCDBoard(Model model,@PathVariable Long deviceId) {
		model.addAttribute("lists", getConfiguration(deviceId));
		LCDBoard lCDBoard = new LCDBoard();
		lCDBoard.setDeviceId(deviceId);
		lCDBoard.setPeriDeviceType("3");
		model.addAttribute("lCDBoard", lCDBoard);
		return "/AddLCDBoard";
	}

	@PostMapping("/addLCDBoard")
	public String addLCDBoard(@ModelAttribute LCDBoard lCDBoard, Model model) {
		lCDBoard.setLastModifiedDate(PapisUtils.getDate());
		lCDBoard.setStatus(PapisConstant.WORKING);
		lCDBoard.setDeviceTypeId(3);
		lCDBoardRepo.save(lCDBoard);
		this.updateModel(model,lCDBoard.getDeviceId());
		return "/LCDBoard";
	}

	@GetMapping("/editLCDBoard/{id}")
	public String editLCDBoard(@PathVariable Long id, Model model) {

		Optional<LCDBoard> lCDBoard = lCDBoardRepo.findById(id);
		if (lCDBoard.isPresent()) {
			LCDBoard pa = lCDBoard.get();
			pa.setPeriDeviceType("3");
			model.addAttribute("lCDBoard", pa);
			model.addAttribute("lists", getConfiguration(pa.getDeviceId()));
		}
		

		return "/LCDBoard";
	}

	@GetMapping("/deleteLCDBoard/{id}")
	public String deleteLCDBoard(@PathVariable Long id, Model model) {

		Optional<LCDBoard> lCDBoard = lCDBoardRepo.findById(id);
		if (lCDBoard.isPresent()) {
			lCDBoardRepo.delete(lCDBoard.get());
			this.updateModel(model,lCDBoard.get().getDeviceId());
		}

		
		return "/LCDBoard";
	}

	private void updateModel(Model model,Long deviceId) {
		model.addAttribute("lists", getConfiguration(deviceId));
		LCDBoard lCDBoard = new LCDBoard();
		lCDBoard.setPeriDeviceType("3");
		lCDBoard.setDeviceId(deviceId);
		model.addAttribute("lCDBoard", lCDBoard);
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("deviceType", papisConfigConstant.getDeviceType());
		model.addAttribute("devices", deviceConfigurationService.getAlldevices());
	}
	
	private List<PeriphralDevices>  getConfiguration(Long deviceId) {
        return deviceConfigurationService.getAllPeriphralDevice(deviceId);
     }
	
	@RequestMapping(value = "/lcdLists/{deviceTypeId}", method = RequestMethod.GET)
	public @ResponseBody
	List<IdNameVO> findAllList(@PathVariable Integer deviceTypeId) {
		
		List<LCDBoard> list = lCDBoardRepo.findByDeviceTypeId(deviceTypeId);
		List<IdNameVO> lists = new ArrayList<>();
		for(LCDBoard ob : list) {
			IdNameVO idNameVO= new IdNameVO();
			idNameVO.setId(ob.getId());
			idNameVO.setName(this.getBoardHardwareId(ob));
			lists.add(idNameVO);
		}
		return lists;
	}

	private List<LCDBoard> getConfiguration1() {

		List<LCDBoard> list = lCDBoardRepo.findAll();
		List<LCDBoard> objects = new ArrayList<>();
		for (LCDBoard dc : list) {
			if (dc.getDeviceTypeId() != null) {
				dc.setDeviceTypeName("3");
			}
			if (dc.getLastModifiedDate() != null) {
				dc.setLastModified(PapisUtils.getFormattedDate(dc.getLastModifiedDate()));
			}

			objects.add(dc);
		}

		return objects;

	}
	
	private String getBoardHardwareId(LCDBoard dc) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(dc.getVendorCode());
		sb.append("-");
		sb.append(dc.getYear());
		sb.append("-");
		sb.append(dc.getUniqueSerialNumber());
		return sb.toString();
		
	}

}
