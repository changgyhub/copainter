import java.awt.Graphics;

import javax.swing.JLabel;

public class DisplayLabel extends JLabel{
	private static final long serialVersionUID = 8723275425329277660L;
	public void paintComponent(Graphics g){
		g.setColor(Painter.defaultColor);
		g.fillOval((Constants.buttonSize-Painter.defaultStroke)/2, (Constants.buttonSize-Painter.defaultStroke)/2, Painter.defaultStroke, Painter.defaultStroke);
	}
}
