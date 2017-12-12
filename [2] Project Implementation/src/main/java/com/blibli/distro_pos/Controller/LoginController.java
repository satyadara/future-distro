package com.blibli.distro_pos.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/success")
    public String success(Authentication authentication) {

        if (authentication.getAuthorities().toString().equals("[MANAGER]")) {

            return "redirect:/admin";
        }
        else {

            return "redirect:/dashboard";
        }
    }
}
