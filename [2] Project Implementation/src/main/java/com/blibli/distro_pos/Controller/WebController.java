package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.UserDao;
import com.blibli.distro_pos.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    @RequestMapping(value = {"/", "home"})
    public String home() {
        return "home";
    }

    @RequestMapping(value = {"/welcome"})
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping(value = {"/login"})
    public String login() {
        return "login";
    }


    @RequestMapping(value = "/403")
    public String Error403() {
        return "403";
    }


    @GetMapping("/add_user")
    public ModelAndView addUser() {

        return new ModelAndView("add_user");
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(@ModelAttribute("user") User user) {

        int status = UserDao.insertUser(user);

        if (status == 1) {

            return new ModelAndView("redirect:/view_user");
        } else {

            return new ModelAndView("redirect:/view_user");
        }
    }

    @RequestMapping("/view_user")
    public ModelAndView viewAllUser() {

        List<User> userList = UserDao.getAllUser();

        return new ModelAndView("view_user", "userList", userList);
    }

    @GetMapping(value = "delete_user/{username}")
    public ModelAndView deleteUser(@ModelAttribute("user") User user) {

        int status = UserDao.deleteUser(user);

        if (status == 1) {

            System.out.println("User with username: " + user.getUsername() + " is deleted");

            return new ModelAndView("redirect:/view_user");
        }
        else {

            System.out.println("Gagal delete user " + user.getUsername());

            return new ModelAndView("redirect:/view_user");
        }
    }
}
