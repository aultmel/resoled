package org.launchcode.liftoff.shoefinder.models.dto;

public class EditProfileDTO {

    private String displayName;
    private String firstName;
    private String lastName;
    private String email;

    public String getDisplayName(){return displayName;}
    public void setDisplayName(String displayName){this.displayName=displayName;}

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
