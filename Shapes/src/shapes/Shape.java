/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: a generic shape, which includes a number of dimensions
 */

package shapes;
import java.util.ArrayList;


public class Shape {
	public int numberOfDimensions;
	public int paramSize;
	public ArrayList<String> paramNames = new ArrayList<String>();
	public double maxMultiplier = 1.0;
	
	public String getName() {
		return this.getClass().getSimpleName();
	}

	public Shape getInstance(String name) {
		Class<?> shape;
		Object instance = null;
		try {
			shape = Class.forName("shapes." + name);
			instance = shape.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Shape) instance;
	}
	
	public boolean setParameters(ArrayList<Double> params) {
		return false;
	}
	
	public double getAreaOrVolume() {
		return 0;
	}
}
