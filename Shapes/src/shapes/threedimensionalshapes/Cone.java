/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a standard geometric cone and volume method
 */

package shapes.threedimensionalshapes;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.DrawMode;

public class Cone extends ThreeDimensionalShape {
	//
	public double radius;
	public double height;
	
	public Cone(double radius, double height) {
		this.radius = radius;
		this.height = height;
		this.volume = getVolume();
		paramSize = 2;
		paramNames.add("radius");
		paramNames.add("height");
		maxMultiplier = 0.35;
	}
	
	public Cone() {
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
		return (Math.PI * Math.pow(radius, 2) * height) / 3.0;
	}
	
	@Override
	public Group getFXGroup() {
		org.fxyz.shapes.Cone cone = new org.fxyz.shapes.Cone(32, radius, height, shapeColor);
		cone.setDrawMode(DrawMode.LINE);
		Node element = cone;
		group = new Group(element);
		return group;
	}
}
