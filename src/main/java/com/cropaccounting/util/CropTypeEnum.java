package com.cropaccounting.util;

public enum CropTypeEnum {
	FIELDCROP(1, "ফিল্ড ক্রপ"), WILDTREE(2, "বনজ"), FRUITTREE(3, "ফলজ");

	CropTypeEnum(int id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	private final int id;
	private final String typeName;

	public int id() {
		return id;
	}

	public String typeName() {
		return typeName;
	}
}
