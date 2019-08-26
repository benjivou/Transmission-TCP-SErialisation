package ui.outputActivity;

import TCP.Client.TCPClientLMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Message;
import serialize.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameOutputMessage extends JFrame{

    // Serializer
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();

    // Attributs
    private JPanel mainPanel;
    private JLabel textArea;

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
        this.textArea = new JLabel("Input anything u want");
        this.mainPanel.add(this.textArea,BorderLayout.CENTER);



        setContentPane(this.mainPanel);
        setVisible(true);



    }



}
