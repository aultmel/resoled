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

import java.time.LocalDate;
import java.time.Period;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

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
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO, Errors errors,
                           BindingResult result, Model model) {


        if (errors.hasErrors()) {
            return "register";
        }

        // checks if username is taken and if it is taken sends an error to the view
        if(userRepository.existsByUsername(registerDTO.getUsername())){
//            model.addAttribute("registerDTO", registerDTO);
            errors.rejectValue("username", "username.unavailable", "Username is unavailable");;
            return "register";
        }


// checks if passwords match for registration and if they don't match sends an error to the view
        String password = registerDTO.getPassword();
        String verifyPassword = registerDTO.getPasswordCheck();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
//            model.addAttribute("registerDTO", registerDTO);
            return "register";
        }

        //Save new user via UserService
        userService.saveUser(registerDTO);

        // possibly return with a success param to use on landing page after registration
        return "redirect:/home/?success";

    }

}


