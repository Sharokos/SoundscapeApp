package com.sharokos.soundscape.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
@Configuration

public class securityConfiguration {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers("/").hasRole("USER")
                                .requestMatchers("/style-login.css").permitAll()
                                .requestMatchers("/registerUser").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/addNewSoundscape/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(form->
                        form.loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                                .defaultSuccessUrl("/main", true))
                .logout(logout->logout.permitAll());

        return http.build();

    }
}