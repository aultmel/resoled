package org.launchcode.liftoff.shoefinder.models;

import jakarta.persistence.*;
import org.launchcode.liftoff.shoefinder.models.dto.SearchListingsDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // UserEntity will have many Messages.  Messages will be connected to only one UserEntity and organized and gathered together by MessageChain
    @OneToMany(mappedBy = "userEntity")
    private List<Message> messages;

    @ManyToMany(mappedBy = "userEntityList", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MessageChain> messageChains;


    // UserEntity can have several Role.  UserEntity will receive role of "USER" by default on creation.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    private String username;
    private String displayName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private int age;

    @OneToOne
    private Location location;

    @OneToMany(mappedBy = "userEntity")
    private List<ShoeListing> shoeListings;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageInfo imageInfo;

    @OneToOne(cascade = CascadeType.ALL)
    //use this to keep search active for pagination?
    private CurrentSearch currentSearch;

    public UserEntity() {
    }


    public long getId() {
        return id;
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }

    public List<ShoeListing> getShoeListings() { return shoeListings; }

    public void setShoeListings(List<ShoeListing> shoeListings) { this.shoeListings = shoeListings; }

    public CurrentSearch getCurrentSearch() {
        return currentSearch;
    }

    public void setCurrentSearch(CurrentSearch currentSearch) {
        this.currentSearch = currentSearch;
    }

    public void removeListing(ShoeListing listing){
        shoeListings.remove(listing);
    }
}
