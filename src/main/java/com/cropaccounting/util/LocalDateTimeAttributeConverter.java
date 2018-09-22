package com.cropaccounting.util;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDateTime locDateTime) {
		if (locDateTime == null)
			return null;
		return (locDateTime == null ? null
				: new java.sql.Date(Date.from(locDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime()));
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date sqlDate) {
		if (sqlDate == null)
			return null;
		System.out.println("converting sql date to localdate :" + sqlDate);
		//return (sqlDate == null ? null : sqlDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		System.out.println("converted :: " + Instant.ofEpochMilli(sqlDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
		return Instant.ofEpochMilli(sqlDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
