package adress.view;

import adress.model.GraphVisual;
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

	private Group nodes;
	private Group edges;
	private double width;
	private double height;

	private Light.Spot light;
	private Lighting lighting;

	private GraphVisual graphVisual;

	@FXML
	public void initialize() {
		initLighting();
		initSliders();
	}

	@FXML
	private void handleButtonAction(ActionEvent event) {
		graphVisual = new GraphVisual((int) numberOfBlocks.getValue(), (int) size.getValue(), alpha.getValue(),
				(int) xMin.getValue(), (int) ers.getValue(), pane.getWidth(), pane.getHeight());
		graphVisual.initNodeColors();
		draw();
	}

	public void draw() {
		new AnimationTimer() {
			long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}
				double time = (now - startTime) / 1E9d;
				reset();
				update();
			}
		}.start();
	}

	public void reset() {
		pane.getChildren().clear();
		nodes = new Group();
		edges = new Group();
	}

	public void update() {
		width = pane.getWidth();
		height = pane.getHeight();
		graphVisual.monteCarlo();
		graphVisual.findEdges();
		graphVisual.addEdges(graphVisual.getNumberOfEdges());
		graphVisual.springEmbedder(width, height);
		drawNodes(graphVisual);
		drawEdges(graphVisual);
		pane.getChildren().addAll(edges);
		pane.getChildren().addAll(nodes);
	}

	public void drawNodes(GraphVisual graphVisual) {
		int i = graphVisual.getNodes().size() - 1;
		for (Circle node : graphVisual.getNodes()) {
			node.setCenterX(graphVisual.getPosX()[i]);
			node.setCenterY(graphVisual.getPosY()[i]);
			node.setFill(graphVisual.getNodeColors().get(i / graphVisual.getSizeOfBlock()));
			node.setEffect(lighting);
			graphVisual.setNodeSizeScale(1.);
			node.setRadius(graphVisual.getNodeSize(i));
			nodes.getChildren().add(node);
			i--;
		}
	}

	public void drawEdges(GraphVisual graphVisual) {
		int i = 0;
		for (int edge[] : graphVisual.getConnected()) {
			graphVisual.getEdges().get(i).setStartX(graphVisual.getPosX()[edge[0]]);
			graphVisual.getEdges().get(i).setStartY(graphVisual.getPosY()[edge[0]]);
			graphVisual.getEdges().get(i).setEndX(graphVisual.getPosX()[edge[1]]);
			graphVisual.getEdges().get(i).setEndY(graphVisual.getPosY()[edge[1]]);
			graphVisual.getEdges().get(i).setFill(Color.rgb(0, 0, 0, 1));
			graphVisual.getEdges().get(i).setStrokeWidth(graphVisual.getNodeSizeSum(edge[0], edge[1]) / 25);
			edges.getChildren().add(graphVisual.getEdges().get(i));
			i++;
		}
	}

	public void initLighting() {
		light = new Light.Spot();
		light.setX(0);
		light.setY(0);
		light.setZ(100);
		light.setPointsAtX(width);
		light.setPointsAtY(height);
		light.setPointsAtZ(-50);
		light.setSpecularExponent(5.);
		lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(1.);
	}

	public void initSliders() {
		sizeLabel.setText("Size = " + Integer.toString((int) size.getValue()));
		numberOfBlocksLabel.setText("Blocks = " + Integer.toString((int) numberOfBlocks.getValue()));
		alphaLabel.setText("alpha = " + Integer.toString((int) alpha.getValue()));
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
