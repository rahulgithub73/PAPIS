package com.dan.papis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.papis.constant.PapisConfigConstant;
import com.dan.papis.entity.DeviceConfiguration;
import com.dan.papis.entity.EmergencyButton;
import com.dan.papis.entity.LCDBoard;
import com.dan.papis.entity.LEDBoard;
import com.dan.papis.entity.PASystem;
import com.dan.papis.entity.PeriphralDevices;
import com.dan.papis.entity.TrainMaster;
import com.dan.papis.repository.DeviceConfigurationRepository;
import com.dan.papis.repository.EmergencyButtonRepo;
import com.dan.papis.repository.LCDBoardRepo;
import com.dan.papis.repository.LEDBoardRepo;
import com.dan.papis.repository.PASystemRepo;
import com.dan.papis.repository.TrainMasterRepo;
import com.dan.papis.utils.PapisUtils;

@Service
public class DeviceConfigurationService {

	@Autowired
	PapisConfigConstant papisConfigConstant;

	@Autowired
	DeviceConfigurationRepository deviceConfigurationRepository;

	@Autowired
	LCDBoardRepo lCDBoardRepo;

	@Autowired
	EmergencyButtonRepo emergencyButtonRepo;

	@Autowired
	LEDBoardRepo lEDBoardRepo;

	@Autowired
	TrainMasterRepo trainMasterRepo;

	@Autowired
	PASystemRepo paSystemRepo;

	public List<TrainMaster> getTrains() {
		return trainMasterRepo.findAll();
	}

	public TrainMaster getTrainByNumber(Long trainNo) {
		return trainMasterRepo.findByTrainNumber(trainNo);
	}
	
	public TrainMaster getTrainByReturnNumber(Long trainReturnNo) {
		return trainMasterRepo.findByTrainReturnNo(trainReturnNo);
	}

	public List<DeviceConfiguration> getAlldevices() {

		List<DeviceConfiguration> list = deviceConfigurationRepository
				.findByDeviceType(papisConfigConstant.getDeviceType());

		List<DeviceConfiguration> listNew = new ArrayList();
		for (DeviceConfiguration dc : list) {
			StringBuilder sb = new StringBuilder();
			sb.append(dc.getZonalCode());
			sb.append("-");
			sb.append(dc.getYear());
			sb.append("-");
			sb.append(dc.getCoachSerialNumber());
			sb.append("-");
			sb.append(dc.getOptionalInfo());
			dc.setDeviceIdDisplay(sb.toString());
			listNew.add(dc);
		}
		return listNew;

	}

	public List<DeviceConfiguration> getAlldevicesAfterRemoved(List<Long> deviceIds) {

		List<DeviceConfiguration> list = deviceConfigurationRepository
				.findByDeviceType(papisConfigConstant.getDeviceType());

		List<DeviceConfiguration> listNew = new ArrayList();
		for (DeviceConfiguration dc : list) {
			StringBuilder sb = new StringBuilder();
			sb.append(dc.getZonalCode());
			sb.append("-");
			sb.append(dc.getYear());
			sb.append("-");
			sb.append(dc.getCoachSerialNumber());
			sb.append("-");
			sb.append(dc.getOptionalInfo());
			dc.setDeviceIdDisplay(sb.toString());
			if (deviceIds != null && deviceIds.size() > 0) {
				if (!deviceIds.contains(dc.getDeviceId())) {
					listNew.add(dc);
				}

			} else {
				listNew.add(dc);
			}

		}
		return listNew;

	}

	public Map<Integer, String> getDeviceType() {

		Map<Integer, String> map = new HashMap<>();
		map.put(1, "LED dislination board");
		map.put(2, "LED slave display board");
		map.put(3, "LCD slave display board");
		map.put(4, "PA system");
		map.put(5, "Emergency switch button");

		return map;
	}

	public Map<Integer, String> getSlogansType() {

		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Welcome message");
		map.put(2, "Farewell message");
		map.put(3, "Slogans");

		return map;
	}

