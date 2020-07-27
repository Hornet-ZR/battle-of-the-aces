package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	private ServerSocket server;
	
	public Server() {
		try {
			this.server = new ServerSocket(2515);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			Socket client = server.accept();
			readMessage(client);
			sendMessage("Hello Client", client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message, Socket socket) {
		try {
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			dout.writeUTF(message);
			dout.flush();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public void readMessage(Socket client) {
		try {
			DataInputStream dis=new DataInputStream(client.getInputStream());  
			String message = (String)dis.readUTF();
			System.out.println("Client : "+message);  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
