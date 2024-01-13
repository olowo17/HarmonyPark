package utils;

import com.harmonypark.harmonypark.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static CustomUser getLoggedInUser() throws UserNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser user = (CustomUser) authentication.getPrincipal();
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
