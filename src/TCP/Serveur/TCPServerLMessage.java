package TCP.Serveur;

import java.io.File;
import java.io.IOException;

public class TCPServerLMessage extends TCPServerBuilder implements Runnable{



	public TCPServerLMessage(String str) {
		super();
	}

	@Override
	public void run() {
		try {
			System.out.println("TCPServerLMessage launched ...");
			setSocket();
			s = ss.accept();
			in = s.getInputStream();

			String chaine  = readLMessage(in);
			System.out.println("Message :"+chaine);

			s.close(); ss.close();
			System.out.println("Server close ");
		}
		catch(IOException e)
		{
			System.out.println("IOException TCPServerLMessage");
		
		}
	}
}
