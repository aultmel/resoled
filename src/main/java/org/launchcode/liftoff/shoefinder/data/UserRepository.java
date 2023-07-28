package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);
    UserEntity findByDisplayName(String displayName);

    Boolean existsByUsername(String username);

    UserEntity findFirstByUsername(String username);

    Boolean existsByDisplayName(String displayName);

    @Query("SELECT username FROM UserEntity")
    List<String> getUsernames();

    @Query("SELECT displayName FROM UserEntity")
    List<String> getDisplayNames();
}