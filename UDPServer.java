/* Name: William O'Mahont
 * ID: 113541393
 * lab 4
 */

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UDPServer {
	
	public static String time(){
		Calendar time = Calendar.getInstance();
		time.getTime();
		SimpleDateFormat timeStamp = new SimpleDateFormat("dd/WW/yyyy HH:mm");
		return timeStamp.format(time.getTime());
	}
	
	public static void main(String[] args) throws IOException{
		//Define the port number to listen on (6789) and establishes it as a UDP connection
		DatagramSocket serverSocket = new DatagramSocket(6789);
		System.out.println("I am the server");
		
		//Create a logFile
		PrintWriter log = new PrintWriter("log.txt");
		
		for(;;){
			//allocates 4kb to receive messages from the client
			byte[] fromClient = new byte[4096];
			//allocates space to send messages back to the client
			byte[] toClient;
			//declare an empty UDP packet, will be used to receive from client
			DatagramPacket packetFromClient = null;
			try{
				packetFromClient = new DatagramPacket(fromClient, fromClient.length);
				//Input from client accepted
				serverSocket.receive(packetFromClient);
			}
			catch(IOException e){
				// always print error to "System.err"
				System.err.println("Accept Failed");
				System.exit(1);
			}
			//Takes the client package and converts it to a String
			String clientMessage = new String(packetFromClient.getData());
			//gets the client's address and port
			InetAddress clientAddress = packetFromClient.getAddress();
			int clientPort = packetFromClient.getPort();
			
			if(clientMessage.equals("close")){
				break;
			}
			//Get the current time(for log file)
			String time = time();
			
			//prints to log file
			log.println(time + " " + clientMessage);
			
			System.out.println("Client: " + clientMessage);
			//convert the client message to upper case before sending it back
			clientMessage = clientMessage.toUpperCase();
			
			System.out.println("Client will receive:" + clientMessage);
			
			toClient = clientMessage.getBytes();
			DatagramPacket packetToClient = new DatagramPacket(toClient, toClient.length, clientAddress, clientPort);
			//sends the packet to client
			serverSocket.send(packetToClient);
		}
	}
}