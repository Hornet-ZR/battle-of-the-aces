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
	
	public ArrayList<Socket> clients = new ArrayList<Socket>(); 
	private ArrayList<Socket> heldClients = new ArrayList<Socket>(); 
	
	
	public Server() {
		try {
			server = new ServerSocket(2515);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			Socket client = null;
			try {
				client = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clients.add(client);
			while (clients.size() == 2) {
				String cd1 = readMessage(clients.get(0));
				String cd2 = readMessage(clients.get(1));
				sendMessage(cd2,clients.get(0));
				sendMessage(cd1,clients.get(1));
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
