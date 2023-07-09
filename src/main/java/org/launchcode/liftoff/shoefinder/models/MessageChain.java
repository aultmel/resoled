package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MessageChain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String messageChainSubject;


    // MessageChain is connected to UserEntity with @ManyToMany.  Current design is for MessageChain to have two UserEntity's
    // MessageChain will have many Messages.  Messages will be connected to only one UserEntity and organized and gathered together by MessageChain
    @OneToMany(mappedBy = "messageChain")
    private List<Message> messages;




    public MessageChain() {
    }

    public String getMessageChainSubject() {
        return messageChainSubject;
    }

    public void setMessageChainSubject(String messageChainSubject) {
        this.messageChainSubject = messageChainSubject;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }




}


