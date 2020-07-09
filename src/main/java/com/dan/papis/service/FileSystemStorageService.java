package com.dan.papis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService {
	
	private String location = "E://workspace/DAN/files/PAPIS/";
	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService() {
		this.rootLocation = Paths.get(location);
	}
	
	public void store(MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			}
			
		} catch (IOException e) {
			
		}
	}

}
