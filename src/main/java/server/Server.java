package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private Set<ClientHandler> clientsHandler = new HashSet<ClientHandler>();
    private Set<String> clientsUsername = new HashSet<>();
    private ServerSocket socket;

    int port;

    public Server(int port){
        this.port = port;
    }

    public void init(){
        try{
            socket = new ServerSocket(port);
            System.out.println("Server start on port: " + port);

            while(true){
                Socket clientSocket = socket.accept();
                System.out.println("New Client Connected: "  + clientSocket.getInetAddress().getHostAddress());
                ClientHandler client = new ClientHandler(clientSocket,this);
                clientsHandler.add(client);
                Thread thread = new Thread(client);
                thread.start();
            }

        }catch (IOException e){
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    public void closeConnection(){
        try{
            if(socket != null){
                socket.close();
            }


        }catch(IOException e){
            System.out.println("Server Socket closed" + e.getMessage());
        }
    }

    public void boardcastMessage(String message , ClientHandler clientsEx){
        for (ClientHandler clients : clientsHandler){
            if(!clientsEx.equals(clients)){
               clients.sendMessage(message);
            }
        }
    }

    void removeUser(String userName, ClientHandler aUser) {
        boolean removed = clientsUsername.remove(userName);
        if (removed) {
            clientsHandler.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }


    public void addUserName(String username){
        clientsUsername.add(username);
    }
    public Set<String> getClientsUsername(){
        return this.clientsUsername;
    }

    public boolean hasUser(){
        return !this.clientsUsername.isEmpty();
    }

    public static void main(String[] args) {
        Server server = new Server(12345);
        server.init();
    }
}

