package util;

import java.util.regex.Pattern;

public final class Utility {
    public static boolean isValidUsername(String username){
        if(username != null){
            return Pattern.compile("^([a-z]|[1-9])+$").matcher(username).matches();
        }

        throw new NullPointerException("username is null");

    }

}
