
package com.storeii.nciproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



/**
 *
 * @author Darren Robert Lowe
 */



@ComponentScan({"com.delivery.request"})
@EnableWebSecurity
public class ApplicationSecurityConfig {
    
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/login.html");
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/cart").hasAuthority("CUSTOMER")
            .antMatchers("/cart/*").hasAuthority("CUSTOMER")
            //.antMatchers("/fulfilments").hasAuthority("SUPPLIER")
            //.antMatchers("/deliveries").hasAuthority("DRIVER")
            .antMatchers("/addUser","/js/*","/css/*","/assets/**","/*").permitAll()
            //.antMatchers("/webstoredb/*").hasAuthority("ADMIN")
            //.anyRequest().denyAll()
            .antMatchers("/**").permitAll()
            .and()
        .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/index", true)
            .failureUrl("/login.html?error=true")
            .and()
        .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/loggedout.html")
            .permitAll();
            
        http.csrf().disable(); // This is temporary and should be enabled for security reasons
        
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
