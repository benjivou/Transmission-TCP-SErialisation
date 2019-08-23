package TCP.Serveur;

import java.io.*;

public class TCPServerQ8 extends TCPServerBuilder implements Runnable {



    @Override
    public void run() {
        try {


            String file = "Tome6.html";

            System.out.println("TCPServerLMessage launched ...");
            setSocket();
            s = ss.accept();

            out = s.getOutputStream();
            transfer(out,file);

            s.close();
            ss.close();
            System.out.println("Server close ");
        } catch (IOException e) {
            System.out.println("IOException TCPServerLMessage");
        }
    }






}