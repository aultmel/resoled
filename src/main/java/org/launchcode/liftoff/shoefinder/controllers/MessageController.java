package org.launchcode.liftoff.shoefinder.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;
import org.launchcode.liftoff.shoefinder.data.MessageRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.ShoeListing;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.AddMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.ReportDTO;
import org.launchcode.liftoff.shoefinder.security.SecurityUtility;
import org.launchcode.liftoff.shoefinder.services.MessageService;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static org.launchcode.liftoff.shoefinder.constants.MessageConstants.MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE;

@Controller
@RequestMapping("/message")
public class MessageController {

    private UserRepository userRepository;
    private UserService userService;
    private MessageService messageService;
    private MessageChainRepository messageChainRepository;
    private MessageRepository messageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MessageController(UserRepository userRepository, UserService userService, MessageService messageService, MessageChainRepository messageChainRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.messageChainRepository = messageChainRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String messageRootGetMapping(Model model) {
        return getOneMessageChainPage(model, 1);
    }

    @GetMapping("/messages")
    public String messagesGetMapping(Model model) {  return getOneMessageChainPage(model, 1); }

    @GetMapping("messages/page/{pageNumber}")
    public String getOneMessageChainPage(Model model, @PathVariable("pageNumber") int currentPage){

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        List<MessageChain> userEntityMessageChains = messageService.sortMessageChainsByRecentMessage(userEntity);

        PagedListHolder<MessageChain> pagedListHolder = new PagedListHolder<>(userEntityMessageChains);
        pagedListHolder.setPage(currentPage - 1);
        pagedListHolder.setPageSize(4);

        List<MessageChain> pageSlice = pagedListHolder.getPageList();
        Pageable pageableSortedByLocalDateTime = PageRequest.of( currentPage - 1, 4);

        Page<MessageChain> pageMessageChains = new PageImpl<>(pageSlice, pageableSortedByLocalDateTime, userEntityMessageChains.size() );

        // Creating a pageable framework from a list of UserEntity MessageChain
        // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
        // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
        // number of items on page is set by the size parameter of the PageRequest.of()

        int totalPages = pageMessageChains.getTotalPages();
        long totalItems = pageMessageChains.getTotalElements();

        //Get content of the list it will be the size set by the Pageable
        List<MessageChain> messageChainList = pageMessageChains.getContent();

        model.addAttribute("pageMessageChains", pageMessageChains);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("messageChainList", messageChainList);

        //Number of pages total that will be listed at once in the pagination menu.  Keep an even number for current code configuration.
        int paginationMenuTotalVisible = 4;
        model.addAttribute("paginationMenuTotalVisible", paginationMenuTotalVisible);
        model.addAttribute("paginationMenuSplitSidesVisible", paginationMenuTotalVisible / 2);

        return "message/messages";
    }



    @GetMapping("/create")
    public String createMessageGetMapping(Model model) {
        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        model.addAttribute("createMessageDTO", createMessageDTO);

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        // api url for suggestions for the username
        model.addAttribute("displayNameSuggestionsUrl", "http://localhost:8080/api/userDisplayNameSuggestion");
        // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
        // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
        // number of items on page is set by the maxDisplayed parameter
        List<MessageChain> userEntityMessageChains = messageService.sortMessageChainsByRecentMessage(userEntity);
        List<MessageChain> messageChainList = messageService.shortenMessageChainList(userEntityMessageChains, MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE);

        model.addAttribute("messageChainList", messageChainList);

        return "message/create";
    }


    @PostMapping("/create")
    public String createMessagePostMapping(@Valid @ModelAttribute("createMessageDTO") CreateMessageDTO createMessageDTO, Errors errors, BindingResult result,
                                           @RequestParam(required = false) String receiver,  Model model) {

        if(receiver != null) {
            createMessageDTO.setReceiverDisplayName(receiver);
        }

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);

        model.addAttribute("createMessageDTO", createMessageDTO);
        model.addAttribute("userEntity", userEntity);
        // api url for suggestions for the username
        model.addAttribute("displayNameSuggestionsUrl", "http://localhost:8080/api/userDisplayNameSuggestion");
        // Sorting so that list of MessageChains userEntityMessageChains is in order of the MessageChain with the
        // newest message is first on the list and the MessageChain with the latest message is at the end of the list.
        // number of items on page is set by the maxDisplayed parameter
        List<MessageChain> userEntityMessageChains = messageService.sortMessageChainsByRecentMessage(userEntity);
        List<MessageChain> messageChainList = messageService.shortenMessageChainList(userEntityMessageChains, MAX_CONVERSATIONS_DISPLAYED_ON_CREATE_MESSAGE);
        model.addAttribute("messageChainList", messageChainList);


//         checks if receiver username exists and if it does not, sends an error to the view
        if (!userRepository.existsByDisplayNameIgnoreCase(createMessageDTO.getReceiverDisplayName())) {
            errors.rejectValue("receiverDisplayName", "displayName.notValid", "That user does not exist.");
            return "message/create";
        }
        if (createMessageDTO.getMessageSubject().isEmpty()) {
            errors.rejectValue("messageSubject", "messageSubject.notValid", "To initiate a message please add a subject.");
            return "message/create";
        }
        if (createMessageDTO.getText().isEmpty()) {
            errors.rejectValue("text", "text.notValid", "Please type a message.");
            return "message/create";
        }

        createMessageDTO.setReceiverUserEntity(userRepository.findByDisplayNameIgnoreCase(createMessageDTO.getReceiverDisplayName()));
        createMessageDTO.setSenderUserEntity(userEntity);

        //using createMessageDTO to create the MessageChain and first Message of the MessageChain
        // this returns the Long id of the MessageChain that is newly created.
        Long messageChainId = messageService.createMessageChain(createMessageDTO);

        // ultimately return to the message chain on the screen using the messageChainId
        return "redirect:../message/message?messageChainId=" + messageChainId;
    }

