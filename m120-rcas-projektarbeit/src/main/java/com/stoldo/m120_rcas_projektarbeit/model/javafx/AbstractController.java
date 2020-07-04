package com.stoldo.m120_rcas_projektarbeit.model.javafx;


import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Getter;

public abstract class AbstractController {
	
	@Getter
	private Pane pane;
	
	public AbstractController() {
		try {
			pane = JavaFxUtils.load(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@FXML
	public abstract void initialize() throws Exception;
	
	public String getFxmlFileName() {
		return StringUtils.replace(getClass().getSimpleName(), "Controller", ".fxml");
	}
}
