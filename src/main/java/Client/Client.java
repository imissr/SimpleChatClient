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



    public Client(String hostname , int port ){
        this.hostname = hostname;
        this.port = port;
        this.init();

    }

    public void init(){
        try{
            Socket socket = new Socket(hostname , port);
            System.out.println("Connected to the chat server");


            ThreadReader reader = new ThreadReader(socket,this);
            reader.start();
            ThreadWriter writer = new ThreadWriter(socket,this);
            writer.start();
        }catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }



   void loginprompt() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter the port: ");
            port = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the hostname: ");
            hostname = scanner.nextLine();
            if(Utility.pingServer(hostname,1000)){
                break;
            }
        }

        //label the looop!!!!
        outtorLoop:
        while (true) {
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    this.username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    this.password = scanner.nextLine();
                    if(Utility.isValidUsername(this.username)){
                        break outtorLoop;
                    }
                    System.out.println("username contains invalid character");
                    break;



                case 2:
                    System.out.print("Enter username: ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String registerPassword = scanner.nextLine();
                    if(Utility.isValidUsername(this.username)){
                        break outtorLoop;
                    }
                    System.out.println("username contains invalid character");
                    break;


                default:
                    System.out.println("Invalid input");
                    break;

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

}
