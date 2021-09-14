/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 1
 * Date: Aug 29, 2021
 * Description: main input/output mechanism
 */

package shapes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;



public class IO extends Application {
	/*
		public static void main(String[] args) {
			System.out.print("*********Welcome to the Java OO Shapes Program **********\r\n"
					+ "Select from the menu below:\r\n" + "1. Construct a Circle\r\n" + "2. Construct a Rectangle\r\n"
					+ "3. Construct a Square\r\n" + "4. Construct a Triangle\r\n" + "5. Construct a Sphere\r\n"
					+ "6. Construct a Cube\r\n" + "7. Construct a Cone\r\n" + "8. Construct a Cylinder\r\n"
					+ "9. Construct a Torus\r\n" + "10. Exit the program\r\n\n");
			while (true) {
				System.out.println("Enter an integer:");
				Scanner in = null;
				in = new Scanner(System.in);
				int response = getInt(in);
				switch (response) {
				// 2D
				case 1:
					System.out.println("You have selected a circle.\nWhat is the radius?");
					double circleRadius = getDouble(in);
					Circle selectedCircle;
					selectedCircle = new Circle(circleRadius);
					System.out.println("The area of the circle is " + selectedCircle.getArea() + ".\n");
					break;
				case 2:
					System.out.println("You have selected a rectangle.\nWhat is the length?");
					double rectangleLength = getDouble(in);
					System.out.println("What is the width?");
					double width = getDouble(in);
					Rectangle selectedRect = new Rectangle(rectangleLength, width);
					System.out.println("The area of the rectangle is " + selectedRect.getArea() + ".\n");
					break;
				case 3:
					System.out.println("You have selected a square.\nWhat is length of a side?");
					double squareLength = getDouble(in);
					Square selectedSquare = new Square(squareLength);
					System.out.println("The area of the square is " + selectedSquare.getArea() + ".\n");
					break;
				case 4:
					System.out.println("You have selected a triangle.\nWhat is the length of side 'a'?");
					double a = getDouble(in);
					System.out.println("Now enter the length of side 'b'.");
					double b = getDouble(in);
					System.out.println("Now enter the length of side 'c'.");
					double c = getDouble(in);
					Triangle selectedTriangle = new Triangle(a, b, c);
					System.out.println("The area of the triangle is " + selectedTriangle.getArea() + ".\n");
					break;
				// 3D
				case 5:
					System.out.println("You have selected a sphere.\nWhat is the radius?");
					double sphereRadius = getDouble(in);
					Sphere selectedSphere = new Sphere(sphereRadius);
					System.out.println("The volume of the sphere is " + selectedSphere.getVolume() + ".\n");
					break;
				case 6:
					System.out.println("You have selected a cube.\nWhat is the length of a side?");
					double cubeLength = getDouble(in);
					Cube selectedCube = new Cube(cubeLength);
					System.out.println("The volume of the cube is " + selectedCube.getVolume() + ".\n");
					break;
				case 7:
					System.out.println("You have selected a cone.\nWhat is the radius of the base?");
					double coneRadius = getDouble(in);
					System.out.println("What is the height?");
					double coneHeight = getDouble(in);
					Cone selectedCone = new Cone(coneRadius, coneHeight);
					System.out.println("The volume of the cone is " + selectedCone.getVolume() + ".\n");
					break;
				case 8:
					System.out.println("You have selected a cylinder.\nWhat is the radius of the base?");
					double cylinderRadius = getDouble(in);
					System.out.println("What is the height?");
					double cylinderHeight = getDouble(in);
					Cylinder selectedCylinder = new Cylinder(cylinderRadius, cylinderHeight);
					System.out.println("The volume of the cylinder is " + selectedCylinder.getVolume() + ".\n");
					break;
				case 9:
					System.out.println("You have selected a torus.\nWhat is the minor radius?");
					double minorRadius = getDouble(in);
					System.out.println("What is the major radius?");
					double majorRadius = getDouble(in);
					Torus selectedTorus = new Torus(minorRadius, majorRadius);
					System.out.println("The volume of the torus is " + selectedTorus.getVolume() + ".\n");
					break;
				case 10:
					System.out.println(
							"Thank you for exploring shapes! The current date and time are " + LocalDateTime.now());
					in.close();
					System.exit(0);
				default:
					System.out.println("That option does not exist. Please try again.");
				}
			}
		}
	*/

