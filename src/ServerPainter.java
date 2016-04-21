import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerPainter extends Painter{
	
	ServerSocket serverSocket;
	ServerItselfHandler selfHandler = new ServerItselfHandler();
	ClientList clientList = new ClientList();

    public void go() {
    	super.go();
    	
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paths = new ArrayList<Path>();
				Painter.defaultStroke = Constants.mStroke;
				Painter.defaultColor = Color.BLACK;
				selfHandler.clear();
				frame.repaint();
			}
		});
		
		
		load.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				try{
					final JFileChooser fc = new JFileChooser();
					int returnVal = fc.showOpenDialog(panel);
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                FileInputStream fis = new FileInputStream(fc.getSelectedFile());
		                ObjectInputStream os = new ObjectInputStream(fis);
		                paths = (ArrayList<Path>)os.readObject();
		                os.close();
		                selfHandler.load();
		                currentPainting = null;
		                frame.repaint();
		            }
				} catch (Exception ee){
					JOptionPane.showMessageDialog(null, "Unable to load", "Fail to load", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(null, "Do you want to save your painting?", "Before you leave", JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.WARNING_MESSAGE, null, new String[]{"Yes", "No", "Cancel"}, null);
				if (n == JOptionPane.YES_OPTION){
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
					selfHandler.exit();
					System.exit(0);
				} else if (n == JOptionPane.NO_OPTION){
					selfHandler.exit();
					System.exit(0);
				}
			}
		});
		
		frame.setJMenuBar(MenuBar); 
		MenuBar.add(menu1);
		menu1.add(clear);
		menu1.add(save);
		menu1.add(load);
		menu1.add(exit);	
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JOptionPane.showMessageDialog(null, "This is a Server with: \nIP: " + InetAddress.getLocalHost().getHostAddress() + "\nPort: " + serverSocket.getLocalPort(), "Information", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ee){}
			}
		});
		MenuBar.add(menu2);
		menu2.add(info);
		
    	frame.setVisible(true);
    	
    	new Thread(selfHandler).start();
    }
    
    public void released(){
    	selfHandler.release();
    }
    
    class ClientList extends ArrayList<ObjectOutputStream> {
		private static final long serialVersionUID = -5610547443878660268L;
		public synchronized boolean add(ObjectOutputStream out) { return super.add(out); }
    	public synchronized boolean remove(ObjectOutputStream out) { return super.remove(out); }
    }
    
    
    public class ServerItselfHandler implements Runnable {

		public void run() {
			while (true) {
    			try {
    				Socket s = serverSocket.accept();
    				ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
    				clientList.add(out);
    				new Thread(new ClientHandler(s, out)).start();
    			} catch (Exception ee) {ee.printStackTrace();}
    		}
		}
		public void release(){
			try{
				for (ObjectOutputStream os: clientList) {
					os.writeObject(ServerPainter.this.currentPainting);
					os.flush();
				}
				currentPainting = null;
			} catch (Exception ee) {ee.printStackTrace();}
		}
		public void load(){
			try{
				for (ObjectOutputStream os: clientList) {
					os.writeObject("clear");
					for (Path pp: paths){
						os.writeObject(pp);
						os.flush();
					}
				}
			} catch (Exception ee) {ee.printStackTrace();}
		}
		public void clear(){
			try{
				for (ObjectOutputStream os: clientList) {
					os.writeObject("clear");
				}
			} catch (Exception ee) {ee.printStackTrace();}
		}public void exit(){
			try{
				for (ObjectOutputStream os: clientList) {
					os.writeObject("exit");
				}
			} catch (Exception ee) {ee.printStackTrace();}
		}
		
    }
      
    public class ClientHandler implements Runnable {

    	Socket handlerSocket;
    	ObjectInputStream ois;
    	ObjectOutputStream out;
    	
    	ClientHandler(Socket s, ObjectOutputStream c){
    		out = c;
    		handlerSocket = s;
    		try{
    			ois = new ObjectInputStream(s.getInputStream());
    			for (Path pp: paths){
        			out.writeObject(pp);
        			out.flush();
        		}
    		} catch (Exception e){e.printStackTrace();}
    	}
    	
		public void run() {
			while (true) {
				try{
					Object temp = ois.readObject();
					if (temp.equals("exit")){
						clientList.remove(out);
					} else {
						Path path = (Path) temp;
						handle(path);
					}
				} catch (Exception ee){}
			}
		}
		
		public synchronized void handle(Path p){
			try{
				
				ServerPainter.this.paths.add(p);
				ServerPainter.this.frame.repaint();
				
				for (ObjectOutputStream os: clientList) {
					if (!os.equals(out)){
						os.writeObject(p);
						os.flush();
					}
				}
			} catch (Exception ee){ee.printStackTrace();}
			
		}
			
	}
    
}