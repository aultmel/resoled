package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;


    // Message is connected to UserEntity with @ManyToOne.  Current design is for Message to have one User per message;
    // Message will have one MessageChain.  Messages will be connected to only one UserEntity and organized and gathered together by MessageChain
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "message_chain_id")
    private MessageChain messageChain;


    public Message() {}


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public MessageChain getMessageChain() {
        return messageChain;
    }

    public void setMessageChain(MessageChain messageChain) {
        this.messageChain = messageChain;
    }
}
