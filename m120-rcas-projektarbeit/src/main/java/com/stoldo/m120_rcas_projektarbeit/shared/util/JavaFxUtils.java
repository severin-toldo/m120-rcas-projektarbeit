package com.stoldo.m120_rcas_projektarbeit.shared.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ResourceBundle;

import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.IconKey;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class JavaFxUtils {
	
	private static final String RESOURCE_BUNDLE_PATH = "com.stoldo.m120_rcas_projektarbeit.RCASResources";
	private static final String ICON_PATH = "src/main/resources/icons/";
	private static final String IMAGE_PATH = "src/main/resources/images/";
	
	
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
	
	public static File getImageDir() {
		File imageDir = new File(IMAGE_PATH);
		
		if (!imageDir.isDirectory()) {
			throw new IllegalStateException("Image Dir not configured correctly!");
		}
		
		return imageDir;
	}
	
	public static Image getIcon(IconKey iconKey) throws FileNotFoundException {
		return getImage(ICON_PATH, iconKey.getKey());	
	}
	
	public static Image getImage(String fileName) throws FileNotFoundException {
		return getImage(IMAGE_PATH, fileName);
	}
	
	private static Image getImage(String dir, String fileName) throws FileNotFoundException {
		String path = dir + fileName;
		
		File f = new File(path);
		
		if (!f.exists() || !f.isFile()) {
			throw new FileNotFoundException("File " + path + "Doesnt exist!");
		}
		
	    InputStream stream = new FileInputStream(f);
	    
	    return new Image(stream);	
	}
}
