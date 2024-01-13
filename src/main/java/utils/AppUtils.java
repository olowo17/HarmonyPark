package utils;

import java.util.Random;

public class AppUtils {
    public Long generateOTP() {
        Random rnd = new Random();
        Long number = (long) rnd.nextInt(999999);
        return number;
    }

    public boolean validEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
