package serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @brief serialize the objet in a file and deserialize objects
 * and give them to as List
 */
public class FileManager {
    private final static String TAG = "FileManager";
    private final static GsonBuilder gsonBuilder = new GsonBuilder();
    private final static Type messageListType = new TypeToken<ArrayList<Message>>(){}.getType();
    private final static Gson gson = gsonBuilder.create();

    // atttribut
    private String path;
    private AtomicBoolean mutex;
    private AtomicInteger numbElements; // the number of element store in it


    public FileManager(String path) throws IOException {
        int intChar;

        List<Message> list; // store the List of message
        BufferedReader br = null;
        File yourFile = new File(path);
        String content = "";
        FileInputStream inFile = null;

        /* Step 1 : create the file if it isn't exist */
        try {
            yourFile.createNewFile(); // if file already exists will do nothing
        } catch (FileNotFoundException e) {
            System.out.println("The file cannot be create");
            throw (e);
        } catch (IOException e) {
            e.printStackTrace();
            throw (e);
        }

        this.mutex = new AtomicBoolean(false);
        this.path = path;

        list = read();
        /* Step 4: store the number of elements in the var */
        if (list != null) {

            this.numbElements = new AtomicInteger(list.size());
        }
        else{
            this.numbElements = new AtomicInteger(0);
        }



    }

    /**
     * @brief Get all elements from the file and add the new Message after it Seriaize allelements in the file
     */
    public void add(Message msg){


        // initialisation
        FileWriter outFile = null;
        List<Message> list = null;
        File myFile = null;


        /* Step 1 : Open the file and deserialize*/
        try {
            list = read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Step 1.1 : unlock mutex & lock
         */

        while (this.mutex.get()){}; // wait for Your turn
        this.mutex.set(true);

        System.out.println(TAG + "Step 1.1 : done ");
        /*
        Step 2 : Add the element to the list
         */
        if (list != null) {
            list.add(msg);
        }
        else {
            list = new ArrayList<Message>();
            list.add(msg);
        }
        System.out.println(TAG +"Step 2 : done ");
        /*
        Step 3 : Add the new list in the file and remove the last save
         */

        //opening
        try {
            myFile = new File(this.path);
            outFile = new FileWriter(myFile,false);
            // gsonizer
            gson.toJson(list, outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(TAG +"Step 3 : done ");
        /*
        Step 4 : Add +1 to the number of elements in the file
         */
        this.numbElements.addAndGet(1);
        this.mutex.set(false);
    }


    /**
     * @brief Deserialize all elements from the file
     */
    public ArrayList<Message> read() throws IOException
    {
        while (mutex.get()){}; // wait for Your turn
        this.mutex.set(true);

        /*
        Step 1 : Open the file
         */
        int intChar;
        ArrayList<Message> list;
        String content="";
        File yourFile = new File(this.path);

        /* Step 2: read the number of elements in the file*/
        FileInputStream inFile = null;
        try {
            inFile = new FileInputStream(yourFile);

            /*
        Step 2 : Get by char the file content
            */

            while ((intChar = inFile.read()) != -1) {

                content += (char) intChar;
            }

            try {
                inFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        /*
        Step 3 : Deserialize & open the access
         */

            this.mutex.set(false);
            System.out.println(TAG+ "in the file : " + content);
            list = gson.fromJson(content, messageListType);
            int size = (list == null )? 0:list.size();
            System.out.println(TAG +" The list size is " + size);
            return list ;
        } catch (FileNotFoundException e) {
            System.out.println("File U search was " + yourFile.toString());
            e.printStackTrace();
        }

        return null;


    }

    public int getNumbElements() {
        return numbElements.get();
    }
}



