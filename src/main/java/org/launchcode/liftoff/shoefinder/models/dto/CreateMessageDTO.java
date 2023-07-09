package org.launchcode.liftoff.shoefinder.models.dto;

import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.security.core.userdetails.User;

public class CreateMessageDTO {



private UserEntity senderUserEntity;

private UserEntity receiverUserEntity;

private String receiverUsername;

private String messageSubject;

private String message;


    public CreateMessageDTO() {}




    public UserEntity getSenderUserEntity() {
        return senderUserEntity;
    }

    public void setSenderUserEntity(UserEntity senderUserEntity) {
        this.senderUserEntity = senderUserEntity;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getReceiverUserEntity() {
        return receiverUserEntity;
    }

    public void setReceiverUserEntity(UserEntity receiverUserEntity) {
        this.receiverUserEntity = receiverUserEntity;
    }
}
