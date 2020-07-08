package com.stoldo.m120_rcas_projektarbeit.gui.pages.race_car_view;

import com.stoldo.m120_rcas_projektarbeit.gui.components.mmm_diagram.MMMDiagramComponentController;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import javafx.scene.layout.Pane;

public class EditRaceCarViewController extends AbstractRaceCarViewController {
	
	private MMMDiagramComponentController mmmDiagram = new MMMDiagramComponentController(raceCar);
	
	public EditRaceCarViewController(RaceCar raceCar) {
		super(raceCar);
	}
	
	@Override
	public void initialize() throws Exception {
		super.initialize();
		
		cwfl.onChange(() -> {
			onChange();
		});
		
		cwfr.onChange(() -> {
			onChange();
		});

		cwrl.onChange(() -> {
			onChange();
		});

		cwrr.onChange(() -> {
			onChange();
		});
	}
	
	public void onChange() throws Exception {
		validate();
		
		if (isFormValid()) {
			applyFormValues();
			mmmDiagram.refresh(raceCar);
		}
	}

	@Override
	protected Pane getExtraPane() {
		mmmDiagram.load(getStage());
		return mmmDiagram.getPane();
	}
}
