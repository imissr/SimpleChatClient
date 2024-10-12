package loginSystem;

import Client.Client;

import java.io.IOException;
import java.sql.*;

public class storUser {
    String url = "jdbc:sqlite:E:\\database\\test.db\\";

    private Connection connection;

    public storUser(){
        this.connect();
    }

    private void connect(){
        connection = null;
        try{
            connection = DriverManager.getConnection(url);

        }catch (SQLException e){
            System.out.println("faild connecting to the database" + e.getMessage());
        }
    }


    public void insert(Client client){
        String query = "INSERT INTO Account(username,password) VALUES(?,?)";
                try{
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, client.getUsername());
                    statement.setString(2, client.getPassword());



                } catch (SQLException e) {
                    System.out.println("faild inserting username and password to the data pase");
                }


    }

    public String getPassword(Client client){
        String query = "SELECT password from Account WHERE useername = ? ";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,client.getUsername());

            try(ResultSet rs = statement.executeQuery()) {
                if(rs.next()){
                    String password = rs.getNString("password");
                    return password;
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return "no password found";
    }


    public boolean checkIfUserExist(Client client ){
        String query = "SELECT EXISTS(SELECT 1 FROM Account WHERE username = ?) ";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,client.getUsername());
            try(ResultSet rst = statement.executeQuery(query)){
                if (rst.next()){
                    return rst.getInt(1 ) == 1;
                }
            }
        } catch (SQLException e) {
            System.out.println("faild finding the username" + e.getMessage());
        }
        return false;
    }

    public void close()  {
        if(connection == null)
            return;
        try {
            connection.close();
            connection = null;
        }catch (SQLException e ){
            System.err.println(e.getMessage());
        }
    }





}
