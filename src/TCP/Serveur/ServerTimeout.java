/**
 * 
 */
package TCP.Serveur;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import TCP.Client.*;
/**
 * @author benji
 *
 */
public class ServerTimeout implements Runnable {

	private Socket s;


	ServerTimeout(Socket s) throws IOException {
		this.s = s;
		}
	
	public void run() {
	try {
		InputStream in;
		s.setSoTimeout(1000);

		in = s.getInputStream();
        in.read(new byte[8192] );

		s.close();
		}
		catch(IOException e)
		{ 
			System.out.println("IOException ServerTimeout"); 
		}
	}
}
