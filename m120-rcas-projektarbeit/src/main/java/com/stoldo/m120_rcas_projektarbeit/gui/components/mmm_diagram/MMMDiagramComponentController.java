package com.stoldo.m120_rcas_projektarbeit.gui.components.mmm_diagram;

import java.util.Iterator;

import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.shared.util.CorneringAnalyserUtil;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.GridPane;


public class MMMDiagramComponentController extends AbstractController {
	
	@FXML
	private GridPane gridPane;
	
	@FXML
	private LineChart<Number, Number> mainChart;
	
	private RaceCar raceCar;
	
	public MMMDiagramComponentController(RaceCar raceCar) {
		this.raceCar = raceCar;
	}
	
	@Override
	public void initialize() throws Exception {
		refresh(raceCar);
	}
	
	public void refresh(RaceCar raceCar) {
		printMMMDiagramValues(raceCar);
		ObservableList<Series<Number, Number>> dataList = CorneringAnalyserUtil.generateMMMChartData(raceCar);
		mainChart.setData(dataList);
		setSeriesStyle(dataList, ".chart-series-line", "-fx-stroke: blue; -fx-stroke-width: 1px;");
	}
	
	private void setSeriesStyle(ObservableList<Series<Number, Number>> dataList_1, String styleSelector, String lineStyle) {
		for (Iterator<Series<Number, Number>> iterator = dataList_1.iterator(); iterator.hasNext();) {
			Series<Number, Number> curve = (Series<Number, Number>) iterator.next();
			curve.getNode().lookup(styleSelector).setStyle(lineStyle);
		}
	}

	private void printMMMDiagramValues(RaceCar raceCar) {
		System.out.println("MMM Diagramm Values:");
		System.out.println(raceCar.toString());
		System.out.println(String.format(
				"%s: Grip = %.2f G\tBalance = %.2f Nm\tControl(entry) = %.2f Nm/deg\t"
						+ "Control(middle) = %.2f Nm/deg\tStability(entry) = %.2f Nm/deg\t"
						+ "Stability(middle) = %.2f Nm/deg",
				raceCar.getName(), CorneringAnalyserUtil.getMMMGripValue(raceCar) / 9.81, CorneringAnalyserUtil.getMMMBalanceValue(raceCar),
				CorneringAnalyserUtil.getMMMControlValue(raceCar, 0.0, 0.0, 10.0), CorneringAnalyserUtil.getMMMControlValue(raceCar, -5.0, 20.0, 30.0),
				CorneringAnalyserUtil.getMMMStabilityValue(raceCar, 0.0, 0.0, 1.0),
				CorneringAnalyserUtil.getMMMStabilityValue(raceCar, 20.0, -5.0, -4.0)));
	}
}
