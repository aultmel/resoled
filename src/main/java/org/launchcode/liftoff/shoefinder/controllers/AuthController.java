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
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
public class AuthController {


    private static final int MIN_USERNAME_LENGTH = 1;
    private static final int MAX_USERNAME_LENGTH = 25;
    private static final int MIN_PASSWORD_LENGTH = 1;
    private static final int MIN_DISPLAY_NAME_LENGTH = 1;
    private static final int MAX_DISPLAY_NAME_LENGTH = 25;
    private static final int MIN_FIRST_NAME_LENGTH = 1;
    private static final int MAX_FIRST_NAME_LENGTH = 25;
    private static final int MIN_LAST_NAME_LENGTH = 1;
    private static final int MAX_LAST_NAME_LENGTH = 25;

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


    @Autowired
    public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;

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
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO, BindingResult bindingResult, Model model) {

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
        // checks size of username
        if (registerDTO.getUsername().length() < MIN_USERNAME_LENGTH || registerDTO.getUsername().length() > MAX_USERNAME_LENGTH) {
            bindingResult.rejectValue("username", "username.invalid",
                    "Username must be " + MAX_USERNAME_LENGTH +" - " + MAX_USERNAME_LENGTH + " characters");;
            return "register";
        }
        // checks if username is taken and if it is taken sends an error to the view
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            bindingResult.rejectValue("username", "username.unavailable", "Username is unavailable");;
            return "register";
        }
        // checks if displayName has blank space
        if (registerDTO.getDisplayName().contains(" ")){
            bindingResult.rejectValue("displayName", "displayName.invalid", "Display name can not have empty space");
            return "register";
        }

        // checks size of displayName
        if (registerDTO.getDisplayName().length() < MIN_DISPLAY_NAME_LENGTH || registerDTO.getDisplayName().length() > MAX_DISPLAY_NAME_LENGTH) {
            bindingResult.rejectValue("displayName", "displayName.invalid",
                    "Display name must be " + MAX_DISPLAY_NAME_LENGTH +" - " + MAX_DISPLAY_NAME_LENGTH + " characters");;
            return "register";
        }

        // checks if displayName is taken and if it is taken sends an error to the view
        if(userRepository.existsByDisplayName(registerDTO.getDisplayName())){
            bindingResult.rejectValue("displayName", "displayName.unavailable", "Display name is unavailable");;
            return "register";
        }

        // checks if passwords match for registration and if they don't match sends an error to the view
        String password = registerDTO.getPassword();
        String verifyPassword = registerDTO.getPasswordCheck();
        if (!password.equals(verifyPassword)) {
            bindingResult.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }
        // checks size of password
        if (registerDTO.getPassword().length() < MIN_PASSWORD_LENGTH) {
            bindingResult.rejectValue("password", "password.invalid",
                    "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long" );
            return "register";
        }

        // checks size of first name
        if (registerDTO.getFirstName().length() < MIN_FIRST_NAME_LENGTH  || registerDTO.getFirstName().length() > MAX_FIRST_NAME_LENGTH ) {
            bindingResult.rejectValue("firstName", "firstName.invalid",
                    "First name must be " + MAX_FIRST_NAME_LENGTH +" - " + MAX_FIRST_NAME_LENGTH + " characters");;
            return "register";
        }

        // checks size of last name
        if (registerDTO.getLastName().length() < MIN_LAST_NAME_LENGTH  || registerDTO.getLastName().length() > MAX_LAST_NAME_LENGTH ) {
            bindingResult.rejectValue("lastName", "lastName.invalid",
                    "Last name must be " + MAX_LAST_NAME_LENGTH +" - " + MAX_LAST_NAME_LENGTH + " characters");;
            return "register";
        }


        //todo uncomment this once we are ready to have age restriction live.

        // checks if old enough
//        if(!userService.checkAge(registerDTO)) {
//            errors.rejectValue("birthday", "age.unavailable", "You must be at least 13 years of age");
//            return "register";
//        }

        //Save new user via UserService
        userService.saveUser(registerDTO);

        // possibly return with a success param to use on landing page after registration
        return "redirect:/home?success";

    }

}
