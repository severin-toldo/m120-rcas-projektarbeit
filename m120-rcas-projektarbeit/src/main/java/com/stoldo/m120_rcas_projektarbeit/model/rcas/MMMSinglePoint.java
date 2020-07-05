package com.stoldo.m120_rcas_projektarbeit.model.rcas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Helper inner class to be used as a return value for the caluclation of a
 * single Point in the yaw moment diagram.
 * 
 * @author suy
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class MMMSinglePoint {
	
	/**
	 *  body slip angle beta in degrees (�).
	 * */
	private Double beta;
	
	/**
	 *  steering angle delta in degrees (�).
	 * */
	private Double delta;
	
	/**
	 *  lateralAccel in m/s^2.
	 * */
	private Double lateralAccel;
	
	/**
	 *  yaw moment around the CoG in Nm.
	 * */
	private Double yawMoment;
}
