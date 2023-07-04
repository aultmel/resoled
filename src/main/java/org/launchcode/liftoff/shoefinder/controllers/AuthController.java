package org.launchcode.liftoff.shoefinder.controllers;

import jakarta.validation.Valid;

import org.launchcode.liftoff.shoefinder.data.RoleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;



import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginGetMapping(Model model){
        return "login";
    }


    @GetMapping("/register")
    public String registerGetMapping(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDTO", registerDTO);

        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
                           BindingResult result, Model model, Errors errors) {


        if (errors.hasErrors()) {
            return "register";
        }

        // checks if username is taken
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            model.addAttribute("registerDTO", registerDTO);
            model.addAttribute("existingUsername", "That username is unavailable.");
            return "register";
        }

        // checks if password and passwordCheck match
        if(!registerDTO.getPassword().equals(registerDTO.getPasswordCheck())){
            model.addAttribute("registerDTO", registerDTO);
            model.addAttribute("passwordCheckFail", "The passwords did not match.");
            return "register";

        }

        userService.saveUser(registerDTO);

        // possibly return with a success param to use on landing page after registration
        return "redirect:/?success";

    }

}
