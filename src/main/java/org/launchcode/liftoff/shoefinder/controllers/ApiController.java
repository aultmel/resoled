package org.launchcode.liftoff.shoefinder.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    UserService userService;

    @Autowired
    ApiController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/message")
    public List<String> messageUserSuggestion(
            @RequestParam String searchTerm
    ) {

        List<String> usernameSuggestionsList = userService.getSuggestionsString(searchTerm);

        return usernameSuggestionsList;

    }
}


//    @PostMapping("/createsuggestions")
//    public String getSuggestions(@RequestBody String jsonData) throws JsonProcessingException {
//
//        System.out.println("received request: " + jsonData);
//
//        // Parse the JSON data.
//        Map<String, String> data = objectMapper.readValue(jsonData, Map.class);
//
//        // Get the keyword.
//        String keyword = data.get("keyword");
//
//        // Create a list of suggestions.
//        List<String> suggestions = userService.getSuggestionsString(keyword);
//
//        // Convert the suggestions to JSON.
//        String jsonString = objectMapper.writeValueAsString(suggestions);
//
//        // Return the JSON string.
//        return jsonString;
//    }

