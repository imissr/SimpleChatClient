package server;

import server.loginService.StorUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket socket;
    Server server;
    PrintWriter writer;



    public ClientHandler(Socket socket , Server server){
        this.socket = socket;
        this.server = server;

    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer  = new PrintWriter(socket.getOutputStream(),true);
            printUser();
            String username = reader.readLine();
            server.addUserName(username);
            String serverMessage = "new user connected to the Chat:  " + username;
            server.boardcastMessage(serverMessage, this);

            String text ;

            do{

                text = reader.readLine();
                serverMessage = "[" + username + "]: " + text;
                server.boardcastMessage(serverMessage,this);
            }while(!text.equalsIgnoreCase("exit"));

            server.removeUser(username,this);
            socket.close();

            serverMessage = username + " has quitted.";
            server.boardcastMessage(serverMessage, this);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessage(String message){
        writer.println(message);
    }

    void printUser(){
        if(server.hasUser()){
            writer.println("connected users : " + server.getClientsUsername());
        }else{
            writer.println(("no other users connected"));
        }
    }


}
