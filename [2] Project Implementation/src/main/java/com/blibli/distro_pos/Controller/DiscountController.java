package com.blibli.distro_pos.Controller;

import com.blibli.distro_pos.DAO.discount.DiscountDAO;
import com.blibli.distro_pos.Model.discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
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
        int count;
        try {
            discountList = discountDAO.getAll();
            count = discountDAO.count();
            modelAndView.addObject("discounts", discountList);
            modelAndView.addObject("count", count);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/create", method = GET)
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        Discount discount = new Discount();
        modelAndView.addObject("dicsount", discount);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = POST)
    public ModelAndView store(@ModelAttribute("discount") Discount discount) {
        ModelAndView modelAndView = new ModelAndView("redirect:/discount");
        discount.setId_disc("TEST");
        discount.setId_emp("EMP-1002");
        try {
            discountDAO.save(discount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = GET)
    public ModelAndView edit(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        Discount discount;
        try {
            discount = discountDAO.getOne(id);
            System.out.println(discount.getId_disc());
            System.out.println(discount.getStart_date());
            System.out.println(discount.getEnd_date());
            modelAndView.addObject("discount", discount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/edit", method = POST)
    public ModelAndView update(@ModelAttribute("discount") Discount discount) {
        ModelAndView modelAndView = new ModelAndView("redirect:/discount");
        try {
            discountDAO.update(discount);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/{id}/delete", method = GET)
    public ModelAndView delete(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        try {
            discountDAO.softDelete(id);
        } catch (Exception e) {
            System.out.println("something error : " + e.toString());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/page/{page}", method = GET)
    public ModelAndView paginate(@PathVariable("page") int page) {
        ModelAndView modelAndView = new ModelAndView("discount/form");
        return modelAndView;
    }
}
