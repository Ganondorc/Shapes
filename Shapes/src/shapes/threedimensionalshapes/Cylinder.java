/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a standard geometric cylinder and volume method
 */

package shapes.threedimensionalshapes;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;

public class Cylinder extends ThreeDimensionalShape {
	public double radius;
	public double height;
	
	public Cylinder(double radius, double height) {
		this.radius = radius;
		this.height = height;
		this.volume = getVolume();
		paramSize = 2;
		paramNames.add("radius");
		paramNames.add("height");
		maxMultiplier = 0.4;
	}
	
	public Cylinder() {
		this(0, 0);
	}
	
	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.radius = params.get(0);
		this.height = params.get(1);
		this.volume = getVolume();
		return true;
	}
	
	public double getVolume() {
		return Math.PI * Math.pow(radius, 2) * height;
	}
	
	@Override
	public Group getFXGroup() {
		Node element = new javafx.scene.shape.Cylinder(radius, height, 32);
		return new Group(element);
	}
}
