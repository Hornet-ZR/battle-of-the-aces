package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	private Socket socket;
	
	public Client(String ip) {
		try {
			this.socket = new Socket(ip,2515);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		sendMessage("Hello Server");
		readMessage();
	}
	
	public void sendMessage(String message) {  
		try {
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			dout.writeUTF(message);
			dout.flush();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	public void readMessage() {
		try {
			DataInputStream dis=new DataInputStream(socket.getInputStream());  
			String message = (String)dis.readUTF();
			System.out.println("Server : "+message);  
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
