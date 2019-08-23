package TCP.Client;

import java.io.*;

public class TCPClientLMessage extends TCPClientBuilder implements  Runnable{
    private String file;
    private String str;
    public TCPClientLMessage(String str) {
        this.file = str;
        str = "";
    }

    @Override
    public void run() {
        try {
            recup_file(); // recup file


            setSocket();
            System.out.println("Client set connection");
            out = s.getOutputStream();
            writeLMesaage( out, str);
            s.close();
            System.out.println("Client close connection");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void recup_file(){
        try{
            InputStream flux=new FileInputStream(file);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String line;
            while ((line=buff.readLine())!=null){
                str += line +"\n";
            }
            buff.close();

        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
