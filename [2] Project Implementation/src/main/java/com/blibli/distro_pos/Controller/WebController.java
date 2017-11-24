package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.UserDao;
import com.blibli.distro_pos.Model.Role;
import com.blibli.distro_pos.Model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    @RequestMapping(value={"/"})
    public String home(){

        return "login";
    }

    @RequestMapping(value = {"/admin2"})
    public String admin2() {

        return "admin2";
    }

    @RequestMapping(value={"/dashboard"})
    public ModelAndView dashboard(){

        return new ModelAndView("cashier/dashboard");
    }

    @RequestMapping(value="/admin")
    public ModelAndView admin(Authentication authentication){

        String username = authentication.getName();

        return new ModelAndView("manager/admin", "username", username);
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }


    @GetMapping("/add_user")
    public ModelAndView addUser() {

        return new ModelAndView("manager/add_user");
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

        return new ModelAndView("manager/view_user", "userList", userList);
    }

    @RequestMapping("/edit_user/{username}")
    public ModelAndView editUser(@PathVariable("username") String username) {

        User user = UserDao.getUserByUsername(username);
        String role = UserDao.getUserRoleByUsername(user.getUsername());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("manager/edit_user");
        mav.addObject("user", user);
        mav.addObject("role", role);

        return mav;
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
