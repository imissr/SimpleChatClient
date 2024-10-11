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
        Console console = System.console();
       String userName = console.readLine("\nEnter Your name: ");
       client.setUsername(userName);
       writer.println(userName);

       String text;

       do {
           text = console.readLine("[" + userName + "]: ");
           writer.println(text);

       } while (!text.equals("exit"));

       try {
           socket.close();
       } catch (IOException ex) {

           System.out.println("Error writing to server: " + ex.getMessage());
       }


   }
}
