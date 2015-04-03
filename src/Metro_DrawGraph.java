import javax.swing.JFrame;


public class Metro_DrawGraph extends JFrame {
	
	private Metro_GraphPanel graph = new Metro_GraphPanel(picWidth, picHeight);

	public Metro_DrawGraph () {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(picWidth, picHeight);
		this.getContentPane().add(graph);
	}
	
	public static int picWidth = 400;
	public static int picHeight = 400;

}
