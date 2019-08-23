package TCP.Serveur;

import java.io.IOException;
import java.io.InputStream;

public class TCPServerMessage extends TCPServerBuilder implements Runnable {
    public void run() {
        try {
            
         
            System.out.println("TCPServerHello launched ...");
            setSocket();
            s = ss.accept();
            in = s.getInputStream();

            String chaine  = readMessage( in);
            System.out.println("Message :"+chaine);

            s.close(); ss.close();
            System.out.println("Server close ");
        }
        catch(IOException e)
        { System.out.println("IOException TCPClientHello"); }
    }
}
