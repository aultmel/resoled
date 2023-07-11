package org.launchcode.liftoff.shoefinder.controllers;


import jakarta.validation.Valid;
import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;
import org.launchcode.liftoff.shoefinder.data.MessageRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;

import org.launchcode.liftoff.shoefinder.models.dto.AddMessageDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageChainRepository messageChainRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String messageRootGetMapping(Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);

        return "message/messages";
    }




        @GetMapping("/messages")
        public String messagesGetMapping(Model model) {

            String username = SecurityUtility.getSessionUser();
            UserEntity userEntity = userRepository.findByUsername(username);
            model.addAttribute("userEntity", userEntity);

            List<MessageChain> userEntityMessageChains = userEntity.getMessageChains();

            // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
            // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
            Collections.sort(userEntityMessageChains, (messageChain1, messageChain2) -> {
                Message latestMessage1 = messageChain1.getMessages().get(messageChain1.getMessages().size() - 1);
                Message latestMessage2 = messageChain2.getMessages().get(messageChain2.getMessages().size() - 1);
                return latestMessage2.getLocalDateTime().compareTo(latestMessage1.getLocalDateTime());
            });

            model.addAttribute("orderedUserMessageChain", userEntityMessageChains);


            return "message/messages";
        }


    @GetMapping("/create")
    public String createMessageGetMapping(Model model) {


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);
        model.addAttribute("userEntity", userEntity);


        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        model.addAttribute("createMessageDTO", createMessageDTO);

        return "message/create";
    }


    @PostMapping("/create")
    public String createMessagePostMapping(@Valid @ModelAttribute("createMessageDTO") CreateMessageDTO createMessageDTO, Errors errors, BindingResult result, Model model) {


        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);

//         checks if receiver username exists and if it does not, sends an error to the view
        if (!userRepository.existsByUsername(createMessageDTO.getReceiverUsername())) {
//            model.addAttribute("createMessageDTO", createMessageDTO);
            errors.rejectValue("receiverUsername", "username.notValid", "Username does not exist");

            return "message/create";
        }

        createMessageDTO.setReceiverUserEntity(userRepository.findByUsername(createMessageDTO.getReceiverUsername()));
        createMessageDTO.setSenderUserEntity(userEntity);

        //using createMessageDTO to create the MessageChain and first Message of the MessageChain
        // this returns the Long id of the MessageChain that is newly created.
        Long messageChainId = messageService.createMessageChain(createMessageDTO);

        // ultimately return to the message chain on the screen using the messageChainId
        return "redirect:../message/message?messageChainId=" + messageChainId;

    }


    @GetMapping("/message")
    public String messageGetMapping(@RequestParam Long messageChainId, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);


        List<MessageChain> userMessageChains = userEntity.getMessageChains();

        Optional<MessageChain> requestMessageChain = messageChainRepository.findById(messageChainId);

        if (!requestMessageChain.isPresent()) {
            // The message chain was not
        }

        MessageChain requestedMessageChain =  requestMessageChain.get();

        for (MessageChain messageChain : userMessageChains) {
            if(messageChain.equals(requestedMessageChain)) {


                AddMessageDTO addMessageDTO = new AddMessageDTO();
                addMessageDTO.setMessageChainId(messageChainId);
                addMessageDTO.setUserEntity(userEntity);
                addMessageDTO.setMessageChain(messageChain);

                model.addAttribute("addMessageDTO", addMessageDTO);

                model.addAttribute("messageChain", messageChain);
                model.addAttribute("userEntity", userEntity);
                return "message/message";

            }
        }

        return "message/messages";
    }

    @PostMapping("/message")
    public String messagePostMapping(@Valid @ModelAttribute("addMessageDTO") AddMessageDTO addMessageDTO,
                                     @Valid @ModelAttribute("messageChain") MessageChain messageChain,
                                     Errors errors, BindingResult result, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsername(username);

        Optional<MessageChain> requestMessageChain = messageChainRepository.findById(addMessageDTO.getMessageChainId());

        if (!requestMessageChain.isPresent()) {
            // The message chain was not
            //todo need to add error handling if the MassageChain does not exist.

        }

        //check if the userEntity should have access to the MessageChain
       List<MessageChain> userEntityMessageChainsList = userEntity.getMessageChains();
        Boolean userHasMessageChain = false;
        for (MessageChain aMessageChain : userEntityMessageChainsList) {
            if (aMessageChain.equals(requestMessageChain)) {
                userHasMessageChain = true;
            }
        }

        if (!userHasMessageChain) {
            //user does not have access to that messageChain
            //todo add error handling for if user should not have access to messageChain.
        }

        MessageChain requestedMessageChain =  requestMessageChain.get();
        addMessageDTO.setMessageChain(requestedMessageChain);
        addMessageDTO.setUserEntity(userEntity);

        // use the AddMessageDTO to create a new Message and save it with the needed information from AddMessageDTO
        messageService.addMessage(addMessageDTO);

        // ultimately return to the message chain on the screen.
        return "redirect:../message/message?messageChainId=" + requestedMessageChain.getId();

    }



}