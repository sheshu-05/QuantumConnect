package com.QC.QuantumConnect.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.QC.QuantumConnect.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    //user creating and login using java code in memory service

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private SecurityCustomUserDetailService UserDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler oauthAuthenticationSuccessHandler;
    
    //configuration of authentication provider with user detail service and password encoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        
        daoAuthenticationProvider.setUserDetailsService(UserDetailService);
        
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //configuration
        //configured urls to be protected and public
        httpSecurity.authorizeHttpRequests(authorize->{
            // authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        
        //form default login page
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            //form submitted to /authenticate
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
            // formLogin.failureForwardUrl("/signin?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.failureHandler(authFailureHandler);
        });

        //disable csrf protection for this application
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutForm-> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oauthAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
