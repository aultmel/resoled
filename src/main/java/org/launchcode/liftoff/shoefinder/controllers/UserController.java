package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ReportRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.Report;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.data.*;
import org.launchcode.liftoff.shoefinder.models.ProfileImage;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.EditProfileDTO;
import org.launchcode.liftoff.shoefinder.models.dto.ReportDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.MessageService;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.launchcode.liftoff.shoefinder.constants.MessageConstants.MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ShoeListingRepository shoeListingRepository;
    private final UserService userService;
    private final ProfileImageRepository profileImageRepository;
    private final MessageService messageService;


    @Autowired
    public UserController(MessageService messageService, UserRepository userRepository, ReportRepository reportRepository, ShoeListingRepository shoeListingRepository, UserService userService, ProfileImageRepository profileImageRepository) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.shoeListingRepository = shoeListingRepository;
        this.userService = userService;
        this.profileImageRepository = profileImageRepository;
        this.messageService = messageService;

    }

    @GetMapping("")
    public String showProfile (Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);
        model.addAttribute("userListings", userEntity.getShoeListings());

        // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
        // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
        // number of items on page is set by the maxDisplayed parameter
        List<MessageChain> userEntityMessageChains = messageService.sortMessageChainsByRecentMessage(userEntity);
        List<MessageChain> messageChainList = messageService.shortenMessageChainList(userEntityMessageChains, MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE);
        model.addAttribute("messageChainList", messageChainList);



        ProfileImage profileImage = profileImageRepository.findByUserEntity(userEntity);
        if (profileImage != null) {
            byte[] profileImageData = profileImage.getImageData();
            String base64Image = Base64.getEncoder().encodeToString(profileImageData);
            model.addAttribute("imageData", base64Image);
        }
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
    public String showProfile (@ModelAttribute("editProfileDTO")EditProfileDTO editProfileDTO, @RequestParam(name="imageFiles", required = false) MultipartFile[] files, Model model) {
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

            if (files != null) {
                userService.saveProfileImage(files);
            }

        return "redirect:/profile";

        } catch (Error blankField) {
            model.addAttribute("error", blankField.getMessage());
            return "profile/profileEdit";

        } catch (IOException e) {
            throw new RuntimeException(e);
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

        model.addAttribute("userListings", otherUser.getShoeListings());

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
