package com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.input_fields;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.model.VoidCallable;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.validators.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class InputField<T> extends AbstractController {
	
	@FXML
	protected TextField textField;
	
	@FXML
	private Text errorMsgText;
	
	@Setter
	@Accessors(fluent = true)
	private VoidCallable onBlur;
	
	@Setter
	@Accessors(fluent = true)
	private VoidCallable onFocus;
	
	@Setter 
	@Accessors(fluent = true)
	private T value;
	
	private String errorMsg;
	private List<Validator> validators = new ArrayList<>();
	
	
	@Override
	public void initialize() throws Exception {
		validators = new ArrayList<>();
		value = value == null ? getDefaultValue() : value;
		errorMsgText.setVisible(false);
		
		textField.setText(value.toString());
		textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			try {
				if (newVal) {
					onFocus();	
				} else {
					onBlur();	
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	@Override
	public String getFxmlFileName() {
		return "InputField.fxml";
	}
	
	// doing it via stylesheet doesn't work in this case. JavaFx bug
	public void validate() {
		if (isValid()) {
			errorMsgText.setVisible(false);
			textField.setStyle("");
		} else {
			errorMsgText.setText(errorMsg);
			errorMsgText.setVisible(true);
			textField.setStyle("-fx-text-box-border: red;-fx-focus-color: red;");
		}
	}
	
	public T getValue() {
		return value;
	}
	
	private boolean isValid() {
		String value = textField.getText();
		value = StringUtils.trim(value);
		
		for (Validator v : validators) {
			if (!v.validate(value)) {
				errorMsg = v.getErrorMsg();
				return false;
			}
		}
		
		return true;
	}
	
	public abstract T getDefaultValue();
	
	private void onBlur() throws Exception {
		validate();
		
		if (onBlur != null) {
			onBlur.call();		
		}
	}
	
	private void onFocus() throws Exception {
		if (onFocus != null) {
			onFocus.call();		
		}
	}
	
	public InputField<T> addValidator(Validator validator) {
		validators.add(validator);
		return this;
	}
}
