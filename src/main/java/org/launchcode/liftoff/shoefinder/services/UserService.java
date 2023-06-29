package org.launchcode.liftoff.shoefinder.services;

import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.launchcode.liftoff.shoefinder.models.dto.RegisterDTO;

public interface UserService {
    void saveUser(RegisterDTO registerDTO);

    UserEntity findByUsername(String username);
}
