package org.launchcode.liftoff.shoefinder.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.launchcode.liftoff.shoefinder.constants.RegistrationConstants;

public class EditProfileDTO {

    @Size(min = RegistrationConstants.MIN_DISPLAY_NAME_LENGTH,
            max = RegistrationConstants.MAX_DISPLAY_NAME_LENGTH,
            message = "Display Name must be between {min} and {max} characters")
    @NotNull
    private String displayName;

    @Size(min = RegistrationConstants.MIN_FIRST_NAME_LENGTH,
            max = RegistrationConstants.MAX_FIRST_NAME_LENGTH,
            message = "First Name must be between {min} and {max} characters")
    @NotNull
    private String firstName;

    @Size(min = RegistrationConstants.MIN_LAST_NAME_LENGTH,
            max = RegistrationConstants.MAX_LAST_NAME_LENGTH,
            message = "Last Name must be between {min} and {max} characters")
    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
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
