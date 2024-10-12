package Client;

import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Client {
    private String hostname;
    private String username;
    private int port;
    private String password;

    Client(String hostname , int port){
        this.hostname = hostname;
        this.port = port;

    }

    public void init(){
        try{
            Socket socket = new Socket(hostname , port);
            System.out.println("Connected to the chat server");
            new ThreadReader(socket,this).start();
            new ThreadWriter(socket,this).start();
        }catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {

        Client client = new Client("localhost", 12345);
        client.init();
    }

}
