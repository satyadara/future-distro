package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.UserDAO;
import com.blibli.distro_pos.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    @RequestMapping(value={"/","home"})
    public String home(){
        return "home";
    }

    @RequestMapping(value={"/dashboard"})
    public String welcome(){
        return "dashboard";
    }

    @RequestMapping(value="/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping(value={"/login"})
    public String login(){
        return "login";
    }


    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }


    @GetMapping("/add_user")
    public ModelAndView addUser() {

        return new ModelAndView("add_user");
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(@ModelAttribute("user") User user,
                                @ModelAttribute("role")Role role) {

        int status = UserDAO.insertUser(user);

        System.out.println("Nama Lengkap: " + user.getNamaLengkap() + ", Username : " + user.getUsername() +
                ", Password : " + user.getPassword() + ", Role : " + role.getRole() + ", KTP: " + user.getKtp() +
                ", HP: " + user.getTelp() + ", Jenis Kelamin: " + user.getJenisKelamin());
        int status = UserDao.insertUser(user, role);

        if (status == 1) {
            System.out.println("BERHASIL");
            return new ModelAndView("redirect:/view_user");
        }
        else {
            System.out.println("GAGAL!");
            return new ModelAndView("redirect:/view_user");
        }
    }


    @RequestMapping("/view_user")
    public ModelAndView viewAllUser() {

        List<User> userList = UserDAO.getAllUser();

        return new ModelAndView("view_user", "userList", userList);
    }

    @GetMapping(value = "delete_user/{username}")
//    public ModelAndView deleteUser(@ModelAttribute("user") User user) {

        int status = UserDAO.deleteUser(user);

        int status = UserDao.deleteUser(username);
//
        if (status == 1) {

            System.out.println("User with username: " + username + " is deleted");

            return new ModelAndView("redirect:/view_user");
        }
        else {

            System.out.println("Failed to delete " + username);

            return new ModelAndView("redirect:/view_user");
        }
    }
}
