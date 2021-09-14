package shapes;
/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: a classic three-dimensional shape
 */

public class ThreeDimensionalShape extends Shape {
	public double volume;
	
	public ThreeDimensionalShape() {
		this.numberOfDimensions = 3;
	}

	public double getVolume() {
		return volume;
	}
	
	@Override
	public double getAreaOrVolume() {
		return getVolume();
	}
}
