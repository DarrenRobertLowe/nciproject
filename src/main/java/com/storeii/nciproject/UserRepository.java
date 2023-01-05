package com.storeii.nciproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darren Robert Lowe
 */

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
    User getByUserName(String userName);
}
