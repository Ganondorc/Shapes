package shapes;
/*
 * Name: Michael Frake
 * Project: CMSC 3 Project 1
 * Date: Aug 25, 2021
 * Description: a standard geometric cube and volume method
 */

import java.util.ArrayList;

public class Cube extends ThreeDimensionalShape {
	public double length;
	
	public Cube(double length) {
		this.length = length;
		this.volume = getVolume();
		paramSize = 1;
		paramNames.add("length");
	}
	
	public Cube() {
		this(0);
	}
	
	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.length = params.get(0);
		this.volume = getVolume();
		return true;
	}
	
	public double getVolume() {
		return Math.pow(length, 3);
	}
}
