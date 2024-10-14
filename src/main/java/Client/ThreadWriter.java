package Client;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadWriter extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;
    private Scanner scanner = new Scanner(System.in);

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


        while (!client.isLogged()){
            try {
                handshake();
                Thread.sleep(100);
                if (client.hasLoginFailed()) {
                    client.requestCredentials(scanner);  // Re-prompt for new credentials
                    client.setLoginFailed(false);  // Reset the login failure flag
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

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
        //System.out.println("ThreadWriter : " + client.getUsername() +client.getPassword() + client.getChoice() );
        writer.println(client.getUsername());
        writer.println(client.getPassword());
        writer.println(client.getChoice());
    }

}
