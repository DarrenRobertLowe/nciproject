/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.storeii.nciproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import security.services.UserDetailsService;



/**
 *
 * @author Main
 */


//@Configuration
@ComponentScan({"com.delivery.request"})
@EnableWebSecurity
public class ApplicationSecurityConfig {
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/login.html");
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
        */
        
        /* .antMatchers must be ordered from most
         *  specific to least specific (more general)
         */
        http.csrf()
            .disable()
        .authorizeRequests()
                /*
            .antMatchers("/cart").hasAuthority("USER")
            .antMatchers("/fulfilments").hasAuthority("SUPPLIER")
            .antMatchers("/deliveries").hasAuthority("DRIVER")
            .antMatchers("/addUser","/js/*","/css/*","/assets/**","/*").permitAll()
            .antMatchers("/**").hasAuthority("ADMIN")
            .anyRequest().denyAll()*/
            .antMatchers("/**").permitAll()
            .and()
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/processLogin")
            .defaultSuccessUrl("/index", true)
            .failureUrl("/login.html?error=true")
            //.failureHandler(authenticationFailureHandler())
            .and()
        .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/loggedout.html")
            .permitAll();
            return http.build();
    }
    
    
    
    // this loads bCryptEncoder via the CustomSecurityConfig class
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Bean
    public static CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Autowired
    CustomUserDetailsService userDetailsService;
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();		
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder);
            return provider;
    }
}
