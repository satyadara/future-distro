package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.UserDao;
import com.blibli.distro_pos.Model.Role;
import com.blibli.distro_pos.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    @RequestMapping(value={"/"})
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

        user.setEnabled(true);

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

        List<User> userList = UserDao.getAllUser();

        return new ModelAndView("view_user", "userList", userList);
    }

    @RequestMapping("/edit_user/{username}")
    public ModelAndView editUser(@PathVariable("username") String username) {

        User user = UserDao.getUserByUsername(username);

        return new ModelAndView("edit_user", "user", user);
    }

    @RequestMapping(value = "/update")
    public ModelAndView saveUpdate(@ModelAttribute("user") User user,
                                   @ModelAttribute("role") Role role) {

        System.out.println("Nama Lengkap: " + user.getNamaLengkap() + ", Username : " + user.getUsername() +
                ", Password : " + user.getPassword() + ", Role : " + role.getRole() + ", KTP: " + user.getKtp() +
                ", HP: " + user.getTelp() + ", Jenis Kelamin: " + user.getJenisKelamin());

        int status = UserDao.editUser(user, role);

        if (status == 1) {
            System.out.println("Berhasil edit user");
            return new ModelAndView("redirect:/view_user");
        }
        else {
            System.out.println("Gagal edit user");
            return new ModelAndView("redirect:/view_user");
        }

    }

    @GetMapping(value = "delete_user/{username}")
//    public ModelAndView deleteUser(@ModelAttribute("user") User user) {

    public ModelAndView deleteUser(@PathVariable("username") String username) {

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
