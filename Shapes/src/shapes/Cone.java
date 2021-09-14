package shapes;
/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: a standard geometric cone and volume method
 */

import java.util.ArrayList;

public class Cone extends ThreeDimensionalShape {
	public double radius;
	public double height;
	
	public Cone(double radius, double height) {
		this.radius = radius;
		this.height = height;
		this.volume = getVolume();
		paramSize = 2;
		paramNames.add("radius");
		paramNames.add("height");
	}
	
	public Cone() {
		this(0, 0);
	}
	
	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.radius = params.get(0);
		this.height = params.get(1);
		this.volume = getVolume();
		return true;
	}
	
	public double getVolume() {
		return (Math.PI * Math.pow(radius, 2) * height) / 3.0;
	}
}