    @GetMapping("/message")
    public String messageGetMapping(@RequestParam(required = false) String messageChainId, Model model) {


        if (messageChainId == null) {
            // The messageChainId is not present
            return "redirect:../message/messages";
        }
        try {
            Long messageChainIdLong = (Long.parseLong(messageChainId));
        } catch (NumberFormatException e) {
            // The messageChainId is not a valid Long
            return "redirect:../message/messages";
        }


        Long messageChainIdLong = Long.parseLong(messageChainId);
        if (messageChainIdLong <= 0) {
            // The messageChainId is 0 or lower and not a valid id
            return "redirect:../message/messages";
        }

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);


        List<MessageChain> userMessageChains = userEntity.getMessageChains();

        Optional<MessageChain> requestMessageChain = messageChainRepository.findById(messageChainIdLong);

        MessageChain requestedMessageChain = requestMessageChain.orElse(null);

        if (requestedMessageChain == null) {
            // The messageChainId is not in the database
            return "redirect:../message/messages";
        }

        // Checks through UserEntity's MessageChains to see if it is associated with the UserEntity
        // adds it to model if it finds a match
            for (MessageChain messageChain : userMessageChains) {
                if (messageChain.equals(requestedMessageChain)) {

                    AddMessageDTO addMessageDTO = new AddMessageDTO();
                    addMessageDTO.setMessageChainId(messageChainIdLong);
                    addMessageDTO.setUserEntity(userEntity);
                    addMessageDTO.setMessageChain(messageChain);

//                   todo  might not need this sortMessagesByRecentMessage
//                   List<Message> sortedMessages = messageService.sortMessagesByRecentMessage(messageChain);

                    model.addAttribute("addMessageDTO", addMessageDTO);
                    model.addAttribute("messageChain", messageChain);
//                    model.addAttribute("sortedMessages", sortedMessages);
                    model.addAttribute("userEntity", userEntity);
                    return "message/message";
                }
            }

            return "redirect:../message/messages";

    }

    @PostMapping("/message")
    public String messagePostMapping(@Valid @ModelAttribute("addMessageDTO") AddMessageDTO addMessageDTO,
                                     @Valid @ModelAttribute("messageChain") MessageChain messageChain,
                                     Errors errors, BindingResult result, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        Optional<MessageChain> requestMessageChain = messageChainRepository.findById(addMessageDTO.getMessageChainId());

        MessageChain requestedMessageChain =  requestMessageChain.orElse(null);

        if (requestedMessageChain == null) {
            // check if the MessageChain is in the repository
            return "redirect:../message/messages";
        }

       List<MessageChain> userEntityMessageChainsList = userEntity.getMessageChains();
        // Check to make sure UserEntity has access to the MessageChain
        for (MessageChain aMessageChain : userEntityMessageChainsList) {
            if (aMessageChain.equals(requestedMessageChain)) {

                // user has access to the messageChain
                addMessageDTO.setMessageChain(requestedMessageChain);
                addMessageDTO.setUserEntity(userEntity);

                // use the AddMessageDTO to create a new Message and save it with the needed information from AddMessageDTO
                messageService.addMessage(addMessageDTO);

                // return to the message chain on the screen.
                return "redirect:../message/message?messageChainId=" + requestedMessageChain.getId();

            }
        }

        //user does not have access to that messageChain
        return "redirect:../message/messages";

    }


    @GetMapping("/userMessage/{displayName}")
    public String getOneListingsPageUserData(@PathVariable("displayName") String displayName, Model model) {

        String username = SecurityUtility.getSessionUser();
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
        model.addAttribute("userEntity", userEntity);

        UserEntity otherUser = userRepository.findByDisplayNameIgnoreCase(displayName);
        model.addAttribute("otherUser", otherUser);

        CreateMessageDTO createMessageDTO = new CreateMessageDTO();
        model.addAttribute("createMessageDTO", createMessageDTO);



        return "message/userMessage";
    }



}