	public List<PeriphralDevices> getAllPeriphralDevice(Long deviceId) {
		List<EmergencyButton> emergencyButtonList = emergencyButtonRepo.findByDeviceId(deviceId);
		List<LCDBoard> lCDBoardList = lCDBoardRepo.findByDeviceId(deviceId);
		List<LEDBoard> lEDBoardsList = lEDBoardRepo.findByDeviceId(deviceId);
		List<PASystem> paSystemList = paSystemRepo.findByDeviceId(deviceId);

		List<PeriphralDevices> devices = new ArrayList<>();

		if (emergencyButtonList != null && !emergencyButtonList.isEmpty()) {
			for (EmergencyButton object : emergencyButtonList) {
				PeriphralDevices ob = new PeriphralDevices();
				ob.setId(object.getId());
				ob.setDeviceId(object.getDeviceId());
				ob.setDeviceIdDisplay(this.getDeviceIdDisplay(deviceId));
				ob.setDeviceTypeId(object.getDeviceTypeId());
				ob.setDeviceTypeName(getDeviceType().get(object.getDeviceTypeId()));
				ob.setBoardIPAddress(null);
				ob.setName(object.getName());
				ob.setStatus(object.getStatus());
				ob.setLastModifiedDate(object.getLastModifiedDate());
				if (ob.getLastModifiedDate() != null) {
					ob.setLastModified(PapisUtils.getFormattedDate(object.getLastModifiedDate()));
				}

				devices.add(ob);

			}
		}

		if (lCDBoardList != null && !lCDBoardList.isEmpty()) {
			for (LCDBoard object : lCDBoardList) {
				PeriphralDevices ob = new PeriphralDevices();
				ob.setId(object.getId());
				ob.setDeviceId(object.getDeviceId());
				ob.setDeviceIdDisplay(this.getDeviceIdDisplay(deviceId));
				ob.setDeviceTypeId(object.getDeviceTypeId());
				ob.setDeviceTypeName(getDeviceType().get(object.getDeviceTypeId()));
				ob.setBoardIPAddress(object.getBoardIPAddress());
				ob.setName(object.getName());
				ob.setStatus(object.getStatus());
				ob.setLastModifiedDate(object.getLastModifiedDate());
				if (ob.getLastModifiedDate() != null) {
					ob.setLastModified(PapisUtils.getFormattedDate(object.getLastModifiedDate()));
				}

				devices.add(ob);

			}
		}

		if (lEDBoardsList != null && !lEDBoardsList.isEmpty()) {
			for (LEDBoard object : lEDBoardsList) {
				PeriphralDevices ob = new PeriphralDevices();
				ob.setId(object.getId());
				ob.setDeviceId(object.getDeviceId());
				ob.setDeviceTypeId(object.getDeviceTypeId());
				ob.setDeviceIdDisplay(this.getDeviceIdDisplay(deviceId));
				ob.setDeviceTypeName(getDeviceType().get(object.getDeviceTypeId()));
				ob.setBoardIPAddress(object.getBoardIPAddress());
				ob.setName(object.getName());
				ob.setStatus(object.getStatus());
				ob.setLastModifiedDate(object.getLastModifiedDate());
				if (ob.getLastModifiedDate() != null) {
					ob.setLastModified(PapisUtils.getFormattedDate(object.getLastModifiedDate()));
				}

				devices.add(ob);

			}
		}

		if (paSystemList != null && !paSystemList.isEmpty()) {
			for (PASystem object : paSystemList) {
				PeriphralDevices ob = new PeriphralDevices();
				ob.setId(object.getId());
				ob.setDeviceId(object.getDeviceId());
				ob.setDeviceIdDisplay(this.getDeviceIdDisplay(deviceId));
				ob.setDeviceTypeId(object.getDeviceTypeId());
				ob.setDeviceTypeName(getDeviceType().get(object.getDeviceTypeId()));
				ob.setBoardIPAddress(null);
				ob.setName(object.getName());
				ob.setStatus(object.getStatus());
				ob.setLastModifiedDate(object.getLastModifiedDate());
				if (ob.getLastModifiedDate() != null) {
					ob.setLastModified(PapisUtils.getFormattedDate(object.getLastModifiedDate()));
				}

				devices.add(ob);

			}
		}

		return devices;

	}

	private String getDeviceIdDisplay(Long deviceId) {
		Optional<DeviceConfiguration> deviceConfiguration = deviceConfigurationRepository.findById(deviceId);
		if (deviceConfiguration.isPresent()) {
			DeviceConfiguration dc = deviceConfiguration.get();
			StringBuilder sb = new StringBuilder();
			sb.append(dc.getZonalCode());
			sb.append("-");
			sb.append(dc.getYear());
			sb.append("-");
			sb.append(dc.getCoachSerialNumber());
			sb.append("-");
			sb.append(dc.getOptionalInfo());
			return sb.toString();
		}

		return "";
	}

}
