import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

import javax.swing.text.html.MinimalHTMLWriter;

import org.apache.commons.lang3.ArrayUtils;



public class Metro_BasicData {
	
	
	public static void generateGraph(int n_stations, int n_timePoints, int max_lines, int max_trainPerLine, int max_stationsPerLine) {
		stationNum = n_stations;
		initialTimePoints = n_timePoints;
		maxiLineNum = max_lines;
		maxiTrainNumPerLine = max_trainPerLine;
		
		int lineNum = maxiLineNum;
		int [] trainsInLines = generateTrainsInLines(lineNum, maxiTrainNumPerLine);
		int [][] stationsInLines = generateStationsInLines(lineNum, max_stationsPerLine, n_stations, trainsInLines, n_timePoints);
		int [] numsOfStationsInLines = new int[lineNum];
		for (int i = 0; i < numsOfStationsInLines.length; i++) {
			numsOfStationsInLines[i] = stationsInLines[i].length;
		}
		int [] intervalForLines = generateIntervalForLines(lineNum, trainsInLines, numsOfStationsInLines, n_timePoints);
		
		vertices = new int[stationNum][initialTimePoints];
		connections = new int[stationNum*initialTimePoints][stationNum*initialTimePoints];
		
		for (int i = 0; i < stationNum; i++) {
			//The beginning and ending time of the metro system
			vertices[i][0] = 1;
			vertices[i][initialTimePoints - 1] = 1;
		}
		
		for (int i = 0; i < lineNum; i++) {
			
			for (int j = 0; j < trainsInLines[i]; j++) {
				int startTimePointOfThisTrain = (j+1)*intervalForLines[i];
				int [] previousVertex = new int[2];
				for (int k = 0; k < stationsInLines[i].length; k++) {
					vertices[stationsInLines[i][k]][startTimePointOfThisTrain+k] = 1;
					if (k > 0) {
						connections[previousVertex[0]*initialTimePoints+previousVertex[1]][stationsInLines[i][k]*initialTimePoints+startTimePointOfThisTrain+k] = 1;
					}
					previousVertex[0] = stationsInLines[i][k];
					previousVertex[1] = startTimePointOfThisTrain+k;
				}
			}
		}
		
		for (int i = 0; i < stationNum; i++) {
			int previousTime = 0;
			for (int j = 0; j < initialTimePoints; j++) {
				if (j > 0 && vertices[i][j] == 1) {
					connections[i*initialTimePoints+previousTime][i*initialTimePoints+j] = 1;
				}
				if (vertices[i][j] == 1){
					previousTime = j;
				}
			}
		}
		
		/*---------------------Print data in the graph----------------------------------*/
		for (int i = 0; i < lineNum; i++) {
			System.out.print("Stations in Line " + i + ": ");
			for (int j = 0; j < stationsInLines[i].length; j++) {
				System.out.print(stationsInLines[i][j] + " ");
			}
			System.out.print("\n");
		}
		
		for (int i = 0; i < lineNum; i++) {
			System.out.print("Train number in Line " + i + ": " + trainsInLines[i] + "; ");
		}
		System.out.print("\n");
		
		for (int i = 0; i < lineNum; i++) {
			System.out.print("Train interval in Line " + i + ": " + intervalForLines[i] + "; ");
		}
		System.out.print("\n");
		
/*		for (int i = 0; i < connections.length; i++) {
			System.out.print(i+": ");
			for (int j = 0; j < connections[i].length; j++) {
				System.out.print(j + ": " + connections[i][j] + " ");
			}
			System.out.println("\n");
			
		}	*/
		/*-----------------------------------------------------------------------------*/
	}
	
	public static int [][] generateStationsInLines(int lineNum, int maximumStationPerLine, int stationNum, int [] trainsInLines, int initialTimePoints) {
		if (maximumStationPerLine > stationNum) {
			System.out.println("Number of stations in a line out of limit!");
			return null;
		}
		
		int [][] stationsInLines = new int[lineNum][];
		for (int i = 0; i < lineNum; i++) {
			int max = maximumStationPerLine < (initialTimePoints - trainsInLines[i]) ? maximumStationPerLine : (initialTimePoints - trainsInLines[i]);
//			int numeStationsInThisLine = generatePositiveNum(max);
			/*-------------------------changed!--------------------------------------*/
			int numeStationsInThisLine = max;
			if (numeStationsInThisLine < 2) {
				numeStationsInThisLine += 1;
			}	
			int [] stationsInThisLine = new int[numeStationsInThisLine];
			for (int j = 0; j < stationsInThisLine.length; j++) {
				int station = generatePositiveNum(stationNum);
//				station = 1;
				if (Arrays.asList(ArrayUtils.toObject(stationsInThisLine)).contains(station)) {
					j--;
					continue;
				}else {
					stationsInThisLine[j] = station;
				}
			}
			
			Arrays.sort(stationsInThisLine);
			for (int j = 0; j < stationsInThisLine.length; j++) {
				stationsInThisLine[j] -= 1;
			}
			
			stationsInLines[i] = stationsInThisLine;
		}
		
		return stationsInLines;
	}
	
	public static int [] generateIntervalForLines(int lineNum, int [] trainsInLines, int [] numStationsInLines, int initialTimePoints) {
		int intervalForLines[] = new int[lineNum];
		for (int i = 0; i < lineNum; i++) {
			intervalForLines[i] = (int)((initialTimePoints - 1 - numStationsInLines[i])/trainsInLines[i]);
		}
		return intervalForLines;
	}
	
	public static int [] generateTrainsInLines(int lineNum, int maximum) {
		int trainsInLines [] = new int[lineNum]; 
		for (int i = 0; i < lineNum; i++) {
			/*-----------------------changed!--------------*/
			trainsInLines[i] = maximum;
		}
		return trainsInLines;
	}
	
	public static int generatePositiveNum(int maximum){
		Random seed = new Random();
		return seed.nextInt(maximum) + 1;
	}
	
	public static void generateDetectingRate(double instation, double ontrain, double transition) {
		instationRate = instation;
		ontrainRate = ontrain;
		transitionRate = transition;
		rationsSet = 1;
	}
	
	public static void setResource(int nRes) {
		nResource = nRes;
	}
	
	public static void setValuesOfVerticesRandomly(int max) {
		valueOfVertices = new int [stationNum][initialTimePoints];
		for (int i = 0; i < stationNum; i++) {
			for (int j = 0; j < initialTimePoints; j++) {
				valueOfVertices[i][j] = generatePositiveNum(max);
			}
		}
	}
	
	public static int stationNum;
	public static int initialTimePoints;
	public static int maxiStationNum;
	public static int maxiLineNum;
	public static int maxiTrainNumPerLine;
	public static double instationRate;
	public static double ontrainRate;
	public static double transitionRate;
	public static int rationsSet = 0; 
	public static int nResource;
	
	public static int [][] vertices;
	public static int [][] connections;
	public static int [][] valueOfVertices;

}
