package com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.input_fields;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class DoubleInputField extends InputField<Double> {
	
	@Override
	public void initialize() throws Exception {
		super.initialize();
		
		Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
		
		StringConverter<Double> converter = new StringConverter<Double>() {
		    @Override
		    public Double fromString(String s) {
		        if (s.isEmpty()) {
		        	return null;
		        } else if ("-".equals(s) || ".".equals(s) || "-.".equals(s)) {
		            return getDefaultValue();
		        } else {
		            return Double.valueOf(s);
		        }
		    }

		    @Override
		    public String toString(Double d) {
		        return d != null ? d.toString() : "";
		    }
		};

		UnaryOperator<TextFormatter.Change> filter = change -> {
			
		    String text = change.getControlNewText();
		    
		    if (validEditingState.matcher(text).matches()) {
		    	return change;
		    }
		    
		    return null;
		};
		
		TextFormatter<Double> textFormatter = new TextFormatter<>(converter, getValue(), filter);
		textField.setTextFormatter(textFormatter);
	}


	@Override
	public Double getDefaultValue() {
		return 0.0;
	}
}
