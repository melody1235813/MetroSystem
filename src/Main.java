
public class Main {
	
	public static void main(String argv[]) {
		int n_stations = 2;
		int n_timePoints = 3;
		int max_lines = 1;
		int max_trainPerLine = 1;
		int max_stationsPerLine = 1;
		int nRes = 1;
		int maxValue = 100;
		int round = 1;
		int iteration = 1;
		double runtime[] = new double[round];
		
		for (int i = 0; i < round; i++) {
			double total = 0;
			for (int j = 0; j < iteration; j++) {
				double begintime = System.currentTimeMillis();
				Metro_BasicData.setResource(nRes);
				Metro_BasicData.generateGraph(n_stations, n_timePoints, max_lines, max_trainPerLine, max_stationsPerLine);
				Metro_BasicData.setValuesOfVerticesRandomly(maxValue);
				Metro_BasicData.generateDetectingRate(1, 1, 0.5);
				new Metro_DrawGraph();
				Metro_FullLPData.convertConnectionToGraph();
				Metro_FullLPData.generateValuesInGraph();
				Metro_FullLPData.generateAttackerRoutes(Metro_FullLPData.nVertex, Metro_FullLPData.graph);
				Metro_CallKnitro.runFullLP("fullLP.mod", "fullLP.dat", "fullLP.run");
				double endtime = System.currentTimeMillis();
				total = total + endtime - begintime;
//			System.out.println("runtime: " + (endtime - begintime));
			}
			runtime[i] = total / iteration;
			System.out.println("runtime for " + n_stations + " stations: " + runtime[i]);
			n_stations += 2;
			max_stationsPerLine = n_stations / 2;
		}
		
		System.out.println("Runtime:");
		for (int i = 0; i < round; i++) {
			System.out.print(runtime[i] + ", ");
		}
	}

}
