package com.stoldo.m120_rcas_projektarbeit;

import java.util.ResourceBundle;

import com.stoldo.m120_rcas_projektarbeit.gui.pages.race_cars_overview.TestController;
import com.stoldo.m120_rcas_projektarbeit.model.ResourceKey;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.util.JavaFxUtils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RCASApplicationStarter extends Application {
	
	private static final String RESOURCE_BUNDLE_PATH = "com.stoldo.m120_rcas_projektarbeit.RCASResources";
	
	public static void main(String[] args) {
		Application.launch(RCASApplicationStarter.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AbstractController mainController = new TestController();
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH); 
		Pane mainPane = JavaFxUtils.load(mainController, resourceBundle);
		
		Scene mainScene = new Scene(mainPane, 800, 600);
		primaryStage.centerOnScreen();
		primaryStage.setTitle(resourceBundle.getString(ResourceKey.APPLICATION_TITLE.getKey()));
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
}
