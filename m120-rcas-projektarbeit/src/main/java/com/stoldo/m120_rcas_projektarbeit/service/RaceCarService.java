package com.stoldo.m120_rcas_projektarbeit.service;

import java.util.ArrayList;
import java.util.List;

import com.stoldo.m120_rcas_projektarbeit.model.rcas.MagicFormulaTireModel;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.TireModel;

import lombok.Getter;

/**
 * Mock Service Singleton to save race cars.
 * */
public class RaceCarService {
	
	private static RaceCarService instance;
	
	@Getter
	private List<RaceCar> raceCars = new ArrayList<>();
	
	private int id = 0;
	
	public static RaceCarService getInstance() {
		if (instance == null) {
			instance = new RaceCarService();
		}

		return instance;
	}

	private RaceCarService() {
		RaceCar myRaceCar1 = new RaceCar(generateId(), "Generated Car 1", 420, 420, 370, 370);
		TireModel myTireModel1 = new MagicFormulaTireModel();
		myRaceCar1.setFrontRollDist(0.55);
		myRaceCar1.setFrontAxleTireModel(myTireModel1);
		myRaceCar1.setRearAxleTireModel(myTireModel1);
		myRaceCar1.setName("Car STD (blue)");
		
		RaceCar myRaceCar2 = new RaceCar(generateId(), "Generated Car 2", 420, 420, 370, 370);
		TireModel myTireModel2Fr = new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.6, 0.000075);
		TireModel myTireModel2Rr = new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.8, 0.000075);
		myRaceCar2.setFrontRollDist(0.55);
		myRaceCar2.setFrontAxleTireModel(myTireModel2Fr);
		myRaceCar2.setRearAxleTireModel(myTireModel2Rr);
		myRaceCar2.setName("Car MOD (red)");
		
		raceCars.add(myRaceCar1);
		raceCars.add(myRaceCar2);
	}
	
	public void addRaceCar(RaceCar raceCar) {
		raceCars.add(raceCar);
		System.out.println(raceCar.getImageFileName());
	}
	
	public void removeRaceCar(RaceCar raceCar) {
		raceCars.remove(raceCar);
	}
	
	public int generateId() {
		return ++id;
	}
	
	public RaceCar generateDefaultRaceCar() {
		return new RaceCar(generateId(), null, 100.00, 100.00, 100.00, 100.00);
	}
}
