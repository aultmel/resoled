package org.launchcode.liftoff.shoefinder.models.dto;


import jakarta.validation.constraints.NotEmpty;

public class RegisterDTO {

    private int id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;


    public RegisterDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
