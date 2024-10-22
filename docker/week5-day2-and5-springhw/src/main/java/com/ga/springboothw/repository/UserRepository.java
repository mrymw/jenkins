package com.ga.springboothw.repository;

import com.ga.springboothw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //to register - I want to know if the user email is already in the database
    Boolean existsByEmailAddress(String userEmailAddress);
    //to login - I want to return the user object based on the email address they have given me
    User findUserByEmailAddress(String userEmailAddress);
}
