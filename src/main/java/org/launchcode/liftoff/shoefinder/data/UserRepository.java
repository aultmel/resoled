package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findById(Long id);

    UserEntity findByUsernameIgnoreCase(String username);

    UserEntity findByDisplayNameIgnoreCase(String displayName);

    Boolean existsByUsernameIgnoreCase(String username);

    UserEntity findFirstByUsernameIgnoreCase(String username);

    Boolean existsByDisplayNameIgnoreCase(String displayName);

    @Query("SELECT username FROM UserEntity")
    List<String> getUsernames();

    @Query("SELECT displayName FROM UserEntity")
    List<String> getDisplayNames();

    Boolean existsByEmailIgnoreCase(String email);

}