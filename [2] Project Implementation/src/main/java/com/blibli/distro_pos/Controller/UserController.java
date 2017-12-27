package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Model.user.Role;
import com.blibli.distro_pos.Model.user.User;
import com.blibli.distro_pos.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user")
    public ModelAndView indexUser() {
        return userService.indexUser();
    }

    @GetMapping("/user/create")
    public ModelAndView createUser() {
        return userService.createUser();
    }

    @PostMapping("/user/create")
    public ModelAndView storeUser(@ModelAttribute("user") User user,
                                  @ModelAttribute("role") Role role) {

        return userService.storeUser(user, role);
    }

    @RequestMapping(value = "/user/{username}/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable("username") String username) {
        return userService.editUser(username);
    }

    @RequestMapping(value = "/user/{username}/edit", method = RequestMethod.POST)
    public ModelAndView updateuUser(@ModelAttribute("user") User user,
                                    @ModelAttribute("role") Role role) {
        return userService.updateUser(user, role);
    }

    @GetMapping(value = "user/{username}/delete")
    public ModelAndView deleteUser(@PathVariable("username") String username) {
        return userService.deleteUser(username);
    }
}
