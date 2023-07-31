package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MessageChain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String messageChainSubject;

    private LocalDateTime localDateTime;


        // UserEntity is connected to MessageChain with @ManyToMany.  Current design is UserEntity to have many MessageChain, but MessageChain only have two UserEntity.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "message_chain_user",
            joinColumns = @JoinColumn(name = "message_chain_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> userEntityList = new ArrayList<>();


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


    public List<UserEntity> getUserEntityList() {
        return userEntityList;
    }
    public void setUserEntityList(List<UserEntity> userEntityList) {
        userEntityList = userEntityList;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}


