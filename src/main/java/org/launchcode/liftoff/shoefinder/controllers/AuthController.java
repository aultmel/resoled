package org.launchcode.liftoff.shoefinder.controllers;

import jakarta.validation.Valid;

import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {

    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginGetMapping(Model model) {

        //  if the user is logged in redirects to profile
        String username = SecurityUtility.getSessionUser();
        if (username != null) {
            return "redirect:/profile";
        }

        return "login";
    }


    @GetMapping("/register")
    public String registerGetMapping(Model model) {

        //  if the user is logged in redirects to profile
        String username = SecurityUtility.getSessionUser();
        if (username != null){
            return "redirect:/profile";
        }

        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDTO", registerDTO);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "register";
        }

        // trim off any spaces before or after
        registerDTO.setUsername(registerDTO.getUsername().trim());
        registerDTO.setFirstName(registerDTO.getFirstName().trim());
        registerDTO.setLastName(registerDTO.getLastName().trim());
        registerDTO.setDisplayName(registerDTO.getDisplayName().trim());

        // checks if username has blank space
        if (registerDTO.getUsername().contains(" ")){
            bindingResult.rejectValue("username", "username.invalid", "Username can not have empty space");;
            return "register";
        }

        // checks if username is taken and if it is taken sends an error to the view
        if(userRepository.existsByUsernameIgnoreCase(registerDTO.getUsername())){
            bindingResult.rejectValue("username", "username.unavailable", "Username is unavailable");
            return "register";
        }
        // checks if displayName has blank space
        if (registerDTO.getDisplayName().contains(" ")){
            bindingResult.rejectValue("displayName", "displayName.invalid", "Display name can not have empty space");
            return "register";
        }

        // checks if displayName is taken and if it is taken sends an error to the view
        if(userRepository.existsByDisplayNameIgnoreCase(registerDTO.getDisplayName())){
            bindingResult.rejectValue("displayName", "displayName.unavailable", "Display name is unavailable");;
            return "register";
        }

        // checks if displayName is taken and if it is taken sends an error to the view
        if(userRepository.existsByEmailIgnoreCase(registerDTO.getEmail())){
            bindingResult.rejectValue("email", "email.unavailable", "Email is unavailable");;
            return "register";
        }


        // checks if passwords match for registration and if they don't match sends an error to the view
        String password = registerDTO.getPassword();
        String verifyPassword = registerDTO.getPasswordCheck();
        if (!password.equals(verifyPassword)) {
            bindingResult.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }

        // checks if zipcode fits standards
        if (!userService.isNumeric(registerDTO.getZipCode())){
            bindingResult.rejectValue("zipCode", "zipCode.invalid", "Zip Code must be numbers 0 to 9");
            return "register";
        }


//        //todo uncomment this once we are ready to have age restriction live.
//
//        // checks if old enough
////        if(!userService.checkAge(registerDTO)) {
////            errors.rejectValue("birthday", "age.unavailable", "You must be at least 13 years of age");
////            return "register";
////        }

        //Save new user via UserService
        userService.saveUser(registerDTO);

        // possibly return with a success param to use on landing page after registration
        return "redirect:/home?success";
    }
}
