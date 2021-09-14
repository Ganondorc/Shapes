/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a simple 3d sphere
 */

package shapes;
import java.util.ArrayList;

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
