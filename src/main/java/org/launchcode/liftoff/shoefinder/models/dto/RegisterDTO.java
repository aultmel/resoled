package org.launchcode.liftoff.shoefinder.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.launchcode.liftoff.shoefinder.constants.RegistrationConstants;
import java.time.LocalDate;


public class RegisterDTO {


    @Size(min = RegistrationConstants.MIN_USERNAME_LENGTH,
            max = RegistrationConstants.MAX_USERNAME_LENGTH,
            message = "Username must be between {min} and {max} characters")
    @NotNull
    private String username;

    @Size(min = RegistrationConstants.MIN_DISPLAY_NAME_LENGTH,
            max = RegistrationConstants.MAX_DISPLAY_NAME_LENGTH,
            message = "Display Name must be between {min} and {max} characters")
    @NotNull
    private String displayName;

    @Size(min = RegistrationConstants.MIN_PASSWORD_LENGTH,
            message = "Password must be more than {min}")
    @NotNull
    private String password;
    private String passwordCheck;


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

    @NotNull(message = "Date of Birth is required")
    private LocalDate birthday;

    @NotEmpty
    @Email
    private String email;

    @Size(min = RegistrationConstants.ZIP_CODE_LENGTH,
            max = RegistrationConstants.ZIP_CODE_LENGTH,
            message = "Zip Code must be {min} numbers")
    @NotEmpty
    private String zipCode;


    public RegisterDTO() {
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }
    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
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

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}
