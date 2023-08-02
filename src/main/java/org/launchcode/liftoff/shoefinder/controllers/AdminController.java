package org.launchcode.liftoff.shoefinder.controllers;

import org.launchcode.liftoff.shoefinder.data.ReportRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Report;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.ReportDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.launchcode.liftoff.shoefinder.services.UserService;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("")
    public String showAdmin (Model model) {
        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        ArrayList<Report> allReports = new ArrayList<>();
        allReports.addAll(reportRepository.findAll());
        model.addAttribute("allReports", allReports);

        return "admin";


    }

    @PostMapping("")
    public String reloadAdmin (@RequestParam("reportedDisplayName") String reportedDisplayName, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        ArrayList<Report> allReports = new ArrayList<>();
        allReports.addAll(reportRepository.findAll());
        model.addAttribute("allReports", allReports);

        UserEntity reportedUser = userRepository.findByDisplayName(reportedDisplayName);


        UserService userService = new UserService();
        userService.banUser(reportedUser);
        userRepository.save(reportedUser);


        return "/admin";
    }

//    @RequestMapping(value="/admin", method = RequestMethod.POST)
//    public String banUser (@RequestParam UserEntity reportedUser) {
//        ArrayList<Role> emptyList = new ArrayList<>();
//        reportedUser.setRoles(emptyList);
//        userRepository.save(user);
//        return "admin";
//    }
}
