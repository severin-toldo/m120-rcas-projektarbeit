package com.stoldo.m120_rcas_projektarbeit.gui.pages.race_car_view;

import java.util.ResourceBundle;

import com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.corner_weight_controll.CornerWeightControllController;
import com.stoldo.m120_rcas_projektarbeit.gui.components.controlls.input_fields.TextInputField;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.model.validators.RequiredValidator;
import com.stoldo.m120_rcas_projektarbeit.service.RaceCarService;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.ResourceKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class AbstractRaceCarViewController extends AbstractController {

	@FXML
	protected GridPane gridPane;
	
	protected RaceCar raceCar;
	
	protected ResourceBundle resourceBundle = JavaFxUtils.getResourceBundle();
	protected CornerWeightControllController cwfl;
	protected CornerWeightControllController cwfr;
	protected CornerWeightControllController cwrl;
	protected CornerWeightControllController cwrr;
	protected TextInputField raceCarNameTextField;

	
	public AbstractRaceCarViewController(RaceCar raceCar) {
		this.raceCar = raceCar == null ? RaceCarService.getInstance().generateDefaultRaceCar() : raceCar;
	}
	
	@Override
	public void initialize() throws Exception {
		raceCarNameTextField = new TextInputField();
		raceCarNameTextField.load();
		raceCarNameTextField
		.setValue(raceCar.getName())
		.addValidator(new RequiredValidator())
		.setPromptText("Race Car Name")
		.setFontSize(24);
		
		gridPane.add(raceCarNameTextField.getPane(), 0, 0);
		GridPane.setMargin(raceCarNameTextField.getPane(), new Insets(20.0, 200.0, 0.0, 20.0));

		
		cwfl = new CornerWeightControllController(
				resourceBundle.getString(ResourceKey.CORNER_WEIGHT_FRONT_LEFT.getKey()),
				raceCar.getCornerWeightFL()
				);
		cwfl.load();
		
		cwfr = new CornerWeightControllController(
				resourceBundle.getString(ResourceKey.CORNER_WEIGHT_FRONT_RIGHT.getKey()),
				raceCar.getCornerWeightFR()
				);
		cwfr.load();
		
		cwrl = new CornerWeightControllController(
				resourceBundle.getString(ResourceKey.CORNER_WEIGHT_REAR_LEFT.getKey()),
				raceCar.getCornerWeightRL()
				);
		cwrl.load();
		
		cwrr = new CornerWeightControllController(
				resourceBundle.getString(ResourceKey.CORNER_WEIGHT_REAR_RIGHT.getKey()),
				raceCar.getCornerWeightRR()
				);
		cwrr.load();
		
		gridPane.add(cwfl.getPane(), 0, 1);
		gridPane.add(cwfr.getPane(), 0, 2);
		gridPane.add(cwrl.getPane(), 0, 3);
		gridPane.add(cwrr.getPane(), 0, 4);
		
		GridPane.setMargin(cwfl.getPane(), new Insets(0.0, 0.0, 0.0, 20.0));
		GridPane.setMargin(cwfr.getPane(), new Insets(0.0, 0.0, 0.0, 20.0));
		GridPane.setMargin(cwrl.getPane(), new Insets(0.0, 0.0, 0.0, 20.0));
		GridPane.setMargin(cwrr.getPane(), new Insets(0.0, 0.0, 0.0, 20.0));
		
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(getExtraPane());
		gridPane.add(hBox, 0, 1, 3, 4);
		GridPane.setMargin(hBox, new Insets(30.0, 20.0, 20.0, 600.0));
	}
	
	@Override
	public String getFxmlFileName() {
		return "AbstractRaceCarView.fxml";
	}
	
	public void save() throws Exception {
		validate();
		
		if (isFormValid()) {
			applyFormValues();
			RaceCarService.getInstance().addRaceCar(raceCar);
			getStage().close();
		}
	}
	
	protected void validate() {
		cwfl.validate();
		cwfr.validate();
		cwrl.validate();
		cwrr.validate();
		raceCarNameTextField.validate();
	}
	
	protected boolean isFormValid() {
		return cwfl.isValid() && cwfr.isValid() && cwrl.isValid() && cwrr.isValid() && raceCarNameTextField.isValid();
	}
	
	protected void applyFormValues() throws Exception {
		raceCar.setName(raceCarNameTextField.getValue());
		raceCar.setCornerWeightFL(cwfl.getValue());
		raceCar.setCornerWeightFR(cwfr.getValue());
		raceCar.setCornerWeightRL(cwrl.getValue());
		raceCar.setCornerWeightRR(cwrr.getValue());
	}
	
	protected abstract Pane getExtraPane();
}