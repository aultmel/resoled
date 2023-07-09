package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // UserEntity will have many Messages.  Messages will be connected to only one UserEntity and organized and gathered together by MessageChain
    @OneToMany(mappedBy = "user")
    private List<Message> messages;


    // UserEntity is connected to MessageChain with @ManyToMany.  Current design is UserEntity to have many MessageChain, but MessageChain only have two UserEntity.
    @ManyToMany
    @JoinTable(
            name = "message_chain_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "message_chain_id", referencedColumnName = "id"))
    private List<MessageChain> messageChains = new ArrayList<>();


    // UserEntity can have several Role.  UserEntity will receive role of "USER" by default on creation.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();


    //    @NotBlank(message = "name is required")
    //    @Size(message = "must be 3 to 20 characters")
    private String username;

    //    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthday;

    private int age;

//    private Location location;

//    private Image image;

//    @OneToMany
//    private List<ShoeListing> shoeListings;





    public UserEntity() {
    }

    public UserEntity(String username, String password, List<Role> roles, String firstName, String lastName, LocalDate birthday) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<MessageChain> getMessageChains() {
        return messageChains;
    }

    public void setMessageChains(List<MessageChain> messageChains) {
        this.messageChains = messageChains;
    }
}
