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
    private boolean logged = false;
    private boolean loginFailed = false;
    ThreadReader readerThread;
    ThreadWriter writerThread;
    Socket socket;
    private boolean chatChoice = false;
    private boolean chatChoiceFaild = false;



    public Client(String hostname , int port){
        this.hostname = hostname;
        this.port = port;

    }

    public void init(){
        try{
            loginPrompt();
            if(choice != 3){
                socket = new Socket(hostname , port);
                System.out.println("Connected to the chat server");
                readerThread = new ThreadReader(socket,this);
                writerThread = new ThreadWriter(socket,this);

                readerThread.start();
                writerThread.start();

                // Main login loop; only attempt login, no reconnection needed
                while (!logged) {
                    // Loop until login is successful (threadReader will update this)
                    Thread.sleep(100);  // Wait for login status update
                }

                System.out.println("You are now logged in. Start chatting!");
            }



        }catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
                case 1, 2 -> {
                    System.out.print("Enter username: ");
                    this.username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    this.password = scanner.nextLine();
                    if (Utility.isValidUsername(this.username)) {
                        return;
                    }
                    System.out.println("username contains invalid character");
                }
                case 3 ->{
                    return;
                }


                default -> System.out.println("Invalid input");
            }
        }
    }

    public int getChoice() {
        return choice;
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

    public void setLoginFailed(boolean loginFailed) {
        this.loginFailed = loginFailed;
    }

    public boolean hasLoginFailed() {
        return loginFailed;
    }

    public String getUsername() {
        return username;
    }

    public void setChatChoice(boolean chatChoice) {
        this.chatChoice = chatChoice;
    }

    public boolean isChatChoice() {
        return chatChoice;
    }

    public void setChatChoiceFaild(boolean chatChoiceFaild) {
        this.chatChoiceFaild = chatChoiceFaild;
    }

    public boolean isChatChoiceFaild() {
        return chatChoiceFaild;
    }

    public void requestCredentials(Scanner scanner) {
        System.out.println("Enter username: ");
        this.username = scanner.nextLine();
        System.out.println("Enter password: ");
        this.password = scanner.nextLine();
    }

    public void chatPrompt(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("1.Private Chat");
            System.out.println("2.Chat room");
            System.out.println("3.global chat");
            System.out.println("4.logout");
            System.out.print("Choose an option: ");
            choice = Integer.parseInt(scanner.nextLine());
            //TODO
            switch (choice){
                case 1 -> {

                }
            }

        }
    }



    public static void main(String[] args) {
        Client c1 = new Client("" , 12345);
        c1.init();
    }
}
