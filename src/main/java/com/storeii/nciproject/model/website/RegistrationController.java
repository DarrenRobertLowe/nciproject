/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.County.County;
import com.storeii.nciproject.model.County.CountyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Main
 */

@Controller
public class RegistrationController {
    
    @Autowired
    CountyRepository countyRepository;
    
    @GetMapping("/register")
    public String getCountiesForRegistration(Model model) {
        //List<Location> locations = locationRepo.findAll();
        //model.addAttribute("locations", locations);
        
        List<County> counties = countyRepository.findAll();
        model.addAttribute("counties", counties);
        
        return "register";
    }
}
