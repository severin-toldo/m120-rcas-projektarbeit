package com.stoldo.m120_rcas_projektarbeit.util;

import com.stoldo.m120_rcas_projektarbeit.model.rcas.MMMSinglePoint;
import com.stoldo.m120_rcas_projektarbeit.model.rcas.RaceCar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

/**
 * Utility Class for the analysis of the cornering capabilities of a RaceCar
 * Object. Calculates various values and chart data for a specific race car with
 * a specific front and rear tire model.
 * 
 * @author suy
 *
 */
public class CorneringAnalyserUtil {

	private static final double G = 9.81;
	private static final double TO_DELTA = 20.0;
	private static final double DELTA_INCREMENT = 0.5;
	private static final double TO_BETA = 10.0;
	private static final double BETA_INCREMENT = 0.5;
	
	// default speed for the calculation of the MMM diagram in m/s.
	private static final double DEFAULT_SPEED = 100 / 3.6;
	
	// body slip angle delta values for the MMM chart in degrees (�).
	private static final double FROM_DELTA = -20.0;
	
	// steering angle beta values for the MMM chart in degrees (�).
	private static final double FROM_BETA = -10.0;
	
	// approximation coefficient defines how exact the calculation
	// of the lateral force lateralAccel will be approximated in
	// m/s2.
	private static final double APPROXIMATION_COEFFICIENT = 0.001;
	

	/*
	 * Other possible calculations to be offered in this class as a TODO for
	 * further development.
	 * 
	 * Double getYawInertia(...): Ann�herung Massentr�gheitsmoment mit der
	 * Formel Jz (kgm2) = 0.1269 * Masse (kg) * Radstand (mm) * Gesamtl�nge des
	 * Fahrzeugs (mm).
	 * 
	 * Methods which return the distribution of the weight in percent.
	 * 
	 */

	/**
	 * 
	 * @param raceCar
	 * @return - front static axle weight in kg.
	 */
	public static Double getStaticFrontAxleWeight(RaceCar raceCar) {
		return raceCar.getCornerWeightFL() + raceCar.getCornerWeightFR();
	}

	/**
	 * 
	 * @param raceCar
	 * @return - rear static axle weight in kg.
	 */
	public static Double getStaticRearAxleWeight(RaceCar raceCar) {
		return raceCar.getCornerWeightRL() + raceCar.getCornerWeightRR();
	}

	/**
	 * 
	 * @param raceCar
	 * @return - static weight of the left side in kg.
	 */
	public static Double getStaticLeftSideWeight(RaceCar raceCar) {
		return raceCar.getCornerWeightFL() + raceCar.getCornerWeightFR();
	}

	/**
	 * 
	 * @param raceCar
	 * @return - static weight of the right side in kg.
	 */
	public static Double getStaticRightSideWeight(RaceCar raceCar) {
		return raceCar.getCornerWeightRL() + raceCar.getCornerWeightRR();
	}

	/**
	 * 
	 * @param raceCar
	 * @return - diagonal weight rear left - front right (RL-FR) in kg.
	 */
	public static Double getDiagonalWeightRLFR(RaceCar raceCar) {
		return raceCar.getCornerWeightRL() + raceCar.getCornerWeightFR();
	}

	/**
	 * 
	 * @param raceCar
	 * @return - diagonal weight rear right - front left (RR-FL) in kg.
	 */
	public static Double getDiagonalWeightRRFL(RaceCar raceCar) {
		return raceCar.getCornerWeightRR() + raceCar.getCornerWeightFL();
	}

	/**
	 * 
	 * @param raceCar
	 * @return - total race car weight in kg.
	 */
	public static Double getTotalCarWeight(RaceCar raceCar) {
		return getStaticFrontAxleWeight(raceCar) + getStaticRearAxleWeight(raceCar);
	}

