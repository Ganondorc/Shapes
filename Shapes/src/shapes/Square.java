package shapes;
import java.awt.Point;
import java.util.ArrayList;

/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: a standard geometric square and area method
 */

public class Square extends TwoDimensionalShape {
	public double length;
	
	public Square(double length) {
		this.length = length;
		this.area = getArea();
		paramSize = 1;
	}
	
	public Square() {
		this(0);
	}

	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize) return false;
		this.length = params.get(0);
		this.area = getArea();
		return true;
	}
	
	public double getArea() {
		return length * length;
	}
	
	private ArrayList<Point> squarePoints() {
		ArrayList<Point> p = new ArrayList<Point>(4);

		Point p0 = new Point(0, 0);
		p.add(p0);
		Point p1 = new Point((int) length, 0);
		p.add(p1);
		Point p2 = new Point((int) length, (int) length);
		p.add(p2);
		Point p3 = new Point(0, (int) length);
		p.add(p3);
		

		Point center = new Point((p1.x + p3.x) / 2, (p1.y + p3.y) / 2);

		for (Point a : p)
			a.translate(-center.x, -center.y);

		return p;
	}

	@Override
	public ArrayList<Point> shapePoints() {
		return squarePoints();
	}
}
