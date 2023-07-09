package org.launchcode.liftoff.shoefinder.controllers;


import jakarta.validation.Valid;
import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;

import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/communication")
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageChainRepository messageChainRepository;

    @GetMapping("/messages")
    public String messagesGetMapping(Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        return "communication/messages";
    }


    @GetMapping("/create")
    public String createMessageGetMapping(Model model) {


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);


        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        model.addAttribute("createMessageDTO", createMessageDTO);

        return "communication/create";
    }



        @PostMapping("/create")
        public String createMessagePostMapping(@Valid @ModelAttribute("createMessageDTO") CreateMessageDTO createMessageDTO, Errors errors, BindingResult result, Model model) {


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);


//         checks if receiver username exists and if it does not, sends an error to the view
        if(!userRepository.existsByUsername(createMessageDTO.getReceiverUsername())){
//            model.addAttribute("createMessageDTO", createMessageDTO);
            errors.rejectValue("receiverUsername", "username.notValid", "Username does not exist");

            return "communication/create";
        }

        createMessageDTO.setReceiverUserEntity(userRepository.findByUsername(createMessageDTO.getReceiverUsername()));
        createMessageDTO.setSenderUserEntity(userEntity);
        messageService.createMessageChain(createMessageDTO);

        // ultimately return to the message chain on the screen.
        return "redirect:../communication/messages";

    }



}

