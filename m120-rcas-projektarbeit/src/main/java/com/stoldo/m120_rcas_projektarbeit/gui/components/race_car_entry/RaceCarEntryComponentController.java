package com.stoldo.m120_rcas_projektarbeit.gui.components.race_car_entry;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.stoldo.m120_rcas_projektarbeit.gui.pages.race_car_view.EditRaceCarViewController;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.service.RaceCarService;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.ResourceKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class RaceCarEntryComponentController extends AbstractController {

	@FXML
	private GridPane entryGridPane;
	
	@FXML
	private ImageView raceCarImageView;
	
	@FXML
	private Text raceCarNameText;
	
	
	private RaceCar raceCar;
	
	public RaceCarEntryComponentController(RaceCar raceCar) {
		this.raceCar = raceCar;
	}
	
	@Override
	public void initialize() throws Exception {
		raceCarNameText.setText(raceCar.getName());
		
		if (StringUtils.isNotEmpty(raceCar.getImageFileName())) {
			Image img = JavaFxUtils.getImage(raceCar.getImageFileName());
			raceCarImageView.setImage(img);	
		}
	}
	
	public void openRaceCar() {
		EditRaceCarViewController c = new EditRaceCarViewController(raceCar);
		JavaFxUtils.openSubWindow(c, 1300.0, 700.0, ResourceKey.ADD_RACE_CAR);
	}
	
	public void deleteRaceCar() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(JavaFxUtils.translate(ResourceKey.DELETE_RACE_CAR));
		alert.setHeaderText(null);
		alert.setContentText(JavaFxUtils.translate(ResourceKey.CONFIRM_DELETE_RACE_CAR));
		
		ButtonType yesBtn = new ButtonType(JavaFxUtils.translate(ResourceKey.YES), ButtonData.YES);
		ButtonType cancelBtn = new ButtonType(JavaFxUtils.translate(ResourceKey.CANCEL), ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(cancelBtn, yesBtn);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesBtn) {
			RaceCarService.getInstance().removeRaceCarById(raceCar.getId());
		}
	}
}
