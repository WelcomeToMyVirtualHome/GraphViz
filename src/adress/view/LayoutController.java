package adress.view;

import adress.model.GraphVisual;
import adress.model.Simulation;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LayoutController {

	@FXML
	private Slider ers;
	@FXML
	private Slider xMin;
	@FXML
	private Slider alpha;
	@FXML
	private Slider numberOfBlocks;
	@FXML
	private Slider size;
	@FXML
	private Button setButton;
	@FXML
	private Label ersLabel;
	@FXML
	private Label xMinLabel;
	@FXML
	private Label alphaLabel;
	@FXML
	private Label numberOfBlocksLabel;
	@FXML
	private Label sizeLabel;
	@FXML
	private Pane pane;
	@FXML
	private Canvas canvas;

	private Simulation simulation;

	@FXML
	public void initialize() {
		initSliders();
		simulation = new Simulation(pane);
	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		GraphVisual graphVisual = new GraphVisual((int) numberOfBlocks.getValue(), (int) size.getValue(), alpha.getValue(),
				(int) xMin.getValue(), (int) ers.getValue(), pane.getWidth(), pane.getHeight());
		graphVisual.initNodeColors();
		simulation.setGraph(graphVisual);
		simulation.draw();
	}

	public void initSliders() {
		sizeLabel.setText("Size = " + Integer.toString((int) size.getValue()));
		numberOfBlocksLabel.setText("Blocks = " + Integer.toString((int) numberOfBlocks.getValue()));
		alphaLabel.setText("α  = " + Integer.toString((int) alpha.getValue()));
		xMinLabel.setText("xMin = " + Integer.toString((int) xMin.getValue()));
		ersLabel.setText("Ers = " + Integer.toString((int) ers.getValue()));

		size.valueProperty().addListener((observable, oldValue, newValue) -> {
			sizeLabel.setText("Size = " + Integer.toString(newValue.intValue()));
		});
		numberOfBlocks.valueProperty().addListener((observable, oldValue, newValue) -> {
			numberOfBlocksLabel.setText("Blocks = " + Integer.toString(newValue.intValue()));
		});
		alpha.valueProperty().addListener((observable, oldValue, newValue) -> {
			alphaLabel.setText("alpha = " + String.format("%.1f", newValue));
		});
		xMin.valueProperty().addListener((observable, oldValue, newValue) -> {
			xMinLabel.setText("xMin = " + Integer.toString(newValue.intValue()));
		});
		ers.valueProperty().addListener((observable, oldValue, newValue) -> {
			ersLabel.setText("Ers = " + Integer.toString(newValue.intValue()));
		});
	}
}
