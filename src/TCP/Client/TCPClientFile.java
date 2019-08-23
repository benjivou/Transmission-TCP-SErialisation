package TCP.Client;

import java.io.*;
import java.nio.charset.Charset;

public class TCPClientFile extends TCPClientBuilder implements  Runnable{

    @Override
    public void run() {
        try {


            System.out.println("Client set connection");

            //Set the connection
            setSocket();

            //Save input stream
            in = s.getInputStream();


            // Stock or display (choice)
            //print(in);
            write(in);

            System.out.println("Client close connection");

        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
    }

}