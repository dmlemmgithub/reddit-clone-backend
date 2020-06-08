package com.programming.techie.springredditclone.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//This is the main annotation which enables the Web Security module in our Project.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//Here, we are configuring Spring to allow all the requests which match the endpoint “/api/auth/**” , 
	//as these endpoints are used for authentication and registration we don’t expect the user to be authenticated at that point of time.
	@Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

	//Now before storing the user in the database, we ideally want to encode the passwords. 
	//One of the best hashing algorithms for passwords is the Bcrypt Algorithm.
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
