package org.launchcode.liftoff.shoefinder.controllers;


import jakarta.validation.Valid;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;

import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("communication")
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String messagesGetMapping(Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        return "communication/messages";
    }


    @GetMapping("/createmessage")
    public String createMessageGetMapping(Model model) {


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        createMessageDTO.setSenderUserEntity(userEntity);
        model.addAttribute("createMessageDTO", createMessageDTO);

        return "communication/createmessage";
    }

    @PostMapping("/createmessage")
    public String register(@Valid @ModelAttribute("createMessageDTO") CreateMessageDTO createMessageDTO, Errors errors, Model model) {


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
       ;

//         checks if receiver username exists and if it does not, sends an error to the view
//        if(!userRepository.existsByUsername(createMessageDTO.getReceiverUsername())){
////            model.addAttribute("registerDTO", registerDTO);
//            errors.rejectValue("receiverUsername", "username.notValid", "Username does not exist");;
//            return "communication/createmessage";
//        }

//        createMessageDTO.setReceiverUserEntity(userRepository.findByUsername(createMessageDTO.getReceiverUsername()));


        messageService.createMessageChain(createMessageDTO, userEntity);

        // ultimately return to the message chain on the screen.
        return "redirect:../communication/messages";

    }



}

