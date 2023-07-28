package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    UserService userService;
    MessageChainRepository messageChainRepository;

    ApiController(UserService userService, MessageChainRepository messageChainRepository) {
        this.userService = userService;
        this.messageChainRepository = messageChainRepository;
    }

    @GetMapping("/messageCreate")
    public List<String> messageUserSuggestion(@RequestParam String searchTerm) {
        List<String> usernameSuggestionsList = userService.getSuggestionsString(searchTerm);
        return usernameSuggestionsList;
    }


}


