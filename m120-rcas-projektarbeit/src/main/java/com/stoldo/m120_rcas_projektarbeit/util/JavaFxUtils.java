package com.stoldo.m120_rcas_projektarbeit.util;

import java.util.ResourceBundle;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class JavaFxUtils {
	
	private static final String RESOURCE_BUNDLE_PATH = "com.stoldo.m120_rcas_projektarbeit.RCASResources";
	
	
	@SuppressWarnings("unchecked")
	public static <T extends Pane> T load(AbstractController controller) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		
		fxmlLoader.setLocation(controller.getClass().getResource(controller.getFxmlFileName()));
		fxmlLoader.setController(controller);
		fxmlLoader.setResources(getResourceBundle());
		
		return (T) fxmlLoader.load();
	}
	
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);
	}
}
