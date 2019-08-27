package handlers.handlerSender;

import TCP.Serveur.TCPServerLMessageAlwaysOn;
import com.sun.jdi.event.ThreadStartEvent;
import model.Message;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @brief the Goal of this handler is to control the serveur
 * thread and restart the server after each message receive
 */
public class HandlerReceiver extends Thread implements Runnable{

    public static final String TAG = "HandlerReceiver";



    private ConcurrentLinkedQueue<Message> buffer;
    private long lastCreation;
    private AtomicInteger lastElementsSeen;
    private int numTry;
    private AtomicBoolean shouldIWork; // true if the handler work, false to kill the handler
    private Thread th;
    /**
     *
     *
     */
    public HandlerReceiver(){
        this.buffer = new ConcurrentLinkedQueue<Message>();
        this.lastCreation = System.currentTimeMillis();
        this.numTry = 0;
        this.lastElementsSeen = new AtomicInteger(0);
        this.shouldIWork = new AtomicBoolean(true);
        new Thread(new TCPServerLMessageAlwaysOn(this.buffer)).start();

        System.out.println(TAG + " The handler is set! ");
        this.start();
    }
    @Override
    public void run() {
        while (this.shouldIWork.get()){
            // When u receive something
            if (this.lastElementsSeen.get() != this.buffer.size()){
                System.out.println(TAG + this.toString());
                // re-open the connexion
                new Thread(new TCPServerLMessageAlwaysOn(buffer)).start();
                this.lastCreation = System.currentTimeMillis();
                this.numTry = 0 ;
            }
            // if the thread is kill
            if (System.currentTimeMillis()-this.lastCreation > 2000){
                System.out.println(TAG + "U make me wait too long");
                this.numTry ++;

                // if we try more than 3 times we break the handler
                if (numTry >3) {
                    this.shouldIWork.set(false);
                    System.out.println(TAG + " that more than 3 time so I kill the server ");

                }
                else {
                    new Thread(new TCPServerLMessageAlwaysOn(buffer)).start();
                    this.lastCreation = System.currentTimeMillis();
                }
            }


        }
    }

    public ConcurrentLinkedQueue<Message> getBuffer() {
        return buffer;
    }

    public void killHandler(){
        this.shouldIWork.set(false);
    }

    /**
     *
     * @return null if nothing inside else the Message
     */
    public Message getMessage(){
        // if there is none view messages
        if (this.buffer.size() > 0 ){
            this.lastElementsSeen.addAndGet(-1);
        }

        Message msg = this.buffer.poll();
        return msg;
    }

    @Override
    public String toString() {
        return "HandlerReceiver{" +
                "buffer=" + buffer.toString() +
                ", lastCreation=" + lastCreation +
                ", lastElementsSeen=" + lastElementsSeen.get() +
                ", numTry=" + numTry +
                ", shouldIWork=" + shouldIWork.get() +
                '}';
    }
}