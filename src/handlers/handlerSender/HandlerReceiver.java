package handlers.handlerSender;

import TCP.Serveur.TCPServerLMessageAlwaysOn;

import model.Message;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static TCP.Base.TCPConst.TRY;
import static TCP.Base.TCPConst.WAIT;

/**
 * @brief the Goal of this handler is to control the serveur
 * thread and restart the server after each message receive
 */
public class HandlerReceiver extends Thread implements Runnable{

    public static final String TAG = "HandlerReceiver";



    private ConcurrentLinkedQueue<Message> buffer;
    private long lastCreation;
    private AtomicInteger lastElementsSeen; // the position of the last eleemnt seen
    private int numTry;
    private AtomicBoolean shouldIWork; // true if the handler work, false to kill the handler
    private Thread th;
    private AtomicBoolean messageRemove;
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
        this.th = new Thread(new TCPServerLMessageAlwaysOn(this.buffer));
        this.messageRemove = new AtomicBoolean(false);
        th.start();

        System.out.println(TAG + " The handler is set! ");
        this.start();
    }
    @Override
    public void run() {
        while (this.shouldIWork.get()){
            // When u receive something

            // if the thread is kill
            if (System.currentTimeMillis()-this.lastCreation > WAIT ){
                System.out.println(TAG + "U make me wait too long");
                this.numTry ++;

                // if we try more than 3 times we break the handler
                if (numTry >TRY ) {
                    this.shouldIWork.set(false);
                    break;
                }

                reStartServer();
                this.lastCreation = System.currentTimeMillis();

            }

            // end message
            if ((this.buffer.peek() != null && this.buffer.peek().getIsLast())){
                this.shouldIWork.set(false);
                break;
            }

            if (this.lastElementsSeen.get() > this.buffer.size() ||
                    (this.messageRemove.get() && this.lastElementsSeen.get() < this.buffer.size())){
                this.messageRemove.set(false);
                this.lastElementsSeen.addAndGet(this.buffer.size());
                System.out.println(TAG + this.toString());
                // re-open the connexion

                reStartServer();

                this.numTry = 0 ;


            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            this.messageRemove.set(true);
        }

        Message msg = this.buffer.poll();
        System.out.println(TAG + " getMessage : " + "the buffer length is now :" + this.buffer.size());
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

    private void reStartServer(){
        this.th.interrupt();

        // wait to have a free port
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.th = new Thread(new TCPServerLMessageAlwaysOn(buffer));
        this.th.start();
        this.lastCreation = System.currentTimeMillis();
    }
    public int getSizeBuffer(){
        return this.buffer.size();
    }
}
