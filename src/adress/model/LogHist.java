package adress.model;

import java.util.Arrays;

public class LogHist {

	private double[] bins;
	private double[] values;

	private int[] arr;
	private double z = 2;
	private int nbins;

	public LogHist(int[] arr) {
		this.arr = arr;
		if(maxValue(arr) <= 0)
			return;
		calculateBins();
		calculateHist();
	}

	private void calculateBins() {
		nbins = (int) (Math.log(maxValue(arr)) / Math.log(z));
		bins = new double[nbins];
		for (int i = 0; i < nbins; i++) {
			bins[i] = Math.pow(z, i);
		}	
	}

	private void calculateHist() {
		values = new double[nbins];
		for (int i = 0; i < nbins - 1; i++) {
			for (Integer l : arr) {
				if (l >= bins[i] && l < bins[i + 1]) {
					values[i]++;
					values[i] /= (Math.pow(z, i) * (z - 1));
				}
			}
		}
	}

	public double[] getValues() {
		return values;
	}

	public double[] getBins() {
		return bins;
	}
	
	public int getNbins() {
		return nbins;
	}

	public int maxValue(int array[]) {
		int max = Arrays.stream(array).max().getAsInt();
		return max;
	}

}
