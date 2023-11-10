package com.cpkld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/combo-control")
    public String getComboControl() {
        return "View/AdminPage/ComboControl/index";
    }

    @GetMapping("/edit-user")
    public String getEditUser() {
        return "View/AdminPage/EditUserModal/index";
    }

    @GetMapping("/film-control")
    public String getFilmControl() {
        return "View/AdminPage/FilmControl/index";
    }

    @GetMapping("/history-page")
    public String getHistoryPage() {
        return "View/AdminPage/HistoryPage/index";
    }

    @GetMapping("/statistic")
    public String getStatistic() {
        return "View/AdminPage/Statistic/index";
    }

    @GetMapping("/time-control")
    public String getTimeControlPage() {
        return "View/AdminPage/TimeControlPage/index";
    }

    @GetMapping("/user-control")
    public String getUserControl() {
        return "View/AdminPage/UserControl/index";
    }
    
}
