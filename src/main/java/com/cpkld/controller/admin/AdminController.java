package com.cpkld.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/user-control")
    public String getUserControl() {
        return "View/AdminPage/UserControl/index";
    }
    
}
