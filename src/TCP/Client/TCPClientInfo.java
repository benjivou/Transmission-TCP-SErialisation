package TCP.Client;

import java.io.IOException;

public class TCPClientInfo extends TCPClientBuilder implements Runnable {
	public void run( ) {
		try {
			setSocket(); sInfo("The client setConnection", s);
			s.close(); sInfo("The client close the connection", s);
		}
		catch(IOException e)
		{ 
			System.out.println("IOException TCPServerInfo"); 
		}
	}

}
