package com.stoldo.m120_rcas_projektarbeit.gui.components.image_uploader;

import java.io.File;
import org.apache.commons.io.FileUtils;

import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.IconKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lombok.Getter;
import lombok.Setter;

public class ImageUploaderComponentController extends AbstractController {
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Pane imagePane;
	
	@Getter
	@Setter
	private String fileName;
	
	private Pane parent;
	private FileChooser fileChooser;
	
	public ImageUploaderComponentController(Pane parent) {
		this.parent = parent;
		this.fileChooser = getFileChooser();
	}
	
	@Override
	public void initialize() throws Exception {
		imagePane.setPrefHeight(Pane.USE_COMPUTED_SIZE);
	    imagePane.setPrefWidth(Pane.USE_COMPUTED_SIZE);
	    
	    imageView.setImage(JavaFxUtils.getIcon(IconKey.UPLOAD_ICON));
	    imageView.fitWidthProperty().bind(parent.widthProperty());
	    imageView.fitHeightProperty().bind(parent.heightProperty());
	}
	
	public void openFileChooser() throws Exception {
		if (getStage() != null) {
	        File file = fileChooser.showOpenDialog(getStage());
	        
	        if (file != null) {
	        	 FileUtils.copyFileToDirectory(file, JavaFxUtils.getImageDir());
	        	 setFileName(file.getName());
	        	 imageView.setImage(JavaFxUtils.getImage(file.getName()));
	        }
		}
	}
	
	private FileChooser getFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
		return fileChooser;
	}
}
