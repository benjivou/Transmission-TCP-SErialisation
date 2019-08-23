package TCP.Serveur;

import java.io.IOException;

public class TCPServerInfo extends TCPServerBuilder implements Runnable {
    public void run( ) {
        try {
            setSocket(); ssInfo("The server sets the passive socket", ss);
            s = ss.accept(); sInfo("The server accepts the connexion",s);
            s.close(); sInfo("The server closes a connexion",s);
            ss.close(); ssInfo("The server closes the passive socket", ss);
        }
        catch(IOException e)
        { System.out.println("IOException TCPServerInfo"); }
    }
}
