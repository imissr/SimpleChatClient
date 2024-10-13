package menu;

import Client.Client;
import server.Server;
import server.loginService.Login;
import util.Utility;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;


//authentication should be server side


public class Menu {
    public static void main(String[] args) {
        Menu m1 = new Menu();
        m1.loginprompt();

    }
    Login loginservice = new Login();

    void loginprompt() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        int port ;
        String hostname;
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
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    if (loginservice.login(loginUsername, loginPassword)) {

                        Client client = new Client(hostname,port,loginUsername,loginPassword);
                        break outtorLoop;
                    }


                case 2:
                    System.out.print("Enter username: ");
                    String registerUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String registerPassword = scanner.nextLine();
                    if (loginservice.register(registerUsername, registerPassword)) {
                        System.out.println("new Client has been created");
                        break;
                    }


            }
        }
    }




}
