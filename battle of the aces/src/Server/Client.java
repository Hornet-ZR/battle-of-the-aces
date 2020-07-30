package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
		while (true) {
			readMessage();
		}
	}
	
	public void sendMessage(String message) {
		try {
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             writer.write(message+"\n\r");
             writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	public void readMessage() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String data;
			while ((data = reader.readLine()) != null) {
				System.out.println(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}
