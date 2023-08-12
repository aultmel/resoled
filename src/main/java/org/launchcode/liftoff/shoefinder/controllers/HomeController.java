package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping("/")
    public String baseUrlGetMapping(Model model) {

        // sample of how to bring in user from the spring security.
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        return "home";
    }

    @GetMapping("/home")
    public String homeGetMapping(Model model) {

        // sample of how to bring in user from the spring security.
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        return "home";
    }


}