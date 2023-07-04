package org.launchcode.liftoff.shoefinder.security;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtility {

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
