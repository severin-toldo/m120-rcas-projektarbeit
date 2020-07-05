package com.stoldo.m120_rcas_projektarbeit.model.validators;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.stoldo.m120_rcas_projektarbeit.model.ResourceKey;

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
	public String getErrorMsg(ResourceBundle resourceBundle) {
		return MessageFormat.format(resourceBundle.getString(ResourceKey.MIN_MAX_ERROR_MSG.getKey()), min, max);
	}
}
