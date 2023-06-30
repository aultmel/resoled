package org.launchcode.liftoff.shoefinder.services.implement;

import org.launchcode.liftoff.shoefinder.data.RoleRepository;
import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.Role;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;
import org.launchcode.liftoff.shoefinder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;


// todo COMMENTED OUT JUST FOR BUILDING restore this for security to function
//import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;


//todo COMMENTED OUT JUST FOR BUILDING restore this for security to function
//        private PasswordEncoder passwordEncoder;


    public UserServiceImpl() {
    }

//todo COMMENTED OUT JUST FOR BUILDING restore this for security to function

//    @Autowired
//    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }


    @Override
    public void saveUser(RegisterDTO registerDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDTO.getUsername());
//todo COMMENTED OUT JUST FOR BUILDING restore this for security to function
    //  userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));
        userRepository.save(userEntity);
    }


    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
