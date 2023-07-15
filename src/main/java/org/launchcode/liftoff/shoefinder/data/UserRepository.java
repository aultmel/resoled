package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);

    UserEntity findFirstByUsername(String username);


    //TEST

    @Query("SELECT username FROM UserEntity")
    List<String> getUsernames();

//    @Query(value="SELECT * from UserEntity u where u.username is like %:keyword%", nativeQuery=true)
//    List<UserEntity> findByKeyword(@Param("keyword") String keyword);

    //TEST

}
