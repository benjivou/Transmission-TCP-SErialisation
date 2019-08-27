package TCP.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import TCP.Base.TCPFile;
import TCP.Base.TCPInfo;
import TCP.Base.TCPMessage;

import static TCP.Base.TCPConst.PORT;


public class TCPClientBuilder extends TCPFile {
	Socket s;//Sockets
	InetSocketAddress isA; // Address + port
	OutputStream out; //to send data
	InputStream in; //to receive data

	//Constructor
	public TCPClientBuilder() {
	s = null;
	isA = null;
	in=null;
	out = null;
	}


	protected void setSocket() throws IOException {
		//Set server address
		isA = new InetSocketAddress("localhost",PORT);
		//Init socket
		s = new Socket(isA.getHostName(), isA.getPort());
		//init StreamBuffer
		setStreamBuffer(s.getReceiveBufferSize());

	}

}