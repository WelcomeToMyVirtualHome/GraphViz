package adress.model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GraphVisual extends Graph {
	private double[] posX;
	private double[] posY;
	private double c1 = 0.1;
	private double c2 = 0.002;
	private double c3 = 35;
	private double c4 = 0.05;
	private ArrayList<Circle> nodes;
	private ArrayList<Line> edges;
	private double nodeSizeScale;
	private ArrayList<Color> nodeColors;

	public GraphVisual(Integer numOB, Integer sizeOB, Double distrExp, Integer xMin, Integer Ers, double width,
			double height) {
		super(numOB, sizeOB, distrExp, xMin, Ers);
		posX = new double[size];
		posY = new double[size];
		for (int i = 0; i < size; i++) {
			posX[i] = rand.nextDouble() * width;
			posY[i] = rand.nextDouble() * height;
		}
		nodeSizeScale = 1.;
		nodes = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			nodes.add(new Circle());
		}
	}

	public void springEmbedder(double width, double height) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (j != i) {
					double distance = dist(posX[i], posX[j], posY[i], posY[j]);
					double deltaX = posX[i] - posX[j];
					double deltaY = posY[i] - posY[j];
					posX[i] += c1 * deltaX / distance;
					posY[i] += c1 * deltaY / distance;
					if (graph[i][j]) {
						deltaX = posX[i] - posX[j];
						deltaY = posY[i] - posY[j];
						posX[i] -= c2 * Math.abs(distance - c3) * deltaX / distance;
						posY[i] -= c2 * Math.abs(distance - c3) * deltaY / distance;
					}
				}
			}
			posX[i] -= c4 * (posX[i] - width / 2) * size / 250;
			posY[i] -= c4 * (posY[i] - height / 2) * size / 250;

			posX[i] = Math.min(width, Math.max(0, posX[i]));
			posY[i] = Math.min(height, Math.max(0, posY[i]));
		}
	}

	public double getNodeSizeSum(int i, int j) {
		return nodeSizeScale * (nodeDegrees[i] + nodeDegrees[j]);
	}

	public double getNodeSize(int i) {
		return nodeSizeScale * nodeDegrees[i];
	}

	public void initNodeColors() {
		nodeColors = new ArrayList<Color>();
		for (int i = 0; i < numberOfBlocks; i++) {
			nodeColors.add(new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1.0));
		}
	}

	public ArrayList<Color> getNodeColors() {
		return nodeColors;
	}

	public double norm(double delta) {
		return delta / Math.abs(delta);
	}

	public double d(double x1, double x2) {
		double delta = x1 - x2;
		return delta;
	}

	public double dist(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public void clear() {
		nodes.clear();
		edges.clear();
	}

	public ArrayList<Circle> getNodes() {
		return nodes;
	}

	public void addNode(Circle node) {
		nodes.add(node);
	}

	public ArrayList<Line> getEdges() {
		return edges;
	}

	public void addEdges(int numberOfEdges) {
		edges = new ArrayList<>();
		for (int i = 0; i < numberOfEdges; i++) {
			edges.add(new Line());
		}
	}

	public double[] getPosX() {
		return posX;
	}

	public double[] getPosY() {
		return posY;
	}

	public void addEdge(Line edge) {
		edges.add(edge);
	}

	public double getNodeSizeScale() {
		return nodeSizeScale;
	}

	public void setNodeSizeScale(double nodeSizeScale) {
		this.nodeSizeScale = nodeSizeScale;
	}
}
