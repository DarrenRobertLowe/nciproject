package com.storeii.nciproject;


//unused for now...
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;


//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//mysql and jdbc
import org.springframework.boot.CommandLineRunner;// CommandLineRunner is part of the mysql connection tutorial, not sure if needed
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jdbc.core.JdbcTemplate;





@SpringBootApplication // this is for convenience and adds @Configuration, @EnableAutoConfiguration, @ComponentScan so we don't have to.
@EntityScan
public class NciprojectApplication implements CommandLineRunner { // is part of the mysql connection tutorial, not sure if needed

        // for jdbc
        @Autowired
        JdbcTemplate jdbcTemplate;
        
        
	public static void main(String[] args) {
		SpringApplication.run(NciprojectApplication.class, args);
	}
        
        
        // required for CommandLineRunner
        @Override
        public void run(String... args) throws Exception {
        }
        
}
