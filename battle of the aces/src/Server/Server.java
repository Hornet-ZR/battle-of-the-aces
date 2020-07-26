package Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Server extends JFrame{
	
	public Server() {
		Dimension size = new Dimension(500,500);
		this.setSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Battle of the aces server");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		JPanel panel = new JPanel();
		final JTextField server_ip = new JTextField();
		final JTextField server_port = new JTextField();
		JButton start = new JButton("Start server");
		panel.setBackground(Color.BLACK);
		server_ip.setFont(new Font("Arial",Font.PLAIN,48));
		server_port.setFont(new Font("Arial",Font.PLAIN,48));
		start.setFont(new Font("Arial",Font.PLAIN,48));
		server_ip.setText("Server IP");
		server_port.setText("Server Port");
		
		panel.add(server_ip);
		panel.add(server_port);
		panel.add(start);
		this.add(panel);
		this.invalidate();
		this.revalidate();
		this.repaint();
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerSocket ss = new ServerSocket(Integer.valueOf(server_port.getText()));
					System.out.println("eee2");
					Socket client = ss.accept();
					System.out.println("eee1");
					PrintWriter out = new PrintWriter(client.getOutputStream(), true);
					System.out.println("eee");
					out.println("Hello World");
				}catch(Exception ex) {
					
				}
			}
		});
	}
	
	public static void main(String args[]) {
		Server s = new Server();
	}
	
}
