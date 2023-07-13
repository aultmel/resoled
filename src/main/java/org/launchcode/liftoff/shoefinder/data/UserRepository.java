package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);

    UserEntity findFirstByUsername(String username);
}
