package com.dan.papis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dan.papis.constant.PapisConfigConstant;
import com.dan.papis.utils.PapisUtils;

@Service
public class FileSystemStorageService {

	@Autowired
	PapisConfigConstant papisConfigConstant;

	public String store(MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				Path rootLocation = Paths.get(papisConfigConstant.getFilePath());
				String fileName = file.getOriginalFilename();
				fileName = PapisUtils.getRandomInteger(100, 1)+"_"+fileName;
				Files.copy(file.getInputStream(), rootLocation.resolve(fileName));
				return fileName;
			}

		} catch (IOException e) {

		}
		return null;
	}

}