	/**
	 * 
	 * @param raceCar
	 * @return - distance lf from front axle to cog in m.
	 */
	public static Double getCogDistanceFromFrontAxle(RaceCar raceCar) {
		return (getStaticRearAxleWeight(raceCar) * raceCar.getWheelbase()) / getTotalCarWeight(raceCar);
	}

	/**
	 * 
	 * @param raceCar
	 * @return - distance lr from rear axle to cog in m.
	 */
	public static Double getCogDistanceFromRearAxle(RaceCar raceCar) {
		return raceCar.getWheelbase() - getCogDistanceFromFrontAxle(raceCar);
	}

	/**
	 * Helper private function to approximate the lateral force of the race car
	 * and to calculate the corresponding lateral force ay in G and yaw moment
	 * Mz in Nm. It returns an MMMSinglePointHelper which represents a single
	 * Point in the MMM diagramm with x=ay and y=Mz. This method is created to
	 * prevent code duplication in the calculation of the MMM charts.
	 * 
	 * 
	 * @param raceCar
	 *            - race car object
	 * @param carSpeed
	 *            - speed of the car in m/s.
	 * @param beta
	 *            - vehicle slip angle "beta" in degrees (�).
	 * @param delta
	 *            - steering angle "delta" in degrees (�).
	 */
	private static MMMSinglePoint calculateMMMSinglePoint(RaceCar raceCar, Double carSpeed, Double beta, Double delta) {
		// yaw rate in radians (s^-1), initially assumed with 0.1.
		Double yawRate = 0.1;
		// lateral acceleration (m/s^2), initially assumed with 0.8g.
		MMMSinglePoint singlePoint = new MMMSinglePoint(0.0, 0.0, (0.8 * G), 0.0);
		
		singlePoint.setBeta(beta);
		singlePoint.setDelta(delta);

		// calculates the difference of the lateral forces.
		Double approxDiff = 100.0;

		Double slipAngleFL = 0.0;
		Double slipAngleFR = 0.0;
		Double slipAngleRL = 0.0;
		Double slipAngleRR = 0.0;

		Double tireLoadFL = 0.0;
		Double tireLoadFR = 0.0;
		Double tireLoadRL = 0.0;
		Double tireLoadRR = 0.0;

		Double tireForceFL = 0.0;
		Double tireForceFR = 0.0;
		Double tireForceRL = 0.0;
		Double tireForceRR = 0.0;

		while (Math.abs(approxDiff) >= APPROXIMATION_COEFFICIENT) {
			Double oldLateralAccel = singlePoint.getLateralAccel();

			// basic variables needed to calculate the slip angle and
			// corner weights for the given lateral acceleration.
			Double vY = carSpeed * Math.sin(Math.toRadians(singlePoint.getBeta()));
			Double vX = carSpeed * Math.cos(Math.toRadians(singlePoint.getBeta()));
			Double lf = getCogDistanceFromFrontAxle(raceCar);
			Double lr = getCogDistanceFromRearAxle(raceCar);
			Double l = raceCar.getWheelbase();
			Double bf = raceCar.getFrontTrack();
			Double br = raceCar.getRearTrack();
			Double hbo = raceCar.getCogHeight();

			// calculate slip angles for all four tires in degrees (�).
			slipAngleFL = singlePoint.getDelta()
					- Math.toDegrees(Math.atan((vY + (yawRate * lf)) / (vX - (yawRate * (bf / 2)))));
			slipAngleFR = singlePoint.getDelta()
					- Math.toDegrees(Math.atan((vY + (yawRate * lf)) / (vX + (yawRate * (bf / 2)))));
			slipAngleRL = -1.0 * Math.toDegrees(Math.atan((vY - (yawRate * lr)) / (vX - (yawRate * (br / 2)))));
			slipAngleRR = -1.0 * Math.toDegrees(Math.atan((vY - (yawRate * lr)) / (vX + (yawRate * (br / 2)))));

			/*
			 * Calculate the corner weights on each wheel for lateralAccel as
			 * forces in N. It is assumed that the longitudinal acceleration
			 * (ax) is 0 and that aerodynamic load (Fw,z,L) is 0, too.
			 * 
			 * As a TODO this part can be extended as soon as the RaceCar class
			 * implements a suspension and aero model.
			 */
			Double ax = 0.0;
			Double aeroLoadFront = 0.0;
			Double aeroLoadRear = 0.0;
			Double frontRollDist = raceCar.getFrontRollDist();
			Double rearRollDist = 1.0 - raceCar.getFrontRollDist();

			tireLoadFL = getTotalCarWeight(raceCar) * ((frontRollDist * G) - ((hbo / l) * ax))
					* (0.5 - ((hbo / bf) * (singlePoint.getLateralAccel() / G))) + aeroLoadFront;
			tireLoadFR = getTotalCarWeight(raceCar) * ((frontRollDist * G) - ((hbo / l) * ax))
					* (0.5 + ((hbo / bf) * (singlePoint.getLateralAccel() / G))) + aeroLoadFront;
			tireLoadRL = getTotalCarWeight(raceCar) * ((rearRollDist * G) + ((hbo / l) * ax))
					* (0.5 - ((hbo / br) * (singlePoint.getLateralAccel() / G))) + aeroLoadRear;
			tireLoadRR = getTotalCarWeight(raceCar) * ((rearRollDist * G) + ((hbo / l) * ax))
					* (0.5 + ((hbo / br) * (singlePoint.getLateralAccel() / G))) + aeroLoadRear;

			// fill up tire forces based on slip angles and tire loads
			// in N and calculate new lateral acceleration.
			tireForceFL = raceCar.getFrontAxleTireModel().getLateralTireForce(slipAngleFL, tireLoadFL);
			tireForceFR = raceCar.getFrontAxleTireModel().getLateralTireForce(slipAngleFR, tireLoadFR);
			tireForceRL = raceCar.getRearAxleTireModel().getLateralTireForce(slipAngleRL, tireLoadRL);
			tireForceRR = raceCar.getRearAxleTireModel().getLateralTireForce(slipAngleRR, tireLoadRR);

			singlePoint.setLateralAccel(
					(tireForceFL + tireForceFR + tireForceRL + tireForceRR) / getTotalCarWeight(raceCar));

			// calculate new yaw rate
			yawRate = singlePoint.getLateralAccel() / carSpeed;
			// calculate difference for loop control.
			approxDiff = oldLateralAccel - singlePoint.getLateralAccel();
		}
		// calculate yaw moment Mz (Nm) after approximating the lateral
		// acceleration ay.
		Double yawMoment = ((tireForceFL + tireForceFR) * getCogDistanceFromFrontAxle(raceCar))
				- ((tireForceRL + tireForceRR) * getCogDistanceFromRearAxle(raceCar));
		singlePoint.setYawMoment(yawMoment);
		return singlePoint;
	}

