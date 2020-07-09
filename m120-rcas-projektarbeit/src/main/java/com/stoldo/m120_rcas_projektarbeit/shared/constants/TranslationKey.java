package com.stoldo.m120_rcas_projektarbeit.shared.constants;

import lombok.Getter;

public enum TranslationKey {
	
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
	EDIT_RACE_CAR("editRaceCar"),
	RACE_CARS("raceCars"),
	DELETE_RACE_CAR("deleteRaceCar"),
	CONFIRM_DELETE_RACE_CAR("confirmDeleteRaceCar"),
	YES("yes"),
	CANCEL("cancel");

	
	@Getter
	private String key;
	
	private TranslationKey(String key) {
		this.key = key;
	}
}
