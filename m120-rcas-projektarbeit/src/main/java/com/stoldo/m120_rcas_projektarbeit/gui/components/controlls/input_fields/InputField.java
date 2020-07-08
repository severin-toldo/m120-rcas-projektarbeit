package com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.input_fields;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.model.VoidCallable;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.FormControll;
import com.stoldo.m120_rcas_projektarbeit.model.validators.Validator;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public abstract class InputField<T> extends AbstractController implements FormControll<T> {
	
	@FXML
	protected TextField textField;
	
	@FXML
	private Text errorMsgText;
	
	@Setter
	private VoidCallable onBlur;
	
	@Setter
	private VoidCallable onFocus;
	
	@Setter
	private VoidCallable onChange;
	
	private String errorMsg;
	private List<Validator> validators;
	private List<String> styles;
	private boolean initialized;
	
	
	public InputField() {
		validators = new ArrayList<>();
		styles = new ArrayList<>();
	}
	
	@Override
	public void initialize() throws Exception {
		errorMsgText.setVisible(false);
		
		textField.setPrefSize(TextField.USE_COMPUTED_SIZE, TextField.USE_COMPUTED_SIZE);
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
		
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				onChange();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}	
		});
		
		initialized = true;
	}
	
	@Override
	public String getFxmlFileName() {
		return "InputField.fxml";
	}
	
	// doing it via stylesheet doesn't work in this case. JavaFx bug
	@Override
	public void validate() {
		if (isValid()) {
			errorMsgText.setVisible(false);
			removeStyle("-fx-text-box-border: red");
			removeStyle("-fx-focus-color: red");
		} else {
			errorMsgText.setText(errorMsg);
			errorMsgText.setVisible(true);
			addStyle("-fx-text-box-border: red");
			addStyle("-fx-focus-color: red");
		}
	}

	@Override
	public boolean isValid() {
		String value = textField.getText();
		value = StringUtils.trim(value);
		
		for (Validator v : validators) {
			if (!v.validate(value)) {
				errorMsg = v.getErrorMsg(JavaFxUtils.getResourceBundle());
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
		validate();
		
		if (onFocus != null) {
			onFocus.call();		
		}
	}
	
	private void onChange() throws Exception {
		validate();
		
		if (initialized && onChange != null) {
			onChange.call();
		}
	}
	
	public abstract T getDefaultValue();
	
	public InputField<T> addValidator(Validator validator) {
		validators.add(validator);
		return this;
	}
	
	public InputField<T> setValue(T value) {
		value = value == null ? getDefaultValue() : value;
		textField.setText(value.toString());
		return this;
	}

	public String getId() {
		return textField.getId();
	}

	public InputField<T> setId(String id) {
		textField.setId(id);
		return this;
	}

	public Pos getAlignment() {
		return textField.getAlignment();
	}
	
	public InputField<T> setAlignment(Pos alignment) {
		textField.setAlignment(alignment);
		return this;
	}

	public String getPromptText() {
		return textField.getPromptText();
	}
	
	public InputField<T> setPromptText(String promptText) {
		textField.setPromptText(promptText);
		return this;
	}
	
	public InputField<T> setFontSize(int fontSize) {
		addStyle("-fx-font-size:" + fontSize);
		return this;
	}
	
	private void addStyle(String style) {
		if (!styles.contains(style)) {
			styles.add(style);	
		}
		
		textField.setStyle(styles.stream().collect(Collectors.joining(";")));
	}
	
	private void removeStyle(String style) {
		styles.remove(style);
		textField.setStyle(styles.stream().collect(Collectors.joining(";")));
	}
}
