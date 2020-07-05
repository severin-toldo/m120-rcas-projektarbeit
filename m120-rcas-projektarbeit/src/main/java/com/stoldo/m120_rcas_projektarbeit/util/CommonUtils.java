package com.stoldo.m120_rcas_projektarbeit.util;

import com.stoldo.m120_rcas_projektarbeit.model.rcas.MagicFormulaTireModel;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.TireModel;

public class CommonUtils {
	
	public static double roundTwoDecimals(double d) {
		return Math.round(d * 100.0) / 100.0;
	}
	
	public static RaceCar getRaceCar1() {
		RaceCar myRaceCar1 = new RaceCar(420, 420, 370, 370);
		TireModel myTireModel1 = new MagicFormulaTireModel();
		myRaceCar1.setFrontRollDist(0.55);
		myRaceCar1.setFrontAxleTireModel(myTireModel1);
		myRaceCar1.setRearAxleTireModel(myTireModel1);
		myRaceCar1.setName("Car STD (blue)");
		return myRaceCar1;
	}
	
	public static RaceCar getRaceCar2() {
		RaceCar myRaceCar2 = new RaceCar(420, 420, 370, 370);
		TireModel myTireModel2Fr = new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.6, 0.000075);
		TireModel myTireModel2Rr = new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.8, 0.000075);
		myRaceCar2.setFrontRollDist(0.55);
		myRaceCar2.setFrontAxleTireModel(myTireModel2Fr);
		myRaceCar2.setRearAxleTireModel(myTireModel2Rr);
		myRaceCar2.setName("Car MOD (red)");
		return myRaceCar2;
	}
}
