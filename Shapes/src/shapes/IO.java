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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.AbstractAction;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import shapes.utility.DoubleFilter;
import shapes.utility.PaintedShape;
import shapes.utility.SimpleFocusTraversalPolicy;

public class IO extends Application {
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
		GroupLayout layout = new GroupLayout(getContent());
		getContent().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		final JPanel welcomePanel = new JPanel();
		welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
		final JLabel welcomeLabel = new JLabel("Welcome to the Shapes program. Select a shape below to begin.");
		final JLabel extraInfoLabel = new JLabel("If the shape is 3D, you can click the shape to rotate it randomly.");
		welcomePanel.add(welcomeLabel);
		welcomePanel.add(extraInfoLabel);

		JPanel dropdownPanel = new JPanel();
		List<String> shapes = Stream.concat(Shape.TwoDShapes.stream(), Shape.ThreeDShapes.stream())
				.collect(Collectors.toList());
		final JComboBox<String> shapesDropdown = new JComboBox<String>();
		shapes.stream().forEach(s -> shapesDropdown.addItem(s));
		dropdownPanel.add(shapesDropdown);

		final JPanel paramsPanel = new JPanel();
		final JPanel drawingAreaPanel = new JPanel();
		final JPanel detailsPanel = new JPanel();

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(welcomePanel)
						.addComponent(dropdownPanel).addComponent(paramsPanel).addComponent(drawingAreaPanel)
						.addComponent(detailsPanel)));
		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(welcomePanel).addComponent(dropdownPanel)
				.addComponent(paramsPanel).addComponent(drawingAreaPanel).addComponent(detailsPanel));

		shapesDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selection = (String) shapesDropdown.getSelectedItem();
				Shape selectedShape = new Shape().getInstance(selection);
				ArrayList<Component> tabbersList = new ArrayList<Component>();

				paramsPanel.removeAll();
				paramsPanel.setLayout(new GridLayout(selectedShape.paramSize + 1, 1));
				for (int i = 1; i <= selectedShape.paramSize; i++) {
					JPanel paramPanel = new JPanel();
					paramPanel.add(new JLabel(selectedShape.paramNames.get(i - 1) + " :"));
					JTextField field = new JTextField();
					field.setColumns(5);
					tabbersList.add(field);
					field.setToolTipText("Enter a double between 0.0 and "
							+ Double.toString((PaintedShape.size - 5) * selectedShape.maxMultiplier));
					PlainDocument doc = (PlainDocument) field.getDocument();
					doc.setDocumentFilter(new DoubleFilter(0, (PaintedShape.size - 5) * selectedShape.maxMultiplier));
					paramPanel.add(field);
					paramsPanel.add(paramPanel);
				}

				// tab to first element
				tabbersList.get(0).requestFocus();

				JButton goBtn = new JButton("Go");
				goBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						final JPanel drawingPanel = new JPanel();
						ArrayList<Double> shapeParamsList = new ArrayList<Double>();
						for (int i = 0; i < paramsPanel.getComponentCount(); i++) {
							if (paramsPanel.getComponent(i).getClass().getSimpleName().equals("JPanel")) {
								JTextField thisTextField = ((JTextField) ((JPanel) paramsPanel.getComponent(i))
										.getComponent(1));
								if (thisTextField.getText().equals("")) {
									JOptionPane.showMessageDialog(frame, "Invalid input: empty parameter.");
									return;
								}
								shapeParamsList.add(Double.parseDouble(thisTextField.getText()));
							}
						}
						// boolean that tells us whether parameters were set successfully
						selectedShape.setParameters(shapeParamsList);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								new JFXPanel(); // initializes JavaFX environment
								if (selectedShape.dimensionSize == 2) {
									Component shapePanel = (Component) new PaintedShape(selectedShape).getPanel();
									drawingPanel.add(shapePanel);
								} else {
									JFXPanel shapePanel = (JFXPanel) new PaintedShape(selectedShape).getPanel();
									Platform.runLater(() -> {
										try {
											SwingUtilities.invokeLater(() -> {
												drawingPanel.add(shapePanel);
												frame.pack();
											});

										} catch (Exception e) {
											e.printStackTrace();
										}

									});
								}
								drawingAreaPanel.removeAll();
								drawingAreaPanel.validate();
								drawingAreaPanel.add(drawingPanel);
								String precursor = (selectedShape.dimensionSize == 2) ? "Area: " : "Volume: ";
								detailsPanel.removeAll();
								detailsPanel.validate();
								detailsPanel
										.add(new JLabel(precursor + Double.toString(selectedShape.getAreaOrVolume())));
								detailsPanel.repaint();
								drawingAreaPanel.repaint();
								frame.pack();
								frame.setSize(new Dimension((int) (frame.getSize().getWidth()),
										(int) (frame.getSize().getHeight() + PaintedShape.size)));
								frame.setLocationRelativeTo(null);
							}
						});
					}
				});
				goBtn.setMnemonic('G');
				paramsPanel.add(goBtn);
				tabbersList.add(goBtn);
				// have last text field activate go on enter pressed
				((JTextField) tabbersList.get(tabbersList.size() - 2)).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for (ActionListener a : goBtn.getActionListeners()) {
							a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
								// trigger go button
							});
						}
					}
				});
				paramsPanel.setFocusTraversalPolicy(new SimpleFocusTraversalPolicy(tabbersList));
				paramsPanel.setFocusTraversalPolicyProvider(true);
				paramsPanel.setFocusCycleRoot(false);

				getContent().validate();
				frame.validate();
				frame.pack();
				frame.setLocationRelativeTo(null);
			}
		});

		shapesDropdown.requestFocus();
		
		content.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0), "getPriorDropdown");
		content.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0), "getSubsequentDropdown");
		class upAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = shapesDropdown.getSelectedIndex();
				shapesDropdown.setSelectedItem((i > 0) ? shapesDropdown.getItemAt(i-1) : shapesDropdown.getItemAt(i));
		    }
		}
		class downAction extends AbstractAction {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = shapesDropdown.getSelectedIndex();
				int max = shapesDropdown.getItemCount() - 1;
				shapesDropdown.setSelectedItem((i < max) ? shapesDropdown.getItemAt(i+1) : shapesDropdown.getItemAt(i));
		    }
		}
		content.getActionMap().put("getPriorDropdown", new upAction());
		content.getActionMap().put("getSubsequentDropdown", new downAction());
		shapesDropdown.setToolTipText("Page up to select previous element, down for next");
		
		for (ActionListener a : shapesDropdown.getActionListeners()) {
			a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
				// trigger event to display first dropdown item parameters
			});
		}
		frame.add(getContent());
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
