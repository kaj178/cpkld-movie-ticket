package com.cpkld.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cpkld.dto.UserDTO;
import com.cpkld.model.entity.User;
import com.cpkld.service.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("")
public class AuthController {

    @Autowired
    @Qualifier("authService")
    private AuthService service;

    @GetMapping("/login")
    public String getLoginPage() {
        return "View/Login_Modal/LoginModal";
    }

    @GetMapping("/login-error")
    public String getLoginErrorPage(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("loginError", "Tên đăng nhập hoặc mật khẩu không đúng");
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
        if (userDTO.getEmail().equals("") || 
            userDTO.getFullname().equals("") || 
            userDTO.getAddress().equals("") || 
            userDTO.getPhone().equals("") || 
            userDTO.getPassword().equals("") ||
            userDTO.getRepeatPassword().equals("")) {
            result.rejectValue("registerError", "Vui lòng nhập đầy đủ thông tin");
        }
        System.out.println(userDTO.toString());
        User existedUser = service.findUserByEmail(userDTO.getEmail());
        if (existedUser != null && existedUser.getEmail() != null && !existedUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Email đã tồn tại!");
        }
        if (!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
            result.rejectValue("repeatPassword", null, "Mật khẩu không khớp");
        }
        if (result.hasErrors()) {
            model.addAttribute("registerError", result.getAllErrors());
            return "View/Signup_Modal/index";
        }
        service.saveCustomerAccount(userDTO);
        return "redirect:/login";
    }
    
}
