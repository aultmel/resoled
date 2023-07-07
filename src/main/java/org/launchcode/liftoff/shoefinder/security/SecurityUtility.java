package org.launchcode.liftoff.shoefinder.security;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtility {


    // This is getting the username from the session.  This method is often used in a controller in order to get the username.
    // With the username in a controller we can look up the UserEntity based on username with the UserRepository findByUsername(String username);
    public static String getSessionUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // if the user is authenticated then it will return the current username
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            return currentUsername;
        }

        return null;
    }
}
