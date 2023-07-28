package org.launchcode.liftoff.shoefinder.models.dto;

import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.security.core.userdetails.User;

public class CreateMessageDTO {



private UserEntity senderUserEntity;

private UserEntity receiverUserEntity;

private String receiverDisplayName;

private String messageSubject;

private String text;


    public CreateMessageDTO() {}




    public UserEntity getSenderUserEntity() {
        return senderUserEntity;
    }
    public void setSenderUserEntity(UserEntity senderUserEntity) {
        this.senderUserEntity = senderUserEntity;
    }

    public String getReceiverDisplayName() { return receiverDisplayName; }
    public void setReceiverDisplayName(String receiverDisplayName) { this.receiverDisplayName = receiverDisplayName; }

    public String getMessageSubject() {
        return messageSubject;
    }
    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getReceiverUserEntity() {
        return receiverUserEntity;
    }
    public void setReceiverUserEntity(UserEntity receiverUserEntity) {
        this.receiverUserEntity = receiverUserEntity;
    }
}
