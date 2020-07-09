package com.stoldo.m120_rcas_projektarbeit.gui.pages.race_cars_overview;

import java.util.List;
import java.util.stream.Collectors;

import com.stoldo.m120_rcas_projektarbeit.gui.components.race_car_entry.RaceCarEntryComponentController;
import com.stoldo.m120_rcas_projektarbeit.gui.pages.race_car_view.AddRaceCarViewController;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.service.RaceCarService;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.TranslationKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;


public class RaceCarsOverviewController extends AbstractController {
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private Text raceCarNameText;
	
	@FXML
	private ScrollPane scrollPane;

	@Override
	public void initialize() throws Exception {
		ObservableList<RaceCar> raceCars = RaceCarService.getInstance().getRaceCars();
		
		raceCars.addListener(new ListChangeListener<RaceCar>() {
			@Override
			public void onChanged(Change<? extends RaceCar> c) {
				List<RaceCar> newRaceCars = c.getList().stream().map(rc -> rc).collect(Collectors.toList());
				scrollPane.setContent(getRaceCarGridPane(newRaceCars));
			}
		});
		
		scrollPane.setContent(getRaceCarGridPane(raceCars));
	}
	
	public void addRaceCar() {
		AddRaceCarViewController c = new AddRaceCarViewController();
		JavaFxUtils.openSubWindow(c, 1300.0, 700.0, TranslationKey.ADD_RACE_CAR);		
	}
	
	/**
	 * Since its a dynamic gridpane (depends on data) its not created programmatically not declaratively
	 * */
	private GridPane getRaceCarGridPane(List<RaceCar> raceCars) {
		GridPane raceCarGridPane = new GridPane();
		
		fillRaceCarGridPane(raceCarGridPane, raceCars);
		setRaceCarGridPaneLayout(raceCarGridPane);
		
		return raceCarGridPane;
	}
	
	private void fillRaceCarGridPane(GridPane raceCarGridPane, List<RaceCar> raceCars) {
		int colIndex = 0;
		int rowIndex = 0;
		
		raceCars.sort((rc1, rc2) -> rc1.getId().compareTo(rc2.getId()));
		
		for (RaceCar rc : raceCars) {
			if (colIndex == 3) {
				colIndex = 0;
				rowIndex++;
			}
			
			RaceCarEntryComponentController c = new RaceCarEntryComponentController(rc);
			c.load(getStage());

			raceCarGridPane.add(c.getPane(), colIndex, rowIndex);
			colIndex++;
		}	
	}
	
	private void setRaceCarGridPaneLayout(GridPane raceCarGridPane) {
		raceCarGridPane.setHgap(1);
		raceCarGridPane.setVgap(60);	
		
		for (int i = 0; i < raceCarGridPane.getRowCount(); i++) {
			RowConstraints row = new RowConstraints();
			row.setVgrow(Priority.ALWAYS);
			row.setValignment(VPos.CENTER);
			raceCarGridPane.getRowConstraints().add(row);	
		}
		
		for (int i = 0; i < raceCarGridPane.getColumnCount(); i++) {
			ColumnConstraints column = new ColumnConstraints();
			column.setHgrow(Priority.ALWAYS);
			column.setHalignment(HPos.CENTER);
			raceCarGridPane.getColumnConstraints().add(column);	
		}
	}
}
