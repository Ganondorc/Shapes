/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a standard geometric cube and volume method
 */

package shapes.threedimensionalshapes;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;

public class Cube extends ThreeDimensionalShape {
	public double length;

	public Cube(double length) {
		this.length = length;
		this.volume = getVolume();
		paramSize = 1;
		paramNames.add("length");
		maxMultiplier = 0.6;
	}

	public Cube() {
		this(0);
	}

	@Override
	public boolean setParameters(ArrayList<Double> params) {
		if (params.size() != paramSize)
			return false;
		this.length = params.get(0);
		this.volume = getVolume();
		return true;
	}

	public double getVolume() {
		return Math.pow(length, 3);
	}
	
	@Override
	public Group getFXGroup() {
		Node element = new javafx.scene.shape.Box(length, length, length);
		return new Group(element);
	}
}
