package Client;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadWriter extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public ThreadWriter(Socket socket , Client client){
        this.socket = socket;
        this.client = client;

        try{
            writer = new PrintWriter(socket.getOutputStream() , true);

        } catch (IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }

   public void run(){

        handshake();


        Console console = System.console();

       String text;

       do {
           text = console.readLine("[" + client.getUsername() + "]: ");
           writer.println(text);

       } while (!text.equals("exit"));

       try {
           socket.close();
       } catch (IOException ex) {

           System.out.println("Error writing to server: " + ex.getMessage());
       }

   }


    public void handshake(){
        writer.println(client.getUsername());
        writer.println(client.getPassword());
        writer.println(client.getChoice());
    }

}
