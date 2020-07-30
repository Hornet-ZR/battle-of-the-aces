package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	public ServerSocket server;
	
	public Server() {
		try {
			System.out.println("Creating server...");
			server = new ServerSocket(2515);
			System.out.println("Created server");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				Socket client = server.accept();
				while (client != null) {
					try {
						readMessage(client);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e1) {
	
			}
		}
	}
	
	public void sendMessage(String message, Socket socket) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(message+"\n");
            writer.flush();
		} catch (Exception e) {

		}  
	}
	
	public String readMessage(Socket socket) {
		String data = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((data = reader.readLine()) != null) {
				data = reader.readLine();
				System.out.println(reader.readLine());
			}
		} catch (Exception e) {

		}
		return data;
	}
	
	public void start_server() {
		new Thread(this);
		this.start();
	}
}
