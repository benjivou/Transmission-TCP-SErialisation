package TCP.Base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class TCPMessage extends  TCPBuffer {

    /**
     * Send a big message
     * @author Benjamin VOUILLON
     * **/
    protected void writeLMesaage(OutputStream out, String msOut) throws IOException{
        byte[] b = msOut.getBytes(); // catch the bytes to sends
        int jobToDO = b.length;
        int posStart,posEnd=0;
        // a loop is composent in those steps :
        // Do I have job?
        while(jobToDO > 0){
            // repositionning
            posStart = posEnd;

            // OK_> catch the max data we can send
            if(jobToDO < size) // last paquet
            {
                posEnd += jobToDO;
            }
            else{
                posEnd += size;
            }



            // OK_> writeMessage
            out.write(Arrays.copyOfRange(b,posStart,posEnd));
            out.flush();
            clearBuffer();
            // decreses job
            jobToDO -= (posEnd - posStart);
        }

        out.flush();
        clearBuffer();

    }

    /** The (simple) text write method. */
    protected void writeMessage(OutputStream out, String msOut) throws IOException {
        if((out!=null)&(msOut!=null)) {
            fillChar(msOut);
            out.write(buffer);
            out.flush();
            clearBuffer();
        }
    }

    private void fillChar(String msOut) {
        if(msOut!=null)
            if(msOut.length() < buffer.length)
                for(int i=0;i<msOut.length();i++)
                    buffer[i] = (byte)msOut.charAt(i);
    }

    /** The (simple) text read a huge message
     * @author Benjamin VOUILLON
     * */
    protected String readLMessage(InputStream in) throws IOException {
        String msg = "";
        if(in != null) {
            while(in.read(buffer) != -1)
            {
                count = count();
                if(count>0)
                    msg += new String(buffer,0,count);
                clearBuffer();
            }
        }
        if (msg.length() !=0)
            return msg;
        return null;
    }
    /** The (simple) text read method. */
    protected String readMessage(InputStream in) throws IOException {

        if(in != null) {
            in.read(buffer);

            count = count();
            if(count>0)
                return new String(buffer,0,count);
        }
        return null;
    }

    protected int count;
    protected int count() {
        for(int i=0;i<buffer.length;i++)
            if(buffer[i] == 0)
                return i;
        return buffer.length;
    }


}
