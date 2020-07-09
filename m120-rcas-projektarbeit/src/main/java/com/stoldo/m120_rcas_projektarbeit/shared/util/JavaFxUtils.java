package com.stoldo.m120_rcas_projektarbeit.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.IconKey;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.PathConstants;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.TranslationKey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFxUtils {
	
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(PathConstants.RESOURCE_BUNDLE_PATH);
	
	@SuppressWarnings("unchecked")
	public static <T extends Pane> T load(AbstractController controller) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		
		fxmlLoader.setLocation(controller.getClass().getResource(controller.getFxmlFileName()));
		fxmlLoader.setController(controller);
		fxmlLoader.setResources(getResourceBundle());
		
		return (T) fxmlLoader.load();
	}
	
	public static Stage openSubWindow(AbstractController c, double width, double height, TranslationKey titleTranslationKey) {
		Stage subStage = new Stage();
		c.load(subStage);
		Scene subScene = new Scene(c.getPane(), width, height);
		subStage.setScene(subScene);
		subStage.setTitle(getResourceBundle().getString(titleTranslationKey.getKey()));
		subStage.initModality(Modality.APPLICATION_MODAL);
		subStage.show();
		
		return subStage;
	}
	
	public static String translate(TranslationKey translationKey) {
		return getResourceBundle().getString(translationKey.getKey());
	}
	
	public static String translate(TranslationKey translationKey, Object... params) {
		return MessageFormat.format(getResourceBundle().getString(translationKey.getKey()), params);
	}
	
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
	
	public static Image getIcon(IconKey iconKey) throws FileNotFoundException {
		return getImage(Paths.get(PathConstants.ICON_PATH + File.separator + iconKey.getKey()));	
	}
	
	public static Image getImage(String fileName) throws FileNotFoundException {
		return getImage(Paths.get(PathConstants.IMAGE_PATH + File.separator + fileName));
	}
	
	public static Image getImage(File file) throws FileNotFoundException {
		return getImage(Paths.get(file.getAbsolutePath()));
	}
	
	private static Image getImage(Path path) throws FileNotFoundException {
		File f = path.toFile();
		
		if (!f.exists() || !f.isFile()) {
			throw new FileNotFoundException("File " + path + " Doesnt exist!");
		}
	    
	    return new Image(new FileInputStream(f));	
	}
}
