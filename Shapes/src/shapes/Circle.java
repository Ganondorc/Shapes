package shapes;
import java.awt.Point;
import java.util.ArrayList;

/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: a standard geometric circle and area method
 */

public class Circle extends TwoDimensionalShape {
	public double radius;

	public Circle(double radius) {
		this.radius = radius;
		this.area = getArea();
		paramSize = 1;
		paramNames.clear();
		paramNames.add("radius");
		maxMultiplier = 0.5;
	}

	public Circle() {
		this(0);
	}

	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.radius = params.get(0);
		return true;
	}

	public double getArea() {
		return Math.PI * Math.pow(radius, 2);
	}

	private ArrayList<Point> circlePoints() {
		final int size = 360;
		ArrayList<Point> p = new ArrayList<Point>(size);
		// Point center = new Point((int) radius, (int) radius);

		int x, y;
		for (int i = 0; i < size; i++) {
			x = (int) (radius * (Math.cos(((2 * Math.PI) / 360) * (i + (i * (360.0 / size))))));
			y = (int) (radius * (Math.sin(((2 * Math.PI) / 360) * (i + (i * (360.0 / size))))));
			p.add(new Point(x, y));
		}
		/*for (Point a : p)
			a.translate(-center.x, -center.y);*/

		return p;
	}

	@Override
	public ArrayList<Point> shapePoints() {
		return circlePoints();
	}
}
