/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a classic three-dimensional shape
 */

package shapes.threedimensionalshapes;

import javafx.scene.paint.Color;
import shapes.Shape;

public class ThreeDimensionalShape extends Shape {
	public double volume;
	protected Color shapeColor = new Color(0, 0, 0, 0.2);
	
	public ThreeDimensionalShape() {
		this.dimensionSize = 3;
	}

	public double getVolume() {
		return volume;
	}
	
	@Override
	public double getAreaOrVolume() {
		return getVolume();
	}
}
