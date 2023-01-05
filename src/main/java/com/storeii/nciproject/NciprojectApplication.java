package com.storeii.nciproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//mysql and jdbc
import org.springframework.boot.autoconfigure.domain.EntityScan;


/**
 *
 * @author Darren Robert Lowe
 */
@SpringBootApplication // this is for convenience and adds @Configuration, @EnableAutoConfiguration, @ComponentScan so we don't have to.
@EntityScan
public class NciprojectApplication{

	public static void main(String[] args) {
		SpringApplication.run(NciprojectApplication.class, args);
	}
}
