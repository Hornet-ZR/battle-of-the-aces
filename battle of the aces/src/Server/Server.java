package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	public ServerSocket server;
	
	private ArrayList<Socket> clients = new ArrayList<Socket>(); 
	
	int ree = 0;
	
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
				clients.add(client);
				System.out.println(clients.size());
				while (true) {
					//2 players
					String cd1 = readMessage(clients.get(0));
					String cd2 = readMessage(clients.get(1));
					sendMessage(cd2,clients.get(0));
					sendMessage(cd1,clients.get(1));
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
			}
		} catch (Exception e) {

		}
		return data;
	}
	
	public static void main(String args[]) {
		Server s = new Server();
		s.run();
	}
}