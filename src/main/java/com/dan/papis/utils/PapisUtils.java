package com.dan.papis.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PapisUtils {

	public static String getFormattedDate(LocalDateTime localDateTime) {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		return localDateTime.format(pattern);

	}

	public static LocalDateTime getDate() {
		Date now = new Date();
		Instant current = now.toInstant();
		return LocalDateTime.ofInstant(current, ZoneId.systemDefault());

	}

	public static int getRandomInteger(int maximum, int minimum) {
		return ((int) (Math.random() * (maximum - minimum))) + minimum;
	}

}
