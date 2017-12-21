package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/ledger")
public class LedgerController {
    private final UserService userService;

    @Autowired
    public LedgerController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("")
    public ModelAndView index() {
        return userService.managerLedger();
    }

    @RequestMapping("/filter/page/{page}")
    public ModelAndView between(@PathVariable("page") int page, @RequestParam("date_from") String date_from,
                                @RequestParam("date_to") String date_to) {
        return userService.managerLedgerBetween(page, date_from, date_to);
    }
}
