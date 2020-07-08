package com.stoldo.m120_rcas_projektarbeit.model.javafx;

public interface FormControll<T> {
	
	public T getValue();
	
	public boolean isValid();
	
	public void validate();

}
