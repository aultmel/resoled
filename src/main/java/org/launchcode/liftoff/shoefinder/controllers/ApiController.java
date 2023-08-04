package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;

import org.launchcode.liftoff.shoefinder.services.SuggestionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api")
@RestController
public class ApiController {

    SuggestionService suggestionService;
    MessageChainRepository messageChainRepository;

    public ApiController(SuggestionService suggestionService, MessageChainRepository messageChainRepository) {
        this.suggestionService = suggestionService;
        this.messageChainRepository = messageChainRepository;
    }

    @GetMapping("/userDisplayNameSuggestion")
    public List<String> userDisplayNameSuggestion(@RequestParam String searchTerm) {
        List<String> suggestionsList = suggestionService.getSuggestions(searchTerm, "displayName");
        return suggestionsList;
    }

    @GetMapping("/brandSuggestion")
    public List<String> brandSuggestion(@RequestParam String searchTerm) {
        List<String> suggestionsList = suggestionService.getSuggestions(searchTerm, "brand");
        return suggestionsList;
    }

    @GetMapping("/styleSuggestion")
    public List<String> styleSuggestion(@RequestParam String searchTerm) {
        List<String> suggestionsList = suggestionService.getSuggestions(searchTerm, "style");
        return suggestionsList;
    }


}


