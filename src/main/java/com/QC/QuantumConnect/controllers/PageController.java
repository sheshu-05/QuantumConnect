package com.QC.QuantumConnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.QC.QuantumConnect.entities.User;
import com.QC.QuantumConnect.forms.UserForm;
import com.QC.QuantumConnect.helpers.Message;
import com.QC.QuantumConnect.helpers.MessageType;
import com.QC.QuantumConnect.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class PageController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    

    @RequestMapping("/home")
    private String home(){
        return "home";
    }

    @RequestMapping("/about")
    private String aboutPage(){
        return "about";
    }

    @RequestMapping("/services")
    private String servicesPage(){
        return "services";
    }

    @RequestMapping("/contact")
    private String contactPage(){
        return "contact";
    }

    @RequestMapping("/login")
    private String loginPage(){
        return "login";
    }

    @RequestMapping("/register")
    private String registerPage(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult, HttpSession session){
        System.out.println("Processing registration");
        
        //fetch form data
        System.out.println(userForm);
        
        //validate form data
        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber()); 
        user.setEnabled(false);
        user.setProfilePic("https://static.vecteezy.com/system/resources/thumbnails/009/734/564/small_2x/default-avatar-profile-icon-of-social-media-user-vector.jpg");
        User savedUser = userService.saveUser(user);

        System.out.println("user saved : ");
        //message = "Registration successful"
        Message message = Message.builder().content("Registration successful").type(MessageType.blue).build();
        session.setAttribute("message", message);
        //redirect to login page
        return "redirect:/register";
    }
}
