package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;
import org.launchcode.liftoff.shoefinder.data.MessageRepository;
import org.launchcode.liftoff.shoefinder.data.RoleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageService {


    private MessageRepository messageRepository;

    private MessageChainRepository messageChainRepository;

    private UserRepository userRepository;


    public MessageService() {
    }

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository, MessageChainRepository messageChainRepository) {
        this.userRepository = userRepository;
        this.messageChainRepository = messageChainRepository;
        this.messageRepository = messageRepository;
    }


    public void createMessageChain(CreateMessageDTO createMessageDTO) {

        MessageChain messageChain = new MessageChain();
        messageChain.setMessageChainSubject(createMessageDTO.getMessageSubject());

        UserEntity senderUserEntity = createMessageDTO.getSenderUserEntity();
        UserEntity receiverUserEntity = createMessageDTO.getReceiverUserEntity();

        messageChain.getUserEntityList().add(senderUserEntity);
        messageChain.getUserEntityList().add(receiverUserEntity);
//
//        senderUserEntity.getMessageChains().add(messageChain);
//        receiverUserEntity.getMessageChains().add(messageChain);
//
        Message message = new Message();
        message.setUser(senderUserEntity);
        message.setText(createMessageDTO.getMessage());
        message.setMessageChain(messageChain);

//        userRepository.save(senderUserEntity);
//        userRepository.save(receiverUserEntity);
        messageChainRepository.save(messageChain);
        messageRepository.save(message);
    }




}