	/**
	 * Calculates and returns Chart Data for an MMM (Milliken Moment Method)
	 * Yaw-Moment (Nm) vs. Lateral Acceleration (G) Diagram for the given race
	 * car at the given speed. For now, no aerodynamic effects are being
	 * considered.
	 * 
	 * @param raceCar
	 *            - the race car object
	 * @return - an observable list containing all the series for a complete MMM
	 *         diagram.
	 */
	public static ObservableList<Series<Number, Number>> generateMMMChartData(RaceCar raceCar) {
		// create observable list with series of chart data.
		ObservableList<Series<Number, Number>> chartDataList = FXCollections.observableArrayList();
		// define the speed in m/s. calculate the MMM chart for 100 km/h, which
		// equals to <speed in km/h>/3.6.
		Double carSpeed = DEFAULT_SPEED;

		/*
		 * Draw the steering angle curves first. Start with steering angle as
		 * delta in degrees (�).
		 */
		for (Double delta = FROM_DELTA; delta <= TO_DELTA; delta += DELTA_INCREMENT) {

			// the curve series need to be created here, because we are drawing
			// the curves for a constant delta value in the next for loop.
			Series<Number, Number> betaCurve = new Series<Number, Number>();
			chartDataList.add(betaCurve);
			betaCurve.setName(String.format("D:%.2f", delta));

			// body slip angle (beta) loop in degrees (�).
			for (Double beta = FROM_BETA; beta <= TO_BETA; beta += BETA_INCREMENT) {
				// calculate a single point based on the approximation of the
				// lateral force and receive results in the helper inner class.
				MMMSinglePoint singlePoint = calculateMMMSinglePoint(raceCar, carSpeed, beta, delta);

				// add data point [ay, Mz] to curve series.
				Data<Number, Number> betaData = new Data<Number, Number>((singlePoint.getLateralAccel() / G),
						singlePoint.getYawMoment());
				betaData.setExtraValue(singlePoint);
				betaCurve.getData().add(betaData);
			}
		}

		/*
		 * Second, draw the body slip angle curves in degrees (�).
		 */
		for (Double beta = FROM_BETA; beta <= TO_BETA; beta += BETA_INCREMENT) {

			// the curve series need to be created here, because we are drawing
			// the curves for a constant beta value in the next for loop.
			Series<Number, Number> deltaCurve = new Series<Number, Number>();
			chartDataList.add(deltaCurve);
			deltaCurve.setName(String.format("B:%.2f", beta));

			// steering angle (delta) loop in degrees (�).
			for (Double delta = FROM_DELTA; delta <= TO_DELTA; delta += DELTA_INCREMENT) {

				// calculate lateral force approximation and receive results in
				// the helper inner class.
				MMMSinglePoint singlePoint = calculateMMMSinglePoint(raceCar, carSpeed, beta, delta);

				// add data point [ay, Mz] to curve series.
				Data<Number, Number> deltaData = new Data<Number, Number>((singlePoint.getLateralAccel() / G),
						singlePoint.getYawMoment());
				deltaData.setExtraValue(singlePoint);
				deltaCurve.getData().add(deltaData);
			}
		}
		return chartDataList;
	}

