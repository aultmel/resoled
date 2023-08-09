package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ReportRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Report;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.EditProfileDTO;
import org.launchcode.liftoff.shoefinder.models.dto.ReportDTO;
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
    @Autowired
    private ReportRepository reportRepository;


    @GetMapping("")
    public String showProfile (Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        return "profile/profileMain";
    }

    @GetMapping("/profileEdit")
    public String editProfile (Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        EditProfileDTO editProfileDTO = new EditProfileDTO();
        model.addAttribute("editProfileDTO", editProfileDTO);

        return "profile/profileEdit";
    }

    @PostMapping("profileEdit")
    public String showProfile (@ModelAttribute("editProfileDTO")EditProfileDTO editProfileDTO, Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        try {
            if (editProfileDTO.getDisplayName().isEmpty() || editProfileDTO.getFirstName().isEmpty() || editProfileDTO.getLastName().isEmpty() || editProfileDTO.getEmail().isEmpty()) {
                Error blankField = new Error ("Field cannot be left blank.");
                throw blankField;
            }

        userEntity.setDisplayName(editProfileDTO.getDisplayName());
        userEntity.setFirstName(editProfileDTO.getFirstName());
        userEntity.setLastName(editProfileDTO.getLastName());
        userEntity.setEmail(editProfileDTO.getEmail());
        userRepository.save(userEntity);

        return "redirect:/profile";

        } catch (Error blankField) {
            model.addAttribute("error", blankField.getMessage());
            return "profile/profileEdit";

        }
    }

    @GetMapping("/{displayName}")
    public String showPage(@PathVariable("displayName") String displayName, Model model) {

        UserEntity otherUser = userRepository.findByDisplayNameIgnoreCase(displayName);
        model.addAttribute("otherUser", otherUser);

        ReportDTO reportDTO = new ReportDTO();
        model.addAttribute("reportDTO", reportDTO);

        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        model.addAttribute("createMessageDTO", createMessageDTO);

        return "profile/userData";
    }

    @PostMapping("/{displayName}")
    public String reportUser(@PathVariable("displayName") String displayName, @ModelAttribute("reportDTO")ReportDTO reportDTO, Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        UserEntity otherUser = userRepository.findByDisplayNameIgnoreCase(displayName);
        model.addAttribute("otherUser", otherUser);

        Report report = new Report(otherUser, reportDTO.getComplaintDetail(), userEntity);

        reportRepository.save(report);

        return"redirect:/profile/{displayName}";
    }

}
