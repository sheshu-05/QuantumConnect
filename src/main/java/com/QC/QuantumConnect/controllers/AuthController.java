package com.QC.QuantumConnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.QC.QuantumConnect.entities.User;
import com.QC.QuantumConnect.helpers.Message;
import com.QC.QuantumConnect.helpers.MessageType;
import com.QC.QuantumConnect.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;
    
    // verify email

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {
        User user = userRepo.findByEmailToken(token).orElse(null);
        
        if (user!=null) {
            if ((user.getEmailToken().equals(token))) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder().type(MessageType.green).content("Your Email is verified, you can login").build());
                return "success_page";
            }
            return "error_page";
        }
        session.setAttribute("message", Message.builder().type(MessageType.red).content("Email not verified").build());
        
        return "error_page";
    }
    
}
