package TCP.Serveur;

import java.net.*;
import java.io.*;
import java.util.*; 

public class TCPServer implements Runnable {
	 private ServerSocket ss; private Socket s; // the passive and active sockets
	 private InetSocketAddress isA; // the address
	 /** The main method for threading. */
	 public TCPServer() {
		 ss = null; s = null;
		 isA = new InetSocketAddress("localhost",8085);
	 }
	 /** The main method for threading. */
	 public void run( ) {
		 try {
			 System.out.println("TCPServer launched ...");
			 ss = new ServerSocket(isA.getPort());
			 s = ss.accept();
			 System.out.println("Hello, the server accepts");
			 s.close(); ss.close();
		 }
		 catch(IOException e)
		 { 
			 System.out.println("IOException TCPServer"); 
		 }
	 }
}