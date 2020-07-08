package com.stoldo.m120_rcas_projektarbeit.model.validators;

import java.util.ResourceBundle;

public interface Validator {
	
	public boolean validate(Object value);
	
	public String getErrorMsg(ResourceBundle resourceBundle);
	
}
