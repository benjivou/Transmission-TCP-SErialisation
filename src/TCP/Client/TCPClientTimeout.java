package TCP.Client;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.in;

public class TCPClientTimeout extends TCPClientBuilder implements Runnable {

    @Override
    public void run() {
        try {
            InputStream in;
            setSocket();
            s.setSoTimeout(1000);
            System.out.println("Client set connection");
            in = s.getInputStream();
            String chaine = readMessage(in);

            s.close();
            System.out.println("Client close connection");
        } catch (IOException e) {
            System.out.println("IOException TCPClientTimeout");
        }


    }
}
