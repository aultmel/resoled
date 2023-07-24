package org.launchcode.liftoff.shoefinder.controllers;

import org.apache.catalina.User;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.EditProfileDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("")
    public String showProfile (Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);
        return "profile/profileMain";
    }

    @GetMapping("/profileEdit")
    public String editProfile (Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        EditProfileDTO editProfileDTO = new EditProfileDTO();
        model.addAttribute("editProfileDTO", editProfileDTO);

        return "profile/profileEdit";
    }

    @PostMapping("profileEdit")
    public String showProfile (@ModelAttribute("editProfileDTO")EditProfileDTO editProfileDTO, Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        userEntity.setDisplayName(editProfileDTO.getDisplayName());
        userRepository.save(userEntity);

        return "profile/profileMain";
    }

}
