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
import org.springframework.jdbc.core.JdbcTemplate;





@SpringBootApplication
public class NciprojectApplication implements CommandLineRunner { //  { //is part of the mysql connection tutorial, not sure if needed

        // for jdbc
        @Autowired
        JdbcTemplate jdbcTemplate;
        
        
	public static void main(String[] args) {
		SpringApplication.run(NciprojectApplication.class, args);
	}
        
        /*
        @GetMapping("/hello")
            public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
            return String.format("Hello %s!", name);
        }
         */
        
        
        // required for CommandLineRunner
        @Override
        public void run(String... args) throws Exception {
            String sql = "INSERT INTO Customers (firstName, lastName, userPass) VALUES (?, ?, ?)";
            int result = jdbcTemplate.update(sql, "Brian", "May", "an0th3rPa$$w0rd");   // returns 0 or 1 depending on success or not.
            
            if (result > 0) {
                System.out.println("A new row has been inserted.");
            }
        }
}
