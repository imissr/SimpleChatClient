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
        try {
        while(true){
            if(readToken()){
                break;
            }
        }

        hear();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public String reciveMessage() throws IOException {
        return reader.readLine();
    }

    public boolean readToken() throws IOException {
        String token = reader.readLine();
        if(token != null){
            if(token.equalsIgnoreCase("OK")){
                client.setLogged(true);
                return true;
            }
            client.setLoginFailed(true);
            return false;
        }
        return false;
    }

    public void hear(){
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
