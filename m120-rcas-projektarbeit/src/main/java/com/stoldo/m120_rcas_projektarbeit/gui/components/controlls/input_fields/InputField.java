package com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.input_fields;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.model.VoidCallable;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.validators.Validator;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public abstract class InputField<T> extends AbstractController {
	
	@FXML
	protected TextField textField;
	
	@FXML
	private Text errorMsgText;
	
	@Setter
	private VoidCallable onBlur;
	
	@Setter
	private VoidCallable onFocus;
	
	@Setter 
	@Getter
	private T value;
	
	@Setter 
	@Getter
	private String id;
	
	@Setter 
	@Getter
	private Pos alignment;
	
	@Setter 
	@Getter
	private String promptText;
	
	
	private String errorMsg;
	private List<Validator> validators = new ArrayList<>();
	
	
	@Override
	public void initialize() throws Exception {
		System.out.println("Hello");
		validators = new ArrayList<>();
		value = value == null ? getDefaultValue() : value;
		errorMsgText.setVisible(false);
		
		textField.setId(id);
		textField.setAlignment(alignment);
		textField.setPromptText(promptText);
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
	
	public InputField<T> addValidator(Validator validator) {
		validators.add(validator);
		return this;
	}
	
	public abstract T getDefaultValue();
	
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
}
