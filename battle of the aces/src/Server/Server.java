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
	
	int loops = 0;
	
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
				while (clients.size() > 1) {
					String cd1 = readMessage(clients.get(0));
					String cd2 = readMessage(clients.get(1));
					//System.out.println(cd1+" "+cd2);
					sendMessage(cd2,clients.get(0));
					sendMessage(cd1,clients.get(1));
				}
			} catch (Exception e1) {
	
			}
		}
	}
	
	public void sendMessage(String message, Socket socket) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(message+"\n\r");
			writer.flush();
		} catch (Exception e) {

		}  
	}
	
	public String readMessage(Socket socket) {
		String data = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			data = reader.readLine();
		} catch (Exception e) {
			
		}
		return data;
	}

}
