package org.launchcode.liftoff.shoefinder.services;


import org.launchcode.liftoff.shoefinder.constants.MessageConstants;
import org.launchcode.liftoff.shoefinder.data.ReportRepository;
import org.launchcode.liftoff.shoefinder.data.RoleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Report;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// User Service contains methods to related UserEntity
// saveUser is for Registering/Creating a new UserEntity from RegisterDTO

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private ReportRepository reportRepository;

    public UserService() {
    }

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void updateAge(UserEntity userEntity) {
        LocalDate birthDate = userEntity.getBirthday();
        LocalDate currentDate = LocalDate.now();
        userEntity.setAge(Period.between(currentDate, birthDate).getYears());
        userRepository.save(userEntity);
    }


    public boolean checkAge(RegisterDTO registerDTO) {
        LocalDate birthDate = registerDTO.getBirthday();
        LocalDate currentDate = LocalDate.now();
        int minAge = 13;
        int age = Period.between(currentDate, birthDate).getYears();
        if (age < minAge) {
            return false;
        }
        return true;
    }


    public void saveUser(RegisterDTO registerDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userEntity.setFirstName(registerDTO.getFirstName());
        userEntity.setLastName(registerDTO.getLastName());
        userEntity.setBirthday(registerDTO.getBirthday());
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));
        userEntity.setDisplayName(registerDTO.getDisplayName());
        userEntity.setMessages(new ArrayList<>());
        userRepository.save(userEntity);
    }

    public List<String> getSuggestionsString(String substring) {
        // Get the list of usernames.
        List<String> displayNames = userRepository.getDisplayNames();
        // Create a list of suggestions.
        List<String> suggestions = new ArrayList<>();
        // Iterate over the usernames.
        for (String displayName : displayNames) {
            //Checking for size of suggestion list.  SETS SIZE OF SUGGESTION LIST
            if (suggestions.size() == MessageConstants.MAX_USER_FORM_SUGGESTIONS) {
                return suggestions;
            }
            // Check if the username contains the substring then adds to suggestions
            if (displayName.contains(substring)) {
                suggestions.add(displayName);
            }
        }
        // Return the suggestions list.
        return suggestions;
    }

    public void banUser(UserEntity user){
        ArrayList<Role> emptyList = new ArrayList<>();
        user.setRoles(emptyList);

    }


}
