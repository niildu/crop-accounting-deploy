package com.cropaccounting.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum UnitEnum {
	katha(1, "কাঠা", 1.65), bigha(2, "বিঘা", 1.65 * 20), acre(3, "একর", 1.65 * 20 * 3);

	UnitEnum(int id, String unitsName, double convertionRate) {
		this.id = id;
		this.unitsName = unitsName;
		this.convertionRate = convertionRate;
	}

	private final int id;
	private final String unitsName; // in kilograms
	private final double convertionRate; // in meters

	public int id() {
		return id;
	}

	public String unitsName() {
		return unitsName;
	}

	public double convertionRate() {
		return convertionRate;
	}
	
	public static BigDecimal getConversionRateById(int id) {
	    for(UnitEnum e : values()) {
	        if(e.id() == id) return BigDecimal.valueOf(e.convertionRate());
	    }
	    return BigDecimal.ONE;
	}
	
	public static UnitEnum getById(int id) {
	    for(UnitEnum e : values()) {
	        if(e.id() == id) return e;
	    }
	    return null;
	}
}
