package com.cropaccounting.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CropVarietyBean {
	private long cropId;
	private String cropName;
	private long varietyId;
	private String varietyName;
}
