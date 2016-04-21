import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientPainter extends Painter{

	Socket socket;
	ClientItselfHandler selfHandler;
			
    public void go() {
    	super.go();
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
					JOptionPane.showMessageDialog(null, "This is a Client with: \nLocal IP: " + socket.getLocalAddress().getHostAddress() + "\nLoacl Port: " + socket.getLocalPort() +"\nServer IP: " + socket.getInetAddress().getHostAddress() + "\nServer Port: " + socket.getPort(), "Information", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ee){}
			}
		});
		MenuBar.add(menu2);
		menu2.add(info);
		
    	frame.setVisible(true);
    	
    	selfHandler = new ClientItselfHandler();
    	new Thread (selfHandler).start();
    	new Thread(new ServerHandler()).start();
    	
    }
    
    public void released(){
    	selfHandler.release();
    }
    
    public class ClientItselfHandler implements Runnable{
    	
    	ObjectOutputStream out;
    	ClientItselfHandler(){
    		try{
    			out = new ObjectOutputStream(socket.getOutputStream());
    		} catch (Exception ee){ee.printStackTrace();}
    	}
    	
    	public void run(){}
    	public void release(){
    		try{
    			out.writeObject(currentPainting);
    			out.flush();
        		currentPainting = null;
    		} catch (Exception ee){ee.printStackTrace();}
    	}public void exit(){
    		try{
    			out.writeObject("exit");
    			out.flush();
    		} catch (Exception ee){ee.printStackTrace();}
    	}
    }
    
	
	public class ServerHandler implements Runnable{
		
		ObjectInputStream ois;
		
		ServerHandler(){
			try{
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (Exception ee){ee.printStackTrace();}
		}
		
		public void run(){
			while (true) {
				try {
					Object temp = ois.readObject();
					if (temp.equals("clear")){
						paths = new ArrayList<Path>();
					} else if (temp.equals("exit")){
						JOptionPane.showMessageDialog(null, "Host is gone!", "Connection dropped", JOptionPane.ERROR_MESSAGE);
						int n = JOptionPane.showOptionDialog(null, "Do you want to save your painting?", "Before you leave", JOptionPane.YES_NO_OPTION,  JOptionPane.WARNING_MESSAGE, null, new String[]{"Yes", "No"}, null);
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
						}
						frame.setVisible(false);
						frame.dispose();
						System.exit(0);
					} else{
						paths.add((Path)temp);
					}
					ClientPainter.this.frame.repaint();
				} catch (Exception ee) {ee.printStackTrace();}
			} 
		}
	}
    
}
