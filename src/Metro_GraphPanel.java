import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.omg.CORBA.PUBLIC_MEMBER;



public class Metro_GraphPanel extends JPanel {
	
	public Metro_GraphPanel (int width, int height) {
		widthRatio = (int)(width / Metro_BasicData.initialTimePoints);
		heightRatio =(int)(height / Metro_BasicData.stationNum);
	}
	
	public int widthRatio;
	public int heightRatio;
	

	@Override public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2= (Graphics2D)g;
		for (int i = 0; i < Metro_BasicData.stationNum; i++) {
			for (int j = 0; j < Metro_BasicData.initialTimePoints; j++) {
//				if (Metro_BasicData.vertices[i][j] == 1) {
					g2.setStroke(new BasicStroke(3.0f));
					g2.drawLine(j*widthRatio, i*heightRatio, j*widthRatio, i*heightRatio);
//				}
			}
		}
		
		for (int i1 = 0; i1 < Metro_BasicData.stationNum; i1++) {
			for (int i2 = 0; i2 < Metro_BasicData.initialTimePoints; i2++) {
				for (int j1 = i1; j1 < Metro_BasicData.stationNum; j1++) {
					for (int j2 = 0; j2 < Metro_BasicData.initialTimePoints; j2++) {
						if (j1*Metro_BasicData.initialTimePoints+j2 <= i1*Metro_BasicData.initialTimePoints+i2){
							continue;
						}else{
							if (Metro_BasicData.connections[i1*Metro_BasicData.initialTimePoints+i2][j1*Metro_BasicData.initialTimePoints+j2] == 1) {
								g.drawLine(i2*widthRatio, i1*heightRatio, j2*widthRatio, j1*heightRatio);
							}
						}
					}
				}
			}
		}
	}
	
}
