package test;
import java.io.File;

import TCP.Client.TCPClientLMessage;
import TCP.Serveur.TCPServerLMessage;

public class Test_LMessage {
    public static void main(String[] args) {

        String file = "Tome6.html";
        //String file = "./JAvaTCP/TCP/test.txt";
        new Thread(new TCPClientLMessage(file)).start();
        new Thread(new TCPServerLMessage(file)).start();
    }

}
