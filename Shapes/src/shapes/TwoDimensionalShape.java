/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a classic two-dimensional shape
 */

package shapes;
import java.awt.Point;
import java.util.ArrayList;

public class TwoDimensionalShape extends Shape {
	public double area;
	
	public TwoDimensionalShape() {
		this.numberOfDimensions = 2;
		paramNames.add("side");
	}
	
	public double getArea() {
		return area;
	}
	
	public ArrayList<Point> shapePoints() {
		return null;
	}
	
	@Override
	public double getAreaOrVolume() {
		return getArea();
	}
}
