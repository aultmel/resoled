package org.launchcode.liftoff.shoefinder.security;



import org.launchcode.liftoff.shoefinder.data.UserRepository;
import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findFirstByUsernameIgnoreCase(username);

        if(userEntity != null) {
            User authUser = new User(
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getRoles().stream().map((role -> new SimpleGrantedAuthority(role.getName())))
                            .collect(Collectors.toList())
            );
            return authUser;

        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }

    }
}
