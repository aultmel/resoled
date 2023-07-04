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

        LocalDate birthday = registerDTO.getBirthday();
        LocalDate currentDate = LocalDate.now();
        int minAge = 13;
        int age = Period.between(currentDate, birthday).getYears();

        if (errors.hasErrors()) {
            return "register";
        }

        // checks if username is taken
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            model.addAttribute("registerDTO", registerDTO);
            model.addAttribute("existingUsername", "That username is unavailable.");
            return "register";
        }

//        // checks if password and passwordCheck match
//        if(!registerDTO.getPassword().equals(registerDTO.getPasswordCheck())){
//            model.addAttribute("registerDTO", registerDTO);
//            model.addAttribute("passwordCheckFail", "The passwords did not match.");
//            return "register";
//
//        }

        String password = registerDTO.getPassword();
        String verifyPassword = registerDTO.getPasswordCheck();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "register";
        }

        if(age < minAge) {
            model.addAttribute("registerDTO", registerDTO);
            model.addAttribute("birthdayCheckFail", "Must be 13 years old to register.");
            return "register";

        }


        userService.saveUser(registerDTO);

        // possibly return with a success param to use on landing page after registration
        return "redirect:/?success";

    }

}
