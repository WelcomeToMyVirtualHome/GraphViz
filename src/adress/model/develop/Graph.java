package adress.model.develop;

import java.util.LinkedList;
import java.util.List;

public class Graph{	
	
	List<Integer> graph[];
	
	public Graph(int n) {
		graph = new LinkedList[n];
		for(int i = 0; i < graph.length; i++) {
			graph[i] = new LinkedList<Integer>();
		}
	}
		
	
	
	
}
