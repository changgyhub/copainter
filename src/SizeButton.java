import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class SizeButton extends JButton{
	private static final long serialVersionUID = 6853316077271655768L;
	public int width, height, size;
	
	SizeButton (int a, int b, int c){
		width = a;
		height = b;
		size = c;
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.fillOval((width-size)/2-3, (height-size)/2-3, size, size);
	}
	
}
