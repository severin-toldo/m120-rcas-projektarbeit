package com.stoldo.m120_rcas_projektarbeit.util;

import java.util.ResourceBundle;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class JavaFxUtils {
	
	public static <T extends Pane> T load(AbstractController controller) throws Exception {
		return load(controller, null);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Pane> T load(AbstractController controller, ResourceBundle resourceBundle) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		
		fxmlLoader.setLocation(controller.getClass().getResource(controller.getFxmlFileName()));
		fxmlLoader.setController(controller);
		
		if (resourceBundle != null) {
			fxmlLoader.setResources(resourceBundle);
		}
		
		return (T) fxmlLoader.load();
	}
}
