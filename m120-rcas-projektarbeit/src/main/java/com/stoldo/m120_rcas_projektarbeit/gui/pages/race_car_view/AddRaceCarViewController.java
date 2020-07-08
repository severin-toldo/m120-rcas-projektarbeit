package com.stoldo.m120_rcas_projektarbeit.gui.pages.race_car_view;

import com.stoldo.m120_rcas_projektarbeit.gui.components.image_uploader.ImageUploaderComponentController;
import com.stoldo.m120_rcas_projektarbeit.shared.util.CommonUtils;

import javafx.scene.layout.Pane;

public class AddRaceCarViewController extends AbstractRaceCarViewController  {
	
	private ImageUploaderComponentController imageUploaderComponent = new ImageUploaderComponentController();

	public AddRaceCarViewController() {
		super(null);
	}

	@Override
	protected Pane getExtraPane() {
		imageUploaderComponent.setStage(getStage());
		imageUploaderComponent.load();
		return imageUploaderComponent.getPane();
	}
	
	@Override
	protected void applyFormValues() throws Exception {
		super.applyFormValues();
		
		if (imageUploaderComponent.getUploadedFile() != null) {
			CommonUtils.copyFileToImageDir(imageUploaderComponent.getUploadedFile());
			raceCar.setImageFileName(imageUploaderComponent.getUploadedFile().getName());	
		}
	}
}
