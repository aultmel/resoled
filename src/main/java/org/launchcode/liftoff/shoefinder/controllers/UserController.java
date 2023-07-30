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
import org.springframework.web.bind.annotation.*;


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

        try {
            if (editProfileDTO.getDisplayName().isEmpty() || editProfileDTO.getFirstName().isEmpty() || editProfileDTO.getLastName().isEmpty() || editProfileDTO.getEmail().isEmpty()) {
                throw new Error ("Field cannot be left blank.");
            }

        userEntity.setDisplayName(editProfileDTO.getDisplayName());
        userEntity.setFirstName(editProfileDTO.getFirstName());
        userEntity.setLastName(editProfileDTO.getLastName());
        userEntity.setEmail(editProfileDTO.getEmail());
        userRepository.save(userEntity);

        return "redirect:/profile";

        } catch (Error e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred.");
            return "error";
        }
    }



    @GetMapping("/userData/{displayName}")
    public String showPage(@PathVariable("displayName") String displayName, Model model) {


        UserEntity otherUser = userRepository.findByDisplayName(displayName);
        model.addAttribute("otherUser", otherUser);

        return "profile/userData/" + displayName ;
    }

}
