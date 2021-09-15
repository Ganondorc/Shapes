/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a classic two-dimensional shape
 */

package shapes.twodimensionalshapes;

import shapes.Shape;

public class TwoDimensionalShape extends Shape {
	public double area;
	
	
	public TwoDimensionalShape() {
		this.dimensionSize = 2;
		paramNames.add("side");
	}
	
	public double getArea() {
		return area;
	}
	
	@Override
	public double getAreaOrVolume() {
		return getArea();
	}
}
