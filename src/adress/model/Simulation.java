package adress.model;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Simulation {

	private Pane pane;
	private GraphVisual graph;

	private Group nodes;
	private Group edges;
	private double width;
	private double height;

	private Light.Spot light;
	private Lighting lighting;

	public Simulation(Pane pane) {
		this.pane = pane;
		initLighting();
	}

	public void draw() {
		new AnimationTimer() {
			long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}
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
		graph.monteCarlo();
		graph.findEdges();
		graph.addEdges(graph.getNumberOfEdges());
		graph.springEmbedder(width, height);
		drawNodes(graph);
		drawEdges(graph);
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

	public void setGraph(GraphVisual graph) {
		this.graph = graph;
	}
}
