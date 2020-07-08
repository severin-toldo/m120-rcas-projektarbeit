package com.stoldo.m120_rcas_projektarbeit.gui.components.image_uploader;

import java.io.File;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.IconKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lombok.Getter;

public class ImageUploaderComponentController extends AbstractController {
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private GridPane gridPane;
	
	@Getter
	private File uploadedFile;
	
	private FileChooser fileChooser;
	
	public ImageUploaderComponentController() {
		this.fileChooser = getFileChooser();
	}
	
	@Override
	public void initialize() throws Exception {
	    imageView.setImage(JavaFxUtils.getIcon(IconKey.UPLOAD_ICON));
	}
	
	public void openFileChooser() throws Exception {
		if (getStage() != null) {
	        File file = fileChooser.showOpenDialog(getStage());
	        uploadedFile = file;
	        
	        if (uploadedFile != null) {
	        	imageView.setImage(JavaFxUtils.getImage(uploadedFile));
	        }
		}
	}
	
	private FileChooser getFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
		return fileChooser;
	}
}