	final String[] TwoDShapes = { "Circle", "Rectangle", "Square", "Triangle" };
	final String[] ThreeDShapes = { "Cone", "Cube", "Cylinder", "Sphere", "Torus" };
	private static JFrame frame = new JFrame("Shapes");
	private final JPanel content = new JPanel();
	private final JPanel drawingArea = new JPanel();

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void createGUI() {
		// Create and set up the window.
		// frame.setPreferredSize(new Dimension(300, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create 1and set up the content pane.
		JComponent newContentPane = new IO().getContent();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		// frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public IO() {
		GroupLayout layout = new GroupLayout(content);
		content.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		final JPanel welcomePanel = new JPanel();
		final JLabel welcome = new JLabel("Welcome to the Shapes program. Select a shape below to begin.");
		welcomePanel.add(welcome);

		final JPanel params = new JPanel();
		// c.ipady = 0;

		// c.fill = GridBagConstraints.NONE;
		// c.anchor = GridBagConstraints.WEST;
		/*c = new GridBagConstraints();
		c.ipady = 10;
		c.ipadx = 10;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;*/
		// c.fill = GridBagConstraints.HORIZONTAL;
		String[] shapes = Stream.concat(Arrays.stream(TwoDShapes), Arrays.stream(ThreeDShapes)).toArray(String[]::new);
		final JComboBox<String> shapesDropdown = new JComboBox<String>(shapes);
		shapesDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = (String) shapesDropdown.getSelectedItem();
				Shape selectedShape = new Shape().getInstance(selection);
				params.removeAll();
				params.setLayout(new GridLayout(selectedShape.paramSize + 1, 1));
				for (int i = 1; i <= selectedShape.paramSize; i++) {
					JPanel param = new JPanel();
					param.add(new JLabel(selectedShape.paramNames.get(i - 1) + " :"));
					JTextArea field = new JTextArea();
					field.setColumns(5);
					PlainDocument doc = (PlainDocument) field.getDocument();
					doc.setDocumentFilter(new DoubleFilter());
					param.add(field);
					params.add(param);
				}
				JButton go = new JButton("Go");
				go.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						drawingArea.removeAll();
						ArrayList<Double> shapeParams = new ArrayList<Double>();
						// System.out.println(params.getComponentCount());
						for (int i = 0; i < params.getComponentCount(); i++) {
							if (params.getComponent(i).getClass().getSimpleName().equals("JPanel")) {
								shapeParams.add(Double.parseDouble(
										((JTextArea) ((JPanel) params.getComponent(i)).getComponent(1)).getText()));
							}
						}
						// is boolean and tells us whether parameters were set successfully
						System.out.println("Shape set parameters: " + selectedShape.setParameters(shapeParams));
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								new JFXPanel(); // initializes JavaFX environment
								Component shapePanel = (Component) new PaintedShape(selectedShape).getPanel();
								drawingArea.add(shapePanel);
								// drawingArea.add(new JLabel("Test"));
								// drawingArea.repaint();
								shapePanel.repaint();
								shapePanel.validate();
								drawingArea.repaint();
								drawingArea.validate();
								frame.pack();
								frame.setSize(
										new Dimension((int) (frame.getSize().getWidth() + PaintedShape.size.getWidth()),
												(int) (frame.getSize().getHeight() + PaintedShape.size.getHeight())));
								frame.setLocationRelativeTo(null);
							}
						});
					}
				});
				params.add(go);
				content.validate();
				frame.validate();
				frame.pack();
				frame.setLocationRelativeTo(null);
			}
		});

		JPanel dropdown = new JPanel();
		dropdown.add(shapesDropdown);

		/*content.add(welcomePanel);
		content.add(dropdown);
		content.add(params);*/
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(welcomePanel)
						.addComponent(dropdown).addComponent(params).addComponent(drawingArea)));
		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(welcomePanel).addComponent(dropdown)
				.addComponent(params).addComponent(drawingArea));
		frame.add(content);
	}

	public JPanel getContent() {
		return content;
	}
	/*
	private static int getInt(Scanner in) {
		int nextInt = 0;
		while (true) {
			try {
				String response = in.next();
				nextInt = Integer.parseInt(response);
				if (nextInt < 0) {
					throw new Exception();
				}
				return nextInt;
			} catch (Exception e) {
				System.out.println("Invalid output. Please enter a natural number (positive integer):");
				continue;
			}
		}
	}
	
	private static double getDouble(Scanner in) {
		double nextDouble = 0;
		while (true) {
			try {
				String response = in.next();
				nextDouble = Double.parseDouble(response);
				if (nextDouble < 0) {
					System.out.println("Double not positive. Please enter a valid double:");
					continue;
				}
				return nextDouble;
			} catch (Exception e) {
				System.out.println("Invalid output. Please enter a valid double:");
				continue;
			}
		}
	}
	*/

	@Override
	public void start(Stage primaryStage) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JFXPanel(); // initializes JavaFX environment
				createGUI();
			}
		});
	}
}
