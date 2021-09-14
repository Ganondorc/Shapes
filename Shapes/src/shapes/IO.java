/*
 * Name: Michael Frake
 * Project: CMSC 335 Project 2
 * Date: Sep 14, 2021
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

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

public class IO extends Application {
	final String[] TwoDShapes = { "Circle", "Rectangle", "Square", "Triangle" };
	final String[] ThreeDShapes = { "Cone", "Cube", "Cylinder", "Sphere", "Torus" };
	private static JFrame frame = new JFrame("Shapes");
	private final JPanel content = new JPanel();

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static void createGUI() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create 1and set up the content pane.
		JComponent newContentPane = new IO().getContent();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@SuppressWarnings("serial")
	public IO() {
		GroupLayout layout = new GroupLayout(content);
		content.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		final JPanel welcome = new JPanel();
		welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));
		final JLabel welcomeLabel = new JLabel("Welcome to the Shapes program. Select a shape below to begin.");
		final JLabel extraInfo = new JLabel("If the shape is 3D, you can click the shape to rotate it randomly.");
		welcome.add(welcomeLabel);
		welcome.add(extraInfo);
		
		JPanel dropdown = new JPanel();
		String[] shapes = Stream.concat(Arrays.stream(TwoDShapes), Arrays.stream(ThreeDShapes)).toArray(String[]::new);
		final JComboBox<String> shapesDropdown = new JComboBox<String>(shapes);
		dropdown.add(shapesDropdown);
		
		final JPanel params = new JPanel();
		final JPanel drawingArea = new JPanel();
		final JPanel details = new JPanel();
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(welcome)
						.addComponent(dropdown).addComponent(params).addComponent(drawingArea).addComponent(details)));
		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(welcome).addComponent(dropdown)
				.addComponent(params).addComponent(drawingArea).addComponent(details));

		shapesDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = (String) shapesDropdown.getSelectedItem();
				Shape selectedShape = new Shape().getInstance(selection);
				params.removeAll();
				params.setLayout(new GridLayout(selectedShape.paramSize + 1, 1));
				ArrayList<Component> tabbers = new ArrayList<Component>();
				for (int i = 1; i <= selectedShape.paramSize; i++) {
					JPanel param = new JPanel();
					param.add(new JLabel(selectedShape.paramNames.get(i - 1) + " :"));
					JTextField field = new JTextField();
					field.setColumns(5);
					tabbers.add(field);
					field.setToolTipText("Enter a double between 0.0 and " + Double.toString((PaintedShape.size - 5) * selectedShape.maxMultiplier));
					PlainDocument doc = (PlainDocument) field.getDocument();
					doc.setDocumentFilter(new DoubleFilter(0, (PaintedShape.size - 5) * selectedShape.maxMultiplier));
					param.add(field);
					params.add(param);
				}
				tabbers.get(0).requestFocus();
				JButton go = new JButton("Go");
				go.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						final JPanel drawing = new JPanel();
						ArrayList<Double> shapeParams = new ArrayList<Double>();
						for (int i = 0; i < params.getComponentCount(); i++) {
							if (params.getComponent(i).getClass().getSimpleName().equals("JPanel")) {
								JTextField thisText = ((JTextField) ((JPanel) params.getComponent(i)).getComponent(1));
								if (thisText.getText().equals("")) {
									JOptionPane.showMessageDialog(frame, "Invalid input: empty parameter.");
									return;
								}
								shapeParams.add(Double.parseDouble(thisText.getText()));
							}
						}
						// boolean that tells us whether parameters were set successfully
						System.out.println("Shape set parameters: " + selectedShape.setParameters(shapeParams));
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								boolean TwoD = false;
								new JFXPanel(); // initializes JavaFX environment
								if (selectedShape.getClass().getSuperclass().getSimpleName()
										.equals("TwoDimensionalShape")) {
									TwoD = true;
									Component shapePanel = (Component) new PaintedShape(selectedShape).getPanel();
									drawing.add(shapePanel);
								} else {
									JFXPanel shapePanel = (JFXPanel) new PaintedShape(selectedShape).getPanel();
									Platform.runLater(() -> {
										try {
											SwingUtilities.invokeLater(() -> {
												drawing.add(shapePanel);
												frame.pack();
											});

										} catch (Exception e) {
											e.printStackTrace();
										}

									});
								}
								drawingArea.removeAll();
								drawingArea.validate();
								drawingArea.add(drawing);
								String precursor = TwoD ? "Area: " : "Volume: ";
								details.removeAll();
								details.validate();
								details.add(new JLabel(precursor + Double.toString(selectedShape.getAreaOrVolume())));
								details.repaint();
								drawingArea.repaint();
								frame.pack();
								frame.setSize(new Dimension((int) (frame.getSize().getWidth()),
										(int) (frame.getSize().getHeight() + PaintedShape.sizeDim.getHeight())));
								frame.setLocationRelativeTo(null);
							}
						});
					}
				});
				params.add(go);
				
				tabbers.add(go);
				// have last text field activate go on enter pressed
				((JTextField) tabbers.get(tabbers.size()-2)).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(ActionListener a: go.getActionListeners()) {
							 a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
								 //trigger go button
						    });
						}
					}
				});
				params.setFocusTraversalPolicy(new SimpleFocusTraversalPolicy(tabbers));
				params.setFocusTraversalPolicyProvider(true);
				params.setFocusCycleRoot(false);
				
				content.validate();
				frame.validate();
				frame.pack();
				frame.setLocationRelativeTo(null);
			}
		});

		shapesDropdown.requestFocus();
		for(ActionListener a: shapesDropdown.getActionListeners()) {
		    a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
		          // trigger shapesdropdown event to display initial circle (since it's first in dropdown) parameters
		    });
		}
		
		frame.add(content);
	}

	public JPanel getContent() {
		return content;
	}
	
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
