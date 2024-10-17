package server.loginService;

import java.sql.*;

public class StorUser {
    String url = "jdbc:sqlite:C:\\Users\\khale\\OneDrive\\Desktop\\test1.db";

    private Connection connection;

    public StorUser(){
        this.connect();
    }

    private void connect(){
        connection = null;
        try{
            connection = DriverManager.getConnection(url);

        }catch (SQLException e){
            System.out.println("faild connecting to the database" + e.getMessage());
            this.close();
        }
    }


    void insert(String username, String passowrd) throws SQLException {
        String query = "INSERT INTO Account(username,password) VALUES(?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, passowrd);
        statement.executeUpdate();

    }





    boolean validate(String username, String password){
        String query = "SELECT 1 FROM Account WHERE username = ? AND password = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,username);
            statement.setString(2,password);
            try(ResultSet rst = statement.executeQuery()){
                return rst.next();
            }
        } catch (SQLException e) {
            System.out.println("faild finding the username" + e.getMessage());
        }
        return false;
    }

    public boolean usernameExists(String username) {
        String query = "SELECT 1 FROM Account WHERE username = ?";

        try (
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Set the parameter for the prepared statement
            pstmt.setString(1, username);

            // Execute the query and check the result
            try (ResultSet rs = pstmt.executeQuery()) {
                // If there is a result, the username exists
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;  // Return false if an exception occurred or if no results were found
    }



    // usernameNormalization not needed white spaces should not be allowed from the beginning
    private String usernameNormlaization(String username){
        return username.trim();
    }





    private void close()  {
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
