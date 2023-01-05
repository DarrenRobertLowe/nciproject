package com.storeii.nciproject.model.website;

import com.storeii.nciproject.model.County.County;
import com.storeii.nciproject.model.County.CountyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Darren Robert Lowe
 */

@Controller
public class RegistrationController {
    
    @Autowired
    CountyRepository countyRepository;
    
    @GetMapping("/register")
    public String getCountiesForRegistration(Model model) {
        
        List<County> counties = countyRepository.findAll();
        model.addAttribute("counties", counties);
        
        return "register";
    }
}
