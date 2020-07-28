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
		while (!server.isClosed()) {
			try {
				Socket client = server.accept();
				readMessage(client);
				sendMessage("Hello Client", client);
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(String message, Socket socket) {
		try {
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			dout.writeUTF(message);
			dout.flush();  
			Thread.sleep(1000);
			//dout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public void readMessage(Socket client) {
		try {
			DataInputStream dis=new DataInputStream(client.getInputStream());  
			String message = (String)dis.readUTF();
			System.out.println("Client : "+message);  
			Thread.sleep(1000);
			//dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public void start_server() {
		Thread m = new Thread(this);
		this.start();
	}
	
	public static void main(String args[]) {
		Server s = new Server();
		s.start_server();
	}
}
