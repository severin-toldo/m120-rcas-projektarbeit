package com.stoldo.m120_rcas_projektarbeit.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.IconKey;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.PathConstants;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.ResourceKey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFxUtils {
	
	@SuppressWarnings("unchecked")
	public static <T extends Pane> T load(AbstractController controller) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		
		fxmlLoader.setLocation(controller.getClass().getResource(controller.getFxmlFileName()));
		fxmlLoader.setController(controller);
		fxmlLoader.setResources(getResourceBundle());
		
		return (T) fxmlLoader.load();
	}
	
	public static Stage openSubWindow(AbstractController c, double width, double height, ResourceKey titleKey) {
		Stage subStage = new Stage();
		
		c.setStage(subStage);
		c.load();
		
		Scene subScene = new Scene(c.getPane(), width, height);
		subStage.setScene(subScene);
		subStage.setTitle(getResourceBundle().getString(titleKey.getKey()));
		subStage.initModality(Modality.APPLICATION_MODAL); // TODO if time: handle refreshing
		subStage.setResizable(true);
		subStage.show();
		
		return subStage;
	}
	
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(PathConstants.RESOURCE_BUNDLE_PATH);
	}
	
	public static Image getIcon(IconKey iconKey) throws FileNotFoundException {
		return getImage(PathConstants.ICON_PATH + File.separator + iconKey.getKey());	
	}
	
	public static Image getImage(File file) throws FileNotFoundException {
		return getImage(file.getAbsolutePath());
	}
	
	private static Image getImage(String path) throws FileNotFoundException {
		File f = new File(path);
		
		if (!f.exists() || !f.isFile()) {
			throw new FileNotFoundException("File " + path + "Doesnt exist!");
		}
	    
	    return new Image(new FileInputStream(f));	
	}
}
