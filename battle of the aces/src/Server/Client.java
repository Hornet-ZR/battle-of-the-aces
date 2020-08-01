package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Client extends Thread{
	
	public Socket socket;

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
		sendMessage(String.valueOf("Connected"));
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
	
	public void enemyDamageRequest(String message) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writer.write(message+"\n\r");
			writer.flush();
		} catch (Exception e) {

		}
	}
	
	public String readMessage() {
		String data = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			data = reader.readLine();
		} catch (Exception e) {
			
		}
		return data;
	}
}