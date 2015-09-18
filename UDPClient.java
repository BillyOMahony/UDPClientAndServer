import java.io.IOException;
import java.net.*;

public class UDPClient {
	public static void main(String[] args) throws IOException{
		final int serverPort = 6789;
		
		//gets the IP address of the server
		InetAddress serverIP = InetAddress.getLocalHost();
		String hostName = serverIP.getHostName();
		//declares packetFromServer which will be used later to receive packets from the server
		DatagramPacket packetFromServer = null;
			
		//allocates 4kb to receive from server
		byte[] fromServer = new byte[4096];
		//allocates space to send to server
		byte[] toServer;
		//creates an empty UDP socket, will be used to send to Server
		DatagramSocket socket = null;
		try{
			socket = new DatagramSocket();
		}
		catch(IOException e){
			System.err.println("Connection Failed");
			System.exit(1);
		}
		//Client will send "Hi there" to Server. This creates that in a string
		String sendToServer = "Hi there";
		
		//Stores "Hi there" in Byte array as toServer
		toServer = sendToServer.getBytes();
		DatagramPacket packetToServer = new DatagramPacket(toServer, toServer.length, serverIP, serverPort);
		//sends the packet to the server
		socket.send(packetToServer);
		
		try{
			packetFromServer = new DatagramPacket(fromServer, fromServer.length);
			socket.receive(packetFromServer);
		}
		catch(IOException e){
			// IOException did not work?
			System.err.println("error receiving from Server");
			System.exit(1);
		}
		
		String serverMessage = new String(packetFromServer.getData());
		System.out.println("Server: " + serverMessage);
		
		//close the connection to server
		socket.close();
	}
}
