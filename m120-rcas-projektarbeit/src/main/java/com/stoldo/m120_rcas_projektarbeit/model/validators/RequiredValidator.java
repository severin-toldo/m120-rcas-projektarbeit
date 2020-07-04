package com.stoldo.m120_rcas_projektarbeit.model.validators;

import org.apache.commons.lang3.StringUtils;

public class RequiredValidator implements Validator {

	@Override
	public boolean validate(Object value) {
		return value != null && StringUtils.isNotEmpty(value.toString()) && StringUtils.isNotBlank(value.toString());
	}

	@Override
	public String getErrorMsg() {
		return "This Field is required.";
	}
}
