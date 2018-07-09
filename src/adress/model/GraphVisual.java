package adress.model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class GraphVisual extends Graph {
	private double[] posX;
	private double[] posY;
	private static double c1 = 0.1;
	private static double c2 = 0.002;
	private static double c3 = 35;
	private static double c4 = 0.05;
	private static int c5 = 1000;
	private ArrayList<Circle> nodes;
	private ArrayList<Line> edges;
	private double temperature;
	private double dt;
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

	public void fruchtermanReingold(double width, double height) {
		double deltaX, deltaY, C = 1.;
		double k = C * Math.sqrt(width * height / size);
		double dis[][] = new double[size][2];
		for (int v = 0; v < size; v++) {
			dis[v][0] = 0;
			dis[v][1] = 0;
			for (int u = 0; u < size; u++) {
				if (v != u) {
					deltaX = d(posX[v], posX[u]);
					deltaY = d(posY[v], posY[u]);
					dis[v][0] += norm(deltaX) * frFR(Math.abs(deltaX), k);
					dis[v][1] += norm(deltaY) * frFR(Math.abs(deltaY), k);
				}
			}
		}
		for (int edge[] : connected) {
			deltaX = d(posX[edge[0]], posX[edge[1]]);
			deltaY = d(posY[edge[0]], posY[edge[1]]);
			dis[edge[0]][0] -= norm(deltaX) * faFR(Math.abs(deltaX), k);
			dis[edge[0]][1] -= norm(deltaY) * faFR(Math.abs(deltaY), k);
			dis[edge[1]][0] += norm(deltaX) * faFR(Math.abs(deltaX), k);
			dis[edge[1]][1] += norm(deltaY) * faFR(Math.abs(deltaY), k);
		}
		for (int v = 0; v < size; v++) {
			posX[v] += norm(dis[v][0]) * Math.min(Math.abs(dis[v][0]), temperature);
			posY[v] += norm(dis[v][1]) * Math.min(Math.abs(dis[v][1]), temperature);
			// posX[v] = Math.min(width, Math.max(0, posX[v]));
			// posY[v] = Math.min(height, Math.max(0, posY[v]));

		}
		fruchtermanReingoldCooldown(dt);
	}

	public void barycentricDraw() {

	}

	public void fruchtermanReingoldCooldown(double dt) {
		if (temperature > dt)
			temperature -= dt;
	}

	public void setdt(double dt) {
		this.dt = dt;
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
		// if (Math.abs(delta) > 0)
		return delta;
		// else
		// return 0.0000001;
	}

	public double dist(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public double frFR(double d, double k) {
		return Math.pow(k, 2) / d;
	}

	public double faFR(double d, double k) {
		return Math.pow(d, 2) / k;
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

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getNodeSizeScale() {
		return nodeSizeScale;
	}

	public void setNodeSizeScale(double nodeSizeScale) {
		this.nodeSizeScale = nodeSizeScale;
	}

	public static double getC1() {
		return c1;
	}

	public static void setC1(double c1) {
		GraphVisual.c1 = c1;
	}

	public static double getC2() {
		return c2;
	}

	public static void setC2(double c2) {
		GraphVisual.c2 = c2;
	}

	public static double getC3() {
		return c3;
	}

	public static void setC3(double c3) {
		GraphVisual.c3 = c3;
	}

	public static double getC4() {
		return c4;
	}

	public static void setC4(double c4) {
		GraphVisual.c4 = c4;
	}

	public static int getC5() {
		return c5;
	}

	public static void setC5(int c5) {
		GraphVisual.c5 = c5;
	}
}
