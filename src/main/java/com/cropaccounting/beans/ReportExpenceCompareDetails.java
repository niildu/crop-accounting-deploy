package com.cropaccounting.beans;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportExpenceCompareDetails {
	private String cropName;
	private String varietyName;
	private ReportExpenceDetails areaDetails;
	private ReportExpenceDetails cropDetails;
	
	@Data
	public static class ReportExpenceDetails {
		private long cropId;
		private String activityName;
		private String taskName;
		private BigDecimal labourExpence;
	}
}
