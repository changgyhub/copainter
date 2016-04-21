import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ColorButton extends JButton{
	private static final long serialVersionUID = 4953593679262705453L;
	public int width, height;
	public Color color;
	
	ColorButton (int a, int b, Color c){
		width = a;
		height = b;
		color = c;
	}
	public void paintComponent(Graphics g){
		g.setColor(color);
		g.fillRect(0, 0, width, height);
	}
	
}
