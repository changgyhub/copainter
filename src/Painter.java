import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

abstract public class Painter implements ChangeListener{

	JFrame frame = new JFrame();
	PaintPanel panel = new PaintPanel();
	DisplayLabel displayLabel = new DisplayLabel();
	static Color defaultColor = Color.BLACK;
	static int defaultStroke = Constants.mStroke, customizedTurn = 1;
	ArrayList<Path> paths = new ArrayList<Path>();
	Path currentPainting = null;
	
	JMenuBar MenuBar = new JMenuBar();
	JMenu menu1 = new JMenu("Action");
	JMenuItem clear = new JMenuItem("clear");
	JMenuItem save = new JMenuItem("save");
	JMenuItem load = new JMenuItem("load");
	JMenuItem exit = new JMenuItem("exit");
	JMenu menu2 = new JMenu("Help");
	JMenuItem info = new JMenuItem("Info");
	
	ColorButton blackC = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.BLACK);
	ColorButton redC = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.RED);
	ColorButton yellowC = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.YELLOW);
	ColorButton blueC = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.BLUE);
	ColorButton greenC = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.GREEN);
	ColorButton whiteC = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.WHITE);
	ColorButton customizedC1 = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.CYAN);
	ColorButton customizedC2 = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.MAGENTA);
	ColorButton customizedC3 = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.PINK);
	ColorButton customizedC4 = new ColorButton(Constants.buttonSize, Constants.buttonSize, Color.ORANGE);
	JButton colorchooser = new JButton("Color");
	
	SizeButton sSize = new SizeButton(Constants.buttonSize, Constants.buttonSize, Constants.sStroke);
	SizeButton mSize = new SizeButton(Constants.buttonSize, Constants.buttonSize, Constants.mStroke);
	SizeButton lSize = new SizeButton(Constants.buttonSize, Constants.buttonSize, Constants.lStroke);
	SizeButton hSize = new SizeButton(Constants.buttonSize, Constants.buttonSize, Constants.hStroke);
	JSlider strokeSlide = new JSlider(JSlider.HORIZONTAL, 0, 15, Painter.defaultStroke);
	JButton customize = new JButton("More");
	

	@SuppressWarnings("static-access")
	public void go(){
		
    	frame.setTitle("Collaborative Painter");
    	frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);
    	frame.getContentPane().add(panel);
        panel.addMouseListener(panel);
        panel.addMouseMotionListener(panel);
        
        //Set Menu
        save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					final JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(panel);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                FileOutputStream fos = new FileOutputStream(fc.getSelectedFile());
		                ObjectOutputStream os = new ObjectOutputStream(fos);
		                os.writeObject(paths);
		                os.close();  
		            }
				} catch (Exception ee){
					JOptionPane.showMessageDialog(null, "Unable to Save", "Fail to save", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
        
        //Set buttons
        
        blackC.addActionListener(new ButtonListener(blackC));
		redC.addActionListener(new ButtonListener(redC));
		yellowC.addActionListener(new ButtonListener(yellowC));
		blueC.addActionListener(new ButtonListener(blueC));
		greenC.addActionListener(new ButtonListener(greenC));
		whiteC.addActionListener(new ButtonListener(whiteC));
		customizedC1.addActionListener(new ButtonListener(customizedC1));
		customizedC2.addActionListener(new ButtonListener(customizedC2));
		customizedC3.addActionListener(new ButtonListener(customizedC3));
		customizedC4.addActionListener(new ButtonListener(customizedC4));
        
        
        sSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Painter.defaultStroke = Constants.sStroke;
				strokeSlide.setValue(Painter.defaultStroke);
				frame.repaint();
			}
		});
		
		mSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Painter.defaultStroke = Constants.mStroke;
				strokeSlide.setValue(Painter.defaultStroke);
				frame.repaint();
			}
		});
		
		lSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Painter.defaultStroke = Constants.lStroke;
				strokeSlide.setValue(Painter.defaultStroke);
				frame.repaint();
			}
		});
		
		hSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Painter.defaultStroke = Constants.hStroke;
				strokeSlide.setValue(Painter.defaultStroke);
				frame.repaint();
			}
		});
		
		strokeSlide.addChangeListener(this);
		strokeSlide.setMajorTickSpacing(3);
		strokeSlide.setPaintTicks(true);
		strokeSlide.setPaintLabels(true);
		strokeSlide.setBorder(BorderFactory.createEmptyBorder(0,0,2,0));
        Font font = new Font("Calibri", Font.PLAIN, 14);
        strokeSlide.setFont(font);
        
        customize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CustomizedPen (Painter.this).go();
			}
		});
        
        colorchooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Painter.defaultColor = JColorChooser.showDialog(frame, "Choose a Color", Painter.defaultColor);
				if (Painter.customizedTurn == 1) customizedC1.color = Painter.defaultColor;
				else if (Painter.customizedTurn == 2) customizedC2.color = Painter.defaultColor;
				else if (Painter.customizedTurn == 3) customizedC3.color = Painter.defaultColor;
				else {
					customizedC4.color = Painter.defaultColor;
					Painter.customizedTurn = 0;
				}
				Painter.customizedTurn++;
				frame.repaint();
			}
		});
        
        panel.add(blackC);
		panel.add(redC);
		panel.add(yellowC);
		panel.add(greenC);
		panel.add(blueC);
		panel.add(whiteC);
		panel.add(customizedC1);
		panel.add(customizedC2);
		panel.add(customizedC3);
		panel.add(customizedC4);
		panel.add(colorchooser);
		
		panel.add(strokeSlide);
		panel.add(sSize);
		panel.add(mSize);
		panel.add(lSize);
		panel.add(hSize);
		panel.add(customize);
		panel.add(displayLabel);
		
		panel.setLayout(null); // Null Layout
		displayLabel.setBounds(12, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		blackC.setBounds(92, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		redC.setBounds(152, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		yellowC.setBounds(212, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		greenC.setBounds(272, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		blueC.setBounds(332, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		whiteC.setBounds(392, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		customizedC1.setBounds(452, 668, 25, 25);
		customizedC2.setBounds(452, 696, 25, 25);
		customizedC3.setBounds(480, 668, 25, 25);
		customizedC4.setBounds(480, 696, 25, 25);
		colorchooser.setBounds(509, 665, Constants.buttonSize, Constants.buttonSize);
		
		strokeSlide.setBounds(570, 666, 160, Constants.buttonSize);
		sSize.setBounds(734, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		mSize.setBounds(794, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		lSize.setBounds(854, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		hSize.setBounds(914, 668, Constants.buttonSize-7, Constants.buttonSize-7);
		
		customize.setBounds(974, 665, 50, Constants.buttonSize);
		
    	frame.setSize(1024, 768);
    	frame.setLocationRelativeTo(null);
    	frame.setResizable(false);
    	
	}
	
	public void released() {}
	
	class ButtonListener implements ActionListener{
		ColorButton cb;
		ButtonListener (ColorButton cb){
			this.cb = cb;
		}
		public void actionPerformed(ActionEvent e) {
			Painter.defaultColor = cb.color;
			frame.repaint();
		}
	}
	
    class PaintPanel extends JPanel implements MouseListener, MouseMotionListener{
		private static final long serialVersionUID = -3242082444018769089L;
		public void paintComponent( Graphics g) {
    		g.setColor(Color.white);   //Erase the previous figures
    		g.fillRect(0, 0, getWidth(), getHeight());
    		g.setColor(Color.black);
    		try{
    			if(g instanceof Graphics2D) {
        			Graphics2D g2D = (Graphics2D) g;
        			Point prevPoint = null;
            		for (Path p: paths){
            			g2D.setStroke(new BasicStroke(p.stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            			for (Point pp: p.points) {
                			if (prevPoint != null) {
                				g.setColor(p.color);
                				g.drawLine(prevPoint.x, prevPoint.y, pp.x, pp.y);
                			}
                			prevPoint = pp;
                		}
            			prevPoint = null; // Mark the end
            			
            		}
            		if (currentPainting != null ){
            			g2D.setStroke(new BasicStroke(currentPainting.stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            			for (Point pp: currentPainting.points) {
                			if (prevPoint != null) {
                				g.setColor(currentPainting.color);
                				g.drawLine(prevPoint.x, prevPoint.y, pp.x, pp.y);
                			}
                			prevPoint = pp;
                		}
            			prevPoint = null; // Mark the end
            		}
            		g2D.setStroke(new BasicStroke(0, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        			
        		}
    		} catch (Exception ee) {}
    		g.setColor(Color.lightGray);
    		g.fillRect(0, 664, 1024, 64);
    		
    	}
    	
    	public void mouseDragged(MouseEvent event) {
    		currentPainting.points.add(event.getPoint());
    		repaint();
    	}
    	public void mousePressed(MouseEvent event) {
        	currentPainting = new Path();
        	currentPainting.points.add(event.getPoint());
        	repaint();
    		
    	}
    	public void mouseMoved(MouseEvent event) {}
    	public void mouseClicked(MouseEvent event) {}
    	public void mouseEntered(MouseEvent event) {}
    	public void mouseExited(MouseEvent event) {}
    	public void mouseReleased(MouseEvent event) {
    		paths.add(currentPainting);
    		released();
    	}
    }

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		defaultStroke = ((int)source.getValue() == 0 ? 1: (int)source.getValue());
		frame.repaint();
	}
	
}
