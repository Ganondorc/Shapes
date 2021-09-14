package shapes;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: 
 */

public class Triangle extends TwoDimensionalShape {
	public double a;
	public double b;
	public double c;

	public Triangle(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.area = getArea();
		paramSize = 3;
		paramNames.clear();
		paramNames.addAll(Arrays.asList("a","b","c"));
	}

	public Triangle() {
		this(0, 0, 0);
	}

	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.a = params.get(0);
		this.b = params.get(1);
		this.c = params.get(2);
		this.area = getArea();
		return true;
	}

	public double getArea() {
		double halfPerimiter;
		halfPerimiter = (a + b + c) / 2.0;
		return Math.sqrt(halfPerimiter * (halfPerimiter - a) * (halfPerimiter - b) * (halfPerimiter - c));
	}

	private ArrayList<Point> trianglePoints() {
		double angle = Math.acos(-(Math.pow(a, 2) - Math.pow(b, 2) - Math.pow(c, 2)) / (2 * b * c));

		ArrayList<Point> p = new ArrayList<Point>(3);

		Point p0 = new Point(0, 0);
		p.add(p0);
		Point p1 = new Point((int) b, 0);
		p.add(p1);
		Point p2 = new Point((int) (Math.cos(angle) * c), (int) (Math.sin(angle) * c));
		p.add(p2);

		Point center = new Point((p0.x + p1.x + p2.x) / 3, (p0.y + p1.y + p2.y) / 3);

		for (Point a : p)
			a.translate(-center.x, -center.y);

		return p;
	}

	@Override
	public ArrayList<Point> shapePoints() {
		return trianglePoints();
	}

}
