package com.stoldo.m120_rcas_projektarbeit.model.validators;

import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.shared.constants.ResourceKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

public class RequiredValidator implements Validator {

	@Override
	public boolean validate(Object value) {
		return value != null && StringUtils.isNotEmpty(value.toString()) && StringUtils.isNotBlank(value.toString());
	}

	@Override
	public String getErrorMsg() {
		return JavaFxUtils.translate(ResourceKey.REQUIRED_ERROR_MSG);
	}
}
