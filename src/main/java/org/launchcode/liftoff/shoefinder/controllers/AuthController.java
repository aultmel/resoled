package org.launchcode.liftoff.shoefinder.controllers;

import jakarta.validation.Valid;

import org.launchcode.liftoff.shoefinder.data.RoleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.LoginDTO;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.launchcode.liftoff.shoefinder.services.implement.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;


// perhaps change to @RestController
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
//        LoginDTO loginDTO = new LoginDTO();
//        model.addAttribute("loginDTO", loginDTO);
        return "login";
    }


    @GetMapping("/register")
    public String registerGetMapping(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("registerDTO", registerDTO);

//        UserEntity userEntity = new UserEntity();
////        userEntity.setUsername(registerDTO.getUsername());
//
//        userEntity.setUsername("TESTNAME");
////        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
//        userEntity.setPassword(passwordEncoder.encode("TESTPASS"));
//        Role role = roleRepository.findByName("USER");
//        userEntity.setRoles(Arrays.asList(role));
//        userRepository.save(userEntity);



        return "register";
    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
                           BindingResult result, Model model) {



//        UserEntity existingUserUsername = userService.findByUsername(registerDTO.getUsername());
//
//        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()
//        ){
//            result.rejectValue("username", "There is already a user with that username.");
//        }
//
//        if(result.hasErrors()) {
//            model.addAttribute("registerDTO", registerDTO);
//            return "register";
//        }


        if(userRepository.existsByUsername(registerDTO.getUsername())){
            // username is taken
            model.addAttribute("registerDTO", registerDTO);
            return "register";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
//
//        userEntity.setUsername("TESTNAME");
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
//        userEntity.setPassword(passwordEncoder.encode("TESTPASS"));
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));
        userRepository.save(userEntity);


//        userService.saveUser(registerDTO);

        // return with a success param to use on landing page after registration
        return "redirect:/";

    }







}
