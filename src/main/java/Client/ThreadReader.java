package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadReader extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    ThreadReader(Socket socket , Client client){
        this.client = client;
        this.socket = socket;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            System.out.println("Error getting input stream: " + e.getMessage());
        }
    }

    public void run(){
        while(true){
            try {
                String responce  = reader.readLine();
                System.out.println("response from client");
                System.out.println("\n" + responce);
                if (client.getUsername() != null) {
                    System.out.print("[" + client.getUsername() + "]: ");
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }


    }
}
