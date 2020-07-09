package com.stoldo.m120_rcas_projektarbeit.service;

import java.util.Arrays;
import java.util.List;

import com.stoldo.m120_rcas_projektarbeit.model.rcas.MagicFormulaTireModel;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;
import com.stoldo.m120_rcas_projektarbeit.shared.util.CommonUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

/**
 * Mock Service Singleton to manage race cars.
 * */
public class RaceCarService {
	
	private static RaceCarService instance;
	private static final int RACE_CAR_AMOUNT = 10;
	
	@Getter
	private ObservableList<RaceCar> raceCars = FXCollections.observableArrayList(); // TODO refresh only entry just maje it better
	
	private int id = 0;
	
	public static RaceCarService getInstance() {
		if (instance == null) {
			instance = new RaceCarService();
		}

		return instance;
	}

	private RaceCarService() {
		generateRaceCars();
	}
	
	public void saveRaceCar(RaceCar raceCar) {
		removeRaceCarById(raceCar.getId()); // if race car with id exists, delete this one and save the new one
		raceCars.add(raceCar);	
	}
	
	public void removeRaceCarById(int id) {
		RaceCar existing = getRaceCarById(id);
		
		if (existing != null) {
			raceCars.remove(existing);
		}
	}
	
	public RaceCar getRaceCarById(int id) {
		return raceCars
				.stream()
				.filter(rc -> rc.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public int generateId() {
		return ++id;
	}
	
	public RaceCar generateDefaultRaceCar() {
		return new RaceCar(generateId(), "", 100.00, 100.00, 100.00, 100.00);
	}
	
	/**
	 * Attempt to make race car generation somewhat random
	 * */
	private void generateRaceCars() {
		List<String> availableImageFileNames = Arrays.asList("racecar1.png", "racecar2.png", "racecar3.png");
		
		for (int i = 0; i < RACE_CAR_AMOUNT; i++) {
			RaceCar rc = null;
			
			if (i % 2 == 0) {
				rc = new RaceCar(0, "", 420, 420, 370, 370);
				rc.setFrontRollDist(0.55);
				rc.setFrontAxleTireModel(new MagicFormulaTireModel());
				rc.setRearAxleTireModel(new MagicFormulaTireModel());	
			} else {
				rc = new RaceCar(0, "", 800, 300, 150, 230);
				rc.setFrontRollDist(0.40);
				rc.setFrontAxleTireModel(new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.6, 0.000075));
				rc.setRearAxleTireModel(new MagicFormulaTireModel(1.3, 15.2, -1.6, 1.8, 0.000075));	
			}
			
			rc.setId(generateId());
			rc.setName("Race Car " + i);
			rc.setImageFileName(CommonUtils.getRandomElementFromList(availableImageFileNames));
			raceCars.add(rc);
		}
	}
}
