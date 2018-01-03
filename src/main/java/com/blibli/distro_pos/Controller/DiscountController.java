package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.Model.discount.Discount;
import com.blibli.distro_pos.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/discount")
public class DiscountController {
    private final DiscountService discountService;

    @Autowired
    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        return discountService.fetch(1);
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        return discountService.create();
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute("discount") Discount discount, Authentication authentication) {
        return discountService.store(discount, authentication);
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        return discountService.edit(id);
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute("discount") Discount discount) {
        return discountService.update(discount);
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable("id") String id, HttpServletRequest request) {
        return discountService.delete(id, request);
    }

    @RequestMapping(value = "/{id}/active", method = GET)
    public ModelAndView active(@PathVariable("id") String id, HttpServletRequest request) {
        return discountService.active(id, request);
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable("page") int page) {
        return discountService.paginate(page);
    }

    @RequestMapping(value = "/search/page/{page}", method = GET)
    public ModelAndView search(@RequestParam("key") String key, @PathVariable("page") int page) {
        return discountService.search(key, page);
    }

}
