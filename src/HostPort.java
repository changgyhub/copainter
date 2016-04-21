import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class HostPort {
	
	public void go(){
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	JPanel inputPanel = new JPanel();
     	JLabel textHost = new JLabel("Host:");
     	JTextField inputHost = new JTextField(20);
     	try{
     		inputHost.setText(InetAddress.getLocalHost().getHostAddress());
     	} catch (Exception ee){}
     	JLabel textPort = new JLabel("Port:");
     	JTextField inputPort = new JTextField(20);
     	
     	inputPanel.add(textHost);
     	inputPanel.add(inputHost);
     	inputPanel.add(textPort);
     	inputPanel.add(inputPort);
     	
     	JPanel buttonPanel = new JPanel();
     	JButton startHost = new JButton("start as a host");
     	startHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					ServerPainter p = new ServerPainter();
					p.serverSocket = new ServerSocket(Integer.parseInt(inputPort.getText()));
					frame.setVisible(false);
					frame.dispose();
					p.go();
				} catch (Exception ee){
					JOptionPane.showMessageDialog(null, "Unable to listen to port " + inputPort.getText() + " !", "Fail to start", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
     	JButton connectHost = new JButton("connect to a host");
     	connectHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					ClientPainter p = new ClientPainter();
					p.socket = new Socket(inputHost.getText(), Integer.parseInt(inputPort.getText()));
					frame.setVisible(false);
					frame.dispose();
					p.go();
				} catch (Exception ee){
					JOptionPane.showMessageDialog(null, "Unable to connect to host!", "Fail to start", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
     	buttonPanel.add(startHost);
     	buttonPanel.add(connectHost);
     	
    	frame.add(inputPanel);
    	frame.add(BorderLayout.SOUTH, buttonPanel);
    	frame.setSize(300, 120);
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);
	}
}
