package server;

import Client.Client;
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
    String username;
    String password;
    BufferedReader reader;
    int choice;

//better to use a webSocket

    public ClientHandler(Socket socket , Server server){
        this.socket = socket;
        this.server = server;

    }
    //TODO client handler should handle the choice
    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer  = new PrintWriter(socket.getOutputStream(),true);

            while(true){
                if(checkPasswordAndUsername()){
                    break;
                }
            }
            String serverMessage = "new user connected to the Chat:  " + username;
            server.addUserName(this.username);
            printUser();
            server.boardcastMessage(serverMessage, this);


            String text = null;
            chat(text);


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

    boolean checkPasswordAndUsername() throws IOException {
        username = reader.readLine();
        password = reader.readLine();
        choice = Integer.parseInt(reader.readLine());

         return switch (choice){
            case 1 :
                if(server.loginSuccessful(username,password)){
                    writer.println("OK");
                    yield  true;
                }
                writer.println("ERROR");
                yield false;


            case 2 :
                if(!server.registerSuccessful(username,password) ){
                    writer.println("ERROR");
                    yield false;
                }
                writer.println("OK");
                yield true;

            default:
                writer.println("ERROR");
                yield false;
        };


    }

    public void chat( String text ){
        try {
            String serverMessage;
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

    //TODO
    public boolean handleChoice() throws IOException {
        choice = Integer.parseInt(reader.readLine());
        switch (choice){
            case 1 -> {
               writer.println("OK");
               return  true;
            }
        }

        return true;
    }



    public String getUsername() {
        return username;
    }
}
