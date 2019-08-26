package TCP.Serveur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Message;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPServerLMessageAlwaysOn extends TCPServerBuilder implements Runnable{

    // attribut

    ConcurrentLinkedQueue<Message> buffer; // the buffer to file when U receive a message

    public TCPServerLMessageAlwaysOn(ConcurrentLinkedQueue<Message> buffer) {
        super();
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            Message msg = null;
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = new Gson();
            Type msgType = new TypeToken<Message>(){}.getType();

            /*
            Step 1 : Open the connexion
             */
            System.out.println("TCPServerLMessage launched ...");
            setSocket();
            ss.setSoTimeout(3000); // this close the connection if nobody is connected

            /*
            Step 2 : Accept connexion & and get the message
             */
            s = ss.accept();
            in = s.getInputStream();
            String chaine  = readLMessage(in);

            /*
            Step 3 : Deserialize
             */
            msg = gson.fromJson(chaine, msgType);

            /*
            Step 4 : Add the msg to the Queue
             */
            this.buffer.add(msg);

            /*
            Step 5 : Close the connexion
             */
            s.close(); ss.close();
            System.out.println("Server close ");
        }
        catch(IOException e)
        {
            System.out.println("IOException TCPServerLMessage");

        }
    }
}
