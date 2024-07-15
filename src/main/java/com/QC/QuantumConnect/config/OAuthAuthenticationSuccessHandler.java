package com.QC.QuantumConnect.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.QC.QuantumConnect.entities.Providers;
import com.QC.QuantumConnect.entities.User;
import com.QC.QuantumConnect.helpers.AppConstants;
import com.QC.QuantumConnect.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
            HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
        logger.info("OAuthenticationSuccessHandler");

        //identify the provider
        var oauth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId =oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key, value)->{
            logger.info(key+" : "+ value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER)); 
        user.setEmailVerified(true);
        user.setEnabled(true);        
        user.setPassword("password");     


        if (authorizedClientRegistrationId.equals("google")) {
            //google
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google");
        }

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);

        if (user2 == null) {
            userRepo.save(user);
        }
        
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
