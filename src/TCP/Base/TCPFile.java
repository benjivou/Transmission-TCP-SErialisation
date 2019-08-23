package TCP.Base;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

public class TCPFile extends TCPMessage {

    //Print downloaded text into the console
    protected void print(InputStream in) throws IOException {
        StringBuffer sb = new StringBuffer(" ");
        if (in != null) {
            //Browse all data send by the server
            while (in.read(buffer) != -1) {
                count = count();
                if (count > 0) {
                    //Insert the buffer into a String buffer
                    sb.insert(0, new String(buffer, Charset.defaultCharset()));
                    //Display
                    System.out.println(sb);
                    //Clear String Buffer
                    sb.delete(0, count - 1);
                    //Clear Buffer
                    clearBuffer();

                }
            }
        }

    }

    //Write downloaded text into a file ("FileReceivedByClient.txt")
    protected  void write(InputStream in) throws IOException {

        try {
            //Create the file (to stock the data)
            FileOutputStream outFile = new FileOutputStream("FileReceivedByClient.txt");
            if (in != null) {
                //Browse all data send by the server
                while (in.read(buffer) != -1) {
                    count = count();
                    if (count > 0) {
                        //Write the buffer into the text file
                        outFile.write(buffer);
                        //Clear Buffer
                        clearBuffer();

                    }
                }
            }
            //Close file
            outFile.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    //Send the contents of the file in entries by TCP (block per block)
    protected void transfer(OutputStream out,String file){
        try{
            //Declarations
            byte[] b = new byte[size]; //Reception buffer
            int r = 0; //Flag to see if buffer is empty (<0)
            int offset = 0; //Place to read into the file

            //Open file
            InputStream flux=new FileInputStream(file);

            //While we can read new block
            while (r!=-1){
                //Stock next block
                r = flux.read(b,offset,size);

                //Send data
                out.write(b);
                out.flush();

                //Free buffer
                for(int i=0;i<b.length;i++)
                    b[i] = 0;
            }
            //Close the connection
            flux.close();

        }
        catch (Exception e){
            System.out.println(e.toString());
        }



    }

}
