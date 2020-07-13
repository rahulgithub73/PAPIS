package com.dan.papis.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class PapisConfigConstant {

	@Value("${device.type}")
	private String deviceType;
	
	@Value("${file.path}")
	private String filePath;

}
