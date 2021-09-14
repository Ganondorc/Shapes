/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: generates the jpanel or jfxpanel for a given shape
 */

package shapes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.fxyz.shapes.Cone;
import org.fxyz.shapes.Torus;

import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.InputEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class PaintedShape {
	private Object panel;
	public static final int size = 205;
	public static final Dimension sizeDim = new Dimension(size, size);
	private Shape shape;
	private static Scene content;
	ArrayList<Point> points;

	public PaintedShape(Shape shape) {
		this.shape = shape;
		if (shape.getClass().getSuperclass().getSimpleName().equals("TwoDimensionalShape")) {
			points = ((TwoDimensionalShape) shape).shapePoints();
			panel = painted2DShapePanel();
		} else if (shape.getClass().getSuperclass().getSimpleName().equals("ThreeDimensionalShape")) {
			try {
				panel = painted3DShapePanel();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else {
			panel = null;
		}
	}

	@SuppressWarnings("serial")
	public JPanel painted2DShapePanel() {
		final Point offset = new Point((int) (sizeDim.getWidth() / 2), (int) (sizeDim.getHeight() / 2));
		return new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setPreferredSize(sizeDim);
				setMinimumSize(sizeDim);
				Border border = BorderFactory.createLineBorder(java.awt.Color.BLACK);
				Border margin = new EmptyBorder(10, 10, 10, 10);
				setBorder(new CompoundBorder(border, margin));

				g.setColor(java.awt.Color.BLACK);
				for (int i = 0; i < points.size(); i++)
					g.drawLine(points.get(i).x + offset.x, points.get(i).y + offset.y,
							points.get((i + 1) % points.size()).x + offset.x,
							points.get((i + 1) % points.size()).y + offset.y);
			};
		};
	}

	public JFXPanel painted3DShapePanel() throws Exception {
		String shapeName = shape.getName();
		Node element;
		Color shapeColor = new Color(0, 0, 0, 0.2);
		if (shapeName.equals("Cone")) {
			shapes.Cone coneShape = (shapes.Cone) shape;
			new Cone();
			Cone cone = new Cone(32, coneShape.radius, coneShape.height, shapeColor);
			cone.setEmissiveLightingColor(Color.BLUE);
			cone.setEmissiveLightingOn(true);
			cone.setDrawMode(DrawMode.LINE);
			element = cone;
		} else if (shapeName.equals("Cube")) {
			double length = ((shapes.Cube) shape).length;
			element = new javafx.scene.shape.Box(length, length, length);
		} else if (shapeName.equals("Cylinder")) {
			element = new javafx.scene.shape.Cylinder(((shapes.Cylinder) shape).radius,
					((shapes.Cylinder) shape).height, 32);
		} else if (shapeName.equals("Sphere")) {
			element = new javafx.scene.shape.Sphere(((shapes.Sphere) shape).radius, 32);
		} else if (shapeName.equals("Torus")) {
			Torus torus = new org.fxyz.shapes.Torus(32, 64, ((shapes.Torus) shape).majorRadius,
					((shapes.Torus) shape).minorRadius, shapeColor);
			torus.setDrawMode(DrawMode.LINE);
			torus.setEmissiveLightingColor(Color.BLUE);
			torus.setEmissiveLightingOn(true);
			element = torus;
		} else {
			System.out.println("Not good.");
			throw new Exception("Invalid shape.");
		}
		Rotate rotateX = new Rotate(30, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(30, Rotate.Y_AXIS);
		Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
		Translate centered = new Translate(sizeDim.getWidth() / 2, sizeDim.getHeight() / 2, 0);
		element.getTransforms().addAll(centered, rotateX, rotateY, rotateZ);

		content = new Scene(new Group(element), 200, 200, true, SceneAntialiasing.BALANCED);
		content.setOnMouseClicked(new EventHandler<InputEvent>() {
			@Override
			public void handle(InputEvent event) {
				Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
				Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
				Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);
				element.getTransforms().setAll(centered, rotateX, rotateY, rotateZ);
			}
		});
		final JFXPanel fxPanel = new JFXPanel();
		fxPanel.setScene(content);
		return fxPanel;
	}

	public Object getPanel() {
		return panel;
	}
}

