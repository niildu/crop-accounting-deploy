package com.cropaccounting.util;

public enum CropTypeEnum {
	FIELDCROP(1, "ফিল্ড ক্রপ"), WILDTREE(2, "বনজ"), FRUITTREE(3, "ফলজ");
	
	CropTypeEnum(int id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}
	
	private final int id;
	private final String typeName;
	
	private int id() {
		return id;
	}

	private String typeName() {
		return typeName;
	}
}
