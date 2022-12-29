/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.website;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.storeii.nciproject.User;
import com.storeii.nciproject.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
/**
 *
 * @author Main
 */


@Controller
public class LoginController {
    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }
}
