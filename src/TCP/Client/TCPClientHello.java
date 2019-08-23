package TCP.Client;

import java.io.IOException;

public class TCPClientHello extends TCPClientBuilder implements Runnable{

	public void run() {
		try {
		System.out.println("TCPClientHello launched ...");
		setSocket();
		System.out.println("Hello, the client is connected");
		s.close();
		}
		catch(IOException e)
		{ System.out.println("IOException TCPClientHello"); }
		}

}
