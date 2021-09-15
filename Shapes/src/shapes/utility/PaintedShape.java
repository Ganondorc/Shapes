/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
 * Description: generates the jpanel or jfxpanel for a given shape
 */

package shapes.utility;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.InputEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import shapes.Shape;
import shapes.twodimensionalshapes.TwoDimensionalShape;

public class PaintedShape {
	private Object panel;
	public static final int size = 205;
	public static final Dimension panelSize = new Dimension(size, size);
	private Shape shape;
	private static Scene content;
	ArrayList<Point> points;

	public PaintedShape(Shape shape) {
		this.shape = shape;
		if (shape.dimensionSize == 2) {
			points = ((TwoDimensionalShape) shape).shapePoints();
			panel = painted2DShapePanel();
		} else if (shape.dimensionSize == 3) {
			try {
				panel = painted3DShapePanel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			panel = null;
		}
	}

	@SuppressWarnings("serial")
	public JPanel painted2DShapePanel() {
		final Point offset = new Point((int) (panelSize.getWidth() / 2), (int) (panelSize.getHeight() / 2));
		return new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setPreferredSize(panelSize);
				setMinimumSize(panelSize);
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
		Group group = shape.getFXGroup();

		Rotate rotateX = new Rotate(30, Rotate.X_AXIS);
		Rotate rotateY = new Rotate(30, Rotate.Y_AXIS);
		Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);
		Translate centered = new Translate(size / 2, size / 2, 0);
		group.getChildren().stream().forEach(n -> n.getTransforms().addAll(centered, rotateX, rotateY, rotateZ));

		content = new Scene(group, 200, 200, true, SceneAntialiasing.BALANCED);
		content.setOnMouseClicked(new EventHandler<InputEvent>() {
			@Override
			public void handle(InputEvent event) {
				Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
				Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
				Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);
				group.getChildren().stream().forEach(n -> n.getTransforms().setAll(centered, rotateX, rotateY, rotateZ));
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

