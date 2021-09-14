/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a classic three-dimensional shape
 */

package shapes;

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
