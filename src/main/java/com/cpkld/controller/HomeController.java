package com.cpkld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHomePage() {
        return "homepage";
    }

    @GetMapping("/order")
    public String getOrderPage() {
        return "View/Order/index";
    }
    
}
