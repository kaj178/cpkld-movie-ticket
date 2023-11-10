package com.cpkld.controller.auth;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cpkld.dto.UserDTO;
import com.cpkld.model.entity.User;
import com.cpkld.service.auth.AuthService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("")
public class AuthController {

    @Autowired
    private AuthService service;

    @GetMapping
    public String getHomePage(Principal principal, HttpSession session) {
        return "homepage";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "View/Login_Modal/LoginModal";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "View/Signup_Modal/index";
    }

    @PostMapping("/signup/auth")
    public String register(
        @ModelAttribute("user") UserDTO userDTO,
        BindingResult result,
        Model model
    ) {
        System.out.println(userDTO.toString());
        User existedUser = service.findUserByEmail(userDTO.getEmail());
        // System.out.println(existedUser.toString());
        if (existedUser != null && existedUser.getEmail() != null && !existedUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Email đã tồn tại!");
        }
        if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
            result.rejectValue("repeatPassword", null, "Mật khẩu không khớp");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "register";
        }
        service.saveAccount(userDTO);
        return "redirect:/login";
    }
    
    
}