	/**
	 * Calculates the MMM Control in Nm/deg for a given body slip angle beta in
	 * degrees for a range of steering angle values in degrees.<br>
	 * <br>
	 * 
	 * @param raceCar
	 *            - the race car object
	 * @param beta
	 *            - body slip angle in degrees
	 * @param deltaFrom
	 *            - steering angle from in degrees
	 * @param deltaTo
	 *            - steering angle to in degrees
	 * @return A formatted string with the results
	 */
	public static String getMMMControlFormatted(RaceCar raceCar, Double beta, Double deltaFrom, Double deltaTo) {
		Double controlValue = getMMMControlValue(raceCar, beta, deltaFrom, deltaTo);
		return String.format("%s Control for beta=%.2f between delta=%.2f and delta=%.2f: %.2f Nm / deg",
				raceCar.getName(), beta, deltaFrom, deltaTo, controlValue);
	}

	/**
	 * See getMMMControlFormatted.
	 */
	public static Double getMMMControlValue(RaceCar raceCar, Double beta, Double deltaFrom, Double deltaTo) {
		MMMSinglePoint point1 = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, beta, deltaFrom);
		MMMSinglePoint point2 = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, beta, deltaTo);
		return (point2.getYawMoment() - point1.getYawMoment());
	}

	/**
	 * Calculates the MMM Stability in Nm/deg for a given steering angle delta
	 * in degrees for a range of body slip angle beta in degrees.<br>
	 * <br>
	 * 
	 * @param raceCar
	 *            - the race car object
	 * @param delta
	 *            - steering angle in degrees
	 * @param betaFrom
	 *            - body slip angle from in degrees
	 * @param betaTo
	 *            - body slip angle to in degrees
	 * @return A formatted string with the results
	 */
	public static String getMMMStabilityFormatted(RaceCar raceCar, Double delta, Double betaFrom, Double betaTo) {
		Double stabilityValue = getMMMStabilityValue(raceCar, delta, betaFrom, betaTo);
		return String.format("%s Stability for delta=%.2f between beta=%.2f and beta=%.2f: %.2f Nm / deg",
				raceCar.getName(), delta, betaFrom, betaTo, stabilityValue);
	}

	/**
	 * See getMMMStabilityValue.
	 */
	public static Double getMMMStabilityValue(RaceCar raceCar, Double delta, Double betaFrom, Double betaTo) {
		MMMSinglePoint point1 = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, betaFrom, delta);
		MMMSinglePoint point2 = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, betaTo, delta);
		return (point2.getYawMoment() - point1.getYawMoment());
	}

	/**
	 * Calculates the MMM Balance Value Mz.lim in Nm. It represents the yaw
	 * moment at the maximum possible lateral acceleration. A positive value
	 * indicated that the car is oversteering and vice versa.
	 * 
	 * @param raceCar
	 *            - the race car object.
	 * @return Yaw Moment at the limit (Mz,lim) value in Nm.
	 */
	public static Double getMMMBalanceValue(RaceCar raceCar) {
		Double maxAy = 0.0;
		Double maxMz = 0.0;
		MMMSinglePoint point = null;
		for (Double beta = FROM_BETA; beta <= TO_BETA; beta += BETA_INCREMENT) {
			for (Double delta = FROM_DELTA; delta <= TO_DELTA; delta += DELTA_INCREMENT) {
				point = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, beta, delta);
				// consider only right side (positive values)
				if (point.getLateralAccel() > 0) {
					if (point.getLateralAccel() > maxAy) {
						maxAy = point.getLateralAccel();
						maxMz = point.getYawMoment();
					}
				}
			}
		}
		return maxMz;
	}

	/**
	 * Calculates the MMM Grip Value ay in m/s^2. It represents the maximum
	 * possible lateral acceleration of the race car.
	 * 
	 * @param raceCar
	 *            - the race car object.
	 * @return Max. lateral acceleration (ay) value in m/s^2.
	 */
	public static Double getMMMGripValue(RaceCar raceCar) {
		Double maxAy = 0.0;
		MMMSinglePoint point = null;
		for (Double beta = FROM_BETA; beta <= TO_BETA; beta += BETA_INCREMENT) {
			for (Double delta = FROM_DELTA; delta <= TO_DELTA; delta += DELTA_INCREMENT) {
				point = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, beta, delta);
				// consider only right side (positive values)
				if (point.getLateralAccel() > 0) {
					if (point.getLateralAccel() > maxAy) {
						maxAy = point.getLateralAccel();
					}
				}
			}
		}
		return maxAy;
	}

	/**
	 * Calculates the MMM maximal lateral acceleration for a race car and the
	 * resulting yaw moment at this point.<br>
	 * <br>
	 * 
	 * @param raceCar
	 *            - the race car object
	 * @return A formatted string with the results
	 */
	public static String getMaxLateralAccelerationFormatted(RaceCar raceCar) {
		Double maxAy = 0.0;
		Double atMz = 0.0;
		Double atBeta = 0.0;
		Double atDelta = 0.0;
		MMMSinglePoint point = null;
		for (Double beta = FROM_BETA; beta <= TO_BETA; beta += BETA_INCREMENT) {
			for (Double delta = FROM_DELTA; delta <= TO_DELTA; delta += DELTA_INCREMENT) {
				point = calculateMMMSinglePoint(raceCar, DEFAULT_SPEED, beta, delta);
				if (Math.abs(point.getLateralAccel()) > Math.abs(maxAy)) {
					maxAy = point.getLateralAccel();
					atMz = point.getYawMoment();
					atBeta = point.getBeta();
					atDelta = point.getDelta();
				}
			}
		}
		return String.format("%s max. ay: %.2f G @ %.2f Nm (beta=%.2f, delta=%.2f)", raceCar.getName(), (maxAy / G),
				atMz, atBeta, atDelta);
	}
}
