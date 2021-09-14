package shapes;
import java.util.ArrayList;


/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 25, 2021
 * Description: a sphere
 */

public class Sphere extends ThreeDimensionalShape {
	public double radius;
	
	public Sphere(double radius) {
		this.radius = radius;
		this.volume = getVolume();
		paramSize = 1;
		paramNames.add("radius");
		maxMultiplier = 0.45;
	}
	
	public Sphere() {
		this(0);
	}
	
	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.radius = params.get(0);
		this.volume = getVolume();
		return true;
	}
	
	public double getVolume() {
		return Math.PI * Math.pow(radius, 3);
	}
	
}
