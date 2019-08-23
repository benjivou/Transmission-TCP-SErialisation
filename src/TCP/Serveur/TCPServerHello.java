package TCP.Serveur;

import java.io.IOException;

public class TCPServerHello  extends TCPServerBuilder implements Runnable {
    public void run() {
        try {
            System.out.println("TCPServerHello launched ...");
            setSocket();
            s = ss.accept();
            System.out.println("Hello, the server accepts");
            s.close(); ss.close();
        }
        catch(IOException e)
        { System.out.println("IOException TCPClientHello"); }
    }
}
