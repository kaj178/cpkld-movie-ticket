package com.cpkld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingController {
    
    @GetMapping("/choose-seat")
    public String getChooseSeat() {
        return "View/Booking/ChooseSeat/index";
    }

    @GetMapping("/combo")
    public String getComBo() {
        return "View/Booking/ComboFoodDrink/index";
    }

    @GetMapping("/payment")
    public String getPayment() {
        return "View/Booking/Thanhtoan/index";
    }

    @GetMapping("/atm-payment")
    public String getAtmPayment() {
        return "View/Booking/ThanhtoanATM/index";
    }

    @GetMapping("/success-payment")
    public String getSuccessPayment() {
        return "View/Booking/SuccessPayment/index";
    }

}
