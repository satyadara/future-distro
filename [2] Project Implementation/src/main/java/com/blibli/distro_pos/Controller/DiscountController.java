package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.discount.DiscountDAO;
import com.blibli.distro_pos.Model.discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/discount")
public class DiscountController {
    private DiscountDAO discountDAO;

    @Autowired
    public DiscountController(DiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }

    @RequestMapping(value = "", method = GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("discount/index");
        List<Discount> discountList;
        try {
            discountList = discountDAO.getAll();
            modelAndView.addObject("discounts", discountList);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        try {

        } catch (Exception e) {
            
        }
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute("discount") Discount discount) {
        ModelAndView modelAndView = new ModelAndView("redirect:/discount");

        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable("id_disc") String id_disc) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute("discount") Discount discount) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable("id_disc") String id_disc) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        return modelAndView;
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        return modelAndView;
    }
}
