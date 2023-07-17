package org.launchcode.liftoff.shoefinder.controllers;

import org.apache.catalina.User;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

//    @Autowired
//    private UserRepository userRepository;

    @GetMapping("/profile")
    public String showProfile (Model model) {
        UserEntity userEntity = new UserEntity();
//        String username = SecurityUtility.getSessionUser();
//        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);
        return ("profiles/profile");
    }

}
