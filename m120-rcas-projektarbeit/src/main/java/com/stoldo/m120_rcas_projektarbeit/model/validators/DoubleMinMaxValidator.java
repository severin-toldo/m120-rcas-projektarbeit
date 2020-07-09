package com.stoldo.m120_rcas_projektarbeit.model.validators;


import com.stoldo.m120_rcas_projektarbeit.shared.constants.TranslationKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DoubleMinMaxValidator implements Validator {
	
	private double min;
	private double max;
	
	@Override
	public boolean validate(Object value) {
		double doubleValue = Double.valueOf(value.toString());
		return doubleValue >= min && doubleValue <= max;
	}

	@Override
	public String getErrorMsg() {
		return JavaFxUtils.translate(TranslationKey.MIN_MAX_ERROR_MSG, min, max);
	}
}
