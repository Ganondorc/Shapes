/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a generic shape, which includes a number of dimensions
 */

package shapes;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Group;

public class Shape {
	public int dimensionSize;
	public int paramSize;
	public ArrayList<String> paramNames = new ArrayList<String>();
	public double maxMultiplier = 1.0;
	protected Group group = null;
	
	final static List<String> TwoDShapes = Arrays.asList("Circle", "Rectangle", "Square", "Triangle");
	final static List<String> ThreeDShapes = Arrays.asList("Cone", "Cube", "Cylinder", "Sphere", "Torus");
	
	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Shape getInstance(String name) {
		Class<?> shape;
		Object instance = null;
		try {
			String classString = "shapes." + (TwoDShapes.contains(name) ? "twodimensionalshapes." : "threedimensionalshapes.") + name;
			shape = Class.forName(classString);
			instance = shape.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Shape) instance;
	}
	
	public boolean setParameters(ArrayList<Double> params) {
		return false;
	}
	
	public double getAreaOrVolume() {
		return 0;
	}
	
	public ArrayList<Point> shapePoints() {
		return null;
	}
	
	public Group getFXGroup() {
		return group;
	}
}
