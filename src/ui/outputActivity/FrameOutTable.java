package ui.outputActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Message;
import model.MessageTableDynamique;
import serialize.FileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class FrameOutTable extends JFrame implements Runnable {

    public static final String TAG = "FrameOutTable :";

    // Serializer
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();
    private MessageTableDynamique modele;

    // Attributs
    // front

    private JTable outTable;

    // back
    private FileManager fm;
    private int lastVersion;
    private ArrayList<Message> liste;


    public FrameOutTable(FileManager fm){
        super();

        setTitle("Table Frame");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut

        //Nous demandons maintenant à notre objet de se positionner au centre
        setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        Create the table
         */
        this.fm = fm; // get the file and the mutex
        this.liste = new ArrayList<>();
        try {
            liste = this.fm.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        modele = (liste == null)? new MessageTableDynamique(liste = new ArrayList<>()):new MessageTableDynamique(liste);
        this.outTable = new JTable(modele);

        getContentPane().add(new JScrollPane(this.outTable), BorderLayout.CENTER);





        pack();

        setVisible(true);

        this.lastVersion = fm.getNumbElements();

        this.outTable.setAutoCreateRowSorter(true);// enable sorting

        new Thread(this).start();

    }

    @Override
    public void run() {
        /*
        each second check if there is something new in the file
         */
        while(true){
            if (this.lastVersion != this.fm.getNumbElements()){
                // something append so reload the last elements
                /*
                Get the new list
                 */
                System.out.println(TAG + " Let's get the list");
                try {
                    this.liste = this.fm.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                for (int i = lastVersion; i<this.fm.getNumbElements();i++) {
                    this.modele.addMessage(this.liste.get(i));
                }
                this.lastVersion = this.fm.getNumbElements();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
