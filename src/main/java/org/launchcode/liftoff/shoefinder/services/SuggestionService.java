package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.constants.MessageConstants;
import org.launchcode.liftoff.shoefinder.data.BrandRepository;
import org.launchcode.liftoff.shoefinder.data.StyleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuggestionService {

    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final StyleRepository styleRepository;

    public SuggestionService(UserRepository userRepository, BrandRepository brandRepository, StyleRepository styleRepository) {
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.styleRepository = styleRepository;
    }

    public List<String> getSuggestions(String substring, String type) {


        if (type.equals("displayName")) {
            // Get the list of usernames.
            List<String> names = userRepository.getDisplayNames();
            return createSuggestionsList( names, substring, type);
        } else if (type.equals("brand")) {
            // Get the list of brand names
            List<String> names = brandRepository.getNames();
            return createSuggestionsList(names, substring, type);
        } else if (type.equals("style")) {
            // Get the list of style names
            List<String> names = styleRepository.getNames();
            return createSuggestionsList(names, substring , type);
        }

        return null;

    }

    public List<String> createSuggestionsList(List<String> names, String substring, String type) {

        // Create a list of suggestions.
        List<String> suggestions = new ArrayList<>();
        // Iterate over the usernames.
        for (String name : names) {
            //Checking for size of suggestion list.  SETS SIZE OF SUGGESTION LIST
            if (suggestions.size() == MessageConstants.MAX_USER_FORM_SUGGESTIONS) {
                return suggestions;
            }
            // Check if the username contains the substring then adds to suggestions
            if (name.toUpperCase().contains(substring.toUpperCase())) {

                if(type.equals("displayName")){
                    suggestions.add(name);
                } else {
                    suggestions.add(capitalizeAllFirstLetters(name));
                }
            }
        }
        // Return the suggestions list.
        return suggestions;
    }

    public String capitalizeAllFirstLetters(String aString)
    {
            String lowerCaseString = aString.toLowerCase();
            char[] array = lowerCaseString.toCharArray();
            array[0] = Character.toUpperCase(array[0]);
            for (int i = 1; i < array.length; i++) {
                if (Character.isWhitespace(array[i - 1])) {
                    array[i] = Character.toUpperCase(array[i]);
                }
            }
            return new String(array);
    }



}
