package com.storeii.nciproject.model.website;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author Darren Robert Lowe
 */


@Controller
public class LoginController {
    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }
}
