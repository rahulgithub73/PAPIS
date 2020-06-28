package com.dan.papis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.papis.constant.PapisConfigConstant;
import com.dan.papis.entity.DeviceConfiguration;
import com.dan.papis.repository.DeviceConfigurationRepository;

@Service
public class DeviceConfigurationService {
	
	@Autowired
	PapisConfigConstant papisConfigConstant; 
	
	@Autowired
	DeviceConfigurationRepository deviceConfigurationRepository;
	
	public List<DeviceConfiguration> getAlldevices(){
		
		List<DeviceConfiguration> list = deviceConfigurationRepository.findByDeviceType(papisConfigConstant.getDeviceType());
		return list;
		
	}
	
	
	public Map<Integer,String> getDeviceType(){
		
		Map<Integer,String> map = new HashMap<>();
		map.put(1, "LED dislination board");
		map.put(2, "LED slave display board");
		map.put(3, "LCD slave display board");
		map.put(4, "PA system");
		map.put(5, "Emergency switch button");
		
		return map;
	}

}
