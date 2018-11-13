package adress.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SplittableRandom;

public class Graph {
	protected boolean[][] graph;
	protected double[] nodeDegrees;
	protected double[] internalLagrangeMultipliers;
	protected double[] externalLagrangeMultipliers;
	protected int size;
	protected int numberOfBlocks;
	protected int sizeOfBlock;
	protected double alpha;
	protected int minimalNodeDegree;
	protected double[] Err;
	protected int ers;
	protected SplittableRandom rand;
	protected int NT = 1000;
	protected int numberOfEdges;
	protected List<int[]> connected;
	private int[][] nodeDegreesHists;
	private double h;


	public Graph(Integer numOB, Integer sizeOB, Double distrExp, Integer xMin, Integer Ers) {
		rand = new SplittableRandom(System.currentTimeMillis());
		numberOfBlocks = numOB;
		sizeOfBlock = sizeOB;
		size = numOB * sizeOB;
		alpha = distrExp;
		minimalNodeDegree = xMin;
		ers = Ers;
		graph = new boolean[size][size];
		nodeDegrees = new double[size];
		internalLagrangeMultipliers = new double[size];
		externalLagrangeMultipliers = new double[size];
		Err = new double[size];
		nodeDegreesHists = new int[numberOfBlocks][size];
		h = 0;
		for (boolean row[] : graph) {
			for (boolean g : row) {
				g = false;
			}
		}
		setDegreeSequence();
		setLagrangeMultipliers();
	}

	public void setDegreeSequence() {
		double x1 = Math.pow(sizeOfBlock, alpha + 1);
		double x0 = Math.pow(minimalNodeDegree, alpha + 1);
		for (int i = 0; i < numberOfBlocks; i++) {
			for (int j = i * sizeOfBlock; j < (i + 1) * sizeOfBlock; j++) {
				nodeDegrees[j] = Math.pow((x1 - x0) * rand.nextDouble() + x0, 1. / (alpha + 1));
			}
		}
		for(int i = 0; i < numberOfBlocks; i++) {
			Arrays.sort(nodeDegrees, i * sizeOfBlock, (i+1) * sizeOfBlock);	
		}
	}

	public void setLagrangeMultipliers() {
		for (int i = 0; i < numberOfBlocks; i++) {
			double sum = 0;
			for (int j = i * sizeOfBlock; j < (i + 1) * sizeOfBlock; j++) {
				sum += nodeDegrees[j] / 2;
			}
			Err[i] = sum;
			for (int j = i * sizeOfBlock; j < (i + 1) * sizeOfBlock; j++) {
				internalLagrangeMultipliers[j] = Math.log(nodeDegrees[j] / Math.sqrt(2 * Err[i]));
				externalLagrangeMultipliers[j] = Math.log(nodeDegrees[j] * Math.sqrt(ers) / (2 * Err[i]));
			}
		}
	}

	public void monteCarlo() {
		int i, j;
		double theta;
		for (int k = 0; k < NT; k++) {
			i = rand.nextInt(size);
			while (true) {
				j = rand.nextInt(size);
				if (j != i)
					break;
			}
			if (i / sizeOfBlock == j / sizeOfBlock)
				theta = internalLagrangeMultipliers[i] + internalLagrangeMultipliers[j];
			else
				theta = externalLagrangeMultipliers[i] + externalLagrangeMultipliers[j];

			if (graph[i][j]) {
				graph[i][j] = false;
				graph[j][i] = false;
				h -= theta;
			} else {
				if (rand.nextDouble() < Math.exp(theta)) {
					graph[i][j] = true;
					graph[j][i] = true;
					h += theta;
				}
			}
		}
		
	}

	public void findEdges() {
		connected = new ArrayList<int[]>();
		numberOfEdges = 0;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (graph[i][j]) {
					int pair[] = new int[2];
					pair[0] = i;
					pair[1] = j;
					connected.add(pair);
					numberOfEdges++;
				}
			}
		}
	}

	public void calculateNodeDegreesHist() {
		for (int i = 0; i < numberOfBlocks; i++) {
			for (int j = i * sizeOfBlock; j < (i + 1) * sizeOfBlock; j++) {
				if(graph[i][j])
					nodeDegreesHists[i][j]++;	
			}
		}
	}

	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	public List<int[]> getConnected() {
		return connected;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double[] getNodeDegrees() {
		return nodeDegrees;
	}

	public boolean[][] getGraph() {
		return graph;
	}

	public void setNT(int NT) {
		this.NT = NT;
	}

	public int getSizeOfBlock() {
		return sizeOfBlock;
	}

	public double getHamiltonian() {
		return h;
	}
	
	public int[][] getNodeDegreesHist(){
		return nodeDegreesHists;
	}

	public int getNumberOfBlocks() {
		return numberOfBlocks;
	}
	
}