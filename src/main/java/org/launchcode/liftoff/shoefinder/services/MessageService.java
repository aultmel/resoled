package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.data.MessageChainRepository;
import org.launchcode.liftoff.shoefinder.data.MessageRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.AddMessageDTO;
import org.launchcode.liftoff.shoefinder.models.dto.CreateMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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


    public Long createMessageChain(CreateMessageDTO createMessageDTO) {

        MessageChain messageChain = new MessageChain();
        messageChain.setMessageChainSubject(createMessageDTO.getMessageSubject());

        UserEntity senderUserEntity = createMessageDTO.getSenderUserEntity();
        UserEntity receiverUserEntity = createMessageDTO.getReceiverUserEntity();

        messageChain.getUserEntityList().add(senderUserEntity);
        messageChain.getUserEntityList().add(receiverUserEntity);

        Message message = new Message();
        message.setUserEntity(senderUserEntity);
        message.setText(createMessageDTO.getText());
        message.setMessageChain(messageChain);
        message.setLocalDateTime(LocalDateTime.now());

        messageChain.setLocalDateTime(message.getLocalDateTime());

        messageChainRepository.save(messageChain);
        messageRepository.save(message);

        return messageChain.getId();
    }


    public void addMessage(AddMessageDTO addMessageDTO) {

        Message message = new Message();
        message.setUserEntity(addMessageDTO.getUserEntity());
        message.setText(addMessageDTO.getText());
        message.setMessageChain(addMessageDTO.getMessageChain());
        message.setLocalDateTime(LocalDateTime.now());

        message.getMessageChain().setLocalDateTime(message.getLocalDateTime());

        messageRepository.save(message);
    }


    public List<MessageChain> sortMessageChainsByRecentMessage(UserEntity userEntity){
        List<MessageChain> userEntityMessageChains = userEntity.getMessageChains();
        Collections.sort(userEntityMessageChains, (messageChain1, messageChain2) -> {
            return messageChain2.getLocalDateTime().compareTo(messageChain1.getLocalDateTime());
        });
        return userEntityMessageChains;
    }


    public List<Message> sortMessagesByRecentMessage(MessageChain messageChain){
        List<Message> messageChainMessages = messageChain.getMessages();

        Collections.sort(messageChainMessages, (message1, message2) -> {
            return message2.getLocalDateTime().compareTo(message1.getLocalDateTime());
        });
        return messageChainMessages;
    }

    public List<MessageChain> shortenMessageChainList(List<MessageChain> messageChains, int maxDisplayed){
        List<MessageChain> messageChainList = new ArrayList<>();

        if(messageChains.size() > maxDisplayed) {
            for (int i = 0; i < maxDisplayed; i++) {
                messageChainList.add(messageChains.get(i));
            }
        } else {
            messageChainList = messageChains;
        }

        return messageChainList;
    }


}
