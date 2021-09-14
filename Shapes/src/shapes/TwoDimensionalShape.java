package shapes;
import java.awt.Point;
import java.util.ArrayList;

/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: a classic two-dimensional shape
 */

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
}
