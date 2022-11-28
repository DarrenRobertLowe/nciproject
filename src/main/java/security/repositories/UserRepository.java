/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package security.repositories;

import security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Main
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByUsername(String username);
}
