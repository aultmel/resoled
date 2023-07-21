package org.launchcode.liftoff.shoefinder.services;


import org.launchcode.liftoff.shoefinder.data.RoleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserService() {
    }

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public int updateAge(UserEntity userEntity) {
        return 0;
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
        List<String> usernames = userRepository.getUsernames();
        // Create a list of suggestions.
        List<String> suggestions = new ArrayList<>();
        // Iterate over the usernames.
        for (String username : usernames) {
            //Checking for size of suggestion list.  SETS SIZE OF SUGGESTION LIST
            if (suggestions.size() == 6) {
                return suggestions;
            }
            // Check if the username contains the substring then adds to suggestions
            if (username.contains(substring)) {
                suggestions.add(username);
            }
        }
        // Return the suggestions list.
        return suggestions;
    }

}
