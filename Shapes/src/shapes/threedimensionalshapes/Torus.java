/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a classic three-dimensional Torus, or doughnut as it is colloquially referred to as
 */

package shapes.threedimensionalshapes;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.DrawMode;

public class Torus extends ThreeDimensionalShape {
	public double minorRadius; // radius of the circle that comprises the "dough" part of the doughnut
	public double majorRadius; // radius of the center of the doughtnut's hole to the center of minorRadius

	public Torus(double minorRadius, double majorRadius) {
		this.minorRadius = minorRadius;
		this.majorRadius = majorRadius;
		this.volume = getVolume();
		paramSize = 2;
		paramNames.add("minor radius");
		paramNames.add("major radius");
		maxMultiplier = 0.25;
	}

	public Torus() {
		this(0, 0);
	}

	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.minorRadius = params.get(0);
		this.majorRadius = params.get(1);
		this.volume = getVolume();
		return true;
	}

	public double getVolume() {
		return (2 * Math.PI * majorRadius) * (Math.PI * Math.pow(minorRadius, 2)); // circumference of major times area
																					// of minor
	}

	public Group getFXGroup() {
		org.fxyz.shapes.Torus torus = new org.fxyz.shapes.Torus(32, 32, majorRadius, minorRadius, shapeColor);
		torus.setDrawMode(DrawMode.LINE);
		Node element = torus;
		group = new Group(element);
		return group;
	}
}
