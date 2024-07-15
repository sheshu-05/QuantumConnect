package com.QC.QuantumConnect.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication){
        if (authentication instanceof OAuth2AuthenticationToken) {
            var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username = "";
            // sign in with google
            if (clientId.equalsIgnoreCase("google")) {
                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();
            }
            // for other social use else
            return username;
            
        }
        else{
            // if logged in with email and password
            System.out.println("Getting data from local db");
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken){
        String link = "http://localhost:8081/auth/verify-email?token="+emailToken;
        return link;
    }
}
