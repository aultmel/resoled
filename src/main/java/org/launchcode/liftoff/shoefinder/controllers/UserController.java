package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ReportRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.Report;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.data.*;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.EditProfileDTO;
import org.launchcode.liftoff.shoefinder.models.dto.ReportDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.MessageService;
import org.launchcode.liftoff.shoefinder.services.StorageService;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.launchcode.liftoff.shoefinder.constants.MessageConstants.MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE;

@Controller
@RequestMapping("/profile")
public class UserController {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ShoeListingRepository shoeListingRepository;
    private final UserService userService;
    private final MessageService messageService;
    private final StorageService storageService;

    public UserController(UserRepository userRepository, ReportRepository reportRepository, ShoeListingRepository shoeListingRepository, UserService userService, MessageService messageService, StorageService storageService) {
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;
        this.shoeListingRepository = shoeListingRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.storageService = storageService;
    }

    @GetMapping("")
    public String showProfile (Model model) {
        return getOneListingsPage(model, 1);
    }



    @GetMapping("/page/{pageNumber}")
    public String getOneListingsPage(Model model, @PathVariable("pageNumber") int currentPage) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        List<ShoeListing> allShoeListings = userEntity.getShoeListings();

        PagedListHolder<ShoeListing> pagedListHolder = new PagedListHolder<>(allShoeListings);
        pagedListHolder.setPage(currentPage - 1);
        pagedListHolder.setPageSize(6);

        List<ShoeListing> pageSlice = pagedListHolder.getPageList();
        Pageable pageable = PageRequest.of(currentPage - 1, 6);

        Page<ShoeListing> pageShoeListings = new PageImpl<>(pageSlice, pageable, allShoeListings.size());

        // Creating a pageable framework from a list of Listings
        // number of items on page is set by the size parameter of the PageRequest.of()

        int totalPages = pageShoeListings.getTotalPages();
        long totalItems = pageShoeListings.getTotalElements();

        //Get content of the list it will be the size set by the Pageable
        List<ShoeListing> shoeListingList = pageShoeListings.getContent();

        model.addAttribute("pageShoeListings", pageShoeListings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("shoeListingList", shoeListingList);

        //Number of pages total that will be listed at once in the pagination menu.  Keep an even number for current code configuration.
        int paginationMenuTotalVisible = 4;
        model.addAttribute("paginationMenuTotalVisible", paginationMenuTotalVisible);
        model.addAttribute("paginationMenuSplitSidesVisible", paginationMenuTotalVisible / 2);


        // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
        // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
        // number of items on page is set by the maxDisplayed parameter
        List<MessageChain> userEntityMessageChains = messageService.sortMessageChainsByRecentMessage(userEntity);
        List<MessageChain> messageChainList = messageService.shortenMessageChainList(userEntityMessageChains, MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE);

        model.addAttribute("messageChainList", messageChainList);

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

    @PostMapping("/profileEdit")
    public String showProfile (@ModelAttribute("editProfileDTO")EditProfileDTO editProfileDTO, BindingResult bindingResult, @RequestParam(name="imageFiles", required = false) MultipartFile[] files, Model model) {

        if(bindingResult.hasErrors()){
            return "profile/profileEdit";
        }

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        // trim off any spaces before or after
        editProfileDTO.setFirstName(editProfileDTO.getFirstName().trim());
        editProfileDTO.setLastName(editProfileDTO.getLastName().trim());
        editProfileDTO.setDisplayName(editProfileDTO.getDisplayName().trim());


        // checks if displayName has blank space
        if (editProfileDTO.getDisplayName().contains(" ")){
            bindingResult.rejectValue("displayName", "displayName.invalid", "Display name can not have empty space");
            return "profile/profileEdit";
        }

        // checks if displayName is taken and if it is taken sends an error to the view
        if(userEntity.getDisplayName().equals(editProfileDTO.getDisplayName())){

        } else if (userRepository.existsByDisplayNameIgnoreCase(editProfileDTO.getDisplayName())){
            bindingResult.rejectValue("displayName", "displayName.unavailable", "Display name is unavailable");;
            return "profile/profileEdit";
        }


        // checks if displayName is taken and if it is taken sends an error to the view

        if(userEntity.getEmail().equals(editProfileDTO.getEmail())) {

        } else if (userRepository.existsByEmailIgnoreCase(editProfileDTO.getEmail())){
            bindingResult.rejectValue("email", "email.unavailable", "Email is unavailable");;
            return "profile/profileEdit";
        }


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


    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        EditProfileDTO editProfileDTO = new EditProfileDTO();
        model.addAttribute("editProfileDTO", editProfileDTO);

        try {
            storageService.saveUserImage(file);
            message = "Uploaded the image successfully: " + file.getOriginalFilename();
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }

        return "profile/profileEdit";
    }



    @GetMapping("/{displayName}")
    public String displayNameGetMapping(@PathVariable("displayName")String displayName, Model model) {
        return getOneListingsPageUserData(displayName, 1, model);
    }

    @GetMapping("/{displayName}/page/{pageNumber}")
    public String getOneListingsPageUserData(@PathVariable("displayName") String displayName, @PathVariable("pageNumber") int currentPage, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        UserEntity otherUser = userRepository.findByDisplayNameIgnoreCase(displayName);
        model.addAttribute("otherUser", otherUser);

        ReportDTO reportDTO = new ReportDTO();
        model.addAttribute("reportDTO", reportDTO);

        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        model.addAttribute("createMessageDTO", createMessageDTO);

        if(otherUser.getImageInfo() == null){
            model.addAttribute("userImageBoolean" , false);
        } else {
            model.addAttribute( "userImageBoolean", true);
        }

        List<ShoeListing> allShoeListings = otherUser.getShoeListings();

        PagedListHolder<ShoeListing> pagedListHolder = new PagedListHolder<>(allShoeListings);
        pagedListHolder.setPage(currentPage - 1);
        pagedListHolder.setPageSize(6);

        List<ShoeListing> pageSlice = pagedListHolder.getPageList();
        Pageable pageable = PageRequest.of(currentPage - 1, 6);

        Page<ShoeListing> pageShoeListings = new PageImpl<>(pageSlice, pageable, allShoeListings.size());

        // Creating a pageable framework from a list of Listings
        // number of items on page is set by the size parameter of the PageRequest.of()

        int totalPages = pageShoeListings.getTotalPages();
        long totalItems = pageShoeListings.getTotalElements();

        //Get content of the list it will be the size set by the Pageable
        List<ShoeListing> shoeListingList = pageShoeListings.getContent();

        model.addAttribute("pageShoeListings", pageShoeListings);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("shoeListingList", shoeListingList);

        //Number of pages total that will be listed at once in the pagination menu.  Keep an even number for current code configuration.
        int paginationMenuTotalVisible = 4;
        model.addAttribute("paginationMenuTotalVisible", paginationMenuTotalVisible);
        model.addAttribute("paginationMenuSplitSidesVisible", paginationMenuTotalVisible / 2);

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
