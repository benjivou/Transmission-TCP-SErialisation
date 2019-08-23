package TCP.Serveur;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerFile extends TCPServerBuilder implements Runnable {


    ServerFile(Socket s) throws IOException {
        //Socket send by the TCPServerFile
        this.s = s;
    }

    public void run() {
        try {

            //Name of the file to send
            String file = "Tome6.html";

            //Set the timeOut
            s.setSoTimeout(1000);

            //Init output stream
            out = s.getOutputStream();

            //Send all data file block per block
            transfer(out,file);

            //Close the connexion
            s.close();

        }
        catch(IOException e)
        {
            System.out.println("IOException ServerTimeout");
        }
    }
}