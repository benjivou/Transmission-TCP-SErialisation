package TCP.Serveur;

        import java.io.IOException;


public class TCPServerFile extends TCPServerBuilder implements Runnable {
    public void run() {
        try {

            // Set  sockets
            setSocket();
            //Set timeOut (stop if there is no connexion on last minute)
            ss.setSoTimeout(60000);


            while(true) {
                //if a client connect
                s = ss.accept();
                //Launch a serverFile in a thread (to send data to the client)
                new Thread(new ServerFile(s)).start();
            }
        }
        catch(IOException e)
        { System.out.println("IOException TCPServerTimeout"); }
    }

}