package server.loginService;

import org.apache.commons.lang3.NotImplementedException;
import util.Utility;

import java.sql.SQLException;

public class login implements IFLogin {

    StorUser database = new StorUser();



    public boolean login(String username , String password){
        if(!database.validate(username ,password)){
            return false;
        }
        return true;
    }

    public boolean register(String username ,String password){
        if(!Utility.isValidUsername(username)){
            System.out.println("username contains invalid character");
            return false;
        }
        if( database.usernameExists(username)){
            System.out.println("username exists");
            return false;
        }
        try {
            database.insert(username ,password);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;


    }

}
