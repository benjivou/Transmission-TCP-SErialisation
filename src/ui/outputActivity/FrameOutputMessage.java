package ui.outputActivity;

import TCP.Client.TCPClientLMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import handlers.handlerSender.HandlerReceiver;
import model.Message;
import serialize.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameOutputMessage extends JFrame implements Runnable{

    private static final String  TAG = "FrameOutputMessage :";
    // Serializer
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();


    // Attributs
    // front attributes
    private JPanel mainPanel;
    private JLabel textArea;

    // back attribute
    private HandlerReceiver handler;


    public FrameOutputMessage() {
        super();

        setTitle("Output Frame");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
        setSize(400, 100);
        //Nous demandons maintenant à notre objet de se positionner au centre
        setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // panel
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());

        // text field
        this.textArea = new JLabel("Input anything U want");
        this.mainPanel.add(this.textArea,BorderLayout.CENTER);



        setContentPane(this.mainPanel);
        setVisible(true);
        this.handler = new HandlerReceiver();

    }


    @Override
    public void run() {
        Message msg;
        boolean on=false;
        while (!on){
            /**
             * Step 1: Check if we have some messages
             */
             msg = this.handler.getMessage();

            /**
             * Step 2 : if there is something to do
             */
            if (msg != null){

                // final state
                if (msg.getIsLast()){
                    System.out.println(TAG + " I kill the output frame");

                    this.textArea.setText("this is the end");
                    this.mainPanel.revalidate();
                    this.mainPanel.repaint();
                    this.handler.killHandler(); // kill handler
                    dispose();       // kill JFrame
                    on = true;
                }else
                {
                    System.out.println(TAG + " I read this ");
                    this.textArea.setText(msg.getContent());
                    this.mainPanel.revalidate();
                    this.mainPanel.repaint();
                }
            }
        }
    }
}
