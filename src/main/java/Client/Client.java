package Client;

import util.Utility;

import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

public class Client {
    private String hostname;
    private String username;
    private int port;
    private String password;
    private int choice;
    private boolean logged;



    public Client(String hostname , int port){
        this.hostname = hostname;
        this.port = port;

    }

    public void init(){
        try{
            Socket socket = new Socket(hostname , port);
            System.out.println("Connected to the chat server");

            ThreadReader readerThread = new ThreadReader(socket,this);
            ThreadWriter writerThread = new ThreadWriter(socket,this);

            readerThread.start();
            writerThread.start();



        }catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }




   void loginPrompt() {
        Scanner scanner = new Scanner(System.in);
       do {
           System.out.println("Enter the port: ");
           String portString = scanner.nextLine();
           if(!Utility.isValidPortSyntax(portString)){
               continue;
           }
           port = Integer.parseInt(portString);

           System.out.println("Enter the hostname: ");
           hostname = scanner.nextLine();
       } while (!Utility.pingServer(hostname, 1000));


        while (true) {
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choiceString = scanner.nextLine();
            if(!Utility.isNumericString(choiceString)){
                System.out.println("Invalid choice!");
                continue;
            }

            choice = Integer.parseInt(choiceString);


            switch (choice) {
                case 1 -> {
                    System.out.print("Enter username: ");
                    this.username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    this.password = scanner.nextLine();
                    if (Utility.isValidUsername(this.username)) {
                        return;
                    }
                    System.out.println("username contains invalid character");
                }



                case 2 -> {
                    //TODO
                    System.out.print("Enter username: ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String registerPassword = scanner.nextLine();
                    if (Utility.isValidUsername(this.username)) {
                       return ;
                    }
                    System.out.println("username contains invalid character");
                }


                default -> System.out.println("Invalid input");
            }
        }
    }

    public int getChoice() {
        return choice;
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

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLogged() {
        return logged;
    }


    public static void main(String[] args) {
        Client c1 = new Client("" , 12345);
        c1.loginPrompt();
        c1.init();
    }
}
