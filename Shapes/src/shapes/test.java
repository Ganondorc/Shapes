package shapes;
/*
 * Name: Michael Frake
 * Project: CMSC 3 Project 1
 * Date: Sep 12, 2021
 * Description: 
 */

import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import org.fxyz.cameras.AdvancedCamera;
import org.fxyz.cameras.controllers.FPSController;
import org.fxyz.shapes.Torus;

public class test extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	/*@Override
	public void start(Stage stage) 
	{
	    // Create a Box
	    Box box = new Box(100, 100, 100);
	    box.setDrawMode(DrawMode.LINE);
	    box.setTranslateX(150);
	    box.setTranslateY(0);
	    box.setTranslateZ(400);
	     
	    // Create a Sphere
	    Sphere sphere = new Sphere(50, 20);
	    sphere.setDrawMode(DrawMode.LINE);
	    sphere.setTranslateX(300);
	    sphere.setTranslateY(-5);
	    sphere.setTranslateZ(400);
	     
	    // Create a Cylinder
	    Cylinder cylinder = new Cylinder(40, 120, 5);
	    cylinder.setDrawMode(DrawMode.LINE);
	    cylinder.setTranslateX(500);
	    cylinder.setTranslateY(-25);
	    cylinder.setTranslateZ(600);
	     
	    // Create a Light
	    PointLight light = new PointLight();
	    light.setTranslateX(350);
	    light.setTranslateY(100);
	    light.setTranslateZ(300);
	
	    // Create a Camera to view the 3D Shapes
	    PerspectiveCamera camera = new PerspectiveCamera(false);
	    camera.setTranslateX(100);
	    camera.setTranslateY(-50);
	    camera.setTranslateZ(300);
	     
	    // Add the Shapes and the Light to the Group
	    Group root = new Group(box, sphere, cylinder, light);
	     
	    JPanel panel = new JPanel();
	    // Create a Scene with depth buffer enabled
	    Scene scene = new Scene(root, 400, 200, true);
	    // Add the Camera to the Scene
	    scene.setCamera(camera);
	
	    // Add the Scene to the Stage
	    stage.setScene(scene);
	    // Set the Title of the Stage
	    stage.setTitle("An Example with specified Draw Mode");
	    // Display the Stage
	    stage.show();
	}
	}*/

	public class DrawnCone extends Group {
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
	}
	
	@Override
	public void start(Stage stage) {
		Group root = new Group();
		// Make a bunch of semi random Torusesessses(toroids?) and stuff : from
		// torustest
		Group torusGroup = new Group();
		for (int i = 0; i < 30; i++) {
			Random r = new Random();
			// A lot of magic numbers in here that just artificially constrain the math
			float randomRadius = (float) 100;
			float randomTubeRadius = (float) 30;
			int randomTubeDivisions = (int) 64;
			int randomRadiusDivisions = (int) 64;
			Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
			Torus torus = new org.fxyz.shapes.Torus(randomTubeDivisions, randomRadiusDivisions, randomRadius, randomTubeRadius,
					randomColor);
			torus.setEmissiveLightingColor(randomColor);
			torus.setEmissiveLightingOn(r.nextBoolean());
			double translationX = Math.random() * 1024 * 1.95;
			if (Math.random() >= 0.5) {
				translationX *= -1;
			}
			double translationY = Math.random() * 1024 * 1.95;
			if (Math.random() >= 0.5) {
				translationY *= -1;
			}
			double translationZ = Math.random() * 1024 * 1.95;
			if (Math.random() >= 0.5) {
				translationZ *= -1;
			}
			Translate translate = new Translate(translationX, translationY, translationZ);
			Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
			Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
			Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);
			torus.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);
			// torus.getTransforms().add(translate);
			torusGroup.getChildren().add(torus);
		}
		root.getChildren().add(torusGroup);
		Scene scene = new Scene(root, 400, 400, true, SceneAntialiasing.BALANCED);
		scene.setFill(Color.BLACK);
		stage.setTitle("SimpleFPSControllerTest");
		stage.setScene(scene);
		stage.show();
		// stage.setMaximized(true);
		FPSController controller = new FPSController();
		controller.setScene(scene);
		controller.setMouseLookEnabled(true);
		AdvancedCamera camera = new AdvancedCamera();
		camera.setController(controller);
		camera.setFieldOfView(40);
		root.getChildren().add(camera);
		scene.setCamera(camera);
	}
}