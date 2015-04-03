import java.util.ArrayList;


public class Metro_FullLPData {
	
	public static void convertConnectionToGraph() {
		
		/*Some initial time points are useless. i.e., no trains departs from or arrives at any stations
		 at these time points. Delete these points when prepare for the graph in LP*/
		
		int [][] connections = Metro_BasicData.connections;
		int length = connections[0].length;
		flag = new int[length];
		int count = 0;
		for (int i = 0; i < length; i++) {
			if (i % Metro_BasicData.initialTimePoints == 0){
					flag[i] = count;
					count++;
					continue;
				}else {
					int isSet = 0;
					for (int j = 0; j < i; j++) {
						if (connections[j][i] == 1) {
							flag[i] = count;
							count++;
							isSet = 1;
							break;
						}
					}
					if (isSet == 0) {
						flag[i] = -1;
					}					
				}
		}
		
		nVertex = count+2;
		graph = new int[nVertex][nVertex];
		
		for (int i = 0; i < length; i++) {
			for (int j = i; j < length; j++) {
				if ((flag[i] >= 0) && flag[j] >= 0) {
					graph[flag[i]+1][flag[j]+1] = connections[i][j];
				}
			}
		}
		
		stationsOfVertex = new int[nVertex];
		for (int i = 0; i < length; i++) {
			if (flag[i] >= 0) {
				stationsOfVertex[flag[i] + 1] = (int)(i/Metro_BasicData.initialTimePoints);
			}
		}
		
		startingPoints = new int[Metro_BasicData.stationNum];
		endintPoints = new int[Metro_BasicData.stationNum];
		for (int i = 0; i < startingPoints.length; i++) {
			startingPoints[i] = flag[i*Metro_BasicData.initialTimePoints] + 1;
			endintPoints[i] = flag[i*Metro_BasicData.initialTimePoints + Metro_BasicData.initialTimePoints - 1] + 1;
		}
		
		for (int i = 0; i < startingPoints.length; i++) {
			graph[0][startingPoints[i]] = 1;
			graph[endintPoints[i]][nVertex - 1] = 1;
		}
		
		
		/*-------------------Print ------------------------------------*/
		System.out.print("initial v.s. flag: ");
		for (int i = 0; i < flag.length; i++) {
			System.out.print(i + "&" + flag[i] + " ");
		}
		System.out.print("\n");
		
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.print("\n");
		} 
		/*-------------------------------------------------------------*/
	}
	
	public static void generateValuesInGraph() {
		valuesInGraph = new int[nVertex];
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] >=  0) {
				valuesInGraph[flag[i] + 1] = Metro_BasicData.valueOfVertices[(int)(i/Metro_BasicData.initialTimePoints)][i%Metro_BasicData.initialTimePoints];
			}
		}
	}
	
	public static void generateAttackerRoutes(int nVertex, int [][] graph) {
		attackerRoutes = new ArrayList<>();
		statusOfRouts = new ArrayList<>();
		
		for (int i = 1; i < nVertex-2; i++) {
			ArrayList<int []> currentRoute = new ArrayList<>();
			ArrayList currentStatus = new ArrayList<>();
			generateRoutesStartAt(i, nVertex, currentRoute, currentStatus, graph);
		}
		
		/*----------------------Print---------------------------------------*/
		for (int i = 0; i < attackerRoutes.size(); i++) {
			System.out.print("Route " + i + ": ");
			for (int j = 0; j < attackerRoutes.get(i).size(); j++) {
				System.out.print("(");
				System.out.print(attackerRoutes.get(i).get(j)[0] + "," + attackerRoutes.get(i).get(j)[1]);
				System.out.print(")");
			}
			System.out.print("\n");
			for (int j = 0; j < attackerRoutes.get(i).size(); j++) {
				System.out.print(statusOfRouts.get(i).get(j) + ", ");
			}
			System.out.print("\n");
		} 
		/*------------------------------------------------------------------*/
		
	}
	
	public static void generateRoutesStartAt (int vertex, int nVertex, ArrayList<int []> currentRoute, ArrayList currentStatus, int [][] graph){
		for (int i = vertex + 1; i < nVertex-1; i++) {
			if (currentRoute.size() == 0) {
				if (stationsOfVertex[i] > stationsOfVertex[vertex]){
					break;
				}
			}
			
			if (graph[vertex][i] == 1) {
				ArrayList<int []> localRoute = cloneRoute(currentRoute);
				ArrayList localStatus = cloneStatus(currentStatus);
				int [] currentTrack = new int[2]; //the starting vertex and ending vertex of current track;
				currentTrack[0] = vertex;
				currentTrack[1] = i;
				localRoute.add(currentTrack);
				attackerRoutes.add(localRoute);
				
				if (Metro_BasicData.rationsSet == 0) {
					System.out.println("Detecting ratios not set!");
				} else {
					if (stationsOfVertex[vertex] == stationsOfVertex[i]) {
						if(currentRoute.size() > 0){
							localStatus.add(Metro_BasicData.transitionRate);
						}else {
							localStatus.add(Metro_BasicData.instationRate);
						}
					} else {
						localStatus.add(Metro_BasicData.ontrainRate);
					}
				}
				statusOfRouts.add(localStatus);
				
				generateRoutesStartAt(i, nVertex, localRoute, localStatus, graph);
			}
		}
	}
	
	public static ArrayList<int []> cloneRoute(ArrayList<int []> al) {
		ArrayList<int []> localArrayList = new ArrayList<>();
		for (int i = 0; i < al.size(); i++) {
			int [] track = new int[2];
			for (int j = 0; j < track.length; j++) {
				track[j] = al.get(i)[j];
			}
			localArrayList.add(track);
		}
		return localArrayList;
	}
	
	public static ArrayList cloneStatus(ArrayList al) {
		ArrayList localArrayList = new ArrayList<>();
		for (int i = 0; i < al.size(); i++) {
			localArrayList.add(al.get(i));
		}
		return localArrayList;
	}

	
	public static int nVertex;
	public static int [] stationsOfVertex;
	public static int [] flag; // from vertex in the nstation-initialTimePoints vertion to vertex in the concise graph version
	
	public static int [][] graph;
	public static int [] startingPoints;
	public static int [] endintPoints;
	public static int [] valuesInGraph;
	
	public static ArrayList<ArrayList<int []>> attackerRoutes;
	public static ArrayList<ArrayList> statusOfRouts; //Whether it's instation, ontrain, or transition.
	
}
