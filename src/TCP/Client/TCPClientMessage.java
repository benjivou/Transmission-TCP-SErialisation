package TCP.Client;

import java.io.IOException;
import java.io.OutputStream;

public class TCPClientMessage extends TCPClientBuilder implements Runnable{

	@Override
	public void run() {
		try {
			setSocket();
			System.out.println("Client set connection");
			out = s.getOutputStream();
			writeMessage( out, "Coucou du TP TCP");
			s.close();
			System.out.println("Client close connection");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}




}
