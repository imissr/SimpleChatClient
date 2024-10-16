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
                    System.out.println("login/register failed");
                    client.loginPrompt();  // Re-prompt for new credentials
                    client.setLoginFailed(false);  // Reset the login failure flag
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        client.chatPrompt();
        sendChoice();


   }


    public void handshake(){
        writer.println(client.getUsername());
        writer.println(client.getPassword());
        writer.println(client.getChoice());
    }

    public void sendChoice(){
        writer.println(client.getChoice());
    }

    public void closeConnection() throws IOException {
        socket.close();
    }

    public void chat(){
        Console console = System.console();

        String text;

        do {
            text = console.readLine("[" + client.getUsername() + "]: ");
            writer.println(text);

        } while (!text.equals("exit"));

        try {
            closeConnection();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }

}
