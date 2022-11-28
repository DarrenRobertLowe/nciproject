/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Main
 */

@Controller
public class UserController {
    
    @RequestMapping("/login")
    public String loginPage() { return "login"; }
    
    @GetMapping("/register")
    public String register() { return "register"; }
    
    @RequestMapping("/index")
    public String homePage() { return "index"; }
}
