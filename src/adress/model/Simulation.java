package adress.model;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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

	private LineChart<Number, Number> hamiltonianChart;
	private LineChart<Number, Number> histogramChart;
	private XYChart.Series<Number, Number> hamiltonianData;
	private List<XYChart.Series<Number, Number>> histogramData;
	private final int dataSeriesSize = 200;
	private int count = 0;
	private int x = 0;

	public Simulation(Pane pane, LineChart<Number, Number> hamiltonianChart, LineChart<Number, Number> histogramChart) {
		this.pane = pane;
		this.hamiltonianChart = hamiltonianChart;
		this.histogramChart = histogramChart;
		initLighting();
		hamiltonianData = new XYChart.Series<>();
		hamiltonianData.setName("H");
		histogramData = new ArrayList<>();	
		
	}

	public void draw() {
		new AnimationTimer() {
			long startTime = -1;

			@Override
			public void handle(long now) {
				if (startTime == -1) {
					startTime = now;
				}
				setLineChartData();
				reset();
				update();
			}
		}.start();
	}

	private void reset() {
		pane.getChildren().clear();
		nodes = new Group();
		edges = new Group();
	}

	private void update() {
		width = pane.getWidth();
		height = pane.getHeight();
		graph.monteCarlo();
		graph.findEdges();
		graph.calculateNodeDegreesHist();
		graph.addEdges(graph.getNumberOfEdges());
		graph.springEmbedder(width, height);
		drawNodes();
		drawEdges();
		pane.getChildren().addAll(edges);
		pane.getChildren().addAll(nodes);
	}

	private void drawNodes() {
		int i = graph.getNodes().size() - 1;
		for (Circle node : graph.getNodes()) {
			node.setCenterX(graph.getPosX()[i]);
			node.setCenterY(graph.getPosY()[i]);
			node.setFill(graph.getNodeColors().get(i / graph.getSizeOfBlock()));
			node.setEffect(lighting);
			graph.setNodeSizeScale(1.);
			node.setRadius(graph.getNodeSize(i));
			nodes.getChildren().add(node);
			i--;
		}
	}

	private void drawEdges() {
		int i = 0;
		for (int edge[] : graph.getConnected()) {
			graph.getEdges().get(i).setStartX(graph.getPosX()[edge[0]]);
			graph.getEdges().get(i).setStartY(graph.getPosY()[edge[0]]);
			graph.getEdges().get(i).setEndX(graph.getPosX()[edge[1]]);
			graph.getEdges().get(i).setEndY(graph.getPosY()[edge[1]]);
			graph.getEdges().get(i).setFill(Color.rgb(0, 0, 0, 1));
			graph.getEdges().get(i).setStrokeWidth(graph.getNodeSizeSum(edge[0], edge[1]) / 25);
			edges.getChildren().add(graph.getEdges().get(i));
			i++;
		}
	}

	private void setLineChartData() {
		hamiltonianData.getData().get(count).setXValue(x++);
		hamiltonianData.getData().get(count).setYValue(graph.getHamiltonian());
		count++;
		if (count > dataSeriesSize - 1)
			count = 0;
		
		
		for(int i = 0; i < graph.getNumberOfBlocks(); i++) {
			LogHist logHist = new LogHist(graph.getNodeDegreesHist()[i]);
			for (int j = 0; j < logHist.getNbins(); j++) {
				histogramData.get(i).getData().get(j).setXValue(logHist.getBins()[j]);
				histogramData.get(i).getData().get(j).setYValue(logHist.getValues()[j]);
			}
		}
	}

	public void clearCharts() {
		count = 0;
		x = 0;
		hamiltonianData.getData().clear();
		hamiltonianChart.getData().clear();
		for (int i = 0; i < dataSeriesSize; i++) {
			hamiltonianData.getData().add(i, new XYChart.Data<Number, Number>(0,0));
		}
		hamiltonianChart.getData().add(hamiltonianData);
	
		histogramChart.getData().clear();
		histogramData.clear();
		for(int i = 0; i < graph.getNumberOfBlocks(); i++) {
			XYChart.Series<Number,Number> series = new XYChart.Series<>();
			for (int j = i * graph.getSizeOfBlock(); j < (i + 1) * graph.getSizeOfBlock(); j++) {
				series.getData().add(new XYChart.Data<Number, Number>(0,0));
			}
//			series.nodeProperty().get().setStyle("-fx-stroke-width: 1px;");
			histogramData.add(i,series);
			histogramChart.getData().add(i,histogramData.get(i));
		}
	}

	private void initLighting() {
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
