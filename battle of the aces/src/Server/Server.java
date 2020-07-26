package Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Server extends JFrame{
	private DatagramSocket socket;
	
	public Server() {
		try {
			this.socket = new DatagramSocket(2515);
		}catch(Exception e) {
			
		}
	}
	
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				
			}
			String message = new String(packet.getData());
			if (message.trim().equalsIgnoreCase("ping")) {
				System.out.println("CLIENT : "+message);
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			}
		}
	}
	
	public void sendData(byte[] data, InetAddress ip, int port) {
		DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
		try {
			socket.send(packet);
		} catch (IOException e) {

			
		}
	}
}
