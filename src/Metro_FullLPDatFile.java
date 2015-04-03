import java.io.File;
import java.io.PrintWriter;
import java.util.EmptyStackException;


public class Metro_FullLPDatFile {
	
	public static void generateFullLPDatFile(String datFileName) {
		try {
			PrintWriter writer = new PrintWriter(new File(datFileName));
			
			writer.println("data;");
			
			writer.println("param nVertex := " + Metro_FullLPData.nVertex + ";");
			writer.println("param nResource := " + Metro_BasicData.nResource + ";");
			writer.println("param nStation := " + Metro_BasicData.stationNum + ";");
			
			writer.println("param startVertices := ");
			for (int i = 0; i < Metro_FullLPData.startingPoints.length; i++) {
				writer.println( (i+1) + " " + (Metro_FullLPData.startingPoints[i]+1));
			}
			writer.println(";");
			
			writer.print("param graph : ");
			for (int i = 0; i < Metro_FullLPData.nVertex; i++) {
				writer.print((i+1) + " ");
			}
			writer.print(":=\n");
			for (int i = 0; i < Metro_FullLPData.nVertex; i++) {
				writer.print((i+1) + " ");
				for (int j = 0; j < Metro_FullLPData.nVertex; j++) {
					writer.print(Metro_FullLPData.graph[i][j] + " ");
				}
				writer.print("\n");
			}
			writer.println(";");
			
			writer.println("param values := ");
			for (int i = 0; i < Metro_FullLPData.valuesInGraph.length; i++) {
				writer.println((i+1) + " " + Metro_FullLPData.valuesInGraph[i]);
			}
			writer.println(";");
			
			for (int i = 0; i < Metro_FullLPData.attackerRoutes.size(); i++) {
				writer.print("param attRoute" + (i+1) + ": 1 2 := \n");
				for (int j = 0; j < Metro_FullLPData.attackerRoutes.get(i).size(); j++) {
					writer.print((j+1) + " ");
					writer.print((Metro_FullLPData.attackerRoutes.get(i).get(j)[0]+1) + " " + (Metro_FullLPData.attackerRoutes.get(i).get(j)[1]+1) + "\n");
				}
				writer.println(";");
			}
			
			for (int i = 0; i < Metro_FullLPData.statusOfRouts.size(); i++) {
				writer.println("param routStatus" + (i+1) + " := ");
				for (int j = 0; j < Metro_FullLPData.statusOfRouts.get(i).size(); j++) {
					writer.println((j+1) + " " + Metro_FullLPData.statusOfRouts.get(i).get(j));
				}
				writer.println(";");
			}
			
			writer.println("end;");
			
			writer.flush();
			writer.close();			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
