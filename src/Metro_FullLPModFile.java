import java.io.File;
import java.io.PrintWriter;


public class Metro_FullLPModFile {
	
	public static void generateFullLPMod(String modFileName) {
		try {
			PrintWriter writer = new PrintWriter(new File(modFileName));
			
			writer.println("param nVertex;");
			writer.println("param nResource;");
			writer.println("param nStation;");
			writer.println("param startVertices {1..nStation};");
			writer.println("param graph {1..nVertex, 1..nVertex};");
			writer.println("param values {1..nVertex};");
			for (int i = 0; i < Metro_FullLPData.attackerRoutes.size(); i++) {
				writer.println("param attRoute" + (i+1) + " {1.." + Metro_FullLPData.attackerRoutes.get(i).size() + ", 1..2};");
			}
			for (int i = 0; i < Metro_FullLPData.statusOfRouts.size(); i++) {
				writer.println("param routStatus" + (i+1) + "{1.." + Metro_FullLPData.statusOfRouts.get(i).size() + "};");
			}
			writer.println("var cov {1..nVertex, 1..nVertex} >= 0;");
			writer.println("var U;");
			writer.println("maximize objective: U;");
			writer.println("subject to");
			
			int count = 1;
			writer.println("bound" + count + ":");
			count++;
			writer.println("sum {i in 1..nStation} cov[1, startVertices[i]] = nResource;");
			
			writer.println("bound" + count + "{i in 2..nVertex-1}:");
			count++;
			writer.println("sum {j in 1..(i-1)} cov[j, i] - sum {j in (i+1)..nVertex} cov[i, j] = 0;");
			
			writer.println("bound" + count + "{i in 1..nVertex, j in 1..nVertex}:");
			count++;
			writer.println("cov[i, j] <= graph[i, j];");
			
			for (int i = 0; i < Metro_FullLPData.attackerRoutes.size(); i++, count++) {
				writer.println("bound" + count + ":");
				writer.println("U <= -((1 - sum {i in 1.." + Metro_FullLPData.attackerRoutes.get(i).size() + "} cov[attRoute" + (i+1) + "[i, 1], attRoute" + (i+1) + "[i, 2]]*routStatus" + (i+1) + "[i])*values[" + (Metro_FullLPData.attackerRoutes.get(i).get(Metro_FullLPData.attackerRoutes.get(i).size() - 1)[1] + 1) + "]);");
			}
			
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e.getMessage());
		}
	}

}
