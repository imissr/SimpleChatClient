package util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.regex.Pattern;

public final class Utility {
    public static boolean isValidUsername(String username){
        if(username != null){
            return Pattern.compile("^([a-z]|[1-9])+$").matcher(username).matches();
        }

        throw new NullPointerException("username is null");
    }

    public static boolean pingServer(String serverIp, int timeout) {
        try {
            InetAddress server = InetAddress.getByName(serverIp);
            // Return true if the server responds within the timeout
            return server.isReachable(timeout);
        } catch (IOException e) {
            System.out.println("Error pinging server: " + e.getMessage());
            return false;
        }
    }

}
