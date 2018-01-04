package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WebController {
    private final UserService userService;

    @Autowired
    public WebController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/"})
    public String home() {

        return "login";
    }

    @RequestMapping(value = {"/login"})
    public String login() {

        return "login";
    }

    @RequestMapping(value = {"/cashier"})
    public ModelAndView cashier(Authentication authentication) {
        return userService.cashierDashboard(authentication);
    }

    @RequestMapping(value = "/admin")
    public ModelAndView admin(Authentication authentication) {
        return userService.managerDashboard(authentication);
    }

    @RequestMapping(value = "/admin/chart")
    @ResponseBody
    public String chartData()   {
        return userService.chartJson();
    }

    @RequestMapping(value = "/403")
    public String Error403() {
        return "403";
    }

}
