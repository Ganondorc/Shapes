package shapes;
/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: 
 */

import java.util.ArrayList;

public class Torus extends ThreeDimensionalShape {
	public double minorRadius; // radius of the circle that comprises the "dough" part of the doughnut
	public double majorRadius; // radius of the center of the doughtnut's hole to the center of minorRadius

	public Torus(double minorRadius, double majorRadius) {
		this.minorRadius = minorRadius;
		this.majorRadius = majorRadius;
		this.volume = getVolume();
		paramSize = 2;
		paramNames.add("minor radius");
		paramNames.add("major radius");
		maxMultiplier = 0.25;
	}
	
	public Torus() {
		this(0, 0);
	}
	
	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.minorRadius = params.get(0);
		this.majorRadius = params.get(1);
		this.volume = getVolume();
		return true;
	}

	public double getVolume() {
		return (2 * Math.PI * majorRadius) * (Math.PI * Math.pow(minorRadius, 2)); // circumference of major times area of minor
	}
}
