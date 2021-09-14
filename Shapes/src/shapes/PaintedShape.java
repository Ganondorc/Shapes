/*
 * Name: Michael Frake
 * Project: CMSC 3 Project 1
 * Date: Sep 13, 2021
 * Description: generates the jpanel or jfxpanel for a given shape
 */

package shapes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
	public static Dimension size = new Dimension(205, 205);
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
		final Point offset = new Point((int) (size.getWidth() / 2), (int) (size.getHeight() / 2));
		return new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setPreferredSize(size);
				setMinimumSize(size);
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
		Translate centered = new Translate(size.getWidth() / 2, size.getHeight() / 2, 0);
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
		// content.setFill(Color.ALICEBLUE);
		final JFXPanel fxPanel = new JFXPanel();
		fxPanel.setScene(content);
		return fxPanel;
	}

	public Object getPanel() {
		return panel;
	}

	public static void main(String[] args) {
		// final CountDownLatch latch = new CountDownLatch(1);
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JFXPanel(); // initializes JavaFX environment
				JFXPanel panel = (JFXPanel) new PaintedShape(new shapes.Cone(20, 50)).getPanel();
				JFXPanel panel1 = (JFXPanel) new PaintedShape(new shapes.Cone(2, 5)).getPanel();
				JFXPanel panel2 = (JFXPanel) new PaintedShape(new shapes.Cone(60, 150)).getPanel();
				// panel.setPreferredSize(new Dimension(200, 200));
				// panel.setLayout(new GridBagLayout());
				Thread t0 = new Thread() {
					public void run() {
						frame.getContentPane().removeAll();
						frame.add(panel);
						frame.repaint();
					}
				};
				t0.start();
				try {
					t0.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread t = new Thread() {
					public void run() {
						frame.getContentPane().removeAll();
						frame.add(panel1);
						frame.repaint();
					}
				};
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread t1 = new Thread() {
					public void run() {
						frame.getContentPane().removeAll();
						frame.add(panel2);
						frame.repaint();
					}
				};
				t1.start();
				try {
					t1.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	/*public class DrawnCone extends Group {
	
	public DrawnCone(Cone myCone) {
		int rounds = 360;
		int r1 = (int) myCone.radius;
		int r2 = (int) myCone.radius;
		int h = (int) myCone.height;
		Group cone = new Group();
		PhongMaterial material = new PhongMaterial();
	
		float[] points = new float[rounds * 12];
		float[] textCoords = { 0.5f, 0, 0, 1, 1, 1 };
		int[] faces = new int[rounds * 12];
	
		for (int i = 0; i < rounds; i++) {
			int index = i * 12;
			// 0
			points[index] = (float) Math.cos(Math.toRadians(i)) * r2;
			points[index + 1] = (float) Math.sin(Math.toRadians(i)) * r2;
			points[index + 2] = h / 2;
			// 1
			points[index + 3] = (float) Math.cos(Math.toRadians(i)) * r1;
			points[index + 4] = (float) Math.sin(Math.toRadians(i)) * r1;
			points[index + 5] = -h / 2;
			// 2
			points[index + 6] = (float) Math.cos(Math.toRadians(i + 1)) * r1;
			points[index + 7] = (float) Math.sin(Math.toRadians(i + 1)) * r1;
			points[index + 8] = -h / 2;
			// 3
			points[index + 9] = (float) Math.cos(Math.toRadians(i + 1)) * r2;
			points[index + 10] = (float) Math.sin(Math.toRadians(i + 1)) * r2;
			points[index + 11] = h / 2;
		}
	
		for (int i = 0; i < rounds; i++) {
			int index = i * 12;
			faces[index] = i * 4;
			faces[index + 1] = 0;
			faces[index + 2] = i * 4 + 1;
			faces[index + 3] = 1;
			faces[index + 4] = i * 4 + 2;
			faces[index + 5] = 2;
	
			faces[index + 6] = i * 4;
			faces[index + 7] = 0;
			faces[index + 8] = i * 4 + 2;
			faces[index + 9] = 1;
			faces[index + 10] = i * 4 + 3;
			faces[index + 11] = 2;
		}
	
		TriangleMesh mesh = new TriangleMesh();
		mesh.getPoints().addAll(points);
		mesh.getTexCoords().addAll(textCoords);
		mesh.getFaces().addAll(faces);
	
		javafx.scene.shape.Cylinder circle1 = new javafx.scene.shape.Cylinder(r1, 0.1);
		circle1.setMaterial(material);
		circle1.setTranslateZ(-h / 2);
		circle1.setRotationAxis(Rotate.X_AXIS);
		circle1.setRotate(90);
	
		javafx.scene.shape.Cylinder circle2 = new javafx.scene.shape.Cylinder(r2, 0.1);
		circle2.setMaterial(material);
		circle2.setTranslateZ(h / 2);
		circle2.setRotationAxis(Rotate.X_AXIS);
		circle2.setRotate(90);
	
		MeshView meshView = new MeshView();
		meshView.setMesh(mesh);
		meshView.setMaterial(material);
		// meshView.setDrawMode(DrawMode.LINE);
		cone.getChildren().addAll(meshView);
		Rotate r3 = new Rotate(90, Rotate.X_AXIS);
		cone.getTransforms().add(r3);
		getChildren().addAll(cone);
	}
	}*/

}
