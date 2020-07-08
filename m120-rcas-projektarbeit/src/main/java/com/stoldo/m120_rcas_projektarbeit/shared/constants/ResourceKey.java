package com.stoldo.m120_rcas_projektarbeit.shared.constants;

import lombok.Getter;

public enum ResourceKey {
	
	APPLICATION_TITLE("applicationTitle"),
	MMM_CHART_TITLE("mmmChartTitle"),
	X_AXIS_LABLE("xAxisLabel"),
	Y_AXIS_LABLE("yAxisLabel"),
	REQUIRED_ERROR_MSG("requiredErrorMsg"),
	MIN_MAX_ERROR_MSG("minMaxErroMsg"),
	UPLOAD_IMAGE("uploadImage"),
	SAVE("save"),
	CORNER_WEIGHT_FRONT_LEFT("cornerWeightFrontLeft"),
	CORNER_WEIGHT_FRONT_RIGHT("cornerWeightFrontRight"),
	CORNER_WEIGHT_REAR_LEFT("cornerWeightRearLeft"),
	CORNER_WEIGHT_REAR_RIGHT("cornerWeightRearRight"),
	ADD_RACE_CAR("addRaceCar"),
	EDIT_RACE_CAR("editRaceCar");

	
	@Getter
	private String key;
	
	private ResourceKey(String key) {
		this.key = key;
	}
}
