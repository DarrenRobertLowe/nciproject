/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model;

import com.storeii.nciproject.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Main
 */
@Entity // This tells Hibernate to make a table out of this class
public class Driver {
    /*
    id smallint UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    userName varchar(50) NOT NULL,
    userPass varchar(512) NOT NULL
    */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String surname;
    private String userName;
    private String userPass;
    
    /*
    // FOREIGN KEYS
    @OneToMany(mappedBy = "id")
    //@JoinColumn(name = "order_ID", referencedColumnName = "id")
    private List<Order> orders = new ArrayList();
   
    
    @OneToOne(cascade=CascadeType.ALL)//one-to-one
    @JoinColumn(name="id")
    private User user;
     */
    
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
    
    /*
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    */
}
