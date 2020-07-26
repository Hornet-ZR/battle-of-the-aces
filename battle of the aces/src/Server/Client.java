package Server;

import java.io.IOException;
import java.net.*;

public class Client extends Thread{
	private InetAddress ip;
	private DatagramSocket socket;
	
	public Client(String ip) {
		try {
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName(ip);
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
			System.out.println("SERVER : "+message);
		}
	}
	
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data,data.length,ip,2515);
		try {
			socket.send(packet);
		} catch (IOException e) {

		}
	}
}
