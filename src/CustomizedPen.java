import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CustomizedPen extends JFrame{
	private static final long serialVersionUID = -8211176890667599085L;
	Painter painter;
	
	CustomizedPen (Painter p){
		painter = p;
	}
	
	void go(){
		setTitle("Customize");
		JPanel p = new JPanel();
    	add(p);
    	
    	JLabel colorR = new JLabel("Color R:");
     	JTextField inputcolorR = new JTextField(6);
     	inputcolorR.setText(""+Painter.defaultColor.getRed());
     	JLabel colorG = new JLabel("Color G:");
     	JTextField inputcolorG = new JTextField(6);
     	inputcolorG.setText(""+Painter.defaultColor.getGreen());
     	JLabel colorB = new JLabel("Color B:");
     	JTextField inputcolorB = new JTextField(6);
     	inputcolorB.setText(""+Painter.defaultColor.getBlue());
     	JLabel stroke = new JLabel("Pen Size:");
     	JTextField strokeSize = new JTextField(4);
     	strokeSize.setText(""+Painter.defaultStroke);
     	
     	JButton set = new JButton("Set");
		set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Painter.defaultColor = new Color(Integer.parseInt(inputcolorR.getText()),Integer.parseInt(inputcolorG.getText()),Integer.parseInt(inputcolorB.getText()));
					Painter.defaultStroke = Integer.parseInt(strokeSize.getText());
					painter.strokeSlide.setValue(Painter.defaultStroke);
					if (Painter.customizedTurn == 1) painter.customizedC1.color = Painter.defaultColor;
					else if (Painter.customizedTurn == 2) painter.customizedC2.color = Painter.defaultColor;
					else if (Painter.customizedTurn == 3) painter.customizedC3.color = Painter.defaultColor;
					else {
						painter.customizedC4.color = Painter.defaultColor;
						Painter.customizedTurn = 0;
					}
					Painter.customizedTurn++;
				} catch (Exception ex){}
				CustomizedPen.this.setVisible(false);
				CustomizedPen.this.dispose();
				painter.frame.repaint();
			}
		});
		
		JButton colorchooser = new JButton("Use color chooser");
		colorchooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Color c = JColorChooser.showDialog(CustomizedPen.this, "Choose a Color", Painter.defaultColor);
					inputcolorR.setText(""+c.getRed());
			     	inputcolorG.setText(""+c.getGreen());
			     	inputcolorB.setText(""+c.getBlue());
					repaint();
				} catch (Exception ee){}
			}
		});
		
		p.setLayout(null);
     	p.add(colorR);
     	p.add(inputcolorR);
     	p.add(colorG);
     	p.add(inputcolorG);
     	p.add(colorB);
     	p.add(inputcolorB);
     	p.add(stroke);
     	p.add(strokeSize);
     	p.add(set);
     	p.add(colorchooser);
     	colorR.setBounds(30, 10, 65, 20);
     	inputcolorR.setBounds(100, 10, 80, 20);
     	colorG.setBounds(30, 50, 65, 20);
     	inputcolorG.setBounds(100, 50, 80, 20);
     	colorB.setBounds(30, 90, 65, 20);
     	inputcolorB.setBounds(100, 90, 80, 20);
     	stroke.setBounds(25, 130, 65, 20);
     	strokeSize.setBounds(100, 130, 80, 20);
     	colorchooser.setBounds(25, 160, 150, 40);
     	set.setBounds(25, 200, 150, 40);
    	
    	setSize(200, 270);
    	setResizable(false);
    	setLocationRelativeTo(null);
    	setVisible(true);
	}
}
