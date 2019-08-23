package TCP.Serveur;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import TCP.Base.*;

class TCPServerBuilder extends TCPFile {
    Socket s; //Socket received by client
    InetSocketAddress isA; // Address + port
    ServerSocket ss; // Server socket
    InputStream in; // To receive data
    OutputStream out; //to send data

    //Constructor
    TCPServerBuilder() {
        s = null;
        ss = null;
        isA = null;
        in = null;
        out = null;
    }

    protected void setSocket() throws IOException {
        //Set address
        isA = new InetSocketAddress("localhost",8080);
        //Set serverSocket
        ss = new ServerSocket(isA.getPort());
        //Set buffer
        setStreamBuffer(ss.getReceiveBufferSize());

    }
}