package ui.inputActivity;

import TCP.Client.TCPClientLMessage;
import model.Message;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.google.gson.*;
import serialize.FileManager;


public class FrameInput extends JFrame implements ActionListener {

	public static final String TAG = "Frameinput :";

	// Serializer
	final GsonBuilder builder = new GsonBuilder();
	final Gson gson = builder.create();

	// Attributs
	private JPanel mainPanel;
	private JTextField inputField;
	private JButton btEnd,btSend;
	private FileManager fm;
	
	public FrameInput(FileManager fm) {
		super();
		
		setTitle("Input Frame");
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
	    this.inputField = new JTextField();
	    this.mainPanel.add(this.inputField,BorderLayout.CENTER);
	    
	    // Butttons
	    this.btSend = new JButton("Send");
	    this.btEnd = new JButton("End");

	    this.btSend.addActionListener(this);
	    this.btEnd.addActionListener(this);

	    this.mainPanel.add(this.btSend,BorderLayout.WEST);
	    this.mainPanel.add(this.btEnd,BorderLayout.EAST);
	    
	    setContentPane(this.mainPanel);
		setVisible(true);

		this.fm = fm; // get the file and the mutex
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		String strBtName = arg0.getActionCommand(), msgToSend;
		Message msg = null;
		this.btSend.setEnabled(false);
		this.btEnd.setEnabled(false);

		// onClick of btSend
		if (strBtName.equals("Send")){
			System.out.println("U press Send");
			System.out.println("the message U wrote, was : " + this.inputField.getText());

			// create the object
			msg = new Message(this.inputField.getText(),false);


		}

		// onClick of btEnd
		if (strBtName.equals("End")){
			System.out.println("U press End");
			msg = new Message("kill app");
			dispose();                  // kill JFrame
		}

		// JSONization
		msgToSend = gson.toJson(msg);

		System.out.println(TAG + " the message U will send is : " + msgToSend);

		// Store in a file
		fm.add(msg);

		// Send it to the cloud
		new Thread(new TCPClientLMessage(msgToSend)).start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.btSend.setEnabled(true);
		this.btEnd.setEnabled(true);
	}

}
