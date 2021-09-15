/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a standard geometric rectangle and area method
 */

package shapes.twodimensionalshapes;
import java.awt.Point;
import java.util.ArrayList;

public class Rectangle extends TwoDimensionalShape {
	public double length;
	public double width;
	
	public Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
		this.area = getArea();
		paramSize = 2;
		paramNames.clear();
		paramNames.add("length");
		paramNames.add("width");
	}
	
	public Rectangle() {
		this(0, 0);
	}

	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize) return false;
		this.length = params.get(0);
		this.width = params.get(1);
		this.area = getArea();
		return true;
	}
	
	public double getArea() {
		return length * width;
	}
	
	private ArrayList<Point> rectPoints() {
		ArrayList<Point> p = new ArrayList<Point>(4);

		Point p0 = new Point(0, 0);
		p.add(p0);
		Point p1 = new Point((int) length, 0);
		p.add(p1);
		Point p2 = new Point((int) length, (int) width);
		p.add(p2);
		Point p3 = new Point(0, (int) width);
		p.add(p3);

		Point center = new Point((p1.x + p3.x) / 2, (p1.y + p3.y) / 2);

		for (Point a : p)
			a.translate(-center.x, -center.y);

		return p;
	}

	@Override
	public ArrayList<Point> shapePoints() {
		return rectPoints();
	}
	
	/*public String getRect() {
		return "get rekt";
	}*/
}
