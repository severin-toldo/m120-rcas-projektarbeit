package com.stoldo.m120_rcas_projektarbeit.shared.constants;

import lombok.Getter;

public enum ResourceKey {
	
	APPLICATION_TITLE("applicationTitle"),
	MMM_CHART_TITLE("mmmChartTitle"),
	X_AXIS_LABLE("xAxisLabel"),
	Y_AXIS_LABLE("yAxisLabel"),
	REQUIRED_ERROR_MSG("requiredErrorMsg"),
	MIN_MAX_ERROR_MSG("minMaxErroMsg"),
	UPLOAD_IMAGE("uploadImage");
	
	@Getter
	private String key;
	
	private ResourceKey(String key) {
		this.key = key;
	}
}
