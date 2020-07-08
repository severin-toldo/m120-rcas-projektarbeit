package com.stoldo.m120_rcas_projektarbeit.model.javafx;


import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractController {
	
	@Getter
	private Pane pane;
	
	@Getter
	@Setter
	private Stage stage;
	
	@FXML
	public abstract void initialize() throws Exception;
	
	public String getFxmlFileName() {
		return StringUtils.replace(getClass().getSimpleName(), "Controller", ".fxml");
	}
	
	public void load(Stage stage) {
		try {
			this.stage = stage;
			this.pane = JavaFxUtils.load(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
