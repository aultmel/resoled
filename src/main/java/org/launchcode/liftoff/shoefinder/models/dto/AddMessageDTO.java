package org.launchcode.liftoff.shoefinder.models.dto;

import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.launchcode.liftoff.shoefinder.models.UserEntity;

public class AddMessageDTO {


    private UserEntity userEntity;
    private String text;

    private Long messageChainId;

    private MessageChain messageChain;

    public AddMessageDTO() {
    }


    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Long getMessageChainId() {
        return messageChainId;
    }

    public void setMessageChainId(Long messageChainId) {
        this.messageChainId = messageChainId;
    }

    public MessageChain getMessageChain() {
        return messageChain;
    }

    public void setMessageChain(MessageChain messageChain) {
        this.messageChain = messageChain;
    }
}
