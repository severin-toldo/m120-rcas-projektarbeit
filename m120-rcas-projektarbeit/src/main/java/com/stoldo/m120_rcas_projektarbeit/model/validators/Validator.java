package com.stoldo.m120_rcas_projektarbeit.model.validators;

public interface Validator {
	
	public boolean validate(Object value);
	
	public String getErrorMsg();
	
}
