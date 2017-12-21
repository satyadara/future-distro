package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.UserDao;
import com.blibli.distro_pos.DAO.summary.SummaryDAO;
import com.blibli.distro_pos.Model.Role;
import com.blibli.distro_pos.Model.User;
import com.blibli.distro_pos.Model.summary.LoyalCustomer;
import com.blibli.distro_pos.Model.summary.MostSoldItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {
    private final SummaryDAO summaryDAO;

    @Autowired
    public WebController(SummaryDAO summaryDAO) {
        this.summaryDAO = summaryDAO;
    }

    @RequestMapping(value = {"/"})
    public String home() {

        return "login";
    }

    @RequestMapping(value = {"/dashboard"})
    public ModelAndView dashboard(Authentication authentication) {

        String username = authentication.getName();

        return new ModelAndView("cashier/dashboard", "username", username);
    }

    @RequestMapping(value = "/admin")
    public ModelAndView admin(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("manager/admin");

        String username = authentication.getName();
        List<MostSoldItem> mostSoldItemsMonth = summaryDAO.getMostSoldItemThisMonth();
        List<MostSoldItem> mostSoldItemsYear = summaryDAO.getMostSoldItemThisYear();
        List<LoyalCustomer> loyalCustomersMonth = summaryDAO.getLoyalCustomerThisMonth();
        List<LoyalCustomer> loyalCustomersYear = summaryDAO.getLoyalCustomerThisYear();

        modelAndView.addObject("username", username);
        modelAndView.addObject("items_month", mostSoldItemsMonth);
        modelAndView.addObject("items_year", mostSoldItemsYear);
        modelAndView.addObject("customers_month", loyalCustomersMonth);
        modelAndView.addObject("customers_year", loyalCustomersYear);

        return modelAndView;
    }

    @RequestMapping(value = "/403")
    public String Error403() {
        return "403";
    }


    @GetMapping("/add_user")
    public ModelAndView addUser() {

        return new ModelAndView("manager/add_user");
    }

    @PostMapping("/add_user")
    public ModelAndView addUser(@ModelAttribute("user") User user,
                                @ModelAttribute("role") Role role) {

        user.setEnabled(true);

        System.out.println("Nama Lengkap: " + user.getNamaLengkap() + ", Username : " + user.getUsername() +
                ", Password : " + user.getPassword() + ", Role : " + role.getRole() + ", KTP: " + user.getKtp() +
                ", HP: " + user.getTelp() + ", Jenis Kelamin: " + user.getJenisKelamin());
        int status = UserDao.insertUser(user, role);

        if (status == 1) {
            System.out.println("BERHASIL");
            return new ModelAndView("redirect:/view_user");
        } else {
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

        int status;

        try {


            if (user.getPassword() == "") {
                status = UserDao.editUserWithoutPassword(user, role);
                System.out.println("password:" + user.getPassword());
            } else {
                status = UserDao.editUser(user, role);

            }

            if (status == 1) {
                System.out.println("Berhasil edit user");
                return new ModelAndView("redirect:/view_user");
            } else {
                System.out.println("Gagal edit user" + status);
                return new ModelAndView("redirect:/view_user");
            }
        } catch (Exception e) {

            System.out.println(e.toString());
            throw e;
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
        } else {

            System.out.println("Failed to delete " + username);

            return new ModelAndView("redirect:/view_user");
        }
    }

    //Version 2
    @RequestMapping(value = {"/admin2"})
    public ModelAndView admin2(Authentication authentication) {

        String username = authentication.getName();

        return new ModelAndView("v2/admin2", "username", username);
    }

    @RequestMapping("/view_user2")
    public ModelAndView viewAllUser2() {

        List<User> userList = UserDao.getAllUser();

        return new ModelAndView("v2/view_user2", "userList", userList);
    }

    @GetMapping("/add_user2")
    public ModelAndView addUser2() {

        return new ModelAndView("v2/add_user2");
    }

    @PostMapping("/add_user2")
    public ModelAndView addUser2(@ModelAttribute("user") User user,
                                 @ModelAttribute("role") Role role) {

        user.setEnabled(true);

        System.out.println("Nama Lengkap: " + user.getNamaLengkap() + ", Username : " + user.getUsername() +
                ", Password : " + user.getPassword() + ", Role : " + role.getRole() + ", KTP: " + user.getKtp() +
                ", HP: " + user.getTelp() + ", Jenis Kelamin: " + user.getJenisKelamin());
        int status = UserDao.insertUser(user, role);

        if (status == 1) {
            System.out.println("BERHASIL");
            return new ModelAndView("redirect:/view_user2");
        } else {
            System.out.println("GAGAL!");
            return new ModelAndView("redirect:/view_user2");
        }
    }
}